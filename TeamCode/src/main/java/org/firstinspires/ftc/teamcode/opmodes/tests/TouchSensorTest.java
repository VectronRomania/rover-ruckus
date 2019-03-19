package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;

@Autonomous(name = "Touch Sensor Test", group = "test")
public class TouchSensorTest extends AutonomousStandard {
    @Override
    protected void initialize() {
        telemetryManager.add(new TelemetryItem<Boolean>("Touch sensor pressed") {
            @Override
            public void update() {
                this.set(Robot.Sensors.touch_sensor.isPressed());
            }
        });

    }

    @Override
    protected void opModeLoop() {

    }
}
