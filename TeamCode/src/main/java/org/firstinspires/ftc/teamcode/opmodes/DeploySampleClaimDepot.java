package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.Lift;
import org.firstinspires.ftc.teamcode.systems.autonomous.LiftDeploy;
import org.firstinspires.ftc.teamcode.systems.autonomous.MineralDetector;
import org.firstinspires.ftc.teamcode.systems.autonomous.MineralSampler;
import org.firstinspires.ftc.teamcode.systems.drivetrain.AutonomousDrivetrain;
import org.firstinspires.ftc.teamcode.systems.drivetrain.WheelBase;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.Controller;
import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTask;
import org.firstinspires.ftc.teamcode.systems.util.Checkable;
@Disabled
@Autonomous(name = "Deploy sample claim depot", group = "autonomous")
public class DeploySampleClaimDepot extends AutonomousStandard {

    private MineralDetector mineralDetector;
    private BackgroundTask<String> mineralDetectorTask;

    private LiftDeploy liftDeploy;

    private MineralSampler mineralSampler;

    @Override
    protected void initialize() {
        drivetrain = new AutonomousDrivetrain(WheelBase.MECANUM);
        drivetrain.initialize();

        Robot.Servos.teamMarkerServo.setDirection(Servo.Direction.FORWARD);
        Robot.Servos.teamMarkerServo.setPosition(0);

        mineralDetector = new MineralDetector(hardwareMap, this);
        mineralDetectorTask = mineralDetector.getDetector();
        mineralDetectorTask.runInitialize();

        liftDeploy = new LiftDeploy(new Lift(), this.drivetrain, this, this.telemetryManager);

        mineralSampler = new MineralSampler(this, this.telemetryManager, this.drivetrain, false);

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
        /*Start the mineral detector*/
        mineralDetectorTask.start();
        telemetryManager.add(mineralDetectorTask.getStatusTelemetryItem());
        telemetryManager.add(mineralDetectorTask.getRunnableTelemetryItem());
        if (!opModeIsActive()) {
            return;
        }

        /*deploy*/
        liftDeploy.run();
        if (!opModeIsActive()) {
            return;
        }

        MineralDetector.Position samplingPosition = mineralDetector.getDeploymentGoldPosition2() != MineralDetector.Position.NOT_DETECTED ?
                mineralDetector.getDeploymentGoldPosition2() :
                mineralDetector.getDeploymentGoldPosition();

        /*sample*/
        mineralSampler.run(samplingPosition);

        /*claim the depot*/
        Checkable drivetrainCheckable = super.drivetrain.move(Controller.Direction.ROTATE_LEFT, Robot.convertDegreesToTicks(180), 0.5);
        while (opModeIsActive() && !drivetrainCheckable.check()) {
            super.telemetryManager.cycle();
            idle();
        }
        super.drivetrain.stop();

        drivetrainCheckable = super.drivetrain.move(Controller.Direction.S, Robot.ENCODER_TICKS_40_1, 0.5);
        while (opModeIsActive() && !drivetrainCheckable.check()) {
            super.telemetryManager.cycle();
            idle();
        }
        super.drivetrain.stop();

        Robot.Servos.teamMarkerServo.setPosition(1);
        sleep(500);
        Robot.Servos.teamMarkerServo.setPosition(0);
    }
}