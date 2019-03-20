package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.Robot;

/**
 * System for controlling the collector.
 */

public class Collector {

    private boolean buttonLock = false;
    private double servoPosition = 0;


    public Collector() {

        Robot.Collector.setServoDirection(Servo.Direction.FORWARD);
        Robot.Collector.setServoPosition(servoPosition);
        Robot.Collector.setDirection(DcMotorSimple.Direction.FORWARD);
        Robot.Collector.setPower(0);
    }

    /**
     * Control the collector manually.
     * @param gamepad
     */

    public void manual(Gamepad gamepad) {

        if (gamepad.left_bumper) {
            Robot.Collector.setServoPosition(0.5, 0.25);
        }
        if (gamepad.right_bumper) {
            Robot.Collector.servoLeft.setPosition(0.75);
        }

        if (gamepad.x) {
            Robot.Collector.collector.setPower(1);
        }
        if (gamepad.y) {
            Robot.Collector.collector.setPower(-1);
        }
        if (gamepad.a) {
            Robot.Collector.collector.setPower(0);
        }
    }
}