package org.firstinspires.ftc.teamcode.systems.telemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

/**
 * A telemetry group is an easy to use container for multiple telemetry items.
 * @param <T> the type of data this group uses for reading and writing.
 */
public abstract class TelemetryGroup<T> extends TelemetryItem {

    /**
     * The items of this group.
     */
    private ArrayList<TelemetryItem<T>> items;

    /**
     * The constructor.
     * @param tag the group identifier.
     */
    public TelemetryGroup(String tag) {
        super(tag);
        items = new ArrayList<TelemetryItem<T>>();
    }

    /**
     * A group does not return anything
     * @return null
     */
    @Override
    public T get() {
        return null;
    }

    /**
     * Update each item.
     */
    @Override
    public void update() {
        for (final TelemetryItem item : items) {
            item.update();
        }
    }

    /**
     * Write each item.
     * @param telemetry the telemetry where the item will write it's data.
     */
    @Override
    public void write(Telemetry telemetry) {
        telemetry.addLine(super.TAG);
        for (final TelemetryItem item : items) {
            item.write(telemetry);
        }
    }

    /**
     * Adds a new telemetry item.
     * @param item the item which has to be added.
     */
    public void add(TelemetryItem<T> item) {
        items.add(item);
    }

    /**
     * Get an item from the list.
     * @param index the index of the item which has to be retrieved.
     * @return the item.
     */
    public TelemetryItem<T> get(int index) {
        return items.get(index);
    }

    /**
     * Remove an item from the list.
     * @param index the index of the item which has to be removed.
     */
    public void remove(int index) {
        items.remove(index);
    }

    /**.
     * Set an item in the list
     * @param index the index of the item.
     * @param item the new item.
     */
    public void set(int index, TelemetryItem<T> item) {
        items.set(index, item);
    }

    /**
     * Write a certain item only.
     * @param telemetry the telemetry where to write the item.
     * @param index the index of the item.
     */
    public void write(Telemetry telemetry, int index) {
        items.get(index).write(telemetry);
    }

}
