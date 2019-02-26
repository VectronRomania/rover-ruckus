package org.firstinspires.ftc.teamcode.systems.util;

import android.support.annotation.NonNull;

import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryGroup;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;

/**
 * Class for managing background tasks.
 */
public final class BackgroundTask<T> extends Thread {

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
        this.statusTelemetryItem = new TelemetryGroup<Boolean>(name){}
            .add(new TelemetryItem<Boolean>("Running") {
                @Override
                public void update() {
                    this.set(isAlive());
                }
            }).add(new TelemetryItem<Boolean>("Finished") {
                @Override
                public void update() {
                    this.set(isFinished());
                }
            });
    }

    /**
     * Build a BackgroundTask from a Checkable.
     * @param checkable
     * @param name
     * @return
     */
    public static BackgroundTask fromCheckable(@NonNull final Checkable checkable, @NonNull final String name) {

        final BackgroundTask<Boolean> backgroundTask = new BackgroundTask<>(new BackgroundTaskRunnable<Boolean>() {
            @Override
            public void run() {
                super.result = checkable.check();
                super.telemetryItem.set(checkable.check());

                if (super.result) {
                    super.finished = true;
                }
            }

            @Override
            protected void initialize() {}

            @Override
            protected void shutdown() {}
        }, name, Type.LOOP);

        backgroundTask.statusTelemetryItem = new TelemetryItem<Boolean>(backgroundTask.taskName) {
            @Override
            public void update() {
                super.set(!backgroundTask.isAlive());
            }
        };

        return backgroundTask;
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
        if (!this.isInitialized)
            this.runnable.initialize();

        if (this.isStopRequested)
            return;

        switch (this.taskType) {
            case LOOP:
                while (!this.runnable.isFinished() && !this.isStopRequested) {
                    this.runnable.run();
                }
                break;
            case ONE_TIME:
                this.runnable.run();
                if (this.isStopRequested) {
                    return;
                }
                break;
        }
        this.runnable.shutdown();
    }

    /**
     * Stop the task.
     */
    public synchronized void stopTask() {
        this.isStopRequested = true;
        this.runnable.stop();

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

    /**
     * Check if the runnable finished.
     * @return
     */
    public boolean isFinished() {
        return this.runnable.isFinished();
    }
}
