package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;

/**
 * The robot hardware.
 */
public class Robot {

    public static final Integer ENCODER_TICKS_20_1 = 1440; // FIXME: 20/02/2019

    public static final Integer ENCODER_TICKS_40_1 = 1120;

    public static final Integer ENCODER_TICKS_60_1 = 1680;

    /**
     * Drivetrain hardware
     */
    public static class Drivetrain {

        public static DcMotor left_front;

        public static DcMotor right_front;

        public static DcMotor left_back;

        public static DcMotor right_back;

        public static void setPower(double power) {
            left_front.setPower(power);
            right_front.setPower(power);
            right_back.setPower(power);
            left_back.setPower(power);
        }

        public static void setPower(double a, double b) {
            left_front.setPower(a);
            right_front.setPower(b);
            right_back.setPower(b);
            left_back.setPower(a);
        }

        public static void setPower(double a, double b, double c, double d) {
            left_front.setPower(a);
            right_front.setPower(b);
            right_back.setPower(c);
            left_back.setPower(d);
        }

        public static void setTargetPosition(Integer a) {
            left_front.setTargetPosition(a);
            right_front.setTargetPosition(a);
            right_back.setTargetPosition(a);
            left_back.setTargetPosition(a);
        }

        public static void setTargetPosition(Integer a, Integer b, Integer c, Integer d) {
            left_front.setTargetPosition(a);
            right_front.setTargetPosition(b);
            right_back.setTargetPosition(c);
            left_back.setTargetPosition(d);
        }

        public static void setDirection(DcMotor.Direction direction) {
            left_front.setDirection(direction);
            right_front.setDirection(direction);
            left_back.setDirection(direction);
            right_back.setDirection(direction);
        }

        public static void setRunMode(DcMotor.RunMode runMode) {
            left_front.setMode(runMode);
            right_front.setMode(runMode);
            left_back.setMode(runMode);
            right_back.setMode(runMode);
        }

        public static void setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior zeroPowerBehaviour) {
            left_front.setZeroPowerBehavior(zeroPowerBehaviour);
            right_front.setZeroPowerBehavior(zeroPowerBehaviour);
            left_back.setZeroPowerBehavior(zeroPowerBehaviour);
            right_back.setZeroPowerBehavior(zeroPowerBehaviour);
        }
    }

    /**
     * Lift hardware
     */
    public static class Lift {

        public static DcMotor               left_lift;

        public static DcMotor               right_lift;

        public static Rev2mDistanceSensor   distance_left;

        public static Rev2mDistanceSensor   distance_right;

        public static void setPower(double power) {
            left_lift.setPower(power);
            right_lift.setPower(power);
        }

        public static void setPower(double left, double right) {
            left_lift.setPower(left);
            right_lift.setPower(right);
        }

        public static void setTargetPosition(Integer left, Integer right) {
            left_lift.setTargetPosition(left);
            right_lift.setTargetPosition(right);
        }

        public static void setDirection(DcMotor.Direction direction) {
            left_lift.setDirection(direction);
            right_lift.setDirection(direction);
        }

        public static void setDirection(DcMotor.Direction left, DcMotor.Direction right) {
            left_lift.setDirection(left);
            right_lift.setDirection(right);
        }

        public static void setRunMode(DcMotor.RunMode runMode) {
            left_lift.setMode(runMode);
            right_lift.setMode(runMode);
        }

        public static void setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior zeroPowerBehaviour) {
            left_lift.setZeroPowerBehavior(zeroPowerBehaviour);
            right_lift.setZeroPowerBehavior(zeroPowerBehaviour);
        }
    }

    /**
     * Extender hardware
     */
    public static class Extender {

        public static DcMotor extender;

        public static void setPower(double a) {
            extender.setPower(a);
        }

        public static void setRunMode(DcMotor.RunMode runMode) {
            extender.setMode(runMode);
        }

        public static void setDirection(DcMotor.Direction direction) {
            extender.setDirection(direction);
        }

        public static void setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior zeroPowerBehaviour) {
            extender.setZeroPowerBehavior(zeroPowerBehaviour);
        }

        public static void setTargetPosition(int a) {
            extender.setTargetPosition(a);
        }

    }

    /**
     * Collector hardware
     */
    public static class Collector {

        public static DcMotor collector;

        public static void setPower(double a) {
            collector.setPower(a);
        }

        public static void setRunMode(DcMotor.RunMode runMode) {
            collector.setMode(runMode);
        }

        public static void setDirection(DcMotor.Direction direction) {
            collector.setDirection(direction);
        }

        public static void setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior zeroPowerBehaviour) {
            collector.setZeroPowerBehavior(zeroPowerBehaviour);
        }

        public static void setTargetPosition(int a) {
            collector.setTargetPosition(a);
        }

        public static Servo servoLeft;

        public static Servo servoRight;

        public static void setServoDirection(Servo.Direction direction) {
            servoLeft.setDirection(direction);
            servoRight.setDirection(direction);
        }

        public static void setServoDirection(Servo.Direction direction1, Servo.Direction direction2) {
            servoLeft.setDirection(direction1);
            servoRight.setDirection(direction2);
        }

        public static void setServoPosition(double position) {
            servoLeft.setPosition(position);
            servoRight.setPosition(position);
        }

        public static void setServoPosition(double positionLeft, double positionRight) {
            servoLeft.setPosition(positionLeft);
            servoRight.setPosition(positionRight);
        }
    }

    /**
     * Other servos
     */
    public static class Servos {

        public static Servo scoopLeft;

        public static Servo scoopRight;

        public static Servo scoopLeftHigh;

        public static Servo scoopRightHigh;

//        public static Servo phone_servo;

    }

    /**
     * Other sensors
     */
    public static class Sensors {

        public static REVImu left_imu;

        public static REVImu right_imu;
    }


    public Robot() {}

    public static void build(HardwareMap hw) {
        Robot robot = new Robot();

        Drivetrain.left_front   = hw.get(DcMotor.class, "left_front");
        Drivetrain.right_front  = hw.get(DcMotor.class, "right_front");
        Drivetrain.left_back    = hw.get(DcMotor.class, "left_back");
        Drivetrain.right_back   = hw.get(DcMotor.class, "right_back");

        Lift.left_lift      = hw.get(DcMotor.class, "left_lift");
        Lift.right_lift     = hw.get(DcMotor.class, "right_lift");
//        Lift.distance_left = hw.get(Rev2mDistanceSensor.class, "lift_distance_left");
//        Lift.distance_right = hw.get(Rev2mDistanceSensor.class, "lift_distance_right");

        Extender.extender = hw.get(DcMotor.class, "extender");

        Collector.collector     = hw.get(DcMotor.class, "collector");
//        Collector.servoLeft     = hw.get(Servo.class, "collector_servo_left");
//        Collector.servoRight    = hw.get(Servo.class, "collector_servo_left");

        Servos.scoopLeft        = hw.get(Servo.class, "scoop_left");
        Servos.scoopRight       = hw.get(Servo.class, "scoop_right");
        Servos.scoopLeftHigh    = hw.get(Servo.class, "scoop_left_high");
        Servos.scoopRightHigh   = hw.get(Servo.class, "scoop_right_high");

        Sensors.left_imu = REVImu.get(hw, "left_imu", Constants.LEFT_IMU_CONFIG_FILE_NAME);
        Sensors.right_imu = REVImu.get(hw, "right_imu", Constants.RIGHT_IMU_CONFIG_FILE_NAME);
    }
}






//        Servos.phone_servo = hw.get(Servo.class, "phone_servo");

//        return robot;