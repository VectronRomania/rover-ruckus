package org.firstinspires.ftc.teamcode.systems.telemetry.items;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryGroup;

public final class DrivetrainPowerTelemetryGroup extends TelemetryGroup<Double> {

    public DrivetrainPowerTelemetryGroup() {
        super("Drivetrain motor powers");

        add(new MotorPowerTelemetryItem(
                "left_front",
                Robot.Drivetrain.left_front
        ));

        add(new MotorPowerTelemetryItem(
                "left_back",
                Robot.Drivetrain.left_back
        ));

        add(new MotorPowerTelemetryItem(
                "right_front",
                Robot.Drivetrain.right_front
        ));

        add(new MotorPowerTelemetryItem(
                "right_back",
                Robot.Drivetrain.right_back
        ));
    }
}
