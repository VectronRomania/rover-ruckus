package org.firstinspires.ftc.teamcode.systems.drivetrain.controller.autonomous_controllers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.AutonomousController;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.Controller;
import org.firstinspires.ftc.teamcode.systems.util.Checkable;
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
    public Checkable move(Direction direction, int ticks, double power) {

        Robot.Drivetrain.setRunMode(DcMotor.RunMode.RUN_TO_POSITION);

        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;

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
                a = Robot.Drivetrain.left_front.getCurrentPosition() + ticks;
                b = Robot.Drivetrain.right_front.getCurrentPosition() + ticks;
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
                a = Robot.Drivetrain.left_front.getCurrentPosition() - ticks;
                b = Robot.Drivetrain.right_front.getCurrentPosition() - ticks;
                c = Robot.Drivetrain.right_back.getCurrentPosition() + ticks;
                d = Robot.Drivetrain.left_back.getCurrentPosition() + ticks;
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
                new MotorEncoderCheckable(Robot.Drivetrain.left_front, a, 15),
                new MotorEncoderCheckable(Robot.Drivetrain.right_front, b, 15),
                new MotorEncoderCheckable(Robot.Drivetrain.right_back, c, 15),
                new MotorEncoderCheckable(Robot.Drivetrain.left_back, d, 15)
        );
    }

    @Override
    public void move(Direction direction, double power) {

        Robot.Drivetrain.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);

        switch (direction) {
            case N:
                Robot.Drivetrain.setPower(-power, power);
                break;
            case NE:
                Robot.Drivetrain.setPower(-power, 0, power, 0);
                break;
            case E:
                Robot.Drivetrain.setPower(power, -power, power, -power);
                break;
            case SE:
                Robot.Drivetrain.setPower(0, -power, 0, power);
                break;
            case S:
                Robot.Drivetrain.setPower(power, -power);
                break;
            case SW:
                Robot.Drivetrain.setPower(power, 0, -power, 0);
                break;
            case W:
                Robot.Drivetrain.setPower(power, power, -power, -power);
                break;
            case NW:
                Robot.Drivetrain.setPower(-power, 0, power, 0);
                break;
            case ROTATE_LEFT:
                Robot.Drivetrain.setPower(power);
                break;
            case ROTATE_RIGHT:
                Robot.Drivetrain.setPower(-power);
                break;
        }
    }
}
