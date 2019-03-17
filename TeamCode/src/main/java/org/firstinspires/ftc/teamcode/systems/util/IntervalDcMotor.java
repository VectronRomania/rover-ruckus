package org.firstinspires.ftc.teamcode.systems.util;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * IntervalDcMotor is a DC Motor that can run like a motor between certain bounds or like a servo.
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
     * The default power used for controlling the motor as a servo.
     */
    private final double defaultPower;

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
                           final int upperBound,
                           final double defaultPower) {
        this.motor = motor;
        this.direction = direction;
        this.lowerBound = lowerBound + 50;
        this.upperBound = upperBound - 50;
        this.defaultPower = defaultPower;

        this.motor.setDirection(DcMotor.Direction.FORWARD);
        this.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        this.motor.setTargetPosition(this.motor.getTargetPosition());
        this.motor.setPower(0);
    }

    /**
     * Make the motor move in a certain direction with the given power.
     * @param power the power to use.
     */
    public void setPower(double power) { // FIXME: 17/03/2019 TEST TEST TEST
        /*if the power is 0, stop the motor entirely*/
        if (power == 0) {
            this.motor.setTargetPosition(this.motor.getTargetPosition());
            this.motor.setPower(0);
        }

        int targetTicks;

        /*if the power is negative, simulate backward movement*/
        if (power < 0) {
            if (direction == DcMotor.Direction.FORWARD) {
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

    /**
     * Move the motor to the desired position using the default power.
     * @param input the position
     */
    public void setPosition(double input) {
        setPosition(input, defaultPower);
    }

    /**
     * Move the motor to the desired position with the desired power.
     * @param input the position
     * @param power the power
     */
    public void setPosition(double input, double power) {
        int ticks;

        /*if the Direction is set to REVERSE, change the value*/
        if (direction == DcMotor.Direction.FORWARD) {
            ticks = scale(input);
        } else {
            ticks = scale(1 - input);
        }

        motor.setTargetPosition(ticks);
        motor.setPower(power);
    }

    /**
     * Scale a value from [0,1] to [lowerBound,upperBound]
     * @param input the input value
     * @return the scaled value
     */
    private int scale(double input) {
        /*don't compute the value if the input is 0 or 1*/
        if (input <= 0) {
            return lowerBound;
        }
        if (input >= 1) {
            return upperBound;
        }
        return Double.valueOf(lowerBound + input * (upperBound - lowerBound)).intValue();
    }
}
