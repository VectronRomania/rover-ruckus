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

        public static void setPower(Double power) {
            left_front.setPower(power);
            right_front.setPower(power);
            right_back.setPower(power);
            left_back.setPower(power);
        }

        public static void setPower(Double a, Double b) {
            left_front.setPower(a);
            right_front.setPower(b);
            right_back.setPower(b);
            left_back.setPower(a);
        }

        public static void setPower(Double a, Double b, Double c, Double d) {
            left_front.setPower(a);
            right_front.setPower(b);
            right_back.setPower(c);
            left_back.setPower(d);
        }

        // TODO: 05/02/2019 move controller funtions here
    }

    /**
     * Lift hardware
     */
    public static class Lift {

        public static DcMotor               left_lift;

        public static DcMotor               right_lift;

        public static void setPower(Double power) {
            left_lift.setPower(power);
            right_lift.setPower(power);
        }

        public static void setPower(Double a, Double b) {
            left_lift.setPower(a);
            right_lift.setPower(b);
        }

        public static Rev2mDistanceSensor   distanceSensor;
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

    public static Robot build(HardwareMap hw) {
        Robot robot = new Robot();

        Drivetrain.left_front   = hw.get(DcMotor.class, "left_front");
        Drivetrain.right_front  = hw.get(DcMotor.class, "right_front");
        Drivetrain.left_back    = hw.get(DcMotor.class, "left_back");
        Drivetrain.right_back   = hw.get(DcMotor.class, "right_back");

        Lift.left_lift      = hw.get(DcMotor.class, "left_lift");
        Lift.right_lift     = hw.get(DcMotor.class, "right_lift");
        Lift.distanceSensor = hw.get(Rev2mDistanceSensor.class, "distance_sensor");

//        Servos.phone_servo = hw.get(Servo.class, "phone_servo");

        Sensors.left_imu = REVImu.get(hw, "left_imu", Constants.LEFT_IMU_CONFIG_FILE_NAME);
        Sensors.right_imu = REVImu.get(hw, "right_imu", Constants.RIGHT_IMU_CONFIG_FILE_NAME);

        return robot;
    }
}




