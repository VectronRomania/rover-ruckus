package org.firstinspires.ftc.teamcode.systems.drivetrain;

import android.support.annotation.NonNull;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.Controller;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.HumanController;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.human_controllers.MecanumController;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.human_controllers.OmniController;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.human_controllers.TankController;

/**
 * A HumanControlledDrivetrain is a drivetrain that is controlled by a human, with a gamepad
 */
public class HumanControlledDrivetrain {

    private HumanController controller;

    public HumanControlledDrivetrain(@NonNull WheelBase wheelBase) {

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
     * Control the drivetrain with a gamepad.
     * @param gamepad
     */
    public void drive(@NonNull Gamepad gamepad) {
        controller.drive(gamepad);
    }

}