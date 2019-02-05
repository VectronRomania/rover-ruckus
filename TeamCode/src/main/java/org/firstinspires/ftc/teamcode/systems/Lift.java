package org.firstinspires.ftc.teamcode.systems;

import android.support.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.Robot;


public class Lift {

    private Double lastPower = 0.0;

    public enum Direction {
        UP,
        DOWN
    }

    private Robot robot;

    public Lift(@NonNull Robot robot) {
        this.robot = robot;
    }

    public void init() {
        Robot.Lift.left_lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Robot.Lift.right_lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Robot.Lift.left_lift.setDirection(DcMotor.Direction.FORWARD);
        Robot.Lift.right_lift.setDirection(DcMotor.Direction.REVERSE);

        Robot.Lift.left_lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Robot.Lift.right_lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        switchToRunUsingEncoder();
    }

    public void switchToRunUsingEncoder() {
        Robot.Lift.left_lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Robot.Lift.right_lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void switchToRunToPosition() {
        Robot.Lift.left_lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Robot.Lift.right_lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void manual(@NonNull Gamepad gamepad) {
        if (gamepad.left_trigger > 0) {
            Robot.Lift.left_lift.setPower(-gamepad.left_trigger);
            Robot.Lift.right_lift.setPower(-gamepad.left_trigger);
            return;
        }
        if (gamepad.right_trigger > 0) {
            Robot.Lift.left_lift.setPower(gamepad.right_trigger);
            Robot.Lift.right_lift.setPower(gamepad.right_trigger);
            return;
        }
        Robot.Lift.left_lift.setPower(0.0);
        Robot.Lift.right_lift.setPower(0.0);
    }

    public void move(@NonNull Direction direction, @NonNull Integer ticks, @NonNull Double power) {
        switch (direction) {
            case UP:
                Robot.Lift.left_lift.setTargetPosition(
                        Robot.Lift.left_lift.getCurrentPosition() + ticks
                );
                Robot.Lift.left_lift.setTargetPosition(
                        Robot.Lift.right_lift.getCurrentPosition() - ticks
                );
                break;
            case DOWN:
                Robot.Lift.left_lift.setTargetPosition(
                        Robot.Lift.left_lift.getCurrentPosition() - ticks
                );
                Robot.Lift.left_lift.setTargetPosition(
                        Robot.Lift.right_lift.getCurrentPosition() + ticks
                );
                break;
        }
        Robot.Lift.left_lift.setPower(power);
        Robot.Lift.right_lift.setPower(power);
    }

    public void move(@NonNull Direction direction, @NonNull Double power) {
        switch (direction) {
            case UP:
                Robot.Lift.left_lift.setPower(power);
                Robot.Lift.right_lift.setPower(power);
                break;
            case DOWN:
                Robot.Lift.left_lift.setPower(-power);
                Robot.Lift.right_lift.setPower(-power);
                break;
        }

    }

    public void stop() {
        Robot.Lift.left_lift.setTargetPosition(Robot.Lift.left_lift.getCurrentPosition());
        Robot.Lift.right_lift.setTargetPosition(Robot.Lift.right_lift.getCurrentPosition());

        Robot.Lift.setPower(0.0);
    }

    public void brake() {
        lastPower = (Robot.Lift.left_lift.getPower() + Robot.Lift.right_lift.getPower()) / 2;

        Robot.Lift.setPower(0.0);
    }

    public void resume() {
        Robot.Lift.left_lift.setPower(lastPower);
        Robot.Lift.right_lift.setPower(lastPower);
    }
}
