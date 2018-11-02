package org.firstinspires.ftc.teamcode.systems.drivetrain;

import com.qualcomm.robotcore.hardware.DcMotor;

public class DriveTrain {


    private DcMotor right_front = null;
    private DcMotor right_back = null;
    private DcMotor left_front = null;
    private DcMotor left_back = null;

    private DriveTrainUtils.Type type;

    private DriveTrainUtils.DrivetrainDriver driver;

    public DriveTrain(DriveTrainUtils.Type t) {

        /**
         * setezi
         * motoare
         */

        this.type = t;

        switch (type) {
            case OMNI:

                break;
            case TANK:

                break;
            case MECANUM:

                break;
        }
    }

//    public void driveOmni(double right_front_input, double right_back_input, double left_front_input, double left_back_input) {
//
//    }
//
//    public void driveTank(double left_input, double right_input) {
//
//    }
//
//    public void driveMecanum(double right_front_input, double right_back_input, double left_front_input, double left_back_input) {
//
//    }

    public void drive() {
        driver.drive();
    }
}
