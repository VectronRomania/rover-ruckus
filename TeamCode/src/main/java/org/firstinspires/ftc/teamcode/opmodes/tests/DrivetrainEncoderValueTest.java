package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.systems.drivetrain.HumanControlledDrivetrain;
import org.firstinspires.ftc.teamcode.systems.drivetrain.WheelBase;
import org.firstinspires.ftc.teamcode.systems.opmode.TeleOpStandard;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;
import org.firstinspires.ftc.teamcode.systems.telemetry.items.DrivetrainEncoderTelemetryGroup;

@Disabled
@TeleOp(name = "Drivetrain encoder value test", group = "test")
public class DrivetrainEncoderValueTest extends TeleOpStandard {

    @Override
    public void initialize() {
        drivetrain = new HumanControlledDrivetrain(WheelBase.MECANUM);
        drivetrain.init();

        telemetryManager.add(new DrivetrainEncoderTelemetryGroup());
    }

    @Override
    public void opModeLoop() {
        while (opModeIsActive()) {
            telemetryManager.cycle();
        }
    }
}
