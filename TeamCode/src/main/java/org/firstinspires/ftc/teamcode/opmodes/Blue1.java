package org.firstinspires.ftc.teamcode.opmodes;

import org.firstinspires.ftc.teamcode.systems.Lift;
import org.firstinspires.ftc.teamcode.systems.autonomous.LiftDeploy;
import org.firstinspires.ftc.teamcode.systems.autonomous.MineralDetectionBackgroundRunnable;
import org.firstinspires.ftc.teamcode.systems.autonomous.MineralSampler;
import org.firstinspires.ftc.teamcode.systems.drivetrain.AutonomousDrivetrain;
import org.firstinspires.ftc.teamcode.systems.drivetrain.WheelBase;
import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTask;

public class Blue1 extends AutonomousStandard {

    private BackgroundTask mineralDetector;
    private MineralSampler mineralSampler;
    private LiftDeploy liftDeploy;

    @Override
    protected void initialize() {
        drivetrain = new AutonomousDrivetrain(WheelBase.MECANUM);
        mineralDetector = new BackgroundTask<>(
                new MineralDetectionBackgroundRunnable(hardwareMap),
                "detector",
                BackgroundTask.Type.LOOP
        );
        mineralDetector.runInitialize();
        liftDeploy = new LiftDeploy(new Lift(), drivetrain);
        mineralSampler = new MineralSampler(drivetrain);
    }

    @Override
    protected void opModeLoop() {
        mineralDetector.start();
        telemetryManager.add(mineralDetector.getStatusTelemetryItem());
        telemetryManager.add(mineralDetector.getRunnableTelemetryItem());

        BackgroundTask dropDownBackgroundTask = liftDeploy.getDeployTask();
        int liftdeploy_index1 = telemetryManager.add(dropDownBackgroundTask.getStatusTelemetryItem());
        int liftdeploy_index2 = telemetryManager.add(dropDownBackgroundTask.getRunnableTelemetryItem());

        dropDownBackgroundTask.start();
        while(opModeIsActive() && dropDownBackgroundTask.isAlive()) {
            idle();
        }
        if (opModeIsActive()) {
            stop();
        }
        telemetryManager.remove(liftdeploy_index1);
        telemetryManager.remove(liftdeploy_index2);

        BackgroundTask mineralSamplingBackgroundTask = mineralSampler.sample((Integer) mineralDetector.getResult());
        mineralSamplingBackgroundTask.start();
        while (opModeIsActive() && mineralSamplingBackgroundTask.isAlive()) {
            idle();
        }
        if (opModeIsActive()) {
            return;
        }
    }
}
