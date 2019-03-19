package org.firstinspires.ftc.teamcode.systems.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.Lift;
import org.firstinspires.ftc.teamcode.systems.drivetrain.AutonomousDrivetrain;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.Controller;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryManager;
import org.firstinspires.ftc.teamcode.systems.util.Checkable;
import org.firstinspires.ftc.teamcode.systems.util.checkables.DistanceSensorCheckable;

/**
 * Container for the Autonomous robot deployment system.
 */
public class LiftDeploy implements Runnable {

    private final Lift                  lift;

    private final AutonomousDrivetrain  drivetrain;

    private final LinearOpMode          parentOpMode;

    private final TelemetryManager      telemetryManager;

    /**
     *
     * @param lift the lift hardware
     * @param drivetrain the drivetrain used for unlatching
     * @param parentOpMode the opmode used for checking if the system should stop
     * @param telemetryManager used for showing telemetry
     */
    public LiftDeploy(final Lift lift,
                      final AutonomousDrivetrain drivetrain,
                      final LinearOpMode parentOpMode,
                      final TelemetryManager telemetryManager) {
        this.lift = lift;
        this.drivetrain = drivetrain;
        this.parentOpMode = parentOpMode;
        this.telemetryManager = telemetryManager;
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
//        this.drivetrain.stop();

//        dropping down using distance sensors
        Checkable distanceCheckable = DistanceSensorCheckable.getGroup(
                Robot.Lift.distance_left,
                Robot.Lift.distance_right,
                DistanceUnit.MM,
                84,
                3
        );
        if (!distanceCheckable.check()) {
            this.lift.move(Lift.Direction.UP, 1.0);
            this.drivetrain.move(Controller.Direction.N, 0.05);
        }
        while (!distanceCheckable.check() && this.parentOpMode.opModeIsActive()) {
            telemetryItem.set("distance sensor phase " +
                    Robot.Lift.distance_left.getDistance(DistanceUnit.MM) +
                    " " +
                    Robot.Lift.distance_right.getDistance(DistanceUnit.MM));
            this.telemetryManager.cycle();
            this.parentOpMode.idle();
        }
        this.lift.stop();
        this.drivetrain.stop();
        if (!this.parentOpMode.opModeIsActive()) {
            return;
        }

        /*elevate the lift a tiny bit to be able to unlatch*/
        telemetryItem.set("elevating the lift a tiny bit to be able to unlatch");
        Checkable liftCheckable = this.lift.move(Lift.Direction.UP, Robot.ENCODER_TICKS_60_1 * 3, 1.0);
        while (!liftCheckable.check() && this.parentOpMode.opModeIsActive()) {
            this.telemetryManager.cycle();
            this.parentOpMode.idle();
        }
        this.lift.stop();
        if (!this.parentOpMode.opModeIsActive()) {
            return;
        }

//        unlatch
        telemetryItem.set("unlatching");
        Checkable drivetrainCheckable = this.drivetrain.move(Controller.Direction.W, Robot.convertGoldToTicks(3.25), 0.5);
        while (!drivetrainCheckable.check() && this.parentOpMode.opModeIsActive()) {
            this.telemetryManager.cycle();
            this.parentOpMode.idle();
        }
        this.drivetrain.stop();


//        finish
        telemetryItem.set("done");
    }
}
