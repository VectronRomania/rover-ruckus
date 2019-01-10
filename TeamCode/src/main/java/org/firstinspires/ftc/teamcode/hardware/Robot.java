package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {

    // movement motors
    public DcMotor right_front = null;
    public DcMotor right_back = null;
    public DcMotor left_front = null;
    public DcMotor left_back = null;

    // lift motors
    public DcMotor left_lift = null;
    public DcMotor right_lift = null;

    // servo for phone rotation
    public Servo phone_servo;

    // sensors
//    public DistanceSensor distanceSensor;

    public void init(HardwareMap hardwareMap) {
        // TODO: 10/9/2018 add hardware
        right_front = hardwareMap.get(DcMotor.class, "right_front");
        right_back = hardwareMap.get(DcMotor.class, "right_back");
        left_front = hardwareMap.get(DcMotor.class, "left_front");
        left_back = hardwareMap.get(DcMotor.class, "left_back");

        left_lift = hardwareMap.get(DcMotor.class, "left_lift");
        right_lift = hardwareMap.get(DcMotor.class, "right_lift");
//        lift_motor_1.setDirection(DcMotor.Direction.FORWARD);
//        lift_motor_2.setDirection(DcMotor.Direction.FORWARD);


//        lift_motor_1 = hw.get(DcMotor.class, "lift_motor_1");
//        lift_motor_2 = hw.get(DcMotor.class, "lift_motor_2");
//        distanceSensor = hw.get(DistanceSensor.class, "distanceSensor");
    }

}




