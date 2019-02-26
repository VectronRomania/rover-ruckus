package org.firstinspires.ftc.teamcode.systems.util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryGroup;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;

/**
 * Class for managing background tasks.
 */
public final class BackgroundTask<T> extends Thread {

    public static final String TAG = "BackgroundTask";

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
     * The op mode of this task.
     */
    private volatile LinearOpMode opMode;

    /**
     * The constructor.
     * @param runnable the background runnable
     * @param name the task name
     * @param taskType the task type
     */
    public BackgroundTask(@NonNull final BackgroundTaskRunnable<T> runnable,
                          @NonNull final String name,
                          @NonNull final Type taskType,
                          @NonNull final LinearOpMode opMode) {
        Log.d(TAG, "BackgroundTask() called with: runnable = [" + runnable + "], name = [" + name + "], taskType = [" + taskType + "]");
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
        this.opMode = opMode;
    }

    /**
     * Build a BackgroundTask from a Checkable.
     * @param checkable
     * @param name
     * @return
     */
    public static BackgroundTask fromCheckable(@NonNull final Checkable checkable,
                                               @NonNull final String name,
                                               @NonNull final LinearOpMode opMode) {
        Log.d(TAG, "fromCheckable() called with: checkable = [" + checkable + "], name = [" + name + "]");

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
        }, name, Type.LOOP, opMode);

        backgroundTask.statusTelemetryItem = new TelemetryItem<Boolean>(backgroundTask.taskName) {
            @Override
            public void update() {
                super.set(!backgroundTask.isAlive());
            }
        };

        Log.d(TAG, "fromCheckable() returned: " + backgroundTask);
        return backgroundTask;
    }

    /**
     * Start the background task.
     */
    @Override
    public synchronized void start() {
        Log.d(TAG, "start() called");
        super.start();
    }

    /**
     * Run the background task.
     */
    @Override
    public synchronized void run() {
        Log.d(TAG, "run() called");
        if (!this.isInitialized) {
            Log.d(TAG, "run: not initialized, initializing");
            this.runnable.initialize();
        }

        if (this.isStopRequested()) {
            Log.d(TAG, "run: stop requested");
            return;
        }

        switch (this.taskType) {
            case LOOP:
                Log.d(TAG, "run: starting loop task");
                while (!this.runnable.isFinished() && !this.isStopRequested) {
                    this.runnable.run();
                    Log.v(TAG, "run: task loop");
                }
                Log.d(TAG, "run: task exit");
                break;
            case ONE_TIME:
                Log.d(TAG, "run: starting one time task");
                this.runnable.run();
                Log.d(TAG, "run: task exit");
                break;
        }
        Log.d(TAG, "run: shutdown");
        this.runnable.shutdown();
    }

    /**
     * Stop the task.
     */
    public synchronized void stopTask() {
        Log.d(TAG, "stopTask() called");
        this.isStopRequested = true;
        this.runnable.stop();
        this.interrupt();
    }

    /**
     * Get the status telemetry item,
     * @return
     */
    public synchronized TelemetryItem<Boolean> getStatusTelemetryItem() {
        Log.d(TAG, "getStatusTelemetryItem() called");
        return statusTelemetryItem;
    }

    /**
     * Get the runnable telemetry item.
     * @return
     */
    public synchronized TelemetryItem<T> getRunnableTelemetryItem() {
        Log.d(TAG, "getRunnableTelemetryItem() called");
        return runnable.getTelemetryItem();
    }

    /**
     * Run the initialize method prematurely.
     */
    public synchronized void runInitialize() {
        Log.d(TAG, "runInitialize() called");
        runnable.initialize();
        isInitialized = true;
    }

    /**
     * Get the result of the runnable.
     * @return
     */
    public T getResult() {
        Log.d(TAG, "getResult() called");
        return runnable.getResult();
    }

    /**
     * Check if the runnable finished.
     * @return
     */
    public boolean isFinished() {
        Log.v(TAG, "isFinished() called");
        return this.runnable.isFinished();
    }

    private boolean isStopRequested() {
        Log.v(TAG, "isStopRequested() called");
        return isStopRequested ||  !opMode.opModeIsActive();
    }
}
