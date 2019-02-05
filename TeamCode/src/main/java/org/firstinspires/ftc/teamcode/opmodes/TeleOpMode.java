package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.systems.drivetrain.WheelBase;
import org.firstinspires.ftc.teamcode.systems.drivetrain.HumanControlledDrivetrain;
import org.firstinspires.ftc.teamcode.systems.opmode.TeleOpStandard;

@TeleOp(name="TeleOpMode", group="teleop")
public class TeleOpMode extends TeleOpStandard {

    @Override
    public void initialize() {
        drivetrain = new HumanControlledDrivetrain(robot, WheelBase.MECANUM);
    }

    @Override
    public void opModeLoop() {
        drivetrain.drive(gamepad1);
    }
}