package org.firstinspires.ftc.teamcode.systems.drivetrain.types;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.systems.drivetrain.Drivetrain;
import org.firstinspires.ftc.teamcode.systems.drivetrain.configs.ControlType;
import org.firstinspires.ftc.teamcode.systems.drivetrain.configs.WheelBase;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.AutonomousController;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.autonomous_controllers.MecanumController;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.autonomous_controllers.OmniController;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.autonomous_controllers.TankController;

public class AutonomousDrivetrain extends Drivetrain {

    private AutonomousController controller;

    public AutonomousDrivetrain(HardwareMap hardwareMap, WheelBase wheelBase) {
        super(hardwareMap, wheelBase, ControlType.AUTONOMOUS);

        switch (wheelBase) {
            case MECANUM:
                this.controller = new MecanumController(hardwareMap);
                break;
            case OMNI:
                this.controller = new OmniController(hardwareMap);
                break;
            case TANK:
                this.controller = new TankController(hardwareMap);
                break;
        }
    }
}
