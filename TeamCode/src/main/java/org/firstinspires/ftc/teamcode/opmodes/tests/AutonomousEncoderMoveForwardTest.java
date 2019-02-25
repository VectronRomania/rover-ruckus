package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.systems.drivetrain.AutonomousDrivetrain;
import org.firstinspires.ftc.teamcode.systems.drivetrain.WheelBase;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.Controller;
import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.telemetry.items.CheckableTelemetryItem;
import org.firstinspires.ftc.teamcode.systems.util.Checkable;

@Autonomous(name = "Encoder Move Forward Test", group = "test")
public class AutonomousEncoderMoveForwardTest extends AutonomousStandard {

    @Override
    public void initialize() {
        drivetrain = new AutonomousDrivetrain(WheelBase.MECANUM);
        drivetrain.init();

        telemetryManager.cycle();
    }

    @Override
    public void opModeLoop() {

        Checkable checkable = drivetrain.move(Controller.Direction.N, 1000, 0.25);
        telemetryManager.add(new CheckableTelemetryItem("Test status", checkable));

        while (opModeIsActive() && !checkable.check()) {
            telemetryManager.cycle();
            idle();
        }

        drivetrain.stop();

        if (opModeIsActive()) {
            sleep(1500);
            stop();
        }
    }
}
