package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.systems.Collector;
import org.firstinspires.ftc.teamcode.systems.RoboticArm;
import org.firstinspires.ftc.teamcode.systems.Lift;
import org.firstinspires.ftc.teamcode.systems.drivetrain.HumanControlledDrivetrain;
import org.firstinspires.ftc.teamcode.systems.drivetrain.WheelBase;
import org.firstinspires.ftc.teamcode.systems.opmode.TeleOpStandard;

@TeleOp(name="TeleOpMode", group="teleop")
public class TeleOpMode extends TeleOpStandard {

    private Lift lift;
    private RoboticArm roboticArm;
//    private Collector collector;

    @Override
    public void initialize() {
        super.drivetrain = new HumanControlledDrivetrain(WheelBase.MECANUM);
        lift = new Lift();
        roboticArm = new RoboticArm();
//        collector = new Collector();
    }
    @Override
    public void opModeLoop() {
        super.drivetrain.drive(gamepad1);
        lift.manual(gamepad1);
        roboticArm.manual(gamepad2);
//        collector.manual(gamepad2);
    }
}