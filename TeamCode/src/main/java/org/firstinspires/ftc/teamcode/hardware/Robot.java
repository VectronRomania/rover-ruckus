package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Constants;

/**
 * The robot hardware.
 */
public class Robot {

    public static final Integer ENCODER_TICKS_20_1 = 560;

    public static final Integer ENCODER_TICKS_40_1 = 1120;

    public static final Integer ENCODER_TICKS_60_1 = 1680;

    /**
     * Drivetrain hardware
     */
    public static class Drivetrain {

        public static volatile DcMotor left_front;

        public static volatile DcMotor right_front;

        public static volatile DcMotor left_back;

        public static volatile DcMotor right_back;

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

        public static volatile DcMotor motor;

        public static volatile Rev2mDistanceSensor   distance_left;

        public static volatile Rev2mDistanceSensor   distance_right;

        public static void setPower(double power) {
            motor.setPower(power);
        }

        public static void setTargetPosition(Integer ticks) {
            motor.setTargetPosition(ticks);
        }

        public static void setDirection(DcMotor.Direction direction) {
            motor.setDirection(direction);
        }

        public static void setRunMode(DcMotor.RunMode runMode) {
            motor.setMode(runMode);
        }

        public static void setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior zeroPowerBehaviour) {
            motor.setZeroPowerBehavior(zeroPowerBehaviour);
        }
    }

    /**
     * RoboticArm hardware
     */
    public static class RoboticArm {

        public static volatile DcMotor arm_left;

        public static volatile DcMotor arm_right;

        public static volatile DcMotor extender;

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

        public static volatile CRServo collector;

        public static void setPower(double a) {
            collector.setPower(a);
        }

        public static void setDirection(DcMotor.Direction direction) {
            collector.setDirection(direction);
        }

        public static volatile Servo servoLeft;

        public static volatile Servo servoRight;

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

        public static volatile Servo teamMarkerServo;

    }

    /**
     * Other sensors
     */
    public static class Sensors {

        public static volatile REVImu left_imu;

        public static volatile REVImu right_imu;

        public static volatile LynxI2cColorRangeSensor color_sensor;

    }


    public Robot() {}

    public static void build(HardwareMap hw) {

        Drivetrain.left_front   = hw.get(DcMotor.class, "left_front");
        Drivetrain.right_front  = hw.get(DcMotor.class, "right_front");
        Drivetrain.left_back    = hw.get(DcMotor.class, "left_back");
        Drivetrain.right_back   = hw.get(DcMotor.class, "right_back");

        Lift.motor = hw.get(DcMotor.class, "lift");
        Lift.distance_left = hw.get(Rev2mDistanceSensor.class, "left_distance_sensor");
        Lift.distance_right = hw.get(Rev2mDistanceSensor.class, "right_distance_sensor");

        RoboticArm.arm_left = hw.get(DcMotor.class, "left_arm");
        RoboticArm.arm_right = hw.get(DcMotor.class, "right_arm");
        RoboticArm.extender = hw.get(DcMotor.class, "arm_extender");

        Collector.collector     = hw.get(CRServo.class, "collector");
        Collector.servoLeft     = hw.get(Servo.class, "collector_servo_left");
        Collector.servoRight    = hw.get(Servo.class, "collector_servo_right");

        Servos.teamMarkerServo = hw.get(Servo.class, "team_marker_servo");

        Sensors.left_imu = REVImu.get(hw, "left_imu", Constants.LEFT_IMU_CONFIG_FILE_NAME);
        Sensors.right_imu = REVImu.get(hw, "right_imu", Constants.RIGHT_IMU_CONFIG_FILE_NAME);
        Sensors.color_sensor = hw.get(LynxI2cColorRangeSensor.class, "color_sensor");
    }

    public static int convertGoldToTicks(double golds) {
        return Robot.ENCODER_TICKS_40_1 * Double.valueOf( golds / (4 * Math.PI) ).intValue();
    }
}