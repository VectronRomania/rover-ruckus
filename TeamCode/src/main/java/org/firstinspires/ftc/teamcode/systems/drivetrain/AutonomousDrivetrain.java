package org.firstinspires.ftc.teamcode.systems.drivetrain;

import android.support.annotation.NonNull;

import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.AutonomousController;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.Controller;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.autonomous_controllers.MecanumController;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.autonomous_controllers.OmniController;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.autonomous_controllers.TankController;
import org.firstinspires.ftc.teamcode.systems.util.Checkable;
import org.firstinspires.ftc.teamcode.systems.util.checkables.DrivetrainCheckableGroup;

/**
 * An AutonomousDrivetrain is a drivetrain that is controlled autonomously, through the use of special methods
 */
public class AutonomousDrivetrain extends Controller {

    private AutonomousController controller;

    public AutonomousDrivetrain(@NonNull WheelBase wheelBase) {
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
    public void initialize() {
        controller.init();
    }

    @NonNull
    public Checkable move(@NonNull Direction direction, int ticks, double power) {
        return controller.move(direction, ticks, power);
    }

    public void move(@NonNull Direction direction, double power) {
        controller.move(direction, power);
    }
}

