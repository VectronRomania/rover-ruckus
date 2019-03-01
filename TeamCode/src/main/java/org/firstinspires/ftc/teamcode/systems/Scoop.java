package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryGroup;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;

/**
 * System for controlling the scoop.
 */

public class Scoop {

    private double scoopPosition = 0;
    private double scoopHighPosition = 0.5;

    private boolean manualButtonLock = false;
    private boolean manualButtonLockHigh = false;

    public Scoop() {
        Robot.Servos.scoopLeft.setDirection(Servo.Direction.FORWARD);
        Robot.Servos.scoopLeftHigh.setDirection(Servo.Direction.FORWARD);

        Robot.Servos.scoopRight.setDirection(Servo.Direction.REVERSE);
        Robot.Servos.scoopRightHigh.setDirection(Servo.Direction.REVERSE);

//        moveScoop(scoopPosition);
//        moveScoopHigh(scoopHighPosition);
    }

    public void moveScoop(double position) {
        Robot.Servos.scoopLeft.setPosition(position);
        Robot.Servos.scoopRight.setPosition(position);
    }

    public void moveScoopHigh(double position) {
        Robot.Servos.scoopLeftHigh.setPosition(position);
        Robot.Servos.scoopRightHigh.setPosition(position);
    }

    public void manual(Gamepad gamepad) {

//        if (gamepad.start && !mechanismButtonLock) {
//            mechanismLock = !mechanismLock;
//            mechanismButtonLock = true;
//        }
//        if (!gamepad.start) {
//            mechanismButtonLock = false;
//        }
//
//        if (mechanismLock) {
//            return;
//        }
//        MACROS
        if (gamepad.right_bumper) {
            scoopPosition = 0;
            scoopHighPosition = 1;
        }

        if (gamepad.left_bumper) {
            scoopPosition = 1;
            scoopHighPosition = 0;
        }

//        Manual
        if (gamepad.dpad_up && !manualButtonLock) {
            if (scoopPosition > 0) {
                scoopPosition -= 0.1;
            }
            manualButtonLock = true;
        }
        if (gamepad.dpad_down && !manualButtonLock) {
            if (scoopPosition < 1) {
                scoopPosition += 0.1;
            }
            manualButtonLock = true;
        }

        if (gamepad.dpad_right && !manualButtonLockHigh) {
            if (scoopHighPosition < 1) {
                scoopHighPosition += 0.1;
            }
            manualButtonLockHigh = true;
        }
        if (gamepad.dpad_left && !manualButtonLockHigh) {
            if (scoopHighPosition > 0) {
                scoopHighPosition -= 0.1;
            }
            manualButtonLockHigh = true;
        }

        if (!gamepad.dpad_left && !gamepad.dpad_right) {
            manualButtonLockHigh = false;
        }
        if (!gamepad.dpad_up && !gamepad.dpad_down) {
            manualButtonLock = false;
        }


        moveScoop(scoopPosition);
        moveScoopHigh(scoopHighPosition);
    }

    public TelemetryItem<Double> getTelemetryItem() {
        return new TelemetryGroup<Double>("Scoop positions") {}
                .add(new TelemetryItem<Double>("Scoop") {
                    @Override
                    public void update() {
                        synchronized ((Object) scoopPosition) {
                            this.set(scoopPosition);
                        }
                    }
                })
                .add(new TelemetryItem<Double>("Scoop high") {
                    @Override
                    public void update() {
                        synchronized ((Object) scoopHighPosition) {
                            this.set(scoopHighPosition);
                        }
                    }
                });
    }
}
