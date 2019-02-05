package org.firstinspires.ftc.teamcode.systems.telemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

/**
 * The telemetry manager is a class that simplifies and automates the telemetry management process.
 */
public class TelemetryManager {

    /**
     * The telemetry where the manager will write.
     */
    private Telemetry telemetry;

    /**
     * The list of items.
     */
    private volatile ArrayList<TelemetryItem> items;

    /**
     * The constructor.
     * @param telemetry the telemetry where the manager will write.
     */
    public TelemetryManager(Telemetry telemetry) {
        this.telemetry = telemetry;
        items = new ArrayList<>();
    }

    /**
     * Writes all items in the telemetry.
     */
    public void write() {
        for (TelemetryItem item : items) {
            item.write(telemetry);
        }
    }

    /**
     * Flushes the telemetry data.
     */
    public void flush() {
        telemetry.update();
    }

    /**
     * Add an item to the telemetry item list.
     * @param item the item that needs to be added.
     * @return the index of the added item.
     */
    public Integer add(TelemetryItem item) {
        items.add(item);
        return items.lastIndexOf(item);
    }

    /**
     * Remove an item from the telemetry item list.
     * @param index the index of the item that has to be removed.
     */
    public void remove(int index) {
        items.remove(index);
    }

    /**
     * Update all the items' values.
     */
    public void update() {
        for (TelemetryItem item : items) {
            item.update();
        }
    }

    /**
     * Do a complete telemetry cycle.
     */
    public void cycle() {
        update();
        write();
        flush();
    }

    /**
     * Get a telemetry item.
     * @param index the index of the item.
     * @return the item.
     */
    public synchronized TelemetryItem get(Integer index) {
        return items.get(index);
    }
}
