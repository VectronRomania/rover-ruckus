package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.systems.autonomous.MineralDetector;
import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTask;

@Autonomous(name = "Mineral Detection Test", group = "test")
public class MineralDetectionTest extends AutonomousStandard {

    private BackgroundTask detector;

    @Override
    protected void initialize() {
        detector = new MineralDetector(hardwareMap, this).getDetector();

        detector.runInitialize();

        telemetryManager.add(detector.getStatusTelemetryItem());
        telemetryManager.add(detector.getRunnableTelemetryItem());
    }

    @Override
    protected void opModeLoop() {
        detector.start();

        while (opModeIsActive() && !detector.isFinished()) {
            idle();
            telemetryManager.cycle();
        }

        if (!opModeIsActive()) {
            detector.stopTask();
            return;
        }

        while (opModeIsActive()) {
            idle();
            telemetryManager.cycle();
        }
    }
}
