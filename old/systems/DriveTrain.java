package org.firstinspires.ftc.teamcode.old.systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.hardware.Robot;

public class DriveTrain extends Robot {


//    void init();

    public interface DrivePowerSet {
        void drive(Gamepad gamepad1);
        void init();
    }

    //useful variables
    private double powerMultiplier = 1f;

    private Type type;

    private DrivePowerSet driver;

    public DriveTrain(HardwareMap hwMap, Type t, Gamepad gamepad1) {

        super();
        super.init(hwMap);

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
                            double x = gamepad1.left_stick_x * powerMultiplier;
                            double y = -gamepad1.left_stick_y * powerMultiplier;
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
                            powerMultiplier = 1;
                        }
                        if (gamepad1.y == true) {
                            powerMultiplier = 0.75;
                        }
                        if (gamepad1.b == true) {
                            powerMultiplier = 0.5;
                        }
                        if (gamepad1.a == true) {
                            powerMultiplier = 0.35;
                        }

//                        double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
//                        double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
//                        double rightX = gamepad1.right_stick_x;
//                        final double v1 = r * Math.cos(robotAngle) + rightX;
//                        final double v2 = r * Math.sin(robotAngle) - rightX;
//                        final double v3 = r * Math.sin(robotAngle) + rightX;
//                        final double v4 = r * Math.cos(robotAngle) - rightX;
//
//                        left_front.setPower(powerMultiplier * v1);
//                        right_front.setPower(powerMultiplier * v2);
//                        left_back.setPower(powerMultiplier * v3);
//                        right_back.setPower(powerMultiplier * v4);

                        if (gamepad1.right_stick_x != 0){
                            left_back.setPower(-gamepad1.right_stick_x*powerMultiplier);
                            left_front.setPower(-gamepad1.right_stick_x*powerMultiplier);
                            right_back.setPower(-gamepad1.right_stick_x*powerMultiplier);
                            right_front.setPower(-gamepad1.right_stick_x*powerMultiplier);
                            return;
                        }

                        if (gamepad1.left_stick_x != 0 || gamepad1.left_stick_y != 0) {
                            double x = gamepad1.left_stick_x * powerMultiplier;
                            double y = -gamepad1.left_stick_y * powerMultiplier;
                            left_front.setPower(Range.clip(-y -x, -1, 1));
                            right_front.setPower(Range.clip(y -x, -1, 1));
                            right_back.setPower(Range.clip(y +x, -1, 1));
                            left_back.setPower(Range.clip(-y +x, -1, 1));
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

                        left_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                        right_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                        left_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                        right_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

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

    public void init() {driver.init();}

}

//// FORWARD
// left_front.setDirection(DcMotor.Direction.FORWARD);
// right_front.setDirection(DcMotor.Direction.REVERSE);
// left_back.setDirection(DcMotor.Direction.FORWARD);
// right_back.setDirection(DcMotor.Direction.REVERSE);
//// BACKWARD
// left_front.setDirection(DcMotor.Direction.REVERSE);
// right_front.setDirection(DcMotor.Direction.FORWARD);
// left_back.setDirection(DcMotor.Direction.REVERSE);
// right_back.setDirection(DcMotor.Direction.FORWARD);
//// RIGHT DIAGONAL
// left_front = null;
// right_front.setDirection(DcMotor.Direction.REVERSE)
// left_back.setDirection(DcMotor.Direction.FORWARD);
// right_back = null;
//// LEFT DIAGONAL
// left_front.setDirection(DcMotor.Direction.FORWARD);
// right_front = null;
// left_back = null;
// right_back.setDirection(DcMotor.Direction.REVERSE);
//// RIGHT ROTATION
// left_front.setDirection(DcMotor.Direction.FORWARD);
// right_front.setDirection(DcMotor.Direction.FORWARD);
// left_back.setDirection(DcMotor.Direction.FORWARD);
// right_back.setDirection(DcMotor.Direction.FORWARD);
//// LEFT ROTATION
// left_front.setDirection(DcMotor.Direction.REVERSE);
// right_front.setDirection(DcMotor.Direction.REVERSE);
// left_back.setDirection(DcMotor.Direction.REVERSE);
// right_back.setDirection(DcMotor.Direction.REVERSE);
//// RIGHT
// left_front.setDirection(DcMotor.Direction.FORWARD);
// right_front.setDirection(DcMotor.Direction.FORWARD);
// left_back.setDirection(DcMotor.Direction.REVERSE);
// right_back.setDirection(DcMotor.Direction.REVERSE);
//// LEFT
// left_front.setDirection(DcMotor.Direction.REVERSE);
// right_front.setDirection(DcMotor.Direction.REVERSE);
// left_back.setDirection(DcMotor.Direction.FORWARD);
// right_back.setDirection(DcMotor.Direction.FORWARD);