package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.Collector;
import org.firstinspires.ftc.teamcode.systems.Extender;
import org.firstinspires.ftc.teamcode.systems.Lift;
import org.firstinspires.ftc.teamcode.systems.Scoop;
import org.firstinspires.ftc.teamcode.systems.drivetrain.HumanControlledDrivetrain;
import org.firstinspires.ftc.teamcode.systems.drivetrain.WheelBase;
import org.firstinspires.ftc.teamcode.systems.opmode.TeleOpStandard;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryGroup;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;
import org.firstinspires.ftc.teamcode.systems.telemetry.items.RevImuOrientationTelemetryGroup;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTask;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTaskRunnable;

@TeleOp(name="TeleOpMode", group="teleop")
public class TeleOpMode extends TeleOpStandard {

    private Lift lift;
    private Extender extender;
    private Collector collector;
//    private Scoop scoop;

    private BackgroundTask<Double> heightChecking;
    private BackgroundTask<String> imuChecking;

    @Override
    public void initialize() {
        drivetrain = new HumanControlledDrivetrain(WheelBase.MECANUM);
        lift = new Lift();
        extender = new Extender();
        collector = new Collector();
//        scoop = new Scoop();

//        telemetryManager.add(scoop.getTelemetryItem());

        heightChecking = new BackgroundTask<>(new BackgroundTaskRunnable<Double>() {
            @Override
            protected void initialize() {
                this.telemetryItem = new TelemetryGroup<Double>("height readings") {}
                        .add(new TelemetryItem<Double>("Left") {
                            @Override
                            public void update() {
                                super.set(Robot.Lift.distance_left.getDistance(DistanceUnit.MM));
                            }
                        })
                        .add(new TelemetryItem<Double>("Right") {
                            @Override
                            public void update() {
                                super.set(Robot.Lift.distance_right.getDistance(DistanceUnit.MM));
                            }
                        });
            }

            @Override
            protected void shutdown() {}

            @Override
            public void run() {
                this.telemetryItem.update();
            }
        }, "Height checking", BackgroundTask.Type.LOOP, this);
        heightChecking.start();

        imuChecking = new BackgroundTask<>(new BackgroundTaskRunnable<String>() {
            @Override
            protected void initialize() {
                this.telemetryItem = new RevImuOrientationTelemetryGroup("imu readings");
            }

            @Override
            protected void shutdown() {}

            @Override
            public void run() {
//                this.telemetryItem.update();
            }
        }, "IMU Checking", BackgroundTask.Type.LOOP, this);
        imuChecking.start();

        telemetryManager.add(heightChecking.getRunnableTelemetryItem());
        telemetryManager.add(imuChecking.getRunnableTelemetryItem());
    }

    @Override
    public void opModeLoop() {
        drivetrain.drive(gamepad1);
        lift.manual(gamepad1);
        extender.manual(gamepad2);
        collector.manual(gamepad2);
//        scoop.manual(gamepad1);
//        if (!opModeIsActive()) {
//            heightChecking.stopTask();
//            imuChecking.stopTask();
//        }
    }
}