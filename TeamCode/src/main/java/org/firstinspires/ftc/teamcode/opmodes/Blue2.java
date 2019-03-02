package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.Extender;
import org.firstinspires.ftc.teamcode.systems.Lift;
import org.firstinspires.ftc.teamcode.systems.autonomous.ClaimerParker;
import org.firstinspires.ftc.teamcode.systems.autonomous.LiftDeploy;
import org.firstinspires.ftc.teamcode.systems.autonomous.MineralDetector;
import org.firstinspires.ftc.teamcode.systems.autonomous.MineralSampler;
import org.firstinspires.ftc.teamcode.systems.drivetrain.AutonomousDrivetrain;
import org.firstinspires.ftc.teamcode.systems.drivetrain.WheelBase;
import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTask;

@Autonomous(name = "Deploy+park", group = "autonomous")
public class Blue2 extends AutonomousStandard {

    private Extender extender;

    private MineralDetector mineralDetector;
    private BackgroundTask<String> mineralDetectorTask;

    private MineralSampler mineralSampler;

    private LiftDeploy liftDeploy;

    private ClaimerParker claimerParker;

    @Override
    protected void initialize() {
        Robot.Lift.setPower(0.1, -0.1);
        drivetrain = new AutonomousDrivetrain(WheelBase.MECANUM);
        drivetrain.init();

        extender = new Extender();

        mineralDetector = new MineralDetector(hardwareMap, this);
        mineralDetectorTask = mineralDetector.getDetector();
        mineralDetectorTask.runInitialize();

        liftDeploy = new LiftDeploy(new Lift(false), drivetrain, this);

        mineralSampler = new MineralSampler(drivetrain, this);

        claimerParker = new ClaimerParker(drivetrain, this, extender);

        telemetry.addData("imu", "calibrating");
        telemetry.update();
        while (opModeIsActive() &&
                Robot.Sensors.left_imu.sensor.isGyroCalibrated() &&
                Robot.Sensors.right_imu.sensor.isGyroCalibrated()) {
            idle();
        }
    }

    @Override
    protected void opModeLoop() {

        telemetryManager.add(mineralDetectorTask.getStatusTelemetryItem());
        telemetryManager.add(mineralDetectorTask.getRunnableTelemetryItem());

//        Start the deployment task and wait for it to finish
        BackgroundTask<Integer> dropDownBackgroundTask = liftDeploy.getDeployTask();
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
        mineralDetectorTask.start();

////        Create the sampling task, start it and wait for it to finish
//        BackgroundTask mineralSamplingBackgroundTask = mineralSampler.sample(mineralDetector);
//        int sampling_index1 = telemetryManager.add(mineralSamplingBackgroundTask.getStatusTelemetryItem());
//        int sampling_index2 = telemetryManager.add(mineralSamplingBackgroundTask.getRunnableTelemetryItem());
//
//        mineralSamplingBackgroundTask.start();
//        while (opModeIsActive() && !mineralSamplingBackgroundTask.isFinished()) {
//            telemetryManager.cycle();
//            idle();
//        }
//        if (!opModeIsActive()) {
//            return;
//        }
//        telemetryManager.remove(sampling_index1);
//        telemetryManager.remove(sampling_index2);

//        park

        BackgroundTask<Integer> claimingParkingTask = claimerParker.getBackGroundTask(false);
        claimingParkingTask.start();
        telemetryManager.add(claimingParkingTask.getStatusTelemetryItem());
        telemetryManager.add(claimingParkingTask.getRunnableTelemetryItem());
        while (!claimingParkingTask.isFinished() && opModeIsActive()) {
            telemetryManager.cycle();
            idle();
        }
    }
}