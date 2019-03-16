package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.RoboticArm;
import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.telemetry.items.MotorEncoderTelemetryItem;

@TeleOp(name = "Robotic Arm Test", group = "test")
public class RoboticArmTest extends AutonomousStandard {

    private RoboticArm roboticArm;

    @Override
    protected void initialize() {
        roboticArm = new RoboticArm(true, 0.3);

        this.telemetryManager.add(new MotorEncoderTelemetryItem("left_arm", Robot.RoboticArm.arm_left));
        this.telemetryManager.add(new MotorEncoderTelemetryItem("right_arm", Robot.RoboticArm.arm_left));
//        this.telemetryManager.add(new MotorEncoderTelemetryItem("extender", Robot.RoboticArm.extender));
    }

    @Override
    protected void opModeLoop() {
        roboticArm.manual(gamepad1);
    }
}
