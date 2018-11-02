package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {

    // movement motors
    public DcMotor right_front = null;
    public DcMotor right_back = null;
    public DcMotor left_front = null;
    public DcMotor left_back = null;

    // lift motors
    public DcMotor lift_motor_1 = null;
    public DcMotor lift_motor_2 = null;

    // servo for phone rotation
    public Servo phone_servo;

    // sensors
    // TODO: 11/1/2018 add sensors

    public void init(HardwareMap hw) {
        // TODO: 10/9/2018 add hardware
        right_front = hw.get(DcMotor.class, "right_front");
        right_back = hw.get(DcMotor.class, "right_back");
        left_front = hw.get(DcMotor.class, "left_front");
        left_back = hw.get(DcMotor.class, "left_back");
        lift_motor_1 = hw.get(DcMotor.class, "lift_motor_1");
        lift_motor_2 = hw.get(DcMotor.class, "lift_motor_2");
    }

    public void initDrivetrain() {
        left_front.setDirection(DcMotor.Direction.FORWARD);
        right_front.setDirection(DcMotor.Direction.FORWARD);
        left_back.setDirection(DcMotor.Direction.FORWARD);
        right_back.setDirection(DcMotor.Direction.FORWARD);
    }

    public void initLift(){
        lift_motor_1.setDirection(DcMotor.Direction.FORWARD);
        lift_motor_2.setDirection(DcMotor.Direction.FORWARD);
    }

}




