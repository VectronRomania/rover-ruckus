package org.firstinspires.ftc.teamcode.systems.drivetrain.types;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.drivetrain.Drivetrain;
import org.firstinspires.ftc.teamcode.systems.drivetrain.configs.ControlType;
import org.firstinspires.ftc.teamcode.systems.drivetrain.configs.WheelBase;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.AutonomousController;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.autonomous_controllers.MecanumController;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.autonomous_controllers.OmniController;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.autonomous_controllers.TankController;

/**
 * An AutonomousDrivetrain is a drivetrain that is controlled autonomously, through the use of special methods
 */
public class AutonomousDrivetrain extends Drivetrain {

    private AutonomousController controller;

    public AutonomousDrivetrain(Robot robot, WheelBase wheelBase) {
        super(robot, wheelBase, ControlType.AUTONOMOUS);

        switch (wheelBase) {
            case MECANUM:
                this.controller = new MecanumController(super.robot);
                break;
            case OMNI:
                this.controller = new OmniController(super.robot);
                break;
            case TANK:
                this.controller = new TankController(super.robot);
                break;
        }
    }

    public AutonomousController getController() {
        return controller;
    }
}

