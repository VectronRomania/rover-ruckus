package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryGroup;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;

@Autonomous(name = "Color Sensor Test", group = "test")
public class ColorSensorTest extends AutonomousStandard {
    @Override
    protected void initialize() {
//        Robot.Sensors.color_sensor

        final TelemetryGroup<Integer> sensorValues = new TelemetryGroup<Integer>("Sensor values") {}
                .add(new TelemetryItem<Integer>("Alpha") {
                    @Override
                    public void update() {
                        this.set(Robot.Sensors.color_sensor.alpha());
                    }
                })
                .add(new TelemetryItem<Integer>("ARGB") {
                    @Override
                    public void update() {
                        this.set(Robot.Sensors.color_sensor.argb());
                    }
                })
                .add(new TelemetryItem<Integer>("Red") {
                    @Override
                    public void update() {
                        this.set(Robot.Sensors.color_sensor.red());
                    }
                })
                .add(new TelemetryItem<Integer>("Green") {
                    @Override
                    public void update() {
                        this.set(Robot.Sensors.color_sensor.green());
                    }
                })
                .add(new TelemetryItem<Integer>("Blue") {
                    @Override
                    public void update() {
                        this.set(Robot.Sensors.color_sensor.blue());
                    }
                })
                .add(new TelemetryItem<Integer>("Distance") {
                    @Override
                    public void update() {
                        this.set(
                                Double.valueOf(Robot.Sensors.color_sensor.getDistance(DistanceUnit.MM)).intValue()
                        );
                    }
                });
        telemetryManager.add(sensorValues);
    }

    @Override
    protected void opModeLoop() {

    }
}
