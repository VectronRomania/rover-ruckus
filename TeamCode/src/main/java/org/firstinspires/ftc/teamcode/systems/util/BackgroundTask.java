package org.firstinspires.ftc.teamcode.systems.util;

import android.support.annotation.NonNull;

import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;

/**
 * Class for managing background tasks.
 */
public class BackgroundTask extends Thread {

    /**
     * The task type.
     */
    public enum Type {
        LOOP,
        ONE_TIME
    }

    /**
     * The telemetry item that indicates the status of the background task.
     */
    private volatile TelemetryItem<Boolean> statusTelemetryItem;

    /**
     * The name of the task.
     */
    private final String taskName;

    /**
     * The runnable of the task.
     */
    private volatile BackgroundTaskRunnable runnable;

    /**
     * The type of the task/
     */
    private final Type taskType;

    /**
     * Is stop requested?
     */
    private volatile Boolean isStopRequested = false;

    /**
     * Was the task initialized?
     */
    private volatile Boolean isInitialized = false;

    /**
     * The constructor.
     * @param runnable the background runnable
     * @param name the task name
     * @param taskType the task type
     */
    public BackgroundTask(@NonNull final BackgroundTaskRunnable runnable, @NonNull final String name, @NonNull final Type taskType) {
        this.runnable = runnable;
        this.taskName = name;
        this.taskType = taskType;
        this.statusTelemetryItem = new TelemetryItem<Boolean>(taskName) {
            @Override
            public void update() {
                set(!isAlive());
            }
        };
    }

    /**
     * The constructor.
     * @param checkable the checkable that needs to be evaluated in the background.
     * @param name the task name.
     */
    public BackgroundTask(@NonNull final Checkable checkable, @NonNull final String name) {
        this.runnable = new BackgroundTaskRunnable<Boolean>() {
            @Override
            public void run() {
                result = checkable.check();
                telemetryItem.set(checkable.check());
            }

            @Override
            protected void initialize() {}

            @Override
            protected void shutdown() {}
        };
        this.taskName = name;
        this.taskType = Type.LOOP;
        this.statusTelemetryItem = new TelemetryItem<Boolean>(taskName) {
            @Override
            public void update() {
                set(!isAlive());
            }
        };
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public synchronized void run() {
        if (!isInitialized)
            runnable.initialize();

        switch (taskType) {
            case LOOP:
                while (!runnable.isFinished() && !isStopRequested) {
                    runnable.run();
                }
                break;
            case ONE_TIME:
                runnable.run();
                break;
        }
        runnable.shutdown();
    }

    /**
     * Stop the task.
     */
    public synchronized void stopTask() {
        isStopRequested = true;
        synchronized (this) {
            try {
                wait(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.runnable.stop();
    }

    /**
     * Get the status telemetry item,
     * @return
     */
    public synchronized TelemetryItem<Boolean> getStatusTelemetryItem() {
        return statusTelemetryItem;
    }

    public synchronized TelemetryItem getRunnableTelemetryItem() {
        return runnable.getTelemetryItem();
    }

    public synchronized void runInitialize() {
        runnable.initialize();
        isInitialized = true;
    }
}
