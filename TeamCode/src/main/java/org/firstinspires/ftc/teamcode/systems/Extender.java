package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.Robot;

public class Extender {

    public Extender() {
        Robot.Extender.setDirection(DcMotor.Direction.FORWARD);
        Robot.Extender.setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Robot.Extender.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Robot.Extender.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void manual(Gamepad gamepad) {
        Robot.Extender.setPower(gamepad.left_stick_y);
    }

//    public void auto()
}
