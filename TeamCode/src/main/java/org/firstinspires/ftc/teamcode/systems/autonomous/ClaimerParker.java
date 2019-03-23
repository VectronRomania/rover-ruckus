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

    /*Pathing values*/
    private static final double SAMPLING_TO_PERIMETER = 25.5;
    private static final double SAMPLING_TO_PERIMETER_2 = 1;
    private static final double PERIMETER_TO_DEPOT = 1.5;
    private static final double DEPOT_TO_CRATER = 2.75;


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

//        Checkable headingCheckable = ImuAxisCheckable.getGroup(
//                Robot.Sensors.left_imu,
//                Robot.Sensors.right_imu,
//                ImuAxisCheckable.Axis.Z,
//                degrees,
//                2
//        );
//
//        if (degrees > 0) {
//            this.drivetrain.move(Controller.Direction.ROTATE_LEFT, 310, 0.1);
//        } else {
//            this.drivetrain.move(Controller.Direction.ROTATE_RIGHT, 310, 0.1);
//        }
//        while (parentOpMode.opModeIsActive() && !headingCheckable.check()) {
//            telemetryManager.cycle();
//            parentOpMode.idle();
//        }
//        this.drivetrain.stop();

        Checkable drivetrainCheckable;

        if (degrees > 0) {
            drivetrainCheckable = this.drivetrain.move(Controller.Direction.ROTATE_LEFT, Robot.convertDegreesToTicks(degrees), 0.5);
        } else {
            drivetrainCheckable = this.drivetrain.move(Controller.Direction.ROTATE_RIGHT, Robot.convertDegreesToTicks(-degrees), 0.5);
        }
        while (parentOpMode.opModeIsActive() && !drivetrainCheckable.check()) {
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
            turn(90);
        } else {
            turn(-90);
        }
        if (!this.parentOpMode.opModeIsActive()) {
            return;
        }

        /*
        * move forward until the robot reaches the middle of the
        * second adjacent tile - to the perimeter
        */
        telemetryItem.set("move to perimeter");
        Checkable drivetrainCheckable;
        if (facingDepot) {
            drivetrainCheckable = this.drivetrain.move(
                    Controller.Direction.S,
                    Robot.convertTilesToTicks(SAMPLING_TO_PERIMETER),
                    0.75
            );
        } else {
            drivetrainCheckable = this.drivetrain.move(
                    Controller.Direction.N,
                    Robot.convertTilesToTicks(SAMPLING_TO_PERIMETER),
                    0.75
            );
        }
        while (this.parentOpMode.opModeIsActive() && !drivetrainCheckable.check()) {
            this.telemetryManager.cycle();
            this.parentOpMode.idle();
        }
        if (!this.parentOpMode.opModeIsActive()) {
            return;
        }
        /*if facing the depot, make another move*/
        if (facingDepot) {
            turn(-45);

            drivetrainCheckable = drivetrain.move(Controller.Direction.N, Robot.convertTilesToTicks(SAMPLING_TO_PERIMETER_2), 0.5);
            while (this.parentOpMode.opModeIsActive() && !drivetrainCheckable.check()) {
                this.telemetryManager.cycle();
                this.parentOpMode.idle();
            }
            if (!this.parentOpMode.opModeIsActive()) {
                return;
            }
        }

        /*turn the robot parallel to the field perimeter*/
        telemetryItem.set("turning || perimeter");
        if (facingDepot) {
            turn(90);
        } else {
            turn(45);
        }

        /*move to the depot*/
        telemetryItem.set("moving to the depot");
        drivetrainCheckable = this.drivetrain.move(
                Controller.Direction.S,
                Robot.convertTilesToTicks(PERIMETER_TO_DEPOT),
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
        Robot.Servos.teamMarkerServo.setPosition(1);
        this.parentOpMode.sleep(750);
        Robot.Servos.teamMarkerServo.setPosition(0);
        if (!this.parentOpMode.opModeIsActive()) {
            return;
        }

        /*move to the crater*/
        telemetryItem.set("moving to the crater");
        drivetrainCheckable = this.drivetrain.move(
                Controller.Direction.N,
                Robot.convertTilesToTicks(DEPOT_TO_CRATER),
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
        roboticArm.left.setPower(0.5);
        roboticArm.right.setPower(0.5);
        this.parentOpMode.sleep(500);

        telemetryItem.set("done");
    }
}
