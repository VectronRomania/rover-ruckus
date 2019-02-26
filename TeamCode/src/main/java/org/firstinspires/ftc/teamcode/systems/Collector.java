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

    private boolean isCollectorMotorRunning = false;

    public Collector() {
        Robot.Collector.setDirection(DcMotorSimple.Direction.FORWARD);
        Robot.Collector.setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Robot.Collector.setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Robot.Collector.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.FLOAT);
        Robot.Collector.setPower(0);

        Robot.Collector.setServoDirection(Servo.Direction.FORWARD, Servo.Direction.REVERSE);
        Robot.Collector.setServoPosition(0.5);
    }

    /**
     * Control the collector manually.
     * @param gamepad
     */

    public void manual(Gamepad gamepad) {

//        Servos
        if (gamepad.y) {
            Robot.Collector.setServoPosition(0);
        } else if (gamepad.b) {
            Robot.Collector.setServoPosition(0.5);
        } else if (gamepad.a){
            Robot.Collector.setServoPosition(0.75);
        }

//        Collector motor
        if (gamepad.right_bumper) {
            isCollectorMotorRunning = true;
        } else if (gamepad.left_bumper) {
            isCollectorMotorRunning = false;
        }
        if (gamepad.right_trigger > 0) {
            Robot.Collector.setPower(-gamepad.right_trigger);
        } else {
            if (isCollectorMotorRunning)
                Robot.Collector.setPower(1);
            else
                Robot.Collector.setPower(0);
        }
    }
}