package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;

@Autonomous(name = "Color sensor gold tetection test", group = "test")
public class ColorSensorGoldDetectionTest extends AutonomousStandard {

    @Override
    protected void initialize() {
        telemetryManager.add(new TelemetryItem<Boolean>("Gold detected") {
            @Override
            public void update() {
                int r = Robot.Sensors.color_sensor.red();
                int g = Robot.Sensors.color_sensor.green();
                int b = Robot.Sensors.color_sensor.blue();
                this.set(
                        0.75 * (g + b) < r
                );
            }
        });
    }

    @Override
    protected void opModeLoop() {

    }
}
