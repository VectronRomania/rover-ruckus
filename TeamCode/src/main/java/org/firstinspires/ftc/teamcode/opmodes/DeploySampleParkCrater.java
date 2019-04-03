package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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
import org.firstinspires.ftc.teamcode.systems.util.Checkable;

@Autonomous(name = "Deploy sample park crater", group = "autonomous")
public class DeploySampleParkCrater extends AutonomousStandard {

    private LiftDeploy liftDeploy;

    private MineralSampler mineralSampler;

    @Override
    protected void initialize() {
        drivetrain = new AutonomousDrivetrain(WheelBase.MECANUM);
        drivetrain.initialize();

        Robot.Servos.teamMarkerServo.setDirection(Servo.Direction.FORWARD);
        Robot.Servos.teamMarkerServo.setPosition(0);

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
        /*deploy*/
        liftDeploy.run();
        if (!opModeIsActive()) {
            return;
        }

        MineralDetector.Position samplingPosition = MineralDetector.Position.NOT_DETECTED;

        /*sample*/
        mineralSampler.run(samplingPosition);

        /*park*/
        Checkable drivetrainCheckable = super.drivetrain.move(Controller.Direction.N, Robot.ENCODER_TICKS_40_1, 0.75);
        while (opModeIsActive() && !drivetrainCheckable.check()) {
            telemetryManager.cycle();
            idle();
        }
        super.drivetrain.stop();
        Robot.RoboticArm.arm_left.setPower(-0.5);
        Robot.RoboticArm.arm_right.setPower(0.5);
        sleep(1500);
        Robot.RoboticArm.arm_left.setPower(0);
        Robot.RoboticArm.arm_right.setPower(0);
    }
}