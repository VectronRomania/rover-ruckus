package org.firstinspires.ftc.teamcode.systems.util;

import android.support.annotation.NonNull;

import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryGroup;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;

/**
 * Class for managing background tasks.
 */
public class BackgroundTask<T> extends Thread {

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
    private volatile BackgroundTaskRunnable<T> runnable;

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
    public BackgroundTask(@NonNull final BackgroundTaskRunnable<T> runnable, @NonNull final String name, @NonNull final Type taskType) {
        this.runnable = runnable;
        this.taskName = name;
        this.taskType = taskType;
        this.statusTelemetryItem = new TelemetryItem<Boolean>("Status") {
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
        this.runnable = new BackgroundTaskRunnable<T>() {
            @Override
            public void run() {
                result = (T) checkable.check();
                telemetryItem.set((T) checkable.check());
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

    /**
     * Start the background task.
     */
    @Override
    public synchronized void start() {
        super.start();
    }

    /**
     * Run the background task.
     */
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
        synchronized (this) {
            try {
                wait(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.interrupt();
    }

    /**
     * Get the status telemetry item,
     * @return
     */
    public synchronized TelemetryItem<Boolean> getStatusTelemetryItem() {
        return statusTelemetryItem;
    }

    /**
     * Get the runnable telemetry item.
     * @return
     */
    public synchronized TelemetryItem<T> getRunnableTelemetryItem() {
        return runnable.getTelemetryItem();
    }

    public synchronized TelemetryItem<T> getFormattedTelemetry() {
        return new TelemetryGroup<T>(taskName){}.add(getStatusTelemetryItem()).add(getRunnableTelemetryItem());
    }

    /**
     * Run the initialize method prematurely.
     */
    public synchronized void runInitialize() {
        runnable.initialize();
        isInitialized = true;
    }

    /**
     * Get the result of the runnable.
     * @return
     */
    public T getResult() {
        return runnable.getResult();
    }
}
