package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.Robot;

/**
 * System for controlling the scoop.
 */

public class Scoop {

    public Scoop() {
        Robot.Servos.scoopLeft.setDirection(Servo.Direction.FORWARD);
        Robot.Servos.scoopLeftHigh.setDirection(Servo.Direction.FORWARD);

        Robot.Servos.scoopLeft.setDirection(Servo.Direction.REVERSE);
        Robot.Servos.scoopLeftHigh.setDirection(Servo.Direction.REVERSE);

        moveScoop(0);
        moveScoopHigh(0);
    }

    public void moveScoop(double position) {
        Robot.Servos.scoopLeft.setPosition(position);
        Robot.Servos.scoopRight.setPosition(position);
    }

    public void moveScoopHigh(double position) {
        Robot.Servos.scoopLeftHigh.setPosition(position);
        Robot.Servos.scoopRightHigh.setPosition(position);
    }
}
