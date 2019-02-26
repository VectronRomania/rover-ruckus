package org.firstinspires.ftc.teamcode.systems;

import android.support.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.Robot;

/**
 * System for controlling the extender.
 */

public class Extender {

    /**
     * The direction of the movement of the extender.
     */

    public enum Direction {
        FORWARD,
        BACKWARD
    }

    public Extender() {
        Robot.Extender.setDirection(DcMotor.Direction.FORWARD);
        Robot.Extender.setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Robot.Extender.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Robot.Extender.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Control the extender manually.
     * @param gamepad
     */

    public void manual(Gamepad gamepad) {
        Robot.Extender.setPower(gamepad.left_stick_y);
    }

    /**
     * Move the extender autonomously
     * @param direction
     * @param power
     */

    public void move(@NonNull Direction direction, double power) {
        if (direction == Direction.FORWARD) {
            Robot.Extender.setPower(power);
        } else if (direction == Direction.BACKWARD) {
            Robot.Extender.setPower(-power);
        }
    }
}
