package org.firstinspires.ftc.teamcode.systems.autonomous;

import android.util.Pair;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

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

//    distance: 135 mm +- 5mm
//    yaw 77 pitch 1

    /**
     * Checkable that checks if the robot is in a certain pitch radius.
     */
    public class RevYawCheckable implements Checkable {

        final double yaw;
        final double bias;
        final REVImu left;
        final REVImu right;

        public RevYawCheckable(double yaw, double bias, REVImu left, REVImu right) {
            this.yaw = yaw;
            this.bias = bias;
            this.left = left;
            this.right = right;
        }

        public double getMedianAngle() {
            return (left.getPitch() + right.getPitch()) / 2;
        }

        @Override
        public Boolean check() {
                return yaw < getMedianAngle() ?
                        yaw > getMedianAngle() - bias :
                        yaw < getMedianAngle() + bias;
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
            final double distanceLeft, distanceRight;
            distanceLeft = Robot.Lift.distance_left.getDistance(DistanceUnit.MM);
            distanceRight = Robot.Lift.distance_right.getDistance(DistanceUnit.MM);
            return (distanceLeft + distanceRight) / 2 < minHeightMM;
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
    private final LinearOpMode opMode;

    public LiftDeploy(final Lift lift,
                      final AutonomousDrivetrain autonomousDrivetrain,
                      final LinearOpMode opMode) {
        this.lift = lift;
        this.autonomousDrivetrain = autonomousDrivetrain;
        this.opMode = opMode;
    }

    /**
     * Return a task that executes the entire deployment process.
     * @return
     */
    public BackgroundTask<Integer> getDeployTask() {
        return new BackgroundTask<>(new BackgroundTaskRunnable<Integer>() {
            @Override
            protected void initialize() {
                result = -1;
                super.telemetryItem.set(result);
            }

            @Override
            protected void shutdown() {
                lift.stop();
                if (isStopRequested) {
                    return;
                }
                result = 0;
                super.telemetryItem.set(result);
                super.finished = true;
            }

            @Override
            public void run() {
//                Drop down reading distance
                result = 1;
                super.telemetryItem.set(result);
                LiftHeightCheckable heightCheckable = new LiftHeightCheckable(138);
                if (!heightCheckable.check()) {
                    lift.move(Lift.Direction.UP, 0.75);
                }
                while (!heightCheckable.check() && !super.isStopRequested) {}
                lift.stop();
                if (isStopRequested) {
                    return;
                }

//                Normalize robot pitch
                result = 2;
                super.telemetryItem.set(result);
                RevYawCheckable revYawCheckable = new RevYawCheckable(-78, 3, Robot.Sensors.left_imu, Robot.Sensors.right_imu);
//                if (Robot.Sensors.left_imu.sensor)
                if (!revYawCheckable.check()) {
                    lift.move(Lift.Direction.UP, 0.3);
                    autonomousDrivetrain.move(Controller.Direction.N, Robot.ENCODER_TICKS_40_1, 0.1);
                }
                while (!revYawCheckable.check() && !super.isStopRequested) {}
                autonomousDrivetrain.stop();
                lift.stop();
                if (isStopRequested) {
                    return;
                }

//                Elevate the lift a tiny bit
                Robot.Lift.setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                Robot.Lift.setRunMode(DcMotor.RunMode.RUN_TO_POSITION);

                result = 3;
                super.telemetryItem.set(result);
                Checkable liftEncoderCheckable = lift.move(Lift.Direction.UP, Robot.ENCODER_TICKS_60_1 * 4 / 3 , 0.5);
                while (!liftEncoderCheckable.check() && !super.isStopRequested) {}
                lift.stop();

//                Move the robot to unlatch
                result = 4;
                super.telemetryItem.set(result);
                Checkable drivetrainEncoderCheckableGroup = autonomousDrivetrain.move(Controller.Direction.W, Robot.ENCODER_TICKS_40_1 * 3 / 4 ,0.3);
                while (!drivetrainEncoderCheckableGroup.check() && !super.isStopRequested) {}
                autonomousDrivetrain.stop();

                result = 4;
                super.telemetryItem.set(result);
                this.finished = true;
                return;

//                Retract the lift
//                liftEncoderCheckable = lift.move(Lift.Direction.DOWN, Robot.ENCODER_TICKS_60_1 * 2, 0.4);
//                while (!liftEncoderCheckable.check() && !super.isStopRequested ) {}
//                lift.stop();
            }
        }, "Lift deploy", BackgroundTask.Type.ONE_TIME, opMode);
    }
}
