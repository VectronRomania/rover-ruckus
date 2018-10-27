package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {

    //    movement motors
    public DcMotor right_front = null;
    public DcMotor right_back = null;
    public DcMotor left_front = null;
    public DcMotor left_back = null;

    public void init(HardwareMap hw) {
        // TODO: 10/9/2018 add hardware
        right_front = hw.get(DcMotor.class, "right_front");
        right_back = hw.get(DcMotor.class, "right_back");
        left_front = hw.get(DcMotor.class, "left_front");
        left_back = hw.get(DcMotor.class, "left_back");
    }

    public void initDrivetrain() {
        left_front.setDirection(DcMotor.Direction.FORWARD);
        right_front.setDirection(DcMotor.Direction.FORWARD);
        left_back.setDirection(DcMotor.Direction.FORWARD);
        right_back.setDirection(DcMotor.Direction.FORWARD);
    }


}







