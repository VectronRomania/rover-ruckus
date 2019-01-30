package org.firstinspires.ftc.teamcode.systems.opmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.drivetrain.types.AutonomousDrivetrain;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryManager;

public abstract class AutonomousStandard extends LinearOpMode {

    /**
     * The hardware.
     */
    protected Robot robot;

    /**
     * The drivetrain.
     */
    protected AutonomousDrivetrain drivetrain;

    /**
     * The telemetry manager.
     */
    protected volatile TelemetryManager telemetryManager;

    @Override
    public void runOpMode() {

        robot = Robot.build(hardwareMap);
        telemetryManager = new TelemetryManager(telemetry);

        initialize();

        waitForStart();

        while (opModeIsActive()) {
            opModeLoop();
            telemetryManager.cycle();
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
