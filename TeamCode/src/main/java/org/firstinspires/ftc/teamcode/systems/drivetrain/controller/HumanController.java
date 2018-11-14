package org.firstinspires.ftc.teamcode.systems.drivetrain.controller;

import com.qualcomm.robotcore.hardware.Gamepad;

public interface HumanController {
    void init();
    void drive(Gamepad gamepad);
}
