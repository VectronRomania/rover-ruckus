package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.Robot;
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
        Robot.Lift.setPower(0.15, -0.15);
        drivetrain = new AutonomousDrivetrain(WheelBase.MECANUM);
        drivetrain.init();

        mineralDetector = new MineralDetector(hardwareMap, this);
        mineralDetectorTask = mineralDetector.getDetector();
        mineralDetectorTask.runInitialize();

        liftDeploy = new LiftDeploy(new Lift(false), drivetrain, this);

        mineralSampler = new MineralSampler(drivetrain, this);

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
//        Start the mineral detector
        mineralDetectorTask.start();
        telemetryManager.add(mineralDetectorTask.getStatusTelemetryItem());
        telemetryManager.add(mineralDetectorTask.getRunnableTelemetryItem());


//        Start the deployment task and wait for it to finish
        BackgroundTask<Integer> dropDownBackgroundTask = liftDeploy.getDeployTask();
        int liftdeploy_index1 = telemetryManager.add(dropDownBackgroundTask.getStatusTelemetryItem());
        int liftdeploy_index2 = telemetryManager.add(dropDownBackgroundTask.getRunnableTelemetryItem());

        dropDownBackgroundTask.start();
        while(opModeIsActive() && !dropDownBackgroundTask.isFinished()) {
            telemetryManager.cycle();
//            if fully landed and ready to move, switch to deployed
            if (dropDownBackgroundTask.getResult() == 4) {
                mineralDetector.switchToDeployed();
            }
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
        int sampling_index1 = telemetryManager.add(mineralSamplingBackgroundTask.getStatusTelemetryItem());
        int sampling_index2 = telemetryManager.add(mineralSamplingBackgroundTask.getRunnableTelemetryItem());

        mineralSamplingBackgroundTask.start();
        while (opModeIsActive() && !mineralSamplingBackgroundTask.isFinished()) {
            telemetryManager.cycle();
            idle();
        }
        if (!opModeIsActive()) {
            return;
        }
        telemetryManager.remove(sampling_index1);
        telemetryManager.remove(sampling_index2);
    }
}