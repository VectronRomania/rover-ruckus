package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

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
    public Rev2mDistanceSensor distanceSensor;

    public void init(HardwareMap hardwareMap) {
        // TODO: 10/9/2018 add hardware
        right_front = hardwareMap.get(DcMotor.class, "right_front");
        right_back = hardwareMap.get(DcMotor.class, "right_back");
        left_front = hardwareMap.get(DcMotor.class, "left_front");
        left_back = hardwareMap.get(DcMotor.class, "left_back");

        left_lift = hardwareMap.get(DcMotor.class, "left_lift");
        right_lift = hardwareMap.get(DcMotor.class, "right_lift");

        left_lift.setDirection(DcMotor.Direction.FORWARD);
        right_lift.setDirection(DcMotor.Direction.FORWARD);

        left_lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        left_lift = hardwareMap.get(DcMotor.class, "left_lift");
        right_lift = hardwareMap.get(DcMotor.class, "right_lift");
        distanceSensor = hardwareMap.get(Rev2mDistanceSensor.class, "distance_sensor");

//        distanceSensor.getDistance(DistanceUnit.CM);

    }

}




