package org.firstinspires.ftc.teamcode.systems.drivetrain.controller.human_controllers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.HumanController;

/**
 * This MecanumController is capable of driving a mecanum wheel base by taking input from
 * a driver
 */

public class MecanumController extends Robot implements HumanController {

    public MecanumController(HardwareMap hw) {
        super(hw);
    }

    @Override
    public void init() {
        super.left_front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        super.left_front.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void drive(Gamepad gamepad) {

    }
}
