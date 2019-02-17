package org.firstinspires.ftc.teamcode.systems.drivetrain;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.AutonomousController;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.Controller;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.autonomous_controllers.MecanumController;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.autonomous_controllers.OmniController;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.autonomous_controllers.TankController;

/**
 * An AutonomousDrivetrain is a drivetrain that is controlled autonomously, through the use of special methods
 */
public class AutonomousDrivetrain extends Controller {

    private AutonomousController controller;

    public AutonomousDrivetrain(WheelBase wheelBase) {
        switch (wheelBase) {
            case MECANUM:
                this.controller = new MecanumController();
                break;
            case OMNI:
                this.controller = new OmniController();
                break;
            case TANK:
                this.controller = new TankController();
                break;
        }
    }

    /**
     * Initialize the drivetrain.
     */
    public void init() {
        controller.init();
    }

    /**
     * Move the drivetrain.
     * @param direction
     * @param ticks
     * @param power
     */
    public void move(Direction direction, Integer ticks, Double power) {
        controller.move(direction, ticks, power);
    }
}

