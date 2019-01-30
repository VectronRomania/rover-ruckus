package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.autonomous.BackgroundChecker;
import org.firstinspires.ftc.teamcode.systems.autonomous.CheckableGroup;
import org.firstinspires.ftc.teamcode.systems.autonomous.CheckableLogicalOperation;
import org.firstinspires.ftc.teamcode.systems.drivetrain.autonomous_checkables.MotorEncoderCheckable;
import org.firstinspires.ftc.teamcode.systems.drivetrain.configs.WheelBase;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.Controller;
import org.firstinspires.ftc.teamcode.systems.drivetrain.types.AutonomousDrivetrain;
import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;

@Autonomous(name = "Encoder Move Forward Test", group = "test")
public class AutonomousEncoderMoveForwardTest extends AutonomousStandard {

//    private volatile CheckableGroup checkable;

    @Override
    public void initialize() {
        drivetrain = new AutonomousDrivetrain(robot, WheelBase.MECANUM);
        drivetrain.getController().init();
    }

    @Override
    public void opModeLoop() {

        drivetrain.getController().move(
                Controller.Direction.N,
                1000,
                0.25
        );

//        checkable = MotorEncoderCheckable.newDrivetrainEncoderCheckableGroup(
//                Robot.Drivetrain.left_front.getTargetPosition(),
//                Robot.Drivetrain.right_front.getTargetPosition(),
//                Robot.Drivetrain.right_back.getTargetPosition(),
//                Robot.Drivetrain.left_back.getTargetPosition(),
//                20, CheckableLogicalOperation.AND
//        );

        final MotorEncoderCheckable a = new MotorEncoderCheckable(Robot.Drivetrain.left_front, Robot.Drivetrain.left_front.getTargetPosition(), 20, CheckableLogicalOperation.AND);
        final MotorEncoderCheckable b = new MotorEncoderCheckable(Robot.Drivetrain.right_front, Robot.Drivetrain.right_front.getTargetPosition(), 20, CheckableLogicalOperation.AND);
        final MotorEncoderCheckable c = new MotorEncoderCheckable(Robot.Drivetrain.right_back, Robot.Drivetrain.right_back.getTargetPosition(), 20, CheckableLogicalOperation.AND);
        final MotorEncoderCheckable d = new MotorEncoderCheckable(Robot.Drivetrain.left_back, Robot.Drivetrain.left_back.getTargetPosition(), 20, CheckableLogicalOperation.AND);

        Integer index = telemetryManager.add(new TelemetryItem<Boolean>("Test status") {
            @Override
            public void update() {
                Boolean x = false;
                x = BackgroundChecker.evaluateCheckable(a, x);
                x = BackgroundChecker.evaluateCheckable(b, x);
                x = BackgroundChecker.evaluateCheckable(c, x);
                x = BackgroundChecker.evaluateCheckable(d, x);
                this.set(x);
            }
        });

        while (true) {
            Boolean x = false;
            x = BackgroundChecker.evaluateCheckable(a, x);
            x = BackgroundChecker.evaluateCheckable(b, x);
            x = BackgroundChecker.evaluateCheckable(c, x);
            x = BackgroundChecker.evaluateCheckable(d, x);
            if (x) {
                break;
            }
            telemetryManager.cycle();
            sleep(100);
        }
        sleep(200);
        stop();
    }
}
