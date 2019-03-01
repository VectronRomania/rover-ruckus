package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;
import org.firstinspires.ftc.teamcode.systems.telemetry.items.CheckableTelemetryItem;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTask;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTaskRunnable;
import org.firstinspires.ftc.teamcode.systems.util.Checkable;
import org.firstinspires.ftc.teamcode.systems.util.CheckableGroup;

@Autonomous(name = "Basic Checkable Group Test", group = "test")
public class BasicCheckableGroupTest extends AutonomousStandard {

    private int x = 0, y = 0;

    private BackgroundTask<Boolean> backgroundTask;

    private CheckableGroup group;

    @Override
    protected void initialize() {
        group = new CheckableGroup()
                .add(new Checkable() {
                    @Override
                    public synchronized Boolean check() {
                        synchronized ((Object) x) {
                            return x > 50000;
                        }
                    }
                }, CheckableGroup.Operation.AND)
                .add(new Checkable() {
                    @Override
                    public synchronized Boolean check() {
                        synchronized ((Object) y) {
                            return y < -100000;
                        }
                    }
                }, CheckableGroup.Operation.AND);

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
                synchronized ((Object) y) {
                    y = y - 1;
                }
            }
        }, "adder", BackgroundTask.Type.LOOP, this);

        telemetryManager.add(new CheckableTelemetryItem("Test status", group));
        telemetryManager.add(new TelemetryItem<Integer>("X") {
            @Override
            public void update() {
                this.set(x);
            }
        });
        telemetryManager.add(new TelemetryItem<Integer>("Y") {
            @Override
            public void update() {
                this.set(y);
            }
        });
    }

    @Override
    protected void opModeLoop() {
        if (!backgroundTask.isAlive()) {
            backgroundTask.start();
        }
        if (group.check()) {
            backgroundTask.stopTask();
            return;
        }
        telemetryManager.cycle();
        x++;
        y--;
//        idle();
    }

}
