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

    private BackgroundTask<Integer> mineralDetector;
    private MineralSampler mineralSampler;
    private LiftDeploy liftDeploy;

    @Override
    protected void initialize() {
        drivetrain = new AutonomousDrivetrain(WheelBase.MECANUM);
        drivetrain.init();

        mineralDetector = new MineralDetector(hardwareMap, this).getDetector();
        mineralDetector.runInitialize();

        liftDeploy = new LiftDeploy(new Lift(), drivetrain, this);

        mineralSampler = new MineralSampler(drivetrain, this);

        telemetryManager.add(new DrivetrainEncoderTelemetryGroup());
    }

    @Override
    protected void opModeLoop() {
        mineralDetector.start();
        telemetryManager.add(mineralDetector.getStatusTelemetryItem());
        telemetryManager.add(mineralDetector.getRunnableTelemetryItem());

//        BackgroundTask dropDownBackgroundTask = liftDeploy.getDeployTask();
//        int liftdeploy_index1 = telemetryManager.add(dropDownBackgroundTask.getStatusTelemetryItem());
//        int liftdeploy_index2 = telemetryManager.add(dropDownBackgroundTask.getRunnableTelemetryItem());
//
//        dropDownBackgroundTask.start();
//        while(opModeIsActive() && dropDownBackgroundTask.isAlive()) {
//            idle();
//        }
//        if (opModeIsActive()) {
//            stop();
//        }
//        telemetryManager.remove(liftdeploy_index1);
//        telemetryManager.remove(liftdeploy_index2);

        while (opModeIsActive() && !mineralDetector.isFinished()) {
            telemetryManager.cycle();
            idle();
        }

        BackgroundTask mineralSamplingBackgroundTask = mineralSampler.sample(mineralDetector.getResult());
        mineralSamplingBackgroundTask.start();
        telemetryManager.add(mineralSamplingBackgroundTask.getStatusTelemetryItem());
        telemetryManager.add(mineralSamplingBackgroundTask.getRunnableTelemetryItem());
        while (opModeIsActive() && mineralSamplingBackgroundTask.isFinished()) {
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