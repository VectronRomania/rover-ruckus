package org.firstinspires.ftc.teamcode.systems.telemetry.items;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryGroup;

public final class DrivetrainEncoderTelemetryGroup extends TelemetryGroup<Integer> {

    public DrivetrainEncoderTelemetryGroup() {
        super("Drivetrain motor encoder values");

        add(new MotorEncoderTelemetryItem(
                "left_front",
                Robot.Drivetrain.left_front
        ));

        add(new MotorEncoderTelemetryItem(
                "left_back",
                Robot.Drivetrain.left_back
        ));

        add(new MotorEncoderTelemetryItem(
                "right_front",
                Robot.Drivetrain.right_front
        ));

        add(new MotorEncoderTelemetryItem(
                "right_back",
                Robot.Drivetrain.right_back
        ));
    }
}
