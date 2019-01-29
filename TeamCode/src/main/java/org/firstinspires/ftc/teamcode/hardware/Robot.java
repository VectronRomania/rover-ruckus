package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Hardware;

public class Robot {

    /**
     * Drivetrain hardware
     */
    public static class Drivetrain {

        public static DcMotor left_front;

        public static DcMotor right_front;

        public static DcMotor left_back;

        public static DcMotor right_back;

    }

    /**
     * Lift hardware
     */
    public static class Lift {

        public static DcMotor               left_lift;

        public static DcMotor               right_lift;

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

        return robot;
    }
}




