package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.systems.AutoDrivetrain;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.Type;
import org.firstinspires.ftc.teamcode.systems.ar.TensorFlow;
import org.firstinspires.ftc.teamcode.systems.ar.Vuforia;

import java.util.List;

@TeleOp(name="Blue1", group="Auto")

public class AutonomBlue1 extends LinearOpMode {

    private int rotation = 1440;
    private double inchsPerRotation = 12.46;

    private Robot robot;
    private AutoDrivetrain autoDrivetrain;

    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private String goldPosition;

    float goldMineral = -1;
    float silverMineral_1 = -1;
    float silverMineral_2 = -1;

    private Vuforia vuforia;
    private TensorFlow tensorFlow;

    // private float value = 1440;

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();


    public void mineralDetection() {

        if (tensorFlow.tfod != null) {
            tensorFlow.tfod.activate();
        }

        while (tensorFlow.tfod != null && opModeIsActive()) {

            List<Recognition> updatedRecognitions = tensorFlow.tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                telemetry.addData("# Object Detected", updatedRecognitions.size());
                if (updatedRecognitions.size() <= 3) {

                    for (Recognition r : updatedRecognitions) {
                        if (r.getLabel().equals(LABEL_GOLD_MINERAL)) {
                            goldMineral = r.getLeft();
                        } else if (silverMineral_1 == -1) {
                            silverMineral_1 = r.getLeft();
                        } else {
                            silverMineral_2 = r.getLeft();
                        }
                    }

                    if (goldMineral < silverMineral_1 || goldMineral < silverMineral_2) {
                        goldPosition = "Left";
                        telemetry.addData("Object detected", goldPosition);
                        telemetry.update();
                        break;
                    } else if (goldMineral > silverMineral_1 || goldMineral > silverMineral_2) {
                        goldPosition = "Right";
                        telemetry.addData("Object detected", goldPosition);
                        telemetry.update();
                        break;
                    } else {
                        goldPosition = "Center";
                        telemetry.addData("Object detected", goldPosition);
                        telemetry.update();
                        break;
                    }
                }
            }
        }

        if (tensorFlow.tfod != null) {
            tensorFlow.tfod.shutdown();
        }
    }

    public void removeGoldLeft() {
        autoDrivetrain.left(2888);
        autoDrivetrain.waitToFinish();
        autoDrivetrain.moveForward(580);
        autoDrivetrain.waitToFinish();
        autoDrivetrain.moveBackward(580);
        autoDrivetrain.waitToFinish();
        autoDrivetrain.right(2888);
        autoDrivetrain.waitToFinish();
    }

    public void removeGoldCenter() {
        autoDrivetrain.moveForward(580);
        autoDrivetrain.waitToFinish();
        autoDrivetrain.moveBackward(580);
        autoDrivetrain.waitToFinish();
    }

    public void removeGoldRight() {
        autoDrivetrain.right(2888);
        autoDrivetrain.waitToFinish();
        autoDrivetrain.moveForward(580);
        autoDrivetrain.waitToFinish();
        autoDrivetrain.moveBackward(580);
        autoDrivetrain.waitToFinish();
        autoDrivetrain.left(2888);
        autoDrivetrain.waitToFinish();
    }

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot = new Robot();
        robot.init(hardwareMap);

        autoDrivetrain = new AutoDrivetrain(hardwareMap, Type.MECANUM);
//        autoDrivetrain.init(hardwareMap);

        vuforia = new Vuforia();
        tensorFlow = new TensorFlow(vuforia, hardwareMap);

        // Wait for the game to start (driver presses PLAY)
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

//          coboara lift
//            autoDrivetrain.moveLift();
//            autoDrivetrain.waitToFinish();

//          detecteaza minerale
//            mineralDetection();
//
////          inlatura minerale
//            switch(goldPosition) {
//                case "Left":
//                    removeGoldLeft();
//                    break;
//                case "Center":
//                    removeGoldCenter();
//                    break;
//                case "Right":
//                    removeGoldRight();
//                    break;
//                default :
//                    telemetry.addData("Status", "not done");
//            }

            autoDrivetrain.rotateLeft(1381);
            autoDrivetrain.waitToFinish();
            autoDrivetrain.moveForward(5444);
            autoDrivetrain.waitToFinish();

//          drop team marker
            // dropMarker();

            autoDrivetrain.rotateRight(1481);
            autoDrivetrain.waitToFinish();
            autoDrivetrain.moveForward(5444);
            autoDrivetrain.waitToFinish();

//            autoDrivetrain.rotateRight(241);
//            autoDrivetrain.waitToFinish();
//            autoDrivetrain.moveForward(1444);
//            autoDrivetrain.waitToFinish();

            telemetry.addData("Status", "Finished");
            telemetry.update();
        }
    }


    public void dropMarker() {

    }
}