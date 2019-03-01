package org.firstinspires.ftc.teamcode.systems.drivetrain.controller;

import org.firstinspires.ftc.teamcode.hardware.Robot;

public abstract class Controller {

    public enum Direction {
        N, NE,
        E, SE,
        S, SW,
        W, NW,
        ROTATE_LEFT,
        ROTATE_RIGHT
    }

    private Double a_power;
    private Double b_power;
    private Double c_power;
    private Double d_power;

    public Controller() {
        a_power = 0.0;
        b_power = 0.0;
        c_power = 0.0;
        d_power = 0.0;
    }

    public void stop() {
        a_power = 0.0;
        b_power = 0.0;
        c_power = 0.0;
        d_power = 0.0;

        brake();
        Robot.Drivetrain.setTargetPosition(
                Robot.Drivetrain.left_front.getCurrentPosition(),
                Robot.Drivetrain.right_front.getCurrentPosition(),
                Robot.Drivetrain.right_back.getCurrentPosition(),
                Robot.Drivetrain.left_back.getCurrentPosition()
        );
    }

    public void brake() {
        a_power = Robot.Drivetrain.left_front.getPower();
        b_power = Robot.Drivetrain.right_front.getPower();
        c_power = Robot.Drivetrain.right_back.getPower();
        d_power = Robot.Drivetrain.left_back.getPower();

        Robot.Drivetrain.setPower(0.0);
    }

    public void resume() {
        Robot.Drivetrain.setPower(a_power, b_power, c_power, d_power);
    }
}
