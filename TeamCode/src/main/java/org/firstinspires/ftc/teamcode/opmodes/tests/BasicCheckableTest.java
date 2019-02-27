package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.telemetry.items.CheckableTelemetryItem;
import org.firstinspires.ftc.teamcode.systems.util.Checkable;

@Autonomous(name = "Basic Checkable Test")
public class BasicCheckableTest extends AutonomousStandard {

    private int x = 0;
    private Checkable xCHeckable;

    @Override
    protected void initialize() {
        xCHeckable = new Checkable() {
            @Override
            public Boolean check() {
                return x > 100;
            }
        };

        telemetryManager.add(new CheckableTelemetryItem("Test status", xCHeckable));
    }

    @Override
    protected void opModeLoop() {
        if (xCHeckable.check()) {
            return;
        }
        x++;
    }
}
