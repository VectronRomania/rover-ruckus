package org.firstinspires.ftc.teamcode.systems.job;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;
import org.firstinspires.ftc.teamcode.systems.util.Checkable;

/**
 * A job is a wrapper for a task that needs to be executed.
 * @param <tResult> the type of the result
 * @param <tTelemetry> the type of the telemetry
 */
public abstract class Job<tResult, tTelemetry> implements Runnable {

    /**
     * Tag used for logging.
     */
    private static final String TAG = "Job";

    /**
     * The OpMode that supervises this job.
     * This is used for checking whether the opmode is active or not.
     */
    private final LinearOpMode parentOpMode;

    /**
     * Flag for knowing whether the task should exit or not.
     */
    private boolean isStopRequested = false;

    /**
     * The result of the job.
     */
    private tResult result = null;

    /**
     * Flag for knowing whether the job is done or not,
     */
    private boolean isFinished = false;

    /**
     * The telemetry item of this job.
     */
    private TelemetryItem<tTelemetry> telemetryItem = null;

    /**
     * Checkable for knowing whether the job finished or not.
     */
    private final Checkable finishedCheckable;

    /**
     * Checkable for knowing whether the job is running or not.
     */
    private final Checkable statusCheckable;


    public Job(LinearOpMode parentOpMode) {
        this.parentOpMode = parentOpMode;
        this.finishedCheckable = new Checkable() {
            @Override
            public Boolean check() {
                synchronized ((Object) isFinished) {
                    return isFinished;
                }
            }
        };
        this.statusCheckable = null;
    }

    /**
     * Check whether the task should stop or not.
     * @return
     */
    protected synchronized boolean isStopRequested() {
        return this.isStopRequested || !this.parentOpMode.opModeIsActive();
    }

    /**
     * Retrieve the result of the job.
     * @return
     */
    public tResult getResult() {
        return this.result;
    }

    /**
     * Retrieve the telemetry item of this job.
     * @return
     */
    public TelemetryItem<tTelemetry> getTelemetryItem() {
        return this.telemetryItem;
    }

    public synchronized Checkable getFinishedCheckable() {
        return finishedCheckable;
    }

    public synchronized Checkable getStatusCheckable() {
        return statusCheckable;
    }
}
