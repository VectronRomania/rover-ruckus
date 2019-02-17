package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.ArrayList;

@TeleOp(name = "Servo Tester", group = "test")
public class ServoTester extends LinearOpMode {

    public class ServoBean {
        Servo servo;
        double position = 0;
        boolean locked = false;

        ServoBean(Servo servo) {
            this.servo = servo;
            this.servo.setDirection(Servo.Direction.FORWARD);
        }
    }

    private ArrayList<ServoBean> left = new ArrayList<>();
    private ServoBean activeLeft;
    private int activeServoLeft = 0;
    private boolean leftLock = false;

    private ArrayList<ServoBean> right = new ArrayList<>();
    private ServoBean activeRight;
    private int activeServoRight = 0;
    private boolean rightLock = false;

    @Override
    public void runOpMode() {
        left.add(new ServoBean(hardwareMap.get(Servo.class, "servo1")));
        left.add(new ServoBean(hardwareMap.get(Servo.class, "servo2")));
        left.add(new ServoBean(hardwareMap.get(Servo.class, "servo3")));

        right.add(new ServoBean(hardwareMap.get(Servo.class, "servo4")));
        right.add(new ServoBean(hardwareMap.get(Servo.class, "servo5")));
        right.add(new ServoBean(hardwareMap.get(Servo.class, "servo6")));

        activeLeft = left.get(activeServoLeft);
        activeRight = right.get(activeServoRight);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.dpad_up) {
                activeServoLeft = 0;
                resetLeft();
            }
            if (gamepad1.dpad_left) {
                activeServoLeft = 1;
                resetLeft();
            }
            if (gamepad1.dpad_down) {
                activeServoLeft = 2;
                resetLeft();
            }
            activeLeft = left.get(activeServoLeft);
            if (gamepad1.dpad_right && !leftLock) {
                activeLeft.locked = !activeLeft.locked;
                leftLock = true;
            }
            if (!gamepad1.dpad_right) {
                leftLock = false;
            }
            if (activeLeft.locked) {
                activeLeft.servo.setPosition(activeLeft.position);
            } else {
                activeLeft.servo.setPosition(scale(gamepad1.left_stick_y));
                activeLeft.position = activeLeft.servo.getPosition();
            }

            if (gamepad1.y) {
                activeServoRight = 0;
                resetRight();
            }
            if (gamepad1.b) {
                activeServoRight = 1;
                resetRight();
            }
            if (gamepad1.a) {
                activeServoRight = 2;
                resetRight();
            }
            activeRight = right.get(activeServoRight);
            if (gamepad1.x && !rightLock) {
                activeRight.locked = !activeRight.locked;
                rightLock = true;
            }
            if (!gamepad1.x) {
                rightLock = false;
            }
            if (activeRight.locked) {
                activeRight.servo.setPosition(activeRight.position);
            } else {
                activeRight.servo.setPosition(scale(gamepad1.right_stick_y));
                activeRight.position = activeRight.servo.getPosition();
            }

            telemetry.addLine("Left\n")
                    .addData("Active servo", activeServoLeft)
                    .addData("Position", activeLeft.position)
                    .addData("Locked", activeLeft.locked);
            telemetry.addLine("\n");
            telemetry.addLine("Right\n")
                    .addData("Active servo", activeServoRight)
                    .addData("Position", activeRight.position)
                    .addData("Locked", activeRight.locked);
            telemetry.update();
        }
    }

    private void resetLeft() {
        if (activeLeft.locked)
            return;
        activeLeft.servo.setPosition(0.5);
    }

    private void resetRight() {
        if (activeRight.locked)
            return;
        activeRight.servo.setPosition(0.5);
    }

    private double scale(double x) {
        if (x == 0)
            return 0.5;
        if (x < 0) {
            return -x/2 + 0.5;
        } else {
            return 0.5 - x/2;
        }
    }
}
