package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;
import org.firstinspires.ftc.teamcode.systems.telemetry.items.CheckableTelemetryItem;
import org.firstinspires.ftc.teamcode.systems.util.Checkable;
import org.firstinspires.ftc.teamcode.systems.util.CheckableGroup;

@Autonomous(name = "Basic Checkable Group Test", group = "test")
public class BasicCheckableGroupTest extends AutonomousStandard {

    private int x = 0, y = 0;

    private CheckableGroup group;

    @Override
    protected void initialize() {
        group = new CheckableGroup()
                .add(new Checkable() {
                    @Override
                    public Boolean check() {
                        return x > 1000;
                    }
                }, CheckableGroup.Operation.AND)
                .add(new Checkable() {
                    @Override
                    public Boolean check() {
                        return y < -1500;
                    }
                }, CheckableGroup.Operation.AND);

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
        if (group.check()) {
            return;
        }
        x++;
        y--;
        idle();
    }
}
