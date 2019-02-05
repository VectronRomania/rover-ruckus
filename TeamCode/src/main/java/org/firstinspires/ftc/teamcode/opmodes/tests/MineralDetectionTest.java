package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.systems.util.background_runnables.MineralDetectionBackgroundRunnable;
import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTask;

@Autonomous(name = "Mineral Detection Test", group = "test")
public class MineralDetectionTest extends AutonomousStandard {

    private BackgroundTask detector;

    @Override
    protected void initialize() {
        detector = new BackgroundTask(
                new MineralDetectionBackgroundRunnable(hardwareMap),
                "Mineral detector",
                BackgroundTask.Type.LOOP
        );

        detector.runInitialize();

        telemetryManager.add(detector.getStatusTelemetryItem());
        telemetryManager.add(detector.getRunnableTelemetryItem());
    }

    @Override
    protected void opModeLoop() {
        detector.start();

        while (opModeIsActive() && detector.isAlive()) {
            sleep(10);
            telemetryManager.cycle();
        }

        if (!opModeIsActive()) {
            detector.stopTask();
            stop();
        }

        while (opModeIsActive()) {
            sleep(10);
            telemetryManager.cycle();
        }
    }
}
