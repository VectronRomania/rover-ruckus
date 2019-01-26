package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.DriveTrain;
import org.firstinspires.ftc.teamcode.systems.Type;

@TeleOp(name="Basic: Linear OpMode", group="Linear Opmode")
public class OPMode1 extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;

    private DriveTrain driveTrain;
    private Robot robot;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        driveTrain = new DriveTrain(hardwareMap, Type.MECANUM, gamepad1);

        robot = new Robot();

        robot.init(hardwareMap);

        robot.left_lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.left_lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.left_lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.right_lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.right_lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.right_lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        driveTrain.init();


        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            driveTrain.drive(gamepad1);
//           distance = robot.distanceSensor.getDistance(DistanceUnit.CM);

            if (gamepad1.left_trigger > 0.25) {
                robot.left_lift.setPower(gamepad1.left_trigger);
                robot.right_lift.setPower(-gamepad1.left_trigger);
            } else if (gamepad1.right_trigger > 0.25) {
                robot.left_lift.setPower(-gamepad1.right_trigger);
                robot.right_lift.setPower(gamepad1.right_trigger);
            } else {
                robot.left_lift.setPower(0);
                robot.right_lift.setPower(0);
            }
            telemetry.addData("Distance sensor:", robot.distanceSensor.getDistance(DistanceUnit.MM));
            telemetry.addData("LeftLift", robot.left_lift.getCurrentPosition());
            telemetry.addData("RightLift", robot.right_lift.getCurrentPosition());

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
//            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }
    }
}