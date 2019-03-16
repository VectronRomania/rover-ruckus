package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.util.IntervalDcMotor;

/**
 * System for controlling the collector.
 */

public class Collector {

    private boolean buttonLock = false;
    private double servoPosition = 0.75;

    public Collector() {


//        Robot.Collector.setServoDirection(Servo.Direction.FORWARD, Servo.Direction.REVERSE);
//        Robot.Collector.setServoPosition(servoPosition);
    }

    /**
     * Control the collector manually.
     * @param gamepad
     */

    public void manual(Gamepad gamepad) {

    }
}