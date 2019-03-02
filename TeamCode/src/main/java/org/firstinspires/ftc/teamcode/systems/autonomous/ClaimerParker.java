package org.firstinspires.ftc.teamcode.systems.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.Extender;
import org.firstinspires.ftc.teamcode.systems.drivetrain.AutonomousDrivetrain;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.Controller;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTask;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTaskRunnable;
import org.firstinspires.ftc.teamcode.systems.util.Checkable;

/**
 * Background task for claiming the depot and parking.
 * Parking only for now.
 */
public class ClaimerParker {

    private final AutonomousDrivetrain autonomousDrivetrain;
    private final Extender extender;
    private final LinearOpMode opMode;

    public ClaimerParker(final AutonomousDrivetrain autonomousDrivetrain,
                         final LinearOpMode opMode,
                         final Extender extender) {
        this.autonomousDrivetrain = autonomousDrivetrain;
        this.opMode = opMode;
        this.extender = extender;
    }

    public BackgroundTask<Integer> getBackGroundTask(final boolean facingDepot) {
        return new BackgroundTask<Integer>(new BackgroundTaskRunnable<Integer>() {
            @Override
            protected void initialize() {
                this.telemetryItem = new TelemetryItem<Integer>("Stage") {
                    @Override
                    public void update() {

                    }
                };
                this.result = -1;
                this.telemetryItem.set(result);
                autonomousDrivetrain.stop();
            }

            @Override
            protected void shutdown() {
                this.finished = true;
                this.result = 0;
                this.telemetryItem.set(result);
                autonomousDrivetrain.stop();
            }

            @Override
            public void run() {

                Checkable drivetrainCheckable;

                if (!facingDepot) {
//                    move forward
                    this.telemetryItem.set(1);
                    drivetrainCheckable = autonomousDrivetrain.move(Controller.Direction.N, Robot.ENCODER_TICKS_40_1 * 4, 0.5);
                    while (!isStopRequested && !drivetrainCheckable.check()) {}
                    if (isStopRequested) {
                        return;
                    }

//                    extend the rack
                    this.telemetryItem.set(2);
                    extender.move(Extender.Direction.FORWARD, 0.75);
                    try {
                        sleep(700);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Robot.Extender.setPower(0);
                    return;
                }
//                more complicated
                // TODO: 02/03/2019
            }
        }, "Claiming-parking", BackgroundTask.Type.ONE_TIME, this.opMode);
    }

}
