package org.firstinspires.ftc.teamcode.systems.drivetrain.controller;

/**
 * An AutonomousController is an interface that specifies how the drivetrain would be controlled
 * autonomously, no matter what wheel base it has
 */
public interface AutonomousController {
    /**
     * Brake the drivetrain(without changing the target position).
     */
    void brake();

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
    void move(Controller.Direction direction, Integer ticks, Double power);

    /**
     * Resume robot movement(use after braking).
     */
    void resume();

    /**
     * Bring the robot to a full stop.
     */
    void stop();
}