package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.opmode.TeleOpStandard;
import org.firstinspires.ftc.teamcode.systems.telemetry.items.MotorEncoderTelemetryItem;

@TeleOp(name = "Robotic Arm Native Test", group = "test")
public class RoboticArmNativeTest extends TeleOpStandard {

    @Override
    public void initialize() {

        Robot.RoboticArm.arm_left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Robot.RoboticArm.arm_right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Robot.RoboticArm.arm_left.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void opModeLoop() {

        if (gamepad1.left_trigger > 0) {
            Robot.RoboticArm.arm_left.setPower(-gamepad1.left_trigger);
            Robot.RoboticArm.arm_right.setPower(-gamepad1.left_trigger);
        } else if (gamepad1.right_trigger > 0) {
            Robot.RoboticArm.arm_left.setPower(gamepad1.right_trigger);
            Robot.RoboticArm.arm_right.setPower(gamepad1.right_trigger);
        } else {
            Robot.RoboticArm.arm_left.setPower(0);
            Robot.RoboticArm.arm_right.setPower(0);
        }
        telemetryManager.cycle();
        idle();
    }
}
