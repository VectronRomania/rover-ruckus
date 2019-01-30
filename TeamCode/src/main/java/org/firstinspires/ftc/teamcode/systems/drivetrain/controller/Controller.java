package org.firstinspires.ftc.teamcode.systems.drivetrain.controller;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryGroup;

public abstract class Controller {

    public enum Direction {
        N, NE,
        E, SE,
        S, SW,
        W, NW
    }

    protected Robot robot;

    protected Double a_power;
    protected Double b_power;
    protected Double c_power;
    protected Double d_power;

    public Controller(Robot robot) {
        this.robot = robot;
    }

    protected void setPower(Double a, Double b, Double c, Double d) {
        Robot.Drivetrain.left_front.setPower(a);
        Robot.Drivetrain.right_front.setPower(b);
        Robot.Drivetrain.right_back.setPower(c);
        Robot.Drivetrain.left_back.setPower(d);

        a_power = a;
        b_power = b;
        c_power = c;
        d_power = d;
    }

    protected void setPower(Double a) {
        Robot.Drivetrain.left_front.setPower(a);
        Robot.Drivetrain.right_front.setPower(a);
        Robot.Drivetrain.right_back.setPower(a);
        Robot.Drivetrain.left_back.setPower(a);

        a_power = a;
        b_power = a;
        c_power = a;
        d_power = a;
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

    protected void setTargetPosition(Integer a, Integer b, Integer c, Integer d) {
        Robot.Drivetrain.left_front.setTargetPosition(a);
        Robot.Drivetrain.right_front.setTargetPosition(b);
        Robot.Drivetrain.left_back.setTargetPosition(c);
        Robot.Drivetrain.right_back.setTargetPosition(d);
    }

    protected void setTargetPosition(Integer a) {
        Robot.Drivetrain.left_front.setTargetPosition(a);
        Robot.Drivetrain.right_front.setTargetPosition(a);
        Robot.Drivetrain.left_back.setTargetPosition(a);
        Robot.Drivetrain.right_back.setTargetPosition(a);
    }

    public void stop() {
        a_power = 0.0;
        b_power = 0.0;
        c_power = 0.0;
        d_power = 0.0;

        brake();
        setTargetPosition(
                Robot.Drivetrain.left_front.getCurrentPosition(),
                Robot.Drivetrain.right_front.getCurrentPosition(),
                Robot.Drivetrain.right_back.getCurrentPosition(),
                Robot.Drivetrain.left_back.getCurrentPosition()
        );
    }

    public void brake() {
        setPower(0.0);
    }

    public void resume() {
        setPower(a_power, b_power, c_power, d_power);
    }
}
