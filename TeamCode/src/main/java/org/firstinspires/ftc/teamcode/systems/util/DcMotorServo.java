package org.firstinspires.ftc.teamcode.systems.util;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * DcMotorServo is a class that transforms a DcMotor into a Servo.
 */
public class DcMotorServo {

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
     * The direction the "Servo"
     */
    private final DcMotor.Direction direction;

    /**
     * The default power used for controlling the motor.
     */
    private final double defaultPower;

    /**
     * Constructor for the DcMotorServo
     * @param motor the motor hardware
     * @param direction the motor direction
     * @param lowerBound the lower ticks bound
     * @param upperBound the upper ticks bound
     * @param defaultPower the default power for the motor
     * @param defaultPosition the default position of the motor
     */
    public DcMotorServo(final DcMotor motor,
                        final DcMotor.Direction direction,
                        final int lowerBound,
                        final int upperBound,
                        final double defaultPower,
                        final double defaultPosition) {
        /*if the upper bound is smaller than the lower bound, crash*/
        if ((upperBound <= lowerBound)) throw new AssertionError();

        this.motor = motor;
        this.direction = direction;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.defaultPower = defaultPower;

        this.motor.setDirection(DcMotor.Direction.FORWARD);
        this.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.setPosition(defaultPosition);
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
