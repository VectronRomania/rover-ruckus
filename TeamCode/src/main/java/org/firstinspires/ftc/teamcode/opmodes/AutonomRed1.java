package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.ClassFactory;

import org.firstinspires.ftc.teamcode.systems.AutoDrivetrain;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.Type;
import org.firstinspires.ftc.teamcode.systems.ar.TensorFlow;
import org.firstinspires.ftc.teamcode.systems.ar.Vuforia;

@TeleOp(name="Red1", group="Auto")

public class AutonomRed1 extends LinearOpMode {

    public String goldPosition;

    float goldMineral = -1;
    float silverMineral_1 = -1;
    float silverMineral_2 = -1;

    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private Vuforia vuforia;
    private TensorFlow tensorFlow;

    private Robot robot;
    private AutoDrivetrain autoDrivetrain;

    private int value = 1440;
    private boolean job1 = false;

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot = new Robot();
        robot.init(hardwareMap);

        autoDrivetrain = new AutoDrivetrain(hardwareMap, Type.MECANUM);

        vuforia = new Vuforia();
        tensorFlow = new TensorFlow(vuforia, hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

//            coboara lift

              // autoDrivetrain.moveLift();

//            detecteaza minerale



//            inlatura minerale

//            switch(goldPosition) {
//                case "Center":
//                    removeGoldCenter();
//                    break;
//                case "Left":
//                    removeGoldLeft();
//                    break;
//                case "Right":
//                    removeGolodRight();
//                    break;
//            }

            autoDrivetrain.moveForward(value * 10);
            autoDrivetrain.waitToFinish();
            autoDrivetrain.rotateRight(value * 5);


                //            autoDrivetrain.moveForward(value * 13);
//            autoDrivetrain.waitToFinish();
//            autoDrivetrain.rotateLeft(value * 20);
//            autoDrivetrain.waitToFinish();
//            autoDrivetrain.moveForward(value * 17);


//            drop team marker
//            autoDrivetrain.rotateRight(300);
//            autoDrivetrain.waitToFinish();
//            autoDrivetrain.moveForward(140);
//            autoDrivetrain.waitToFinish();

            telemetry.addData("Status", "Finished");
            telemetry.update();

            autoDrivetrain.stop();

        }
    }

    public void mineralDetection() {

    }

    public void removeGoldCenter() {

    }

    public void removeGoldLeft() {

    }

    public void removeGolodRight() {

    }
}