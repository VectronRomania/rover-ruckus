package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;

@Autonomous(name = "Lift Distance Sensor Test", group = "test")
public class LiftDistanceSensorTest extends AutonomousStandard {
    @Override
    protected void initialize() {
        telemetryManager.add(new TelemetryItem<Double>("left") {
            @Override
            public void update() {
                this.set(Robot.Lift.distance_left.getDistance(DistanceUnit.MM));
            }
        });
        telemetryManager.add(new TelemetryItem<Double>("right") {
            @Override
            public void update() {
                this.set(Robot.Lift.distance_right.getDistance(DistanceUnit.MM));
            }
        });
    }

    @Override
    protected void opModeLoop() {

    }
}
