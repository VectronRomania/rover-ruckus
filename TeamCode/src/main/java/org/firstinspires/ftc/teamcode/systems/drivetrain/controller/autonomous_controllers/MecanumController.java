package org.firstinspires.ftc.teamcode.systems.drivetrain.controller.autonomous_controllers;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.AutonomousController;

/**
 * This MecanumController is capable of driving a mecanum wheel base using special methods that move
 * the robot precisely and autonomously
 */

public class MecanumController extends Robot implements AutonomousController {
    public MecanumController(HardwareMap hw) {
        super(hw);
    }

    @Override
    public void init() {

    }
}
