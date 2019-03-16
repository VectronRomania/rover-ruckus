package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.telemetry.items.MotorEncoderTelemetryItem;

@Autonomous(name = "Robotic Arm Encoder Value Test", group = "test")
public class RoboticArmEncoderValueTest extends AutonomousStandard {
    @Override
    protected void initialize() {
        Robot.RoboticArm.arm_left.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        Robot.RoboticArm.arm_right.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        this.telemetryManager.add(new MotorEncoderTelemetryItem("left_arm", Robot.RoboticArm.arm_left.motor));
        this.telemetryManager.add(new MotorEncoderTelemetryItem("right_arm", Robot.RoboticArm.arm_left.motor));
//        this.telemetryManager.add(new MotorEncoderTelemetryItem("extender", Robot.RoboticArm.extender));
    }

    @Override
    protected void opModeLoop() {

    }
}
