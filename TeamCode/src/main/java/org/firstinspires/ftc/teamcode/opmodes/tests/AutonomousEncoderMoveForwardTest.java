package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.util.Checkable;
import org.firstinspires.ftc.teamcode.systems.telemetry.items.CheckableTelemetryItem;
import org.firstinspires.ftc.teamcode.systems.util.checkables.MotorEncoderCheckable;
import org.firstinspires.ftc.teamcode.systems.util.checkables.DrivetrainEncoderCheckableGroup;
import org.firstinspires.ftc.teamcode.systems.drivetrain.WheelBase;
import org.firstinspires.ftc.teamcode.systems.telemetry.items.DrivetrainEncoderTelemetryGroup;
import org.firstinspires.ftc.teamcode.systems.drivetrain.AutonomousDrivetrain;
import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;

@Autonomous(name = "Encoder Move Forward Test", group = "test")
public class AutonomousEncoderMoveForwardTest extends AutonomousStandard {

    private Checkable motorEncoderCheckable;

    @Override
    public void initialize() {
        drivetrain = new AutonomousDrivetrain(robot, WheelBase.MECANUM);
        drivetrain.init();

        motorEncoderCheckable = new DrivetrainEncoderCheckableGroup(
                new MotorEncoderCheckable(Robot.Drivetrain.left_front, -1000, 10),
                new MotorEncoderCheckable(Robot.Drivetrain.right_front, 1000, 10),
                new MotorEncoderCheckable(Robot.Drivetrain.right_back, 1000, 10),
                new MotorEncoderCheckable(Robot.Drivetrain.left_front, -1000, 10)
        );

        TelemetryItem telemetryEncoderRecorder = new DrivetrainEncoderTelemetryGroup();
        TelemetryItem telemetryTestStatus = new CheckableTelemetryItem("Test status", motorEncoderCheckable);

        telemetryManager.add(telemetryTestStatus);
        telemetryManager.add(telemetryEncoderRecorder);

        telemetryManager.cycle();
    }

    @Override
    public void opModeLoop() {

        while (opModeIsActive() && !motorEncoderCheckable.check()) {
            telemetryManager.cycle();
            sleep(50);
        }

        if (opModeIsActive()) {
            sleep(1500);
            stop();
        }
    }
}
