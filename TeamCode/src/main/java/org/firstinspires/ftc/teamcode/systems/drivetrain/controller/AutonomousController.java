package org.firstinspires.ftc.teamcode.systems.drivetrain.controller;

/**
 * An AutonomousController is an interface that specified how the drivetrain would be controlled
 * autonomously, no matter what wheel base it has
 */
public interface AutonomousController {
    void brake();
    void init();
    void move(Controller.Direction direction, Integer ticks, Double power);
    void resume();
    void stop();
}