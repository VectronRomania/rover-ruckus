package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.hardware.Robot;

@TeleOp(name="Single motor encoder test", group="Tests")


public class SingleMotorEncoderTest extends LinearOpMode {

    public Robot robot;

    @Override
    public void runOpMode() {

        robot = new Robot();

        robot.init(hardwareMap);

        robot.left_front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.left_front.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.left_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.left_front.setDirection(DcMotorSimple.Direction.FORWARD);

        robot.left_back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.left_back.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.left_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.left_back.setDirection(DcMotorSimple.Direction.FORWARD);

        robot.right_front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.right_front.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.right_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.right_front.setDirection(DcMotorSimple.Direction.FORWARD);

        robot.right_back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.right_back.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.right_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.right_back.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();

        robot.left_front.setTargetPosition(-2500);
        robot.left_back.setTargetPosition(-2500);
        robot.right_front.setTargetPosition(2500);
        robot.right_back.setTargetPosition(-2500);

        robot.left_back.setPower(0.3);
        robot.left_front.setPower(0.3);
        robot.right_back.setPower(0.3);
        robot.right_front.setPower(0.3);

        while (opModeIsActive()) {
            if (robot.left_front.isBusy() ||
                    robot.left_back.isBusy() ||
                    robot.right_front.isBusy() ||
                    robot.right_back.isBusy()) {
                telemetry.addData("left_front", robot.left_front.getCurrentPosition());
                telemetry.addData("left_back", robot.left_back.getCurrentPosition());
                telemetry.addData("right_front", robot.right_front.getCurrentPosition());
                telemetry.addData("right_back", robot.right_back.getCurrentPosition());
                telemetry.update();
                continue;
            }
            robot.left_back.setPower(0);
            robot.right_back.setPower(0);
            robot.left_front.setPower(0);
            robot.right_front.setPower(0);
//            telemetry.addData("Encoder", motor.getCurrentPosition());
            telemetry.update();
        }

    }
}
