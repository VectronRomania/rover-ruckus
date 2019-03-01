package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;
import org.firstinspires.ftc.teamcode.systems.telemetry.items.CheckableTelemetryItem;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTask;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTaskRunnable;
import org.firstinspires.ftc.teamcode.systems.util.Checkable;

@Autonomous(name = "Basic Checkable Test", group = "test")
public class BasicCheckableTest extends AutonomousStandard {

    private int x = 0;

    private Checkable xCheckable;

    private BackgroundTask<Boolean> backgroundTask;

    @Override
    protected void initialize() {
        xCheckable = new Checkable() {
            @Override
            public synchronized Boolean check() {
                synchronized ((Object) x) {
                    return x > 50000;
                }
            }
        };

        backgroundTask = new BackgroundTask<>(new BackgroundTaskRunnable<Boolean>() {
            @Override
            protected void initialize() {
            }

            @Override
            protected void shutdown() {

            }

            @Override
            public void run() {
                synchronized ((Object) x) {
                    x = x + 1;
                }
            }
        }, "adder", BackgroundTask.Type.LOOP, this);

        telemetryManager.add(new CheckableTelemetryItem("Test status", xCheckable));
        telemetryManager.add(new TelemetryItem<Integer>("X") {
            @Override
            public void update() {
                this.set(x);
            }
        });
    }

    @Override
    protected void opModeLoop() {
        backgroundTask.start();
        while (opModeIsActive()) {
            boolean check = xCheckable.check();
            if (check) {
                backgroundTask.stopTask();
                break;
            }
            telemetryManager.cycle();
        }
    }
}
