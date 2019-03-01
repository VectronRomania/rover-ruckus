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

    /**
     * LiftEncoderCheckableGroup for checking encoder values in the lift
     */
//    public class LiftEncoderCheckableGroup extends CheckableGroup {
//        public LiftEncoderCheckableGroup(Integer ticksLeft, Integer ticksRight, Integer bias) {
//            items.add(
//                    new Pair<Checkable, Operation>(
//                        new MotorEncoderCheckable(Robot.Lift.left_lift, ticksLeft, bias),
//                        Operation.AND
//                    )
//            );
//            items.add(
//                    new Pair<Checkable, Operation>(
//                            new MotorEncoderCheckable(Robot.Lift.right_lift, ticksRight, bias),
//                            Operation.AND
//                    )
//            );
//        }
//    }

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
                super.telemetryItem.set(0);
                result = 1;

                Robot.Lift.setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                Robot.Lift.setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            @Override
            public void run() {

                Checkable liftEncoderCheckable;
                super.telemetryItem.set(0);
                liftEncoderCheckable = lift.move(Lift.Direction.DOWN, Robot.ENCODER_TICKS_60_1 * 11 / 2, 0.75);
                while (!liftEncoderCheckable.check() && !super.isStopRequested) {}
                lift.stop();

//                Elevate the lift a tiny bit
                super.telemetryItem.set(3);
                // FIXME: 27/02/2019 test necessary ticks
                liftEncoderCheckable = lift.move(Lift.Direction.UP, Robot.ENCODER_TICKS_60_1  / 3, 0.75);
                while (!liftEncoderCheckable.check() && !super.isStopRequested) {}
                lift.stop();

//                Move the robot to unlatch
                result = 4;
                super.telemetryItem.set(result);
                Checkable drivetrainEncoderCheckableGroup = autonomousDrivetrain.move(Controller.Direction.W, Robot.ENCODER_TICKS_40_1 / 4,0.3);
                while (!drivetrainEncoderCheckableGroup.check() && !super.isStopRequested) {}
                autonomousDrivetrain.stop();

//                Retract the lift
                liftEncoderCheckable = lift.move(Lift.Direction.DOWN, Robot.ENCODER_TICKS_60_1 * 3, 0.4);
                while (!liftEncoderCheckable.check() && !super.isStopRequested ) {}
                lift.stop();
            }
        }, "Lift deploy", BackgroundTask.Type.ONE_TIME, opMode);
    }
}
