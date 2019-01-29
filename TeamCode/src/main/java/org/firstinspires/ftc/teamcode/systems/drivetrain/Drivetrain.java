package org.firstinspires.ftc.teamcode.systems.drivetrain;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.drivetrain.configs.ControlType;
import org.firstinspires.ftc.teamcode.systems.drivetrain.configs.WheelBase;

/**
 * A Drivetrain is an object that controls the robot's wheels in order to move
 */
public class Drivetrain {

    protected Robot robot;

    protected WheelBase wheelBase;

    protected ControlType controlType;

    public Drivetrain(Robot robot, WheelBase wheelBase, ControlType controlType) {
        this.robot = robot;
        this.wheelBase = wheelBase;
        this.controlType = controlType;
    }

    public WheelBase getWheelBase() {
        return wheelBase;
    }

    public ControlType getControlType() {
        return controlType;
    }


}
