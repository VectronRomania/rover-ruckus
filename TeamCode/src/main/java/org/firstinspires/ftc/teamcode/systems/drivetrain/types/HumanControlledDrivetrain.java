package org.firstinspires.ftc.teamcode.systems.drivetrain.types;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.drivetrain.Drivetrain;
import org.firstinspires.ftc.teamcode.systems.drivetrain.configs.ControlType;
import org.firstinspires.ftc.teamcode.systems.drivetrain.configs.WheelBase;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.HumanController;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.human_controllers.MecanumController;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.human_controllers.OmniController;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.human_controllers.TankController;

/**
 * A HumanControlledDrivetrain is a drivetrain that is controlled by a human, with a gamepad
 */
public class HumanControlledDrivetrain extends Drivetrain {

    private HumanController controller;

    public HumanControlledDrivetrain(Robot robot, WheelBase wheelBase) {
        super(robot, wheelBase, ControlType.HUMAN_CONTROLLED);

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

    public void init() {
        controller.init();
    }

    public void drive(Gamepad gamepad) {
        controller.drive(gamepad);
    }

}