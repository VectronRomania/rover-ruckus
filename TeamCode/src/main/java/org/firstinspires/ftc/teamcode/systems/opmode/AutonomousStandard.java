package org.firstinspires.ftc.teamcode.systems.opmode;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.autonomous.Task;
import org.firstinspires.ftc.teamcode.systems.drivetrain.telemetry_items.MotorPowersTelemetryGroup;
import org.firstinspires.ftc.teamcode.systems.drivetrain.types.HumanControlledDrivetrain;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryManager;

import java.util.ArrayList;

public abstract class AutonomousStandard extends OpModeStandard {

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
    protected TelemetryManager telemetryManager;

    protected ArrayList<Task> tasks;

    @Override
    public void runOpMode() {

        robot = Robot.build(hardwareMap);

        telemetryManager = new TelemetryManager(telemetry);

        telemetryManager.add(new MotorPowersTelemetryGroup());

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
    public void opModeLoop() {

    }
}
