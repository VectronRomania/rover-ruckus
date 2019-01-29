package org.firstinspires.ftc.teamcode.systems.telemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * A telemetry item is a simple object that contains a single line of telemetry data.
 * @param <T> the type of data this item uses for reading and writing.
 */
public abstract class TelemetryItem<T> {

    /**
     * The item identifier.
     */
    protected final String TAG;

    /**
     * The item value.
     */
    private T item;

    /**
     * The costructor.
     * @param tag the item identifier.
     */
    public TelemetryItem(String tag) {
        this.TAG = tag;
    }

    /**
     * Retrieve the item value.
     * @return the item's value.
     */
    public T get() {
        return item;
    }

    /**
     * Set the item value.
     * @param expr the value that needs to be replaced.
     */
    public void set(T expr) {
        this.item = expr;
    }

    /**
     * Write the item value in the telemetry(without flushing).
     * @param telemetry the telemetry where the item will write it's data.
     */
    public void write(Telemetry telemetry) {
        telemetry.addData(TAG, item);
    }

    /**
     * Method for updating the item value.
     */
    public abstract void update();
}
