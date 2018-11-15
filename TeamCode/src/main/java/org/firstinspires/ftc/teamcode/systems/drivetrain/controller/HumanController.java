package org.firstinspires.ftc.teamcode.systems.drivetrain.controller;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * A HumanController is an interface that specified how the drivetrain would be controlled
 * by a human, no matter what wheel base it has
 */

public interface HumanController {
    void init();
    void drive(Gamepad gamepad);
}
