package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryGroup;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;

@Autonomous(name = "Distance Sensors Test", group = "test")
public class DistanceSensorsOpModes extends AutonomousStandard {
    @Override
    protected void initialize() {
        telemetryManager.add(new TelemetryGroup<Double>("Sensor values in MM") {
        }
                .add(new TelemetryItem<Double>("Left") {
                    @Override
                    public void update() {
                        this.set(Robot.Lift.distance_left.getDistance(DistanceUnit.MM));
                    }
                })
                .add(new TelemetryItem<Double>("Right") {
                    @Override
                    public void update() {
                        this.set(Robot.Lift.distance_right.getDistance(DistanceUnit.MM));
                    }
                })
        );
    }

    @Override
    protected void opModeLoop() {

    }
}
