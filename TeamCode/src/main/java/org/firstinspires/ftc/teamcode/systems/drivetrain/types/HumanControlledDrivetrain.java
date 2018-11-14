package org.firstinspires.ftc.teamcode.systems.drivetrain.types;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.systems.drivetrain.Drivetrain;
import org.firstinspires.ftc.teamcode.systems.drivetrain.configs.ControlType;
import org.firstinspires.ftc.teamcode.systems.drivetrain.configs.WheelBase;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.HumanController;

public class HumanControlledDrivetrain extends Drivetrain {

    private HumanController controller;

    public HumanControlledDrivetrain(HardwareMap hardwareMap, WheelBase wheelBase) {
        super(hardwareMap, wheelBase, ControlType.HUMAN_CONTROLLED);
    }
}
