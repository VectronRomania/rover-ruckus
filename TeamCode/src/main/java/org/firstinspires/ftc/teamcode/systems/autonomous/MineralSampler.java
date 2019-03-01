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
                super.telemetryItem.set("done");
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

//                If gold mineral was detected when 2 minerals were detected
                    if (mineralDetector.getDeploymentGoldPosition2() != MineralDetector.Position.NOT_DETECTED) {
                        mineralDetectionAccuarcy = 1;
                    } else if (mineralDetector.getDeploymentGoldPosition() == MineralDetector.Position.DETECTED) {
                        mineralDetectionAccuarcy = 0;
                    }
                }

                // FIXME: 01/03/2019 move forward to be in line with the minerals
                drivetrainCheckable = drivetrain.move(Controller.Direction.N, Robot.ENCODER_TICKS_40_1 * 3, 0.4);
                while (!drivetrainCheckable.check() && !isStopRequested) {
                }
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
                    // TODO: 02/03/2019 check both minerals
//                    if a mineral was not detected, move to the center mineral
                    if (!mineralDetector.isSamplingGoldDetected()) {
                        // FIXME: 01/03/2019 move right from in between the left and center minerals
                        drivetrainCheckable = drivetrain.move(Controller.Direction.E, 2030, 0.4);
                        while (!drivetrainCheckable.check() && !isStopRequested) {
                        }
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
                        // FIXME: 01/03/2019 move right from the center mineral
                        drivetrainCheckable = drivetrain.move(Controller.Direction.E, 4060, 0.4);
                        while (!drivetrainCheckable.check() && !isStopRequested) {
                        }
                        drivetrain.stop();

                        try {
                            sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
//                        score the mineral
                        // FIXME: 01/03/2019 scoring
                        drivetrainCheckable = drivetrain.move(Controller.Direction.N, Robot.ENCODER_TICKS_40_1 / 2, 0.2);
                        while (!drivetrainCheckable.check() && !isStopRequested) {}
                        drivetrain.stop();
                        return;
                    }
//                    else if 2 minerals were detected, use that info to score a mineral in Left or Center
                    if (mineralDetector.getSamplingtGoldPosition2() != MineralDetector.Position.NOT_DETECTED) {
                        switch (mineralDetector.getSamplingtGoldPosition2()) {
                            case LEFT:
                                drivetrainCheckable = drivetrain.move(Controller.Direction.W, 2030, 0.4);
                                break;
                            case RIGHT:
                                drivetrainCheckable = drivetrain.move(Controller.Direction.E, 2030, 0.4);
                                break;
                            case CENTER:
                                drivetrainCheckable = drivetrain.move(Controller.Direction.W, 6090, 0.4);
                                break;
                        }
//                        else if a single mineral was detected move left to scan
                    } else if (mineralDetector.getSamplingGoldPosition() != MineralDetector.Position.NOT_DETECTED) {
                        drivetrainCheckable = drivetrain.move(Controller.Direction.W, 2030, 0.4);
                        while (!drivetrainCheckable.check() && !isStopRequested) {}
                        drivetrain.stop();
//                        wait
                        try {
                            sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
//                        score
                        if (mineralDetector.getSamplingGoldPosition() == MineralDetector.Position.NOT_DETECTED) {
                            drivetrainCheckable = drivetrain.move(Controller.Direction.E, 4060, 0.4);
                            while (!drivetrainCheckable.check() && !isStopRequested) {}
                            drivetrain.stop();
                            // FIXME: 02/03/2019 scoring
                            drivetrainCheckable = drivetrain.move(Controller.Direction.N, Robot.ENCODER_TICKS_40_1 / 2, 0.2);
                            while (!drivetrainCheckable.check() && !isStopRequested) {}
                            drivetrain.stop();
                        }
                        return;
                    }
                }

//                if gold is detected but it's position is unknown
                if (mineralDetectionAccuarcy == 0) {

                    // FIXME: 01/03/2019 move to the center mineral
                    drivetrainCheckable = drivetrain.move(Controller.Direction.E, 2030, 0.4);
                    while (!drivetrainCheckable.check() && !isStopRequested) {
                    }
                    drivetrain.stop();

                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                    if (!mineralDetector.isSamplingGoldDetected()) {
                        // FIXME: 01/03/2019 move to the right mineral
                        drivetrainCheckable = drivetrain.move(Controller.Direction.E, 4060, 0.4);
                        while (!drivetrainCheckable.check() && !isStopRequested) {
                        }
                        drivetrain.stop();
                    }

                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                    if (mineralDetector.getDeploymentGoldPosition() == MineralDetector.Position.DETECTED) {
                        // FIXME: 01/03/2019 scoring
                        drivetrainCheckable = drivetrain.move(Controller.Direction.N, Robot.ENCODER_TICKS_40_1 / 2, 0.2);
                        while (!drivetrainCheckable.check() && !isStopRequested) {
                        }
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
                    // FIXME: 01/03/2019 proper ticks
                    case LEFT:
                        drivetrainCheckable = drivetrain.move(Controller.Direction.W, 2030, 0.4);
                    case CENTER:
                        drivetrainCheckable = drivetrain.move(Controller.Direction.E, 2030, 0.4);
                        break;
                    case RIGHT:
                        drivetrainCheckable = drivetrain.move(Controller.Direction.E, 6090, 0.5);
                        break;
                }
                while (!drivetrainCheckable.check() && !isStopRequested) {
                }
                drivetrain.stop();

                // FIXME: 01/03/2019 scoring
                drivetrainCheckable = drivetrain.move(Controller.Direction.N, Robot.ENCODER_TICKS_40_1 / 2, 0.2);
                while (!drivetrainCheckable.check() && !isStopRequested) {
                }
                drivetrain.stop();
            }
        }, "Mineral sampler", BackgroundTask.Type.ONE_TIME, opMode);
    }
}
