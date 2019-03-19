package org.firstinspires.ftc.teamcode.systems.autonomous;

import android.support.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.drivetrain.AutonomousDrivetrain;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.Controller;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryManager;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTask;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTaskRunnable;
import org.firstinspires.ftc.teamcode.systems.util.Checkable;

/**
 * Container for the mineral sampling process.
 */
public class MineralSampler {

    /*Pathing values measured in gold minerals*/
    private static final double LANDER_TO_SAMPLING_FIELD    = 8.5;
    private static final double MINERAL_SPACING             = 7;
    private static final double LEFT_TO_END_1               = 6.5;
    private static final double CENTER_TO_END_1             = 0.5;
    private static final double RIGHT_TO_END_1              = 7.5;
    private static final double SAMPLE_DISTANCE             = 3;
    private static final double MOVE_TO_END_2               = 3;
    private static final double START_TO_LEFT               = 3.5;
    private static final double START_TO_CENTER             = 3.5;
    private static final double START_TO_RIGHT              = 11.5;


    private final LinearOpMode parentOpMode;

    private final TelemetryManager telemetryManager;

    private final AutonomousDrivetrain drivetrain;

    public MineralSampler(final LinearOpMode parentOpMode,
                          final TelemetryManager telemetryManager,
                          final AutonomousDrivetrain drivetrain) {
        this.parentOpMode = parentOpMode;
        this.telemetryManager = telemetryManager;
        this.drivetrain = drivetrain;
    }

    /*sample the mineral by going forward then backward*/
    private void sample() {
        Checkable drivetrainCheckable = drivetrain.move(Controller.Direction.N, Robot.convertGoldToTicks(SAMPLE_DISTANCE), 0.5);
        while (this.parentOpMode.opModeIsActive() && !drivetrainCheckable.check()) {
            this.telemetryManager.cycle();
            this.parentOpMode.idle();
        }
        if (!this.parentOpMode.opModeIsActive()) {
            return;
        }

        drivetrainCheckable = drivetrain.move(Controller.Direction.S, Robot.convertGoldToTicks(SAMPLE_DISTANCE), 0.5);
        while (this.parentOpMode.opModeIsActive() && !drivetrainCheckable.check()) {
            this.telemetryManager.cycle();
            this.parentOpMode.idle();
        }
    }

    /*return to the end position from any robot position*/
    private void goToEndPoint(@NonNull MineralDetector.Position position) {
        Checkable drivetrainCheckable;
        switch (position) {
            case LEFT:
                drivetrainCheckable = drivetrain.move(Controller.Direction.E, Robot.convertGoldToTicks(LEFT_TO_END_1), 0.5);
                break;
            case CENTER:
                drivetrainCheckable = drivetrain.move(Controller.Direction.W, Robot.convertGoldToTicks(CENTER_TO_END_1), 0.5);
                break;
            default:
                drivetrainCheckable = drivetrain.move(Controller.Direction.W, Robot.convertGoldToTicks(RIGHT_TO_END_1), 0.5);
                break;
        }
        while (this.parentOpMode.opModeIsActive() && !drivetrainCheckable.check()) {
            this.telemetryManager.cycle();
            this.parentOpMode.idle();
        }
        if (!this.parentOpMode.opModeIsActive()) {
            return;
        }

        drivetrainCheckable = drivetrain.move(Controller.Direction.S, Robot.convertGoldToTicks(MOVE_TO_END_2), 0.5);
        while (this.parentOpMode.opModeIsActive() && !drivetrainCheckable.check()) {
            this.telemetryManager.cycle();
            this.parentOpMode.idle();
        }
    }

    /*check whether the color sensor detects the gold mineral or not*/
    private boolean isGoldColourDetected() {
        int r = Robot.Sensors.color_sensor.red();
        int g = Robot.Sensors.color_sensor.green();
        int b = Robot.Sensors.color_sensor.blue();
        return 0.75 * (g + b) < r;
    }

    /*sample the mineral then go to the final position*/
    private void finish(final TelemetryItem<String> telemetryItem, final MineralDetector.Position position) {
        telemetryItem.set("sampling");
        sample();
        Robot.Sensors.color_sensor.enableLed(false);
        telemetryItem.set("moving to end position");
        goToEndPoint(position);
        telemetryItem.set("done");
    }

