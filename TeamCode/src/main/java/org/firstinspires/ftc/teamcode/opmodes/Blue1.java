package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.systems.Lift;
import org.firstinspires.ftc.teamcode.systems.autonomous.LiftDeploy;
import org.firstinspires.ftc.teamcode.systems.autonomous.MineralDetector;
import org.firstinspires.ftc.teamcode.systems.autonomous.MineralSampler;
import org.firstinspires.ftc.teamcode.systems.drivetrain.AutonomousDrivetrain;
import org.firstinspires.ftc.teamcode.systems.drivetrain.WheelBase;
import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.telemetry.items.DrivetrainEncoderTelemetryGroup;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTask;

@Autonomous(name = "Deploy+sample", group = "autonomous")
public class Blue1 extends AutonomousStandard {

    private MineralDetector mineralDetector;
    private BackgroundTask<String> mineralDetectorTask;

    private MineralSampler mineralSampler;

    private LiftDeploy liftDeploy;

    @Override
    protected void initialize() {
        drivetrain = new AutonomousDrivetrain(WheelBase.MECANUM);
        drivetrain.init();

        mineralDetector = new MineralDetector(hardwareMap, this);
        mineralDetectorTask = mineralDetector.getDetector();
        mineralDetectorTask.runInitialize();

        liftDeploy = new LiftDeploy(new Lift(), drivetrain, this);

        mineralSampler = new MineralSampler(drivetrain, this);
    }

    @Override
    protected void opModeLoop() {
//        Start the mineral detector
        mineralDetectorTask.start();
        telemetryManager.add(mineralDetectorTask.getStatusTelemetryItem());
        telemetryManager.add(mineralDetectorTask.getRunnableTelemetryItem());

//        Start the deployment task and wait for it to finish
        BackgroundTask dropDownBackgroundTask = liftDeploy.getDeployTask();
        int liftdeploy_index1 = telemetryManager.add(dropDownBackgroundTask.getStatusTelemetryItem());
        int liftdeploy_index2 = telemetryManager.add(dropDownBackgroundTask.getRunnableTelemetryItem());

        dropDownBackgroundTask.start();
        while(opModeIsActive() && !dropDownBackgroundTask.isFinished()) {
            telemetryManager.cycle();
            idle();
        }
        if (!opModeIsActive()) {
            return;
        }
        telemetryManager.remove(liftdeploy_index1);
        telemetryManager.remove(liftdeploy_index2);

//        Switch the detector to deployed detection
        mineralDetector.switchToDeployed();

//        Create the sampling task, start it and wait for it to finish
        BackgroundTask mineralSamplingBackgroundTask = mineralSampler.sample(mineralDetector);
        mineralSamplingBackgroundTask.start();

        telemetryManager.add(mineralSamplingBackgroundTask.getStatusTelemetryItem());
        telemetryManager.add(mineralSamplingBackgroundTask.getRunnableTelemetryItem());

        while (opModeIsActive() && !mineralSamplingBackgroundTask.isFinished()) {
            telemetryManager.cycle();
            idle();
        }

//        drivetrain.move(Controller.Direction.N, 500, 0.5);
//        drivetrain.move(Controller.Direction.ROTATE_LEFT, 340, 0.5);
//        drivetrain.move(Controller.Direction.N, 3000, 0.5);
//        drivetrain.move(Controller.Direction.ROTATE_LEFT, 420, 0.5);
//        drivetrain.move(Controller.Direction.N, 1000, 0.5);
//
//        // drop team marker
//
//        drivetrain.move(Controller.Direction.ROTATE_LEFT, 1444, 0.5);
//        drivetrain.move(Controller.Direction.N, 6000, 0.5);
    }
}