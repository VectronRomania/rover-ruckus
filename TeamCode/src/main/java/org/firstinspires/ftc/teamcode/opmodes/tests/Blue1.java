package org.firstinspires.ftc.teamcode.opmodes.tests;

import org.firstinspires.ftc.teamcode.systems.Lift;
import org.firstinspires.ftc.teamcode.systems.drivetrain.WheelBase;
import org.firstinspires.ftc.teamcode.systems.drivetrain.controller.Controller;
import org.firstinspires.ftc.teamcode.systems.drivetrain.AutonomousDrivetrain;
import org.firstinspires.ftc.teamcode.systems.opmode.AutonomousStandard;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTask;
import org.firstinspires.ftc.teamcode.systems.util.background_runnables.MineralDetectionBackgroundRunnable;

public class Blue1 extends AutonomousStandard {

    private BackgroundTask mineralDetection;
    private Lift lift;

    @Override
    protected void initialize() {
        drivetrain = new AutonomousDrivetrain(robot, WheelBase.MECANUM);
        mineralDetection = new BackgroundTask(
                new MineralDetectionBackgroundRunnable(hardwareMap),
                "detector",
                BackgroundTask.Type.LOOP
        );
        mineralDetection.runInitialize();
        lift = new Lift(robot);
        lift.init();
    }

    @Override
    protected void opModeLoop() {
        lift.move(Lift.Direction.DOWN, 2000, 0.7);
        mineralDetection.start();
        mineralDetection.getRunnableTelemetryItem().get();
        drivetrain.move(
                Controller.Direction.N,
                1200,
                0.5
        );
    }
}
