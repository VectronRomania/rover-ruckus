/* MIT License
 *
 * Copyright (c) 2019 Vectron RO034 <teamvectron @ gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
