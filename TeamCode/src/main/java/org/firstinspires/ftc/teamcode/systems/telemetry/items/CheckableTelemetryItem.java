package org.firstinspires.ftc.teamcode.systems.telemetry.items;

import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;
import org.firstinspires.ftc.teamcode.systems.util.Checkable;

public final class CheckableTelemetryItem extends TelemetryItem<Boolean> {

    private final Checkable checkable;

    /**
     * The costructor.
     * @param tag the item identifier.
     */
    public CheckableTelemetryItem(String tag, Checkable checkable) {
        super(tag);
        this.checkable = checkable;
    }

    @Override
    public void update() {
        super.set(checkable.check());
    }
}
