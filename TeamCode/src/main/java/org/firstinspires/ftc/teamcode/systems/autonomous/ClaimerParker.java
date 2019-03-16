package org.firstinspires.ftc.teamcode.systems.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.RoboticArm;
import org.firstinspires.ftc.teamcode.systems.drivetrain.AutonomousDrivetrain;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.Controller;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryManager;
import org.firstinspires.ftc.teamcode.systems.util.Checkable;
import org.firstinspires.ftc.teamcode.systems.util.checkables.ImuAxisCheckable;

public class ClaimerParker {

    private final LinearOpMode parentOpMode;

    private final TelemetryManager telemetryManager;

    private final AutonomousDrivetrain drivetrain;

    private final RoboticArm roboticArm;

    private final boolean facingDepot;

    public ClaimerParker(final LinearOpMode parentOpMode,
                         final TelemetryManager telemetryManager,
                         final AutonomousDrivetrain drivetrain,
                         final RoboticArm roboticArm,
                         final boolean facingDepot) {
        this.parentOpMode = parentOpMode;
        this.telemetryManager = telemetryManager;
        this.drivetrain = drivetrain;
        this.roboticArm = roboticArm;
        this.facingDepot = facingDepot;
    }

    private void turn(double degrees) {

        Checkable headingCheckable = ImuAxisCheckable.getGroup(
                Robot.Sensors.left_imu,
                Robot.Sensors.right_imu,
                ImuAxisCheckable.Axis.Z,
                degrees,
                2
        );

        if (degrees > 0) {
            this.drivetrain.move(Controller.Direction.ROTATE_LEFT, 0.5);
        } else {
            this.drivetrain.move(Controller.Direction.ROTATE_RIGHT, 0.5);
        }
        while (parentOpMode.opModeIsActive() && !headingCheckable.check()) {
            telemetryManager.cycle();
            parentOpMode.idle();
        }
        this.drivetrain.stop();
    }

    public void run() {

        TelemetryItem<String> telemetryItem = new TelemetryItem<String>("ClaimerParker") {
            @Override
            public void update() {

            }
        };
        this.telemetryManager.add(telemetryItem);
        telemetryItem.set("starting");

        /*turn the robot parallel to the lander*/
        telemetryItem.set("turning || lander");
        if (facingDepot) {
            turn(45);
        } else {
            turn(-45);
        }
        if (!this.parentOpMode.opModeIsActive()) {
            return;
        }

        /*
        * move forward until the robot reaches the middle of the
        * second adjacent tile
        */
        telemetryItem.set("move to perimeter");
        Checkable drivetrainCheckable = this.drivetrain.move(Controller.Direction.S, 1000, 0.75);
        while (this.parentOpMode.opModeIsActive() && !drivetrainCheckable.check()) {
            this.telemetryManager.cycle();
            this.parentOpMode.idle();
        }
        if (!this.parentOpMode.opModeIsActive()) {
            return;
        }

        /*turn the robot parallel to the field perimeter*/
        telemetryItem.set("turning || perimeter");
        turn(45);

        /*move to the depot*/
        telemetryItem.set("moving to the depot");
        drivetrainCheckable = this.drivetrain.move(
                Controller.Direction.S,
                Double.valueOf(Robot.ENCODER_TICKS_40_1 * 9 / Math.PI).intValue(),
                0.75
        );
        while (this.parentOpMode.opModeIsActive() && !drivetrainCheckable.check()) {
            this.telemetryManager.cycle();
            this.parentOpMode.idle();
        }
        if (!this.parentOpMode.opModeIsActive()) {
            return;
        }

        /*claim the depot*/
        telemetryItem.set("claiming");
        Robot.Servos.teamMarkerServo.setPosition(0.5); // FIXME: 16/03/2019 servo positions
        this.parentOpMode.sleep(250);
        Robot.Servos.teamMarkerServo.setPosition(0);
        if (!this.parentOpMode.opModeIsActive()) {
            return;
        }

        /*move to the crater*/
        telemetryItem.set("moving to the crater");
        drivetrainCheckable = this.drivetrain.move(
                Controller.Direction.N,
                Double.valueOf(Robot.ENCODER_TICKS_40_1 * 13.5 / Math.PI).intValue(),
                0.75
        );
        while (this.parentOpMode.opModeIsActive() && !drivetrainCheckable.check()) {
            this.telemetryManager.cycle();
            this.parentOpMode.idle();
        }
        if (!this.parentOpMode.opModeIsActive()) {
            return;
        }

        /*park*/
        telemetryItem.set("parking");
        roboticArm.setPosition(1);

        telemetryItem.set("done");
    }
}
