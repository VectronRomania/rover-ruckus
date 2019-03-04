package org.firstinspires.ftc.teamcode.systems.drivetrain.controller.autonomous_controllers;

import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.AutonomousController;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.Controller;
import org.firstinspires.ftc.teamcode.systems.util.checkables.DrivetrainCheckableGroup;

public final class OmniController extends Controller implements AutonomousController {
    public OmniController() {}

    @Override
    public void init() {

    }

    @Override
    public DrivetrainCheckableGroup move(Direction direction, int ticks, double power) {
        return null;
    }

    @Override
    public void move(Direction direction, double power) {

    }
}
