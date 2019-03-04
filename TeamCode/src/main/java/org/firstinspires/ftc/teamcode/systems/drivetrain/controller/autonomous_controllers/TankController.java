package org.firstinspires.ftc.teamcode.systems.drivetrain.controller.autonomous_controllers;

import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.AutonomousController;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.Controller;
import org.firstinspires.ftc.teamcode.systems.util.checkables.DrivetrainCheckableGroup;

public final class TankController implements AutonomousController {
    public TankController() {}

    @Override
    public void init() {

    }

    @Override
    public DrivetrainCheckableGroup move(Controller.Direction direction, int ticks, double power) {
        return null;
    }

    @Override
    public void move(Controller.Direction direction, double power) {

    }
}
