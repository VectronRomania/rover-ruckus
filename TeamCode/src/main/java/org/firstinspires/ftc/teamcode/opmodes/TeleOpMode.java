package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.Collector;
import org.firstinspires.ftc.teamcode.systems.Extender;
import org.firstinspires.ftc.teamcode.systems.Lift;
import org.firstinspires.ftc.teamcode.systems.Scoop;
import org.firstinspires.ftc.teamcode.systems.drivetrain.WheelBase;
import org.firstinspires.ftc.teamcode.systems.drivetrain.HumanControlledDrivetrain;
import org.firstinspires.ftc.teamcode.systems.opmode.TeleOpStandard;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryGroup;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;
import org.firstinspires.ftc.teamcode.systems.telemetry.items.RevImuOrientationTelemetryGroup;

@TeleOp(name="TeleOpMode", group="teleop")
public class TeleOpMode extends TeleOpStandard {

    Lift lift;
    Extender extender;
    Collector collector;
    Scoop scoop;

    @Override
    public void initialize() {
        drivetrain = new HumanControlledDrivetrain(WheelBase.MECANUM);
        lift = new Lift();
        extender = new Extender();
//        collector = new Collector();
//        scoop = new Scoop();

        TelemetryGroup height = new TelemetryGroup("Distance"){};
        height.add(new TelemetryItem<Double>("Left") {
            @Override
            public void update() {
                super.set(Robot.Lift.distance_left.getDistance(DistanceUnit.MM));
            }
        });
        height.add(new TelemetryItem<Double>("Right") {
            @Override
            public void update() {
                super.set(Robot.Lift.distance_right.getDistance(DistanceUnit.MM));
            }
        });
        telemetryManager.add(height);

        telemetryManager.add(new RevImuOrientationTelemetryGroup("IMU Readings"));
    }

    @Override
    public void opModeLoop() {
        drivetrain.drive(gamepad1);
        lift.manual(gamepad1);
        extender.manual(gamepad2);
//        collector.manual(gamepad2);
    }
}