package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTask;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTaskRunnable;

@Autonomous(name = "Background Task Test", group = "test")
public class BackgroundTaskTest extends AutonomousStandard {

    private BackgroundTask testTask;

    @Override
    protected void initialize() {
        testTask = new BackgroundTask(new BackgroundTaskRunnable() {

            private int i;

            @Override
            protected void initialize() {
                i = 0;
            }

            @Override
            protected void shutdown() {

            }

            @Override
            public void run() {
                if (i > 100) {
                    finished = true;
                    return;
                }
                i++;
                try {
                    this.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"Background task test", BackgroundTask.Type.LOOP);

        telemetryManager.add(testTask.getStatusTelemetryItem());
    }

    @Override
    protected void opModeLoop() {
        testTask.start();

        while (opModeIsActive() && testTask.isAlive()) {
            telemetryManager.cycle();
            sleep(10);
        }

        if (!opModeIsActive()) {
            testTask.stopTask();
        }

        stop();
    }
}
