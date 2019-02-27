package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.opmode.TeleOpStandard;

@TeleOp(name = "Lift manual controller", group = "test")
public class LiftManualController extends TeleOpStandard {
    @Override
    public void initialize() {
        Robot.Lift.setDirection(DcMotorSimple.Direction.REVERSE, DcMotorSimple.Direction.FORWARD);
        Robot.Lift.setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Robot.Lift.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void opModeLoop() {
        Robot.Lift.setPower(-gamepad1.left_stick_y, -gamepad1.right_stick_y);
    }
}
