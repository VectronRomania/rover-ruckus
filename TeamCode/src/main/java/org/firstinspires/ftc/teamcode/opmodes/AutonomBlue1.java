package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.systems.AutoDrivetrain;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.Type;

@TeleOp(name="Blue1", group="Auto")
@Disabled
public class AutonomBlue1 extends LinearOpMode {

    private Robot robot;
    private AutoDrivetrain autoDrivetrain;

    // private float value = 1440;

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot = new Robot();
        robot.init(hardwareMap);

        autoDrivetrain = new AutoDrivetrain(hardwareMap, Type.MECANUM);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

//            coboara lift
//            detecteaza minerale
//            inlatura minerale

            autoDrivetrain.rotateLeft(160);
            autoDrivetrain.moveForward(200);
            autoDrivetrain.rotateLeft(50);
            autoDrivetrain.moveForward(500);

//            drop team marker
            autoDrivetrain.rotateRight(300);
            autoDrivetrain.moveForward(1140);


            telemetry.addData("Status", "Finished");
            telemetry.update();
        }
    }
}