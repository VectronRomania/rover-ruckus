package org.firstinspires.ftc.teamcode.systems.drivetrain.autonomous_checkables;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.autonomous.Checkable;
import org.firstinspires.ftc.teamcode.systems.autonomous.CheckableGroup;
import org.firstinspires.ftc.teamcode.systems.autonomous.CheckableLogicalOperation;

public class MotorEncoderCheckable implements Checkable {

    private final DcMotor motor;

    private final Integer bias;

    private final Integer targetPosition;

    private final CheckableLogicalOperation operation;

    public MotorEncoderCheckable(DcMotor motor, Integer targetPosition, Integer bias, CheckableLogicalOperation operation) {
        this.motor = motor;
        this.targetPosition = targetPosition;
        this.bias = bias;
        this.operation = operation;
    }

    @Override
    public Boolean check() {
        if (motor.getCurrentPosition() > targetPosition - bias || motor.getCurrentPosition() < targetPosition + bias)
            return true;
        return false;
    }

    @Override
    public CheckableLogicalOperation getOperation() {
        return operation;
    }

    public static CheckableGroup newDrivetrainEncoderCheckableGroup(Integer a, Integer b, Integer c, Integer d,
                                                                    Integer bias, CheckableLogicalOperation operation) {
        CheckableGroup group = new CheckableGroup(operation);

        group.add(new MotorEncoderCheckable(
                        Robot.Drivetrain.left_front, a, bias, CheckableLogicalOperation.AND
                ))
                .add(new MotorEncoderCheckable(
                        Robot.Drivetrain.right_front, b, bias, CheckableLogicalOperation.AND
                ))
                .add(new MotorEncoderCheckable(
                        Robot.Drivetrain.right_back, c, bias, CheckableLogicalOperation.AND
                ))
                .add(new MotorEncoderCheckable(
                        Robot.Drivetrain.left_back, d, bias, CheckableLogicalOperation.AND
                ));

        return group;
    }
}
