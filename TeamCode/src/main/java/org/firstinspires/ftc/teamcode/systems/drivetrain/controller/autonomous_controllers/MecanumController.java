package org.firstinspires.ftc.teamcode.systems.drivetrain.controller.autonomous_controllers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.AutonomousController;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.Controller;

public final class MecanumController extends Controller implements AutonomousController {

    public MecanumController(Robot robot) {
        super(robot);
    }

    @Override
    public void init() {
        super.setDirection(DcMotorSimple.Direction.FORWARD);
        super.setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        super.setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
        super.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void move(Direction direction, Integer ticks, Double power) {
        switch (direction) {
            case N:
                super.setTargetPosition(
                        Robot.Drivetrain.left_front.getCurrentPosition()    - ticks, Robot.Drivetrain.right_front.getCurrentPosition()  + ticks,
                        Robot.Drivetrain.left_back.getCurrentPosition()     - ticks, Robot.Drivetrain.right_back.getCurrentPosition()   + ticks
                );
                break;
            case NE:
                super.setTargetPosition(
                        Robot.Drivetrain.left_front.getCurrentPosition()    - ticks, Robot.Drivetrain.right_front.getCurrentPosition(),
                        Robot.Drivetrain.left_back.getCurrentPosition(),                Robot.Drivetrain.right_back.getCurrentPosition()   + ticks
                );
                break;
            case E:
                super.setTargetPosition(
                        Robot.Drivetrain.left_front.getCurrentPosition()    - ticks, Robot.Drivetrain.right_front.getCurrentPosition()  - ticks,
                        Robot.Drivetrain.left_back.getCurrentPosition()     + ticks, Robot.Drivetrain.right_back.getCurrentPosition()   + ticks
                );
                break;
            case SE:
                super.setTargetPosition(
                        Robot.Drivetrain.left_front.getCurrentPosition(),               Robot.Drivetrain.right_front.getCurrentPosition()  - ticks,
                        Robot.Drivetrain.left_back.getCurrentPosition()     + ticks, Robot.Drivetrain.right_back.getCurrentPosition()
                );
                break;
            case S:
                super.setTargetPosition(
                        Robot.Drivetrain.left_front.getCurrentPosition()    + ticks, Robot.Drivetrain.right_front.getCurrentPosition()  - ticks,
                        Robot.Drivetrain.left_back.getCurrentPosition()     + ticks, Robot.Drivetrain.right_back.getCurrentPosition()   - ticks
                );
                break;
            case SW:
                super.setTargetPosition(
                        Robot.Drivetrain.left_front.getCurrentPosition()    + ticks, Robot.Drivetrain.right_front.getCurrentPosition(),
                        Robot.Drivetrain.left_back.getCurrentPosition(),                Robot.Drivetrain.right_back.getCurrentPosition()   - ticks
                );
                break;
            case W:
                super.setTargetPosition(
                        Robot.Drivetrain.left_front.getCurrentPosition()    + ticks, Robot.Drivetrain.right_front.getCurrentPosition()  + ticks,
                        Robot.Drivetrain.left_back.getCurrentPosition()     - ticks, Robot.Drivetrain.right_back.getCurrentPosition()   - ticks
                );
                break;
            case NW:
                super.setTargetPosition(
                        Robot.Drivetrain.left_front.getCurrentPosition()    - ticks, Robot.Drivetrain.right_front.getCurrentPosition(),
                        Robot.Drivetrain.left_back.getCurrentPosition(),                Robot.Drivetrain.right_back.getCurrentPosition()   + ticks
                );
                break;
        }
        super.setPower(power);
    }
}
