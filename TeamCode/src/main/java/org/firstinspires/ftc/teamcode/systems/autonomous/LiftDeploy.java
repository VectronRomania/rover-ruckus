package org.firstinspires.ftc.teamcode.systems.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.Lift;
import org.firstinspires.ftc.teamcode.systems.drivetrain.AutonomousDrivetrain;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.Controller;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryManager;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTask;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTaskRunnable;
import org.firstinspires.ftc.teamcode.systems.util.Checkable;
import org.firstinspires.ftc.teamcode.systems.util.checkables.DistanceSensorCheckable;
import org.firstinspires.ftc.teamcode.systems.util.checkables.ImuAxisCheckable;

/**
 * Container for the Autonomous robot deployment system.
 */
public class LiftDeploy implements Runnable {

    private final Lift                  lift;

    private final AutonomousDrivetrain  autonomousDrivetrain;

    private final LinearOpMode          parentOpMode;

    private final TelemetryManager      telemetryManager;

    private final MineralDetector       mineralDetector;
    /**
     *
     * @param lift the lift hardware
     * @param autonomousDrivetrain the drivetrain used for unlatching
     * @param parentOpMode the opmode used for checking if the system should stop
     * @param telemetryManager used for showing telemetry
     * @param mineralDetector used for switching to deployed once the robot is deployed
     */
    public LiftDeploy(final Lift lift,
                      final AutonomousDrivetrain autonomousDrivetrain,
                      final LinearOpMode parentOpMode,
                      final TelemetryManager telemetryManager,
                      final MineralDetector mineralDetector) {
        this.lift = lift;
        this.autonomousDrivetrain = autonomousDrivetrain;
        this.parentOpMode = parentOpMode;
        this.telemetryManager = telemetryManager;
        this.mineralDetector = mineralDetector;
    }

    /**
     * Run the lift deployment
     * @return
     */
    public void run() {
        final TelemetryItem<String> telemetryItem = new TelemetryItem<String>("Deployment") {
            @Override
            public void update() {}
        };
        this.telemetryManager.add(telemetryItem);

//        initializing
        telemetryItem.set("initializing");
        this.autonomousDrivetrain.stop();

//        dropping down using distance sensors
        telemetryItem.set("distance sensors phase");
        Checkable distanceCheckable = DistanceSensorCheckable.getGroup(
                Robot.Lift.distance_left,
                Robot.Lift.distance_right,
                DistanceUnit.MM,
                135,
                3
        );
        if (!distanceCheckable.check()) {
            this.lift.move(Lift.Direction.UP, 0.75);
        }
        while (!distanceCheckable.check() && this.parentOpMode.opModeIsActive()) {
            this.telemetryManager.cycle();
            this.parentOpMode.idle();
        }
        this.lift.stop();
        if (!this.parentOpMode.opModeIsActive()) {
            return;
        }

//        dropping down using the rev imu
        telemetryItem.set("rev imu phase");
        Checkable angleCheckable = ImuAxisCheckable.getGroup(
                Robot.Sensors.left_imu,
                Robot.Sensors.right_imu,
                ImuAxisCheckable.Axis.X,
                -78,
                2
        );
        if (!angleCheckable.check()) {
            this.lift.move(Lift.Direction.UP, 0.5);
        }
        while (!angleCheckable.check() && this.parentOpMode.opModeIsActive()) {
            this.telemetryManager.cycle();
            this.parentOpMode.idle();
        }
        this.lift.stop();
        if (!this.parentOpMode.opModeIsActive()) {
            return;
        }

//        unlatch
        telemetryItem.set("unlatching");

//        retracting the lift
        telemetryItem.set("retracting the lift");

//        terminating
        telemetryItem.set("terminating");

//        finish
        telemetryItem.set("done");
//        return new BackgroundTask<>(new BackgroundTaskRunnable<Integer>() {
//            @Override
//            protected void initialize() {
//                result = -1;
//                super.telemetryItem.set(result);
//            }
//
//            @Override
//            protected void shutdown() {
//                lift.stop();
//                if (isStopRequested) {
//                    return;
//                }
//                super.telemetryItem.set(0);
//                result = 1;
//
//                Robot.Lift.setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//                Robot.Lift.setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
//            }
//
//            @Override
//            public void run() {
//
//                Checkable liftEncoderCheckable;
//                super.telemetryItem.set(0);
//                liftEncoderCheckable = lift.move(Lift.Direction.DOWN, Robot.ENCODER_TICKS_60_1 * 11 / 2, 0.75);
//                while (!liftEncoderCheckable.check() && !super.isStopRequested) {}
//                lift.stop();
//
////                Elevate the lift a tiny bit
//                super.telemetryItem.set(3);
//                // FIXME: 27/02/2019 test necessary ticks
//                liftEncoderCheckable = lift.move(Lift.Direction.UP, Robot.ENCODER_TICKS_60_1  / 3, 0.75);
//                while (!liftEncoderCheckable.check() && !super.isStopRequested) {}
//                lift.stop();
//
////                Move the robot to unlatch
//                result = 4;
//                super.telemetryItem.set(result);
//                Checkable drivetrainEncoderCheckableGroup = autonomousDrivetrain.move(Controller.Direction.W, Robot.ENCODER_TICKS_40_1 * 3 / 4 ,0.3);
//                while (!drivetrainEncoderCheckableGroup.check() && !super.isStopRequested) {}
//                if (isStopRequested) {
//                    finished = true;
//                    return;
//                }
//                autonomousDrivetrain.stop();
//
//                result = 4;
//                super.telemetryItem.set(result);
//                this.finished = true;
//                return;
//
////                Retract the lift
////                liftEncoderCheckable = lift.move(Lift.Direction.DOWN, Robot.ENCODER_TICKS_60_1 * 2, 0.4);
////                while (!liftEncoderCheckable.check() && !super.isStopRequested ) {}
////                lift.stop();
//            }
//        }, "Lift deploy", BackgroundTask.Type.ONE_TIME, parentOpMode);
    }
}
