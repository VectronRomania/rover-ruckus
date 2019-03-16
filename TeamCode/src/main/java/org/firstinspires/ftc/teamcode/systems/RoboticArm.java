package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.Robot;

/**
 * System for controlling the extender.
 */

public class RoboticArm {

    /**
     * The direction of the movement of the extender.
     */

    public RoboticArm() {
        Robot.RoboticArm.setDirection(DcMotor.Direction.FORWARD);
        Robot.RoboticArm.setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Robot.RoboticArm.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Robot.RoboticArm.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);

        Robot.RoboticArm.setServoPosition(0.5);
    }

    /**
     * Control the extender manually.
     * @param gamepad
     */

    public void manual(Gamepad gamepad) {
        Robot.RoboticArm.setPower(gamepad.right_stick_y / 2);
        Robot.RoboticArm.setServoPosition(gamepad.right_trigger);
    }
}
