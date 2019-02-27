package org.firstinspires.ftc.teamcode.systems.util;

import android.util.Log;

import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;

/**
 * Runnable for a background task.
 */
public abstract class BackgroundTaskRunnable<T> implements Runnable {

    public static final String TAG = "BackgroundTaskRunnable";

    /**
     * The status of the runnable.
     */
    protected volatile Boolean finished = false;

    /**
     * The telemetry item provided by the runnable.
     */
    protected volatile TelemetryItem<T> telemetryItem;

    /**
     * The result of this runnable.
     */
    protected volatile T result = null;

    /**
     * Flag for requesting stop.
     */
    protected volatile Boolean isStopRequested = false;

    public BackgroundTaskRunnable() {
        Log.d(TAG, "BackgroundTaskRunnable() called");
        this.telemetryItem = new TelemetryItem<T>("DEFAULT") {
            @Override
            public void update() {}
        };
    }

    /**
     * Wait.
     * @param millis milliseconds
     * @throws InterruptedException
     */
    protected synchronized void sleep(long millis) throws InterruptedException {
        Log.v(TAG, "sleep() called with: millis = [" + millis + "]");
        synchronized (this) {
            wait(millis);
        }
    }

    /**
     * Check if the runnable finished.
     * @return
     */
    synchronized Boolean isFinished() {
        Log.v(TAG, "isFinished() called");
        return finished;
    }

    /**
     * Get the telemetry item.
     * @return
     */
    TelemetryItem<T> getTelemetryItem() {
        Log.d(TAG, "getTelemetryItem() called");
        return telemetryItem;
    }

    /**
     * Get the result.
     * @return
     */
    public T getResult() {
        Log.d(TAG, "getResult() called");
        return result;
    }

    /**
     * Set the appropriate flag for stopping.
     */
    public void stop() {
        Log.d(TAG, "stop() called");
        this.isStopRequested = true;
    }

    /**
     * Initialize the runnable.
     * This is executed prior to run().
     */
    protected abstract void initialize();

    /**
     * Do any cleanup necessary after finishing.
     * This is executed after run().
     */
    protected abstract void shutdown();

    /**
     * Run the runnable.
     */
    @Override
    public abstract void run();
}
