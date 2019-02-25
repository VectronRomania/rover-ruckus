package org.firstinspires.ftc.teamcode.systems.drivetrain.controller.autonomous_controllers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.AutonomousController;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.Controller;
import org.firstinspires.ftc.teamcode.systems.util.checkables.DrivetrainCheckableGroup;
import org.firstinspires.ftc.teamcode.systems.util.checkables.MotorEncoderCheckable;

public final class MecanumController extends Controller implements AutonomousController {

    public MecanumController() {}

    @Override
    public void init() {
        Robot.Drivetrain.setDirection(DcMotorSimple.Direction.FORWARD);
        Robot.Drivetrain.setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Robot.Drivetrain.setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
        Robot.Drivetrain.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public DrivetrainCheckableGroup move(Direction direction, Integer ticks, Double power) {

        int a = 0, b = 0, c = 0, d = 0;

        switch (direction) {
            case N:
                a = Robot.Drivetrain.left_front.getCurrentPosition() - ticks;
                b = Robot.Drivetrain.right_front.getCurrentPosition() + ticks;
                c = Robot.Drivetrain.right_back.getCurrentPosition() + ticks;
                d = Robot.Drivetrain.left_back.getCurrentPosition() - ticks;
                break;
            case NE:
                a = Robot.Drivetrain.left_front.getCurrentPosition() - ticks;
                b = Robot.Drivetrain.right_front.getCurrentPosition();
                c = Robot.Drivetrain.right_back.getCurrentPosition() + ticks;
                d = Robot.Drivetrain.left_back.getCurrentPosition();
                break;
            case E:
                a = Robot.Drivetrain.left_front.getCurrentPosition() - ticks;
                b = Robot.Drivetrain.right_front.getCurrentPosition() - ticks;
                c = Robot.Drivetrain.right_back.getCurrentPosition() - ticks;
                d = Robot.Drivetrain.left_back.getCurrentPosition() - ticks;
                break;
            case SE:
                a = Robot.Drivetrain.left_front.getCurrentPosition();
                b = Robot.Drivetrain.right_front.getCurrentPosition() - ticks;
                c = Robot.Drivetrain.right_back.getCurrentPosition();
                d = Robot.Drivetrain.left_back.getCurrentPosition() + ticks;
                break;
            case S:
                a = Robot.Drivetrain.left_front.getCurrentPosition() + ticks;
                b = Robot.Drivetrain.right_front.getCurrentPosition() - ticks;
                c = Robot.Drivetrain.right_back.getCurrentPosition() - ticks;
                d = Robot.Drivetrain.left_back.getCurrentPosition() + ticks;
                break;
            case SW:
                a = Robot.Drivetrain.left_front.getCurrentPosition() + ticks;
                b = Robot.Drivetrain.right_front.getCurrentPosition();
                c = Robot.Drivetrain.right_back.getCurrentPosition() - ticks;
                d = Robot.Drivetrain.left_back.getCurrentPosition();
                break;
            case W:
                a = Robot.Drivetrain.left_front.getCurrentPosition() + ticks;
                b = Robot.Drivetrain.right_front.getCurrentPosition() + ticks;
                c = Robot.Drivetrain.right_back.getCurrentPosition() - ticks;
                d = Robot.Drivetrain.left_back.getCurrentPosition() - ticks;
                break;
            case NW:
                a = Robot.Drivetrain.left_front.getCurrentPosition() - ticks;
                b = Robot.Drivetrain.right_front.getCurrentPosition();
                c = Robot.Drivetrain.right_back.getCurrentPosition() + ticks;
                d = Robot.Drivetrain.left_back.getCurrentPosition();
                break;
            case ROTATE_LEFT:
                a = Robot.Drivetrain.left_front.getCurrentPosition() + ticks;
                b = Robot.Drivetrain.right_front.getCurrentPosition() + ticks;
                c = Robot.Drivetrain.right_back.getCurrentPosition() + ticks;
                d = Robot.Drivetrain.left_back.getCurrentPosition() + ticks;
                Robot.Drivetrain.setTargetPosition(ticks);
                break;
            case ROTATE_RIGHT:
                a = Robot.Drivetrain.left_front.getCurrentPosition() - ticks;
                b = Robot.Drivetrain.right_front.getCurrentPosition() - ticks;
                c = Robot.Drivetrain.right_back.getCurrentPosition() - ticks;
                d = Robot.Drivetrain.left_back.getCurrentPosition() - ticks;
                break;
        }

        Robot.Drivetrain.setTargetPosition(a, b, c, d);
        Robot.Drivetrain.setPower(power);

        return new DrivetrainCheckableGroup(
                new MotorEncoderCheckable(Robot.Drivetrain.left_front, a, 10),
                new MotorEncoderCheckable(Robot.Drivetrain.right_front, b, 10),
                new MotorEncoderCheckable(Robot.Drivetrain.right_back, c, 10),
                new MotorEncoderCheckable(Robot.Drivetrain.left_back, d, 10)
        );
    }
}
