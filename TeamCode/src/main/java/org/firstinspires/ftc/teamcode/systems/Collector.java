package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.Robot;

/**
 * System for controlling the collector.
 */

public class Collector {

    private boolean buttonLock = false;
    private double servoPosition = 0.75;

    public Collector() {
        Robot.Collector.setDirection(DcMotorSimple.Direction.REVERSE);
        Robot.Collector.setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Robot.Collector.setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Robot.Collector.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.FLOAT);
        Robot.Collector.setPower(0);

        Robot.Collector.setServoDirection(Servo.Direction.FORWARD, Servo.Direction.REVERSE);
        Robot.Collector.setServoPosition(servoPosition);
    }

    /**
     * Control the collector manually.
     * @param gamepad
     */

    public void manual(Gamepad gamepad) {

//        Servos
        if (gamepad.y) {
            servoPosition = 0.85;
        }
        if (gamepad.a && !buttonLock){
            if (servoPosition > 0) {
                servoPosition -= 0.1;
            }
            buttonLock = true;
        }
        if (gamepad.b && !buttonLock) {
            if (servoPosition < 1) {
                servoPosition += 0.1;
            }
            buttonLock = true;
        }
        if (!gamepad.a && !gamepad.b) {
            buttonLock = false;
        }
        Robot.Collector.setServoPosition(servoPosition);

//        Collector motor
        if (gamepad.left_trigger > 0) {
            Robot.Collector.setPower(-gamepad.left_trigger);
        } else if (gamepad.right_trigger > 0) {
            Robot.Collector.setPower(gamepad.right_trigger);
        } else {
            Robot.Collector.setPower(0);
        }
    }
}