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

//                If the gold mineral was detected during deployment
                if (mineralDetector.isDeploymentGoldDetected()) {

//                    If gold mineral was detected when 3 minerals were detected
                    if (mineralDetector.getDeploymentGoldPosition3() != MineralDetector.Position.NOT_DETECTED) { // FIXME: 27/02/2019
                        // TODO: 27/02/2019 sample the mineral
                    }

//                If gold mineral was detected when 2 minerals were detected
                    else if (mineralDetector.getDeploymentGoldPosition2() != MineralDetector.Position.NOT_DETECTED) {
                        // TODO: 27/02/2019 sample the mineral
                    }

                    else if (mineralDetector.getDeploymentGoldPosition() == MineralDetector.Position.DETECTED) {
                        // TODO: 27/02/2019 scan center, gold can't be left
                    }
                }

//                continue with samplingx
                

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
