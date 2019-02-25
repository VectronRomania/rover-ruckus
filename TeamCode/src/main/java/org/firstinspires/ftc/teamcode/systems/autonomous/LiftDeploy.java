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

/**
 * Container for the Autonomous deployment system.
 */
public class LiftDeploy {

    /**
     * Checkable that checks if the robot is in a certain pitch radius.
     */
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
     * Checkable for checking the height reported by the distance sensors.
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
    public class LiftEncoderCheckableGroup extends CheckableGroup {
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

    private final Lift lift;
    private final AutonomousDrivetrain autonomousDrivetrain;

    public LiftDeploy(final Lift lift,
                      final AutonomousDrivetrain autonomousDrivetrain) {
        this.lift = lift;
        this.autonomousDrivetrain = autonomousDrivetrain;
    }

    /**
     * Return a task that executes the entire deployment process.
     * @return
     */
    public BackgroundTask getDeployTask() {
        return new BackgroundTask<>(new BackgroundTaskRunnable<Integer>() {
            @Override
            protected void initialize() {
                super.telemetryItem.set(-1);
                lift.stop();
                result = 0;
            }

            @Override
            protected void shutdown() {
                lift.stop();
                if (isStopRequested) {
                    return;
                }
                super.telemetryItem.set(0);
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
                RevPitchCheckable revPitchCheckable = new RevPitchCheckable(90, 5, Robot.Sensors.left_imu, Robot.Sensors.right_imu);
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
