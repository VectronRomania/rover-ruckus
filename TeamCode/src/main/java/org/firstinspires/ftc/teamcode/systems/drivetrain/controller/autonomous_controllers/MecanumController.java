package org.firstinspires.ftc.teamcode.systems.drivetrain.controller.autonomous_controllers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.AutonomousController;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.Controller;

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
    public void move(Direction direction, Integer ticks, Double power) {
        switch (direction) {
            case N:
                Robot.Drivetrain.setTargetPosition(
                        Robot.Drivetrain.left_front.getCurrentPosition()    - ticks, Robot.Drivetrain.right_front.getCurrentPosition()  + ticks,
                        Robot.Drivetrain.left_back.getCurrentPosition()     - ticks, Robot.Drivetrain.right_back.getCurrentPosition()   + ticks
                );
                break;
            case NE:
                Robot.Drivetrain.setTargetPosition(
                        Robot.Drivetrain.left_front.getCurrentPosition()    - ticks, Robot.Drivetrain.right_front.getCurrentPosition(),
                        Robot.Drivetrain.left_back.getCurrentPosition(),                Robot.Drivetrain.right_back.getCurrentPosition()   + ticks
                );
                break;
            case E:
                Robot.Drivetrain.setTargetPosition(
                        Robot.Drivetrain.left_front.getCurrentPosition()    - ticks, Robot.Drivetrain.right_front.getCurrentPosition()  - ticks,
                        Robot.Drivetrain.left_back.getCurrentPosition()     + ticks, Robot.Drivetrain.right_back.getCurrentPosition()   + ticks
                );
                break;
            case SE:
                Robot.Drivetrain.setTargetPosition(
                        Robot.Drivetrain.left_front.getCurrentPosition(),               Robot.Drivetrain.right_front.getCurrentPosition()  - ticks,
                        Robot.Drivetrain.left_back.getCurrentPosition()     + ticks, Robot.Drivetrain.right_back.getCurrentPosition()
                );
                break;
            case S:
                Robot.Drivetrain.setTargetPosition(
                        Robot.Drivetrain.left_front.getCurrentPosition()    + ticks, Robot.Drivetrain.right_front.getCurrentPosition()  - ticks,
                        Robot.Drivetrain.left_back.getCurrentPosition()     + ticks, Robot.Drivetrain.right_back.getCurrentPosition()   - ticks
                );
                break;
            case SW:
                Robot.Drivetrain.setTargetPosition(
                        Robot.Drivetrain.left_front.getCurrentPosition()    + ticks, Robot.Drivetrain.right_front.getCurrentPosition(),
                        Robot.Drivetrain.left_back.getCurrentPosition(),                Robot.Drivetrain.right_back.getCurrentPosition()   - ticks
                );
                break;
            case W:
                Robot.Drivetrain.setTargetPosition(
                        Robot.Drivetrain.left_front.getCurrentPosition()    + ticks, Robot.Drivetrain.right_front.getCurrentPosition()  + ticks,
                        Robot.Drivetrain.left_back.getCurrentPosition()     - ticks, Robot.Drivetrain.right_back.getCurrentPosition()   - ticks
                );
                break;
            case NW:
                Robot.Drivetrain.setTargetPosition(
                        Robot.Drivetrain.left_front.getCurrentPosition()    - ticks, Robot.Drivetrain.right_front.getCurrentPosition(),
                        Robot.Drivetrain.left_back.getCurrentPosition(),                Robot.Drivetrain.right_back.getCurrentPosition()   + ticks
                );
                break;
            case ROTATE_LEFT:
                Robot.Drivetrain.setTargetPosition(ticks);
                break;
            case ROTATE_RIGHT:
                Robot.Drivetrain.setTargetPosition(-ticks);
                break;
        }
        Robot.Drivetrain.setPower(power);
    }
}
