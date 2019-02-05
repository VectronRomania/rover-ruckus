package org.firstinspires.ftc.teamcode.systems.util.checkables;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.systems.util.Checkable;

public class MotorEncoderCheckable implements Checkable {

    private final DcMotor motor;

    private final Integer bias;

    private final Integer targetPosition;

    public MotorEncoderCheckable(DcMotor motor, Integer targetPosition, Integer bias) {
        this.motor = motor;
        this.targetPosition = targetPosition;
        this.bias = bias;
    }

    @Override
    public Boolean check() {
        return motor.getCurrentPosition() > targetPosition - bias || motor.getCurrentPosition() < targetPosition + bias;
    }

}
