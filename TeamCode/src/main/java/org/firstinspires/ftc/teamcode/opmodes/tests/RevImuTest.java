package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.telemetry.items.RevImuOrientationTelemetryGroup;

@Autonomous(name = "Rev IMU Test", group = "test")
public class RevImuTest extends AutonomousStandard {

    @Override
    protected void initialize() {
        telemetryManager.add(
                new RevImuOrientationTelemetryGroup("REV Orientation")
        );
    }

    @Override
    protected void opModeLoop() {}
}
