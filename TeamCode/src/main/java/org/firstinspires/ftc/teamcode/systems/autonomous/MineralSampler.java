package org.firstinspires.ftc.teamcode.systems.autonomous;

import android.support.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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
     * @param position
     * @return
     */

    @NonNull
    public BackgroundTask sample(final int position) {
        return new BackgroundTask<>(new BackgroundTaskRunnable<String>() {
            @Override
            protected void initialize() {
//                make sure the drivetrain is stopped
                drivetrain.stop();
                super.telemetryItem.set("not started");
                result = "not started";
            }

            @Override
            protected void shutdown() {
                drivetrain.stop();
                finished = true;
            }

            @Override
            public void run() {
                Checkable drivetrainCheckable;
                result = "started";
                switch (position) {
                    case 1:
                        drivetrainCheckable = drivetrain.move(Controller.Direction.ROTATE_LEFT, 1000, 0.5);
                        telemetryItem.set("rotate left");
                        while (!drivetrainCheckable.check() && !super.isStopRequested) {
                            try {
                                super.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        if (isStopRequested) {
                            return;
                        }

                        drivetrainCheckable = drivetrain.move(Controller.Direction.N, 1000, 0.5);
                        telemetryItem.set("move forward");
                        while (!drivetrainCheckable.check() && !super.isStopRequested) {
                            try {
                                super.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        if (isStopRequested) {
                            return;
                        }

                        drivetrainCheckable = drivetrain.move(Controller.Direction.S, 1000, 0.5);
                        telemetryItem.set("move backward");
                        while (!drivetrainCheckable.check() && !super.isStopRequested) {
                            try {
                                super.sleep(20);
                            } catch(InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        if (isStopRequested) {
                            return;
                        }

                        drivetrainCheckable = drivetrain.move(Controller.Direction.ROTATE_RIGHT, 1000, 0.5);
                        telemetryItem.set("rotate right");
                        while (!drivetrainCheckable.check() && !super.isStopRequested) {
                            try {
                                super.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                result = "error";
                                telemetryItem.set("error");
                                return;
                            }
                        }
                        drivetrain.stop();
                        if (isStopRequested) {
                            return;
                        }

                        result = "done";
                        super.telemetryItem.set("done");
                        break;
                    case 2:
                        drivetrainCheckable = drivetrain.move(Controller.Direction.N, 1000, 0.5);
                        telemetryItem.set("move forward");
                        while (!drivetrainCheckable.check() && !super.isStopRequested) {
                            try {
                                super.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        if (isStopRequested) {
                            return;
                        }

                        drivetrainCheckable = drivetrain.move(Controller.Direction.S, 1000, 0.5);
                        telemetryItem.set("move backward");
                        while (!drivetrainCheckable.check() && !super.isStopRequested) {
                            try {
                                super.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                result = "error";
                                telemetryItem.set("error");
                                return;
                            }
                        }
                        drivetrain.stop();
                        if (isStopRequested) {
                            return;
                        }

                        result = "done";
                        super.telemetryItem.set("done");
                        break;
                    case 3:
                        drivetrainCheckable = drivetrain.move(Controller.Direction.ROTATE_RIGHT, 1000, 0.5);
                        telemetryItem.set("rotate right");
                        while (!drivetrainCheckable.check() && !super.isStopRequested) {
                            try {
                                super.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        if (isStopRequested) {
                            return;
                        }

                        drivetrainCheckable = drivetrain.move(Controller.Direction.N, 1000, 0.5);
                        telemetryItem.set("move forward");
                        while (!drivetrainCheckable.check() && !super.isStopRequested) {
                            try {
                                super.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        if (isStopRequested) {
                            return;
                        }

                        drivetrainCheckable = drivetrain.move(Controller.Direction.S, 1000, 0.5);
                        telemetryItem.set("move backward");
                        while (!drivetrainCheckable.check() && !super.isStopRequested) {
                            try {
                                super.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        if (isStopRequested) {
                            return;
                        }

                        drivetrainCheckable = drivetrain.move(Controller.Direction.ROTATE_LEFT, 1000, 0.5);
                        telemetryItem.set("rotate left");
                        while (!drivetrainCheckable.check() && !super.isStopRequested) {
                            try {
                                super.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                result = "error";
                                telemetryItem.set("error");
                                return;
                            }
                        }
                        drivetrain.stop();
                        if (isStopRequested) {
                            return;
                        }

                        result = "done";
                        super.telemetryItem.set("done");
                        break;
                }
            }
        }, "Mineral sampler", BackgroundTask.Type.ONE_TIME, opMode);
    }
}
