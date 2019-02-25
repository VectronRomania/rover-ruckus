package org.firstinspires.ftc.teamcode.systems.drivetrain.controller.human_controllers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.Controller;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.HumanController;

/**
 * This MecanumController is capable of driving a mecanum wheel base by taking input from
 * a driver
 */
public final class MecanumController extends Controller implements HumanController {

    private Double powerLevel = 1.0;

    public MecanumController() {}

    @Override
    public void init() {

        Robot.Drivetrain.setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Robot.Drivetrain.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Robot.Drivetrain.setDirection(DcMotorSimple.Direction.FORWARD);

        Robot.Drivetrain.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    @Override
    public void drive(Gamepad gamepad) {

        if (gamepad.x)
            powerLevel = 1.0;
        if (gamepad.y)
            powerLevel = 0.75;
        if (gamepad.b)
            powerLevel = 0.5;
        if (gamepad.a)
            powerLevel = 0.25;

        if (gamepad.right_stick_x != 0) {
            Robot.Drivetrain.setPower(powerLevel * -gamepad.right_stick_x);
            return;
        }

        if (gamepad.left_stick_x != 0 || gamepad.left_stick_y != 0) {
            Double x = gamepad.left_stick_x * powerLevel;
            Double y = -gamepad.left_stick_y * powerLevel;
            Robot.Drivetrain.setPower(
                    -y -x,
                    y -x,
                    y +x,
                    -y +x
                    );
            return;
        }

        super.stop();
    }
}