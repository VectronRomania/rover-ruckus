package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Hardware;

import org.firstinspires.ftc.teamcode.Constants;

/**
 * The robot hardware.
 */
public class Robot {

    public static final Integer ENCODER_TICKS_PER_FULL_ROTATION = 1440;

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

        public static Rev2mDistanceSensor   distanceSensor;

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

    }

    /**
     * Collector hardware
     */
    public static class Collector {

    }

    /**
     * Other servos
     */
    public static class Servos {

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
        Lift.distanceSensor = hw.get(Rev2mDistanceSensor.class, "distance_sensor");

        Sensors.left_imu = REVImu.get(hw, "left_imu", Constants.LEFT_IMU_CONFIG_FILE_NAME);
        Sensors.right_imu = REVImu.get(hw, "right_imu", Constants.RIGHT_IMU_CONFIG_FILE_NAME);
    }
}






//        Servos.phone_servo = hw.get(Servo.class, "phone_servo");

//        return robot;