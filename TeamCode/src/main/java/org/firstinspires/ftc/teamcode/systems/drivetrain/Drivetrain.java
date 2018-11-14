package org.firstinspires.ftc.teamcode.systems.drivetrain;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.drivetrain.configs.ControlType;
import org.firstinspires.ftc.teamcode.systems.drivetrain.configs.WheelBase;

public class Drivetrain extends Robot {

    private WheelBase wheelBase;

    private ControlType controlType;

    public Drivetrain(HardwareMap hardwareMap, WheelBase wheelBase, ControlType controlType) {
        super(hardwareMap);
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
