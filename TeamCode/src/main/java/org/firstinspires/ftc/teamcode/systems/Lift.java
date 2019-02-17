package org.firstinspires.ftc.teamcode.systems;

import android.support.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.Robot;


public class Lift {

    private Double lastPower = 0.0;

    public enum Direction {
        UP,
        DOWN
    }

    public Lift() {}

    public void init() {
        Robot.Lift.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);

        Robot.Lift.setDirection(DcMotor.Direction.FORWARD);

        Robot.Lift.setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Robot.Lift.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void manual(@NonNull Gamepad gamepad) {
        if (gamepad.left_trigger > 0) {
            Robot.Lift.setPower(-gamepad.left_trigger, gamepad.left_trigger);
            return;
        }
        if (gamepad.right_trigger > 0) {
            Robot.Lift.setPower(gamepad.right_trigger, -gamepad.right_trigger);
            return;
        }
        Robot.Lift.setPower(0.0);
    }

    public void move(@NonNull Direction direction, @NonNull Integer ticks, @NonNull Double power) {
        switch (direction) {
            case UP:
                Robot.Lift.setTargetPosition(
                        Robot.Lift.left_lift.getCurrentPosition()   + ticks,
                        Robot.Lift.right_lift.getCurrentPosition() - ticks
                );
                break;
            case DOWN:
                Robot.Lift.setTargetPosition(
                        Robot.Lift.left_lift.getCurrentPosition()   - ticks,
                        Robot.Lift.right_lift.getCurrentPosition() + ticks
                );
                break;
        }
        Robot.Lift.setPower(power);
    }

    public void move(@NonNull Direction direction, @NonNull Double power) {
        switch (direction) {
            case UP:
                Robot.Lift.setPower(
                        power,
                        -power
                );
                break;
            case DOWN:
                Robot.Lift.setPower(
                        -power,
                        power
                );
                break;
        }

    }

    public void stop() {
        Robot.Lift.setTargetPosition(
                Robot.Lift.left_lift.getCurrentPosition(),
                Robot.Lift.right_lift.getCurrentPosition()
        );
        Robot.Lift.setPower(0.0);
    }

    public void brake() {
        lastPower = (Robot.Lift.left_lift.getPower() + Robot.Lift.right_lift.getPower()) / 2;

        Robot.Lift.setPower(0.0);
    }

    public void resume() {
        Robot.Lift.setPower(
                lastPower, -lastPower
        );
    }
}
