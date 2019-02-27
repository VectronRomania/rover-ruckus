package org.firstinspires.ftc.teamcode.systems.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.drivetrain.HumanControlledDrivetrain;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryManager;

/**
 * Standard TeleOp OpMode.
 */
public abstract class TeleOpStandard extends LinearOpMode {

    /**
     * The drivetrain.
     */
    protected HumanControlledDrivetrain drivetrain;

    /**
     * The telemetry manager.
     */
    protected volatile TelemetryManager telemetryManager;

    @Override
    public void runOpMode() {

        Robot.build(hardwareMap);
        telemetryManager = new TelemetryManager(telemetry);

        initialize();

        telemetry.addData(">", "Ready");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            opModeLoop();
            telemetryManager.cycle();
        }
    }

    /**
     * Initialization method.
     */
    public abstract void initialize();

    /**
     * The Op Mode loop method.
     */
    public abstract void opModeLoop();
}
