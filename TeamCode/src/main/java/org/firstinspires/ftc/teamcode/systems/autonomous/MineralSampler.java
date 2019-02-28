package org.firstinspires.ftc.teamcode.systems.autonomous;

import android.support.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.drivetrain.AutonomousDrivetrain;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.Controller;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTask;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTaskRunnable;
import org.firstinspires.ftc.teamcode.systems.util.Checkable;

/**
 * Container for the mineral sampling process.
 */
public class MineralSampler {

    private final AutonomousDrivetrain drivetrain;

    private final LinearOpMode opMode;

    public MineralSampler(AutonomousDrivetrain drivetrain,
                          LinearOpMode opMode) {
        this.drivetrain = drivetrain;
        this.opMode = opMode;
    }

    /**
     * Return a background task for eliminating the proper mineral.
     * @param mineralDetector
     * @return
     */

    @NonNull
    public BackgroundTask sample(final MineralDetector mineralDetector) {
        return new BackgroundTask<>(new BackgroundTaskRunnable<String>() {
            @Override
            protected void initialize() {
//                make sure the drivetrain is stopped
                drivetrain.stop();
                super.telemetryItem.set("not started");
            }

            @Override
            protected void shutdown() {
                drivetrain.stop();
                finished = true;
            }

            @Override
            public void run() {
                Checkable drivetrainCheckable;
                super.telemetryItem.set("started");

                /**
                 * -1 -> not detected
                 * 0  -> detected, inaccurate position(either Center or Right)
                 * 1  -> accurate detection
                 */
                int mineralDetectionAccuarcy = -1;

//                If the gold mineral was detected during deployment
                if (mineralDetector.isDeploymentGoldDetected()) {

////                    If gold mineral was detected when 3 minerals were detected
//                    if (mineralDetector.getDeploymentGoldPosition3() != MineralDetector.Position.NOT_DETECTED) {
//                        mineralDetectionAccuarcy = 1;
//                    }

//                If gold mineral was detected when 2 minerals were detected
                    if (mineralDetector.getDeploymentGoldPosition2() != MineralDetector.Position.NOT_DETECTED) {
                        mineralDetectionAccuarcy = 1;
                    }

                    else if (mineralDetector.getDeploymentGoldPosition() == MineralDetector.Position.DETECTED) {
                        mineralDetectionAccuarcy = 0;
                    }
                }

                drivetrainCheckable = drivetrain.move(Controller.Direction.N, Robot.ENCODER_TICKS_40_1, 0.4);
                while (!drivetrainCheckable.check() && !isStopRequested) {}
                drivetrain.stop();

//                if gold was not detected
                if (mineralDetectionAccuarcy == -1) {
//                    wait a bit so that the detector has time to detect
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
//                    if a mineral was not detected, move to the center mineral
                    if (!mineralDetector.isSamplingGoldDetected()) {
                        drivetrainCheckable = drivetrain.move(Controller.Direction.E, Robot.ENCODER_TICKS_40_1, 0.4);
                        while (!drivetrainCheckable.check() && !isStopRequested) {}
                        drivetrain.stop();

                        try {
                            sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
//                    if a mineral was not detected, move to the right mineral
                    if (!mineralDetector.isSamplingGoldDetected()) {
                        drivetrainCheckable = drivetrain.move(Controller.Direction.E, Robot.ENCODER_TICKS_40_1, 0.4);
                        while (!drivetrainCheckable.check() && !isStopRequested) {}
                        drivetrain.stop();

                        try {
                            sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    if (mineralDetector.getSamplingGoldPosition() == MineralDetector.Position.NOT_DETECTED) {
                        return;
                    }
//                    score the mineral
                    drivetrainCheckable = drivetrain.move(Controller.Direction.N, Robot.ENCODER_TICKS_40_1 / 2, 0.2);
                    while (!drivetrainCheckable.check() && !isStopRequested) {}
                    drivetrain.stop();
                    return;
                }

//                if gold is detected but it's position is unknown
                if (mineralDetectionAccuarcy == 0) {

                    drivetrainCheckable = drivetrain.move(Controller.Direction.E, Robot.ENCODER_TICKS_40_1, 0.4);
                    while (!drivetrainCheckable.check() && !isStopRequested) {}
                    drivetrain.stop();

                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }

                    if (!mineralDetector.isSamplingGoldDetected()) {
                        drivetrainCheckable = drivetrain.move(Controller.Direction.E, Robot.ENCODER_TICKS_40_1, 0.4);
                        while (!drivetrainCheckable.check() && !isStopRequested) {}
                        drivetrain.stop();
                    }
                    if (mineralDetector.getDeploymentGoldPosition() == MineralDetector.Position.DETECTED) {
                        drivetrainCheckable = drivetrain.move(Controller.Direction.N, Robot.ENCODER_TICKS_40_1 / 2, 0.2);
                        while (!drivetrainCheckable.check() && !isStopRequested) {}
                        drivetrain.stop();
                    }
                    return;
                }

//                if gold was detected and it's position is known
                final MineralDetector.Position position;
                if (mineralDetector.getDeploymentGoldPosition() != MineralDetector.Position.NOT_DETECTED
                        && mineralDetector.getSamplingtGoldPosition2() == MineralDetector.Position.NOT_DETECTED) {
                    position = mineralDetector.getDeploymentGoldPosition();
                } else if (mineralDetector.getDeploymentGoldPosition() == MineralDetector.Position.NOT_DETECTED
                        && mineralDetector.getSamplingtGoldPosition2() != MineralDetector.Position.NOT_DETECTED) {
                    position = mineralDetector.getDeploymentGoldPosition2();
                } else if (mineralDetector.getDeploymentGoldPosition() == mineralDetector.getDeploymentGoldPosition2()) {
                    position = mineralDetector.getDeploymentGoldPosition();
                } else {
                    position = mineralDetector.getDeploymentGoldPosition2();
                }
                switch (position) {
                    case LEFT:
                        break;
                    case CENTER:
                        drivetrainCheckable = drivetrain.move(Controller.Direction.E, Robot.ENCODER_TICKS_40_1 * 2, 0.5);
                        break;
                    case RIGHT:
                        drivetrainCheckable = drivetrain.move(Controller.Direction.E, Robot.ENCODER_TICKS_40_1 * 2, 0.5);
                        break;
                }
                while (!drivetrainCheckable.check() && !isStopRequested) {}
                drivetrain.stop();

                drivetrainCheckable = drivetrain.move(Controller.Direction.N, Robot.ENCODER_TICKS_40_1 / 2, 0.3);
                while (!drivetrainCheckable.check() && !isStopRequested) {}
                drivetrain.stop();




//                switch (position) {
////                    left
//                    case 0:
//                        drivetrainCheckable = drivetrain.move(Controller.Direction.N,
//                                Robot.ENCODER_TICKS_40_1 * 3 / 2,
//                                0.3);
//                        telemetryItem.set("move forward");
//                        while (!drivetrainCheckable.check() && !super.isStopRequested) {
//                            try {
//                                super.sleep(20);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                                result = "error";
//                                telemetryItem.set("error");
//                                return;
//                            }
//                        }
//
//                        result = "done";
//                        super.telemetryItem.set("done");
//                        break;
////                        center
//                    case 1:
//                        drivetrainCheckable = drivetrain.move(Controller.Direction.E,
//                                1000,
//                                0.3);
//                        telemetryItem.set("rotate");
//                        while (!drivetrainCheckable.check() && !super.isStopRequested) {
//                            try {
//                                super.sleep(20);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                                result = "error";
//                                telemetryItem.set("error");
//                                return;
//                            }
//                        }
//                        if (isStopRequested) {
//                            return;
//                        }
//
//                        drivetrainCheckable = drivetrain.move(Controller.Direction.N,
//                                Robot.ENCODER_TICKS_40_1 * 3 / 2,
//                                0.3);
//                        telemetryItem.set("move forward");
//                        while (!drivetrainCheckable.check() && !super.isStopRequested) {
//                            try {
//                                super.sleep(20);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                                result = "error";
//                                telemetryItem.set("error");
//                                return;
//                            }
//                        }
//
//                        result = "done";
//                        super.telemetryItem.set("done");
//                        break;
////                        right
//                    case 2:
//                        drivetrainCheckable = drivetrain.move(Controller.Direction.E,
//                                2000,
//                                0.3);
//                        telemetryItem.set("rotate right");
//                        while (!drivetrainCheckable.check() && !super.isStopRequested) {
//                            try {
//                                super.sleep(20);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                                result = "error";
//                                telemetryItem.set("error");
//                                return;
//                            }
//                        }
//                        if (isStopRequested) {
//                            return;
//                        }
//
//                        drivetrainCheckable = drivetrain.move(Controller.Direction.N,
//                                Robot.ENCODER_TICKS_40_1 * 7 / 2,
//                                0.5);
//                        telemetryItem.set("move forward");
//                        while (!drivetrainCheckable.check() && !super.isStopRequested) {
//                            try {
//                                super.sleep(20);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                                return;
//                            }
//                        }
//
//                        result = "done";
//                        super.telemetryItem.set("done");
//                        break;
//                }
            }
        }, "Mineral sampler", BackgroundTask.Type.ONE_TIME, opMode);
    }
}
