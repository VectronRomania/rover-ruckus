package org.firstinspires.ftc.teamcode.systems;

import android.support.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.util.Checkable;
import org.firstinspires.ftc.teamcode.systems.util.CheckableGroup;
import org.firstinspires.ftc.teamcode.systems.util.checkables.MotorEncoderCheckable;

/**
 * System for controlling the lift.
 */
public class Lift {

    /**
     * The direction of movement of the lift.
     */
    public enum Direction {
        DOWN,
        UP
    }

    public Lift() {
        Robot.Lift.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);

        Robot.Lift.setDirection(DcMotor.Direction.REVERSE);

        Robot.Lift.setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Robot.Lift.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);

        Robot.Lift.setPower(0);
    }

    /**
     * Control the lift manually.
     * @param gamepad the gamepad used for extracting the controls.
     */
    public void manual(@NonNull Gamepad gamepad) {
        if (gamepad.left_trigger > 0) {
            Robot.Lift.setPower(gamepad.left_trigger);
            return;
        }
        if (gamepad.right_trigger > 0) {
            Robot.Lift.setPower(-gamepad.right_trigger);
            return;
        }
        Robot.Lift.setPower(0.0);
    }

    /**
     * Control the lift in run to position mode.
     * @param direction
     * @param ticks
     * @param power
     */
    public Checkable move(@NonNull Direction direction, @NonNull Integer ticks, @NonNull Double power) {
        Robot.Lift.setRunMode(DcMotor.RunMode.RUN_TO_POSITION);

        int mTicks = 0;

        switch (direction) {
            case DOWN:
                mTicks = Robot.Lift.motor.getCurrentPosition() + ticks;
                break;
            case UP:
                mTicks = Robot.Lift.motor.getCurrentPosition() - ticks;
                break;
        }
        Robot.Lift.setTargetPosition(mTicks);
        Robot.Lift.setPower(power);

        return new MotorEncoderCheckable(
                        Robot.Lift.motor,
                        mTicks, 10
        );
    }

    /**
     * Control the lift in run using encoder mode.
     * @param direction
     * @param power
     */
    public void move(@NonNull Direction direction, @NonNull Double power) {
        Robot.Lift.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        switch (direction) {
            case DOWN:
                Robot.Lift.setPower(power);
                break;
            case UP:
                Robot.Lift.setPower(-power);
                break;
        }
    }

    /**
     * Stop the lift entirely.
     */
    public void stop() {
        Robot.Lift.setTargetPosition(Robot.Lift.motor.getCurrentPosition());
        Robot.Lift.setPower(0.0);
    }
}
