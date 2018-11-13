package org.firstinspires.ftc.teamcode.systems.drivetrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.Robot;

public class DriveTrain extends Robot {


//    void init();

    public enum Type {
        TANK,
        OMNI,
        MECANUM
    }

    public interface DrivePowerSet {
        void drive(Gamepad gamepad1);
        void init();
    }

    private DcMotor right_front = null;
    private DcMotor right_back = null;
    private DcMotor left_front = null;
    private DcMotor left_back = null;

    //useful variables
    private float powerMultiplier = 1f;

    private Type type;

    private DrivePowerSet driver;

    public DriveTrain(HardwareMap hwMap, Type t, Gamepad gamepad1) {

        super();
        super.init(hwMap);

        left_front = super.left_front;
        left_back = super.left_back;
        right_back = super.right_back;
        right_front = super.right_front;

        this.type = t;

        switch (type) {
            case OMNI:
                driver = new DrivePowerSet() {
                    @Override
                    public void drive(Gamepad gamepad1) {
                        if (gamepad1.x == true) {
                            powerMultiplier = 1f;
                        }
                        if (gamepad1.y == true) {
                            powerMultiplier = 0.75f;
                        }
                        if (gamepad1.b == true) {
                            powerMultiplier = 0.5f;
                        }
                        if (gamepad1.a == true) {
                            powerMultiplier = 0.35f;
                        }
                        if (gamepad1.right_stick_x != 0){
                            left_back.setPower(-gamepad1.right_stick_x*powerMultiplier);
                            left_front.setPower(-gamepad1.right_stick_x*powerMultiplier);
                            right_back.setPower(-gamepad1.right_stick_x*powerMultiplier);
                            right_front.setPower(-gamepad1.right_stick_x*powerMultiplier);
                            return;
                        }

                        if (gamepad1.left_stick_x != 0 || gamepad1.left_stick_y != 0) {
                            float x = gamepad1.left_stick_x * powerMultiplier;
                            float y = -gamepad1.left_stick_y * powerMultiplier;
                            left_front.setPower(-y-x);
                            right_front.setPower(y-x);
                            right_back.setPower(y+x);
                            left_back.setPower(-y+x);
                            return;
                        }

                        left_front.setPower(0);
                        right_front.setPower(0);
                        right_back.setPower(0);
                        left_back.setPower(0);
                        return;

                    }
                    @Override
                    public void init() {
                        left_front.setDirection(DcMotor.Direction.FORWARD);
                        right_front.setDirection(DcMotor.Direction.FORWARD);
                        left_back.setDirection(DcMotor.Direction.FORWARD);
                        right_back.setDirection(DcMotor.Direction.FORWARD);
                    }
                };
                break;
            case TANK:
                driver = new DrivePowerSet() {
                    @Override
                    public void drive(Gamepad gamepad1) {
                        left_front.setPower(-gamepad1.left_stick_y);
                        right_front.setPower(-gamepad1.right_stick_y);
                        left_back.setPower(-gamepad1.left_stick_y);
                        right_back.setPower(-gamepad1.right_stick_y);

                    }

                    @Override
                    public void init() {
                        left_front.setDirection(DcMotor.Direction.FORWARD);
                        right_front.setDirection(DcMotor.Direction.REVERSE);
                        left_back.setDirection(DcMotor.Direction.FORWARD);
                        right_back.setDirection(DcMotor.Direction.REVERSE);
                    }
                };
                break;
            case MECANUM:
                driver = new DrivePowerSet() {
                    @Override
                    public void drive(Gamepad gamepad1) {
                        if (gamepad1.x == true) {
                            powerMultiplier = 1f;
                        }
                        if (gamepad1.y == true) {
                            powerMultiplier = 0.75f;
                        }
                        if (gamepad1.b == true) {
                            powerMultiplier = 0.5f;
                        }
                        if (gamepad1.a == true) {
                            powerMultiplier = 0.35f;
                        }
                        if (gamepad1.right_stick_x != 0){
                            left_back.setPower(-gamepad1.right_stick_x*powerMultiplier);
                            left_front.setPower(-gamepad1.right_stick_x*powerMultiplier);
                            right_back.setPower(-gamepad1.right_stick_x*powerMultiplier);
                            right_front.setPower(-gamepad1.right_stick_x*powerMultiplier);
                            return;
                        }

                        if (gamepad1.left_stick_x != 0 || gamepad1.left_stick_y != 0) {
                            float x = gamepad1.left_stick_x * powerMultiplier;
                            float y = -gamepad1.left_stick_y * powerMultiplier;
                            left_front.setPower(-y-x);
                            right_front.setPower(y-x);
                            right_back.setPower(y+x);
                            left_back.setPower(-y+x);
                            return;
                        }

                        left_front.setPower(0);
                        right_front.setPower(0);
                        right_back.setPower(0);
                        left_back.setPower(0);
                        return;

                    }

                    @Override
                    public void init() {
                        left_front.setDirection(DcMotor.Direction.FORWARD);
                        right_front.setDirection(DcMotor.Direction.FORWARD);
                        left_back.setDirection(DcMotor.Direction.FORWARD);
                        right_back.setDirection(DcMotor.Direction.FORWARD);
                    }
                };
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

    public void drive(Gamepad gamepad) {
        driver.drive(gamepad);
    }

}

//// FATA
// left_front.setDirection(DcMotor.Direction.FORWARD);
// right_front.setDirection(DcMotor.Direction.REVERSE);
// left_back.setDirection(DcMotor.Direction.FORWARD);
// right_back.setDirection(DcMotor.Direction.REVERSE);
//// SPATE
// left_front.setDirection(DcMotor.Direction.REVERSE);
// right_front.setDirection(DcMotor.Direction.FORWARD);
// left_back.setDirection(DcMotor.Direction.REVERSE);
// right_back.setDirection(DcMotor.Direction.FORWARD);
//// DIAGONALA DREAPTA
// left_front = null;
// right_front.setDirection(DcMotor.Direction.REVERSE)
// left_back.setDirection(DcMotor.Direction.FORWARD);
// right_back = null;
//// DIAGONALA STANGA
// left_front.setDirection(DcMotor.Direction.FORWARD);
// right_front = null;
// left_back = null;
// right_back.setDirection(DcMotor.Direction.REVERSE);
//// ROTIRE DREAPTA
// left_front.setDirection(DcMotor.Direction.FORWARD);
// right_front.setDirection(DcMotor.Direction.FORWARD);
// left_back.setDirection(DcMotor.Direction.FORWARD);
// right_back.setDirection(DcMotor.Direction.FORWARD);
//// ROTIRE STINGA
// left_front.setDirection(DcMotor.Direction.REVERSE);
// right_front.setDirection(DcMotor.Direction.REVERSE);
// left_back.setDirection(DcMotor.Direction.REVERSE);
// right_back.setDirection(DcMotor.Direction.REVERSE);
//// DREAPTA
// left_front.setDirection(DcMotor.Direction.FORWARD);
// right_front.setDirection(DcMotor.Direction.FORWARD);
// left_back.setDirection(DcMotor.Direction.REVERSE);
// right_back.setDirection(DcMotor.Direction.REVERSE);
//// STINGA
// left_front.setDirection(DcMotor.Direction.REVERSE);
// right_front.setDirection(DcMotor.Direction.REVERSE);
// left_back.setDirection(DcMotor.Direction.FORWARD);
// right_back.setDirection(DcMotor.Direction.FORWARD);