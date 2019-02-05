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
public class AutonomousDrivetrain extends Controller implements AutonomousController {

    private AutonomousController controller;

    public AutonomousDrivetrain(Robot robot, WheelBase wheelBase) {
        super(robot);
        switch (wheelBase) {
            case MECANUM:
                this.controller = new MecanumController(robot);
                break;
            case OMNI:
                this.controller = new OmniController(robot);
                break;
            case TANK:
                this.controller = new TankController(robot);
                break;
        }
    }

    /**
     * Initialize the drivetrain.
     */
    @Override
    public void init() {
        controller.init();
    }

    /**
     * Move the drivetrain.
     * @param direction
     * @param ticks
     * @param power
     */
    @Override
    public void move(Direction direction, Integer ticks, Double power) {
        controller.move(direction, ticks, power);
    }
}

