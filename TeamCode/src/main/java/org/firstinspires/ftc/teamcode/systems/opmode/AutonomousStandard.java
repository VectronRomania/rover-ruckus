package org.firstinspires.ftc.teamcode.systems.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.drivetrain.AutonomousDrivetrain;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryManager;

/**
 * Standard Autonomous OpMode.
 */
public abstract class AutonomousStandard extends LinearOpMode {

    /**
     * The drivetrain.
     */
    protected volatile AutonomousDrivetrain drivetrain;

    /**
     * The telemetry manager.
     */
    protected TelemetryManager telemetryManager;

    @Override
    public void runOpMode() {

        telemetry.addData("****", "Initializing");
        telemetry.update();

        Robot.build(hardwareMap);
        telemetryManager = new TelemetryManager(telemetry);

        initialize();
        telemetry.addData(">", "Ready");
        telemetry.update();

        waitForStart();

        opModeLoop();

        while (opModeIsActive()) {
            telemetryManager.cycle();
//            idle();
        }
    }

    /**
     * Initialization method.
     */
    protected abstract void initialize();

    /**
     * The Op Mode loop method.
     */
    protected abstract void opModeLoop();

}
