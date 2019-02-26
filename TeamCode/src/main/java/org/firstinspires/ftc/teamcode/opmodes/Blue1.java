package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.systems.Lift;
import org.firstinspires.ftc.teamcode.systems.autonomous.LiftDeploy;
import org.firstinspires.ftc.teamcode.systems.autonomous.MineralDetector;
import org.firstinspires.ftc.teamcode.systems.autonomous.MineralSampler;
import org.firstinspires.ftc.teamcode.systems.drivetrain.AutonomousDrivetrain;
import org.firstinspires.ftc.teamcode.systems.drivetrain.WheelBase;
import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTask;

@Autonomous(name = "Deploy+sample", group = "autonomous")
public class Blue1 extends AutonomousStandard {

    private BackgroundTask<Integer> mineralDetector;
    private MineralSampler mineralSampler;
    private LiftDeploy liftDeploy;

    @Override
    protected void initialize() {
        drivetrain = new AutonomousDrivetrain(WheelBase.MECANUM);

        mineralDetector = new MineralDetector(hardwareMap, this).getDetector();
        mineralDetector.runInitialize();

        liftDeploy = new LiftDeploy(new Lift(), drivetrain, this);

        mineralSampler = new MineralSampler(drivetrain, this);
    }

    @Override
    protected void opModeLoop() {
        mineralDetector.start();
        telemetryManager.add(mineralDetector.getStatusTelemetryItem());
        telemetryManager.add(mineralDetector.getRunnableTelemetryItem());

        while (opModeIsActive() && (Integer) mineralDetector.getResult() != -1) {
            idle();
        }
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

//        BackgroundTask mineralSamplingBackgroundTask = mineralSampler.sample((Integer) mineralDetector.getResult());
//        mineralSamplingBackgroundTask.start();
//        while (opModeIsActive() && mineralSamplingBackgroundTask.isAlive()) {
//            idle();
//        }
//        if (opModeIsActive()) {
//            return;
//        }

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