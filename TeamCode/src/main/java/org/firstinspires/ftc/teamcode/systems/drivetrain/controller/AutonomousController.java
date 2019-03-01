package org.firstinspires.ftc.teamcode.systems.drivetrain.controller;

import org.firstinspires.ftc.teamcode.systems.util.Checkable;

/**
 * An AutonomousController is an interface that specifies how the drivetrain would be controlled
 * autonomously, no matter what wheel base it has
 */
public interface AutonomousController {

    /**
     * Initialize the drivetrain.
     */
    void init();

    /**
     * Move the robot in a certain direction.
     * @param direction
     * @param ticks
     * @param power
     */
    Checkable move(Controller.Direction direction, Integer ticks, Double power);
}