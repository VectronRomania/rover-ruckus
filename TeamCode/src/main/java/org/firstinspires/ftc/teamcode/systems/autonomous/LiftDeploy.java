package org.firstinspires.ftc.teamcode.systems.autonomous;

import android.util.Pair;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.REVImu;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.Lift;
import org.firstinspires.ftc.teamcode.systems.drivetrain.AutonomousDrivetrain;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.Controller;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTask;
import org.firstinspires.ftc.teamcode.systems.util.CheckableGroup;
import org.firstinspires.ftc.teamcode.systems.util.checkables.MotorEncoderCheckable;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTaskRunnable;
import org.firstinspires.ftc.teamcode.systems.util.Checkable;

public class LiftDeploy {

    public class RevPitchCheckable implements Checkable {

        final double pitch;
        final double bias;
        final REVImu left;
        final REVImu right;

        public RevPitchCheckable(double pitch, double bias, REVImu left, REVImu right) {
            this.pitch = pitch;
            this.bias = bias;
            this.left = left;
            this.right = right;
        }

        public double getMedianAngle() {
            return (left.getPitch() + right.getPitch()) / 2;
        }

        @Override
        public Boolean check() {
            return pitch < getMedianAngle() ?
                    pitch > getMedianAngle() - bias :
                    pitch < getMedianAngle() + bias;

        }
    }

    /**
     * Checkable for checking the height reported by the distance sensors
     */
    public class LiftHeightCheckable implements Checkable {

        private final Integer minHeightMM;

        public LiftHeightCheckable(Integer minHeightMM) {
            this.minHeightMM = minHeightMM;
        }

        @Override
        public Boolean check() {
            return (Robot.Lift.distance_left.getDistance(DistanceUnit.MM) +
                    Robot.Lift.distance_right.getDistance(DistanceUnit.MM))
                    / 2
                    < minHeightMM;
        }
    }

    /**
     * DrivetrainCheckableGroup for checking encoder values in the lift
     */
    public class LiftEncoderCheckableGroup extends org.firstinspires.ftc.teamcode.systems.util.CheckableGroup {
        public LiftEncoderCheckableGroup(Integer ticksLeft, Integer ticksRight, Integer bias) {
            items.add(
                    new Pair<Checkable, Operation>(
                        new MotorEncoderCheckable(Robot.Lift.left_lift, ticksLeft, bias),
                        Operation.AND
                    )
            );
            items.add(
                    new Pair<Checkable, Operation>(
                            new MotorEncoderCheckable(Robot.Lift.right_lift, ticksRight, bias),
                            Operation.AND
                    )
            );
        }
    }

//    /**
//     * BackgroundTaskRunnable for pulling down the lift after deployment
//     */
//    public class LiftDropDownRunnable extends BackgroundTaskRunnable<Boolean> {
//
//        private final Lift lift;
//
//        public LiftDropDownRunnable(final Lift lift) {
//            this.lift = lift;
//        }
//
//        @Override
//        protected void initialize() {
//            Robot.Lift.setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
//            this.telemetryItem = new TelemetryItem<Boolean>("Lift pulling down status") {
//                @Override
//                public void update() {
//                    set(result);
//                }
//            };
//        }
//
//        @Override
//        protected void shutdown() {
//            lift.stop();
//        }
//
//        @Override
//        public void run() {
//            lift.move(Lift.Direction.DOWN, 100, 1.0);
//        }
//    }
//
//
//    /**
//     * Stage one of deployment moves the robot down based on the distance sensors' readings.
//     */
//    public void stageOne() {
//        Robot.Lift.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        lift.move(Lift.Direction.DOWN, 0.75);
//    }
//
//    /**
//     * Stage two of deployment completes the touchdown process using predefined encoder ticks.
//     */
//    public void stageTwo() {
//        Robot.Lift.setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
//        lift.move(Lift.Direction.DOWN, Robot.ENCODER_TICKS_40_1, 0.75);
//    }
//
//    /**
//     * Stage three deployment unlatches the robot from the lander.
//     */
//    public void stageThree() {
//        Robot.Lift.setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
//        lift.move(Lift.Direction.UP, Robot.ENCODER_TICKS_40_1 * 3/2, 0.75);
//        // TODO: 31/01/2019 write unlatching
//    }

    private final Lift lift;
    private final REVImu imu_left;
    private final REVImu imu_right;
    private final AutonomousDrivetrain autonomousDrivetrain;

    public LiftDeploy(final Lift lift, final REVImu imu_left, final REVImu imu_right, final AutonomousDrivetrain autonomousDrivetrain) {
        this.lift = lift;
        this.imu_left = imu_left;
        this.imu_right = imu_right;
        this.autonomousDrivetrain = autonomousDrivetrain;
    }

    public BackgroundTask dropDown() {
        return new BackgroundTask<>(new BackgroundTaskRunnable<Integer>() {
            @Override
            protected void initialize() {
                super.telemetryItem.set(-1);
                lift.stop();
                result = 0;
            }

            @Override
            protected void shutdown() {
                super.telemetryItem.set(0);
                lift.stop();
                result = 1;
            }

            @Override
            public void run() {
//                Drop down reading distance
                super.telemetryItem.set(1);
                LiftHeightCheckable heightCheckable = new LiftHeightCheckable(92);
                while (!heightCheckable.check() && !super.isStopRequested) {
                    try {
                        this.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
                if (isStopRequested) {
                    return;
                }

//                Normalize robot pitch
                super.telemetryItem.set(2);
                RevPitchCheckable revPitchCheckable = new RevPitchCheckable(90, 5, imu_left, imu_right);
                while (!revPitchCheckable.check() && !super.isStopRequested) {
                    lift.move(Lift.Direction.DOWN, 0.1);
                    try {
                        this.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
                lift.stop();
                if (isStopRequested) {
                    return;
                }

//                Move the robot to unlatch
                super.telemetryItem.set(3);
                CheckableGroup drivetrainEncoderCheckableGroup = autonomousDrivetrain.move(
                        Controller.Direction.W,
                        1000,
                        0.25);
                lift.move(Lift.Direction.DOWN, 100, 0.75);
                while (!drivetrainEncoderCheckableGroup.check() && !super.isStopRequested) {
                    try {
                        this.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
                if (isStopRequested) {
                    return;
                }
                autonomousDrivetrain.stop();
                lift.stop();

//                drivetrainEncoderCheckableGroup = autonomousDrivetrain.move(Controller.Direction.N, 500, 0.25);
//                while (!drivetrainEncoderCheckableGroup.check() && !super.isStopRequested) {
//                    try {
//                        this.sleep(10);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                        return;
//                    }
//                }
//                if (isStopRequested) {
//                    return;
//                }
//                autonomousDrivetrain.stop();
//
//                drivetrainEncoderCheckableGroup = autonomousDrivetrain.move(Controller.Direction.E, 1000, 0.25);
//                while (!drivetrainEncoderCheckableGroup.check() && !super.isStopRequested) {
//                    try {
//                        this.sleep(10);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                        return;
//                    }
//                }
//                if (isStopRequested) {
//                    return;
//                }
//                autonomousDrivetrain.stop();
            }
        }, "Lift deploy", BackgroundTask.Type.ONE_TIME);
    }
}