    /**
     * Run the mineral sampler.
     * Use MineralDetector.Position.NOT_DETECTED when starting at the crater, otherwise
     * plug in the result from the mineral detector.
     * @param goldPosition the gold position reported by the detector.
     */
    public void run(@NonNull MineralDetector.Position goldPosition) {
        MineralDetector.Position currentPosition;

        TelemetryItem<String> telemetryItem = new TelemetryItem<String>("MineralSampler") {
            @Override
            public void update() {

            }
        };
        telemetryItem.set("starting");
        telemetryManager.add(telemetryItem);
        telemetryManager.cycle();

        if (!parentOpMode.opModeIsActive()) {
            return;
        }

        /*move forward in the sampling field*/
        telemetryItem.set("moving towards the sampling field");
        Checkable drivetrainCheckable = this.drivetrain.move(Controller.Direction.N, Robot.convertGoldToTicks(LANDER_TO_SAMPLING_FIELD), 0.75);
        while (this.parentOpMode.opModeIsActive() && !drivetrainCheckable.check()) {
            this.telemetryManager.cycle();
            this.parentOpMode.idle();
        }
        if (!this.parentOpMode.opModeIsActive()) {
            return;
        }

        Robot.Sensors.color_sensor.enableLed(true);
        /*move to a mineral*/
        switch (goldPosition) {
            case LEFT:
                telemetryItem.set("moving to LEFT");
                currentPosition = goldPosition;
                drivetrainCheckable = drivetrain.move(Controller.Direction.W, Robot.convertGoldToTicks(START_TO_LEFT), 0.5);
                break;
            case NOT_DETECTED:
                telemetryItem.set("moving to LEFT");
                currentPosition = MineralDetector.Position.LEFT;
                drivetrainCheckable = drivetrain.move(Controller.Direction.W, Robot.convertGoldToTicks(START_TO_CENTER), 0.5);
                break;
            case RIGHT:
                telemetryItem.set("moving to RIGHT");
                currentPosition = goldPosition;
                drivetrainCheckable = drivetrain.move(Controller.Direction.E, Robot.convertGoldToTicks(START_TO_RIGHT), 0.5);
                break;
            default: /*CENTER or DETECTED*/
                currentPosition = MineralDetector.Position.CENTER;
                telemetryItem.set("moving to CENTER");
                drivetrainCheckable = drivetrain.move(Controller.Direction.E, Robot.convertGoldToTicks(3.5), 0.5);
        }
        while (this.parentOpMode.opModeIsActive() && !drivetrainCheckable.check()) {
            this.telemetryManager.cycle();
            this.parentOpMode.idle();
        }
        if (!this.parentOpMode.opModeIsActive()) {
            return;
        }

        /*if the mineral was specifically detected, sample it*/
        if (goldPosition != MineralDetector.Position.NOT_DETECTED && goldPosition != MineralDetector.Position.DETECTED) {
            finish(telemetryItem, currentPosition);
            return;
        }

        /*check the first mineral(either LEFT or CENTER*/
        telemetryItem.set("checking " + currentPosition.toString());
        if (isGoldColourDetected()) {
            finish(telemetryItem, currentPosition);
            return;
        }

        /*if the first checked mineral was LEFT, go to the CENTER intermediate position and scan it too*/
        if (currentPosition == MineralDetector.Position.LEFT) {
            currentPosition = MineralDetector.Position.CENTER;
            telemetryItem.set("checking " + currentPosition.toString());
            drivetrainCheckable = drivetrain.move(Controller.Direction.E, Robot.convertGoldToTicks(MINERAL_SPACING), 0.5);
            while (this.parentOpMode.opModeIsActive() && !drivetrainCheckable.check()) {
                this.telemetryManager.cycle();
                this.parentOpMode.idle();
            }
            if (!this.parentOpMode.opModeIsActive()) {
                return;
            }

            if (isGoldColourDetected()) {
                finish(telemetryItem, currentPosition);
                return;
            }
            if (!this.parentOpMode.opModeIsActive()) {
                return;
            }
        }

        /*go to the RIGHT position and sample it*/
        currentPosition = MineralDetector.Position.RIGHT;
        telemetryItem.set("checking " + currentPosition.toString());
        drivetrainCheckable = drivetrain.move(Controller.Direction.E, Robot.convertGoldToTicks(MINERAL_SPACING), 0.5);
        while (this.parentOpMode.opModeIsActive() && !drivetrainCheckable.check()) {
            this.telemetryManager.cycle();
            this.parentOpMode.idle();
        }
        if (!this.parentOpMode.opModeIsActive()) {
            return;
        }
        finish(telemetryItem, currentPosition);
    }
}