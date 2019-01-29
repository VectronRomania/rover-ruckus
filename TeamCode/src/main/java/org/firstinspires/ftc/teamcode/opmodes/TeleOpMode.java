package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.systems.drivetrain.configs.WheelBase;
import org.firstinspires.ftc.teamcode.systems.drivetrain.types.HumanControlledDrivetrain;
import org.firstinspires.ftc.teamcode.systems.opmode.OpModeStandard;

@TeleOp(name="TeleOpMode", group="teleop")
public class TeleOpMode extends OpModeStandard {

    @Override
    public void initialize() {
        drivetrain = new HumanControlledDrivetrain(robot, WheelBase.MECANUM);
    }

    @Override
    public void opModeLoop() {
        drivetrain.drive(gamepad1);

    }
}