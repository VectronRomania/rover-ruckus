package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.drivetrain.AutonomousDrivetrain;
import org.firstinspires.ftc.teamcode.systems.drivetrain.WheelBase;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.Controller;
import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.telemetry.items.CheckableTelemetryItem;
import org.firstinspires.ftc.teamcode.systems.util.Checkable;

@Autonomous(name = "Encoder Move Forward Test", group = "test")
public class EncoderMoveForwardTest extends AutonomousStandard {

    @Override
    public void initialize() {
        drivetrain = new AutonomousDrivetrain(WheelBase.MECANUM);
        drivetrain.initialize();

        telemetryManager.cycle();
    }

    @Override
    public void opModeLoop() {

        final Checkable checkable = drivetrain.move(Controller.Direction.N, Robot.ENCODER_TICKS_40_1, 0.25);
        telemetryManager.add(new CheckableTelemetryItem("Test status", checkable));

        while (opModeIsActive() && !checkable.check()) {
            telemetryManager.cycle();
            idle();
        }
        telemetryManager.cycle();
        drivetrain.stop();
    }
}
