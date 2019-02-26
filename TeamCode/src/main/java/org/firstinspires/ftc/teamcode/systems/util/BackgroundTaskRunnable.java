package org.firstinspires.ftc.teamcode.systems.util;

import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;

/**
 * Runnable for a background task.
 */
public abstract class BackgroundTaskRunnable<T> implements Runnable {

    /**
     * The status of the runnable.
     */
    protected volatile Boolean finished = false;

    /**
     * The telemetry item provided by the runnable.
     */
    protected volatile TelemetryItem<T> telemetryItem = null;

    /**
     * The result of this runnable.
     */
    protected volatile T result = null;

    /**
     * Flag for requesting stop.
     */
    protected volatile Boolean isStopRequested = null;

    public BackgroundTaskRunnable() {
        this.finished = true;
        this.telemetryItem = new TelemetryItem<T>("DEFAULT") {
            @Override
            public void update() {}
        };
        this.finished = true;
    }

    /**
     * Wait.
     * @param millis milliseconds
     * @throws InterruptedException
     */
    protected synchronized void sleep(long millis) throws InterruptedException {
        synchronized (this) {
            wait(millis);
        }
    }

    /**
     * Check if the runnable finished.
     * @return
     */
    synchronized Boolean isFinished() {
        return finished;
    }

    /**
     * Get the telemetry item.
     * @return
     */
    TelemetryItem<T> getTelemetryItem() {
        return telemetryItem;
    }

    /**
     * Get the result.
     * @return
     */
    public T getResult() {
        return result;
    }

    /**
     * Set the appropriate flag for stopping.
     */
    public void stop() {
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
