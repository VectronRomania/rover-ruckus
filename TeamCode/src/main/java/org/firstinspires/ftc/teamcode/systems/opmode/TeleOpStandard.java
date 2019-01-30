package org.firstinspires.ftc.teamcode.systems.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.drivetrain.telemetry_items.MotorPowersTelemetryGroup;
import org.firstinspires.ftc.teamcode.systems.drivetrain.types.HumanControlledDrivetrain;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryManager;

/**
 * Standard TeleOp Op Mode.
 */
public abstract class TeleOpStandard extends LinearOpMode {

    /**
     * The hardware.
     */
    protected Robot robot;

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
    public abstract void initialize();

    /**
     * The Op Mode loop method.
     */
    public abstract void opModeLoop();
}
