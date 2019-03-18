package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.util.IntervalDcMotor;

/**
 * System for controlling the extender.
 */

public class RoboticArm {

    public static final int ABSOLUTE_MAXIMUM_TICK = 2300;

    public final DcMotor left;

    public final DcMotor right;

    public final DcMotor extender;

    /**
     * The direction of the movement of the extender.
     */

    public RoboticArm(final boolean isUp,
                      final double defaultPower) {

//        int leftLow;
//        int leftHigh;
//
//        int rightLow;
//        int rightHigh;
//
//        if (isUp) {
//            leftLow = -ABSOLUTE_MAXIMUM_TICK;
//            leftHigh = 0;
//            rightLow = 0;
//            rightHigh = ABSOLUTE_MAXIMUM_TICK;
//        } else {
//            leftLow = 0;
//            leftHigh = ABSOLUTE_MAXIMUM_TICK;
//            rightLow = -ABSOLUTE_MAXIMUM_TICK;
//            rightHigh = 0;
//        }
//
//        this.left = new IntervalDcMotor(
//                Robot.RoboticArm.arm_left,
//                DcMotor.Direction.REVERSE,
//                leftLow,
//                leftHigh,
//                defaultPower
//        );
//        this.right = new IntervalDcMotor(
//                Robot.RoboticArm.arm_right,
//                DcMotor.Direction.FORWARD,
//                rightLow,
//                rightHigh,
//                defaultPower
//        );
//        this.extender = new IntervalDcMotor(
//                Robot.RoboticArm.extender,
//                DcMotorSimple.Direction.FORWARD,
//                0,
//                Robot.ENCODER_TICKS_20_1 * 6,
//                1
//        );

        this.left = Robot.RoboticArm.arm_left;
        this.right = Robot.RoboticArm.arm_right;
        this.extender = Robot.RoboticArm.extender;

        this.right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.extender.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        this.left.setDirection(DcMotorSimple.Direction.FORWARD);
        this.right.setDirection(DcMotorSimple.Direction.REVERSE);

        this.left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Control the extender manually.
     * @param gamepad the gamepad to use.
     */

    public void manual(Gamepad gamepad) {
        if (gamepad.left_trigger > 0) {
            this.left.setPower(-gamepad.left_trigger * 0.75);
            this.right.setPower(-gamepad.left_trigger * 0.75);
        } else if (gamepad.right_trigger > 0) {
            this.left.setPower(gamepad.right_trigger * 0.75);
            this.right.setPower(gamepad.right_trigger * 0.75);
            return;
        } else {
            this.left.setPower(0);
            this.right.setPower(0);
        }
        this.extender.setPower(gamepad.right_stick_y);
    }

//    public void setPosition(double position) {
//        this.left.setPosition(position);
//        this.right.setPosition(position);
//    }
}
