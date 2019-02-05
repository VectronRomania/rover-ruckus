package org.firstinspires.ftc.teamcode.systems.util;

import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;

/**
 * Runnable for a background task.
 */
public abstract class BackgroundTaskRunnable<T> implements Runnable {

    /**
     * Is stop requested?
     */
    protected volatile Boolean isStopRequested = false;

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
     * Stop this runnable.
     */
    synchronized void stop() {
        isStopRequested = true;
    }

    /**
     * Wait
     * @param milis milliseconds
     * @throws InterruptedException
     */
    protected synchronized void sleep(long milis) throws InterruptedException {
        synchronized (this) {
            wait(milis);
        }
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
}
