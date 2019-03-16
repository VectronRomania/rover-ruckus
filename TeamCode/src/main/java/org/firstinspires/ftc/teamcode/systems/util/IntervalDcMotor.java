package org.firstinspires.ftc.teamcode.systems.util;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * IntervalDcMotor is a DC Motor that can run like a motor, but only between certain bounds.
 */
public class IntervalDcMotor {

    /**
     * The motor used as hardware.
     */
    public final DcMotor motor;

    /**
     * The lowest tick the motor should ever reach.
     */
    private final int lowerBound;

    /**
     * The highest tick the motor should ever reach.
     */
    private final int upperBound;

    /**
     * The direction of the motor.
     */
    private final DcMotor.Direction direction;

    /**
     * Constructor for IntervalDcMotor.
     * @param motor the motor used.
     * @param direction the direction of the motor.
     * @param lowerBound the lower bound.
     * @param upperBound the upper bound.
     */
    public IntervalDcMotor(final DcMotor motor,
                           final DcMotor.Direction direction,
                           final int lowerBound,
                           final int upperBound) {
        this.motor = motor;
        this.direction = direction;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;

        this.motor.setDirection(DcMotor.Direction.FORWARD);
        this.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        this.motor.setTargetPosition(this.motor.getTargetPosition());
        this.motor.setPower(0);
    }

    public void setPower(double power) {
        /*if the power is 0, stop the motor entirely*/
        if (power == 0) {
            this.motor.setTargetPosition(this.motor.getTargetPosition());
            this.motor.setPower(0);
        }

        int targetTicks = this.motor.getCurrentPosition();

        /*if the power is negative, simulate backward movement*/
        if (power < 0) {
            if (direction == DcMotorSimple.Direction.FORWARD) {
                targetTicks = lowerBound;
            } else {
                targetTicks = upperBound;
            }
            power *= -1;
        } else { /*otherwise, simulate forward movement*/
            if (direction == DcMotor.Direction.FORWARD) {
                targetTicks = upperBound;
            } else {
                targetTicks = lowerBound;
            }
        }

        /*apply the computed values*/
        this.motor.setTargetPosition(targetTicks);
        this.motor.setPower(power);
    }
}
