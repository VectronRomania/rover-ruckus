package org.firstinspires.ftc.teamcode.systems.drivetrain.controller;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryGroup;

public abstract class Controller {

    protected Robot robot;

    public Controller(Robot robot) {
        this.robot = robot;
    }

    protected void setPower(Double a, Double b, Double c, Double d) {
        Robot.Drivetrain.left_front.setPower(a);
        Robot.Drivetrain.right_front.setPower(b);
        Robot.Drivetrain.right_back.setPower(c);
        Robot.Drivetrain.left_back.setPower(d);
    }

    protected void setPower(Double a) {
        Robot.Drivetrain.left_front.setPower(a);
        Robot.Drivetrain.right_front.setPower(a);
        Robot.Drivetrain.right_back.setPower(a);
        Robot.Drivetrain.left_back.setPower(a);
    }

    protected void setPower(Float a, Float b, Float c, Float d) {
        Robot.Drivetrain.left_front.setPower(a);
        Robot.Drivetrain.right_front.setPower(b);
        Robot.Drivetrain.right_back.setPower(c);
        Robot.Drivetrain.left_back.setPower(d);
    }

    protected void setPower(Float a) {
        Robot.Drivetrain.left_front.setPower(a);
        Robot.Drivetrain.right_front.setPower(a);
        Robot.Drivetrain.right_back.setPower(a);
        Robot.Drivetrain.left_back.setPower(a);
    }

    protected void setDirection(DcMotor.Direction direction) {
        Robot.Drivetrain.left_front.setDirection(direction);
        Robot.Drivetrain.right_front.setDirection(direction);
        Robot.Drivetrain.left_back.setDirection(direction);
        Robot.Drivetrain.right_back.setDirection(direction);
    }

    protected void setRunMode(DcMotor.RunMode runMode) {
        Robot.Drivetrain.left_front.setMode(runMode);
        Robot.Drivetrain.right_front.setMode(runMode);
        Robot.Drivetrain.left_back.setMode(runMode);
        Robot.Drivetrain.right_back.setMode(runMode);
    }

    protected void setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior zeroPowerBehaviour) {
        Robot.Drivetrain.left_front.setZeroPowerBehavior(zeroPowerBehaviour);
        Robot.Drivetrain.right_front.setZeroPowerBehavior(zeroPowerBehaviour);
        Robot.Drivetrain.left_back.setZeroPowerBehavior(zeroPowerBehaviour);
        Robot.Drivetrain.right_back.setZeroPowerBehavior(zeroPowerBehaviour);
    }
}
