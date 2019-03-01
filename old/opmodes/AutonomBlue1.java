package org.firstinspires.ftc.teamcode.old.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.old.systems.AutoDrivetrain;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.old.systems.ar.TensorFlow;
import org.firstinspires.ftc.teamcode.old.systems.ar.Vuforia;

import java.util.List;

@TeleOp(name="Blue1", group="Auto")

public class AutonomBlue1 extends LinearOpMode {

    private int rotation = 1440;
    private double inchsPerRotation = 12.46;

    private Robot robot;
    private AutoDrivetrain autoDrivetrain;

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private int goldPosition = -1;

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
                if (updatedRecognitions.size() == 2) {
                    int goldMineral = -1;
                    int silverMineral_1 = -1;
                    int silverMineral_2 = -1;

                    for (Recognition r : updatedRecognitions) {
                        if (r.getLabel().equals(LABEL_GOLD_MINERAL)) {
                            goldMineral = (int)(r.getLeft());
                        } else if (silverMineral_1 == -1) {
                            silverMineral_1 = (int)(r.getLeft());
                        } else {
                            silverMineral_2 = (int)(r.getLeft());
                        }
                    }

                    if (goldMineral == -1 && silverMineral_1 != -1 && silverMineral_2 != -1) {
                        telemetry.addData("GOLD MINERAL", "0");
                        goldPosition = 0;
//                        goldMineral <- left
                    } else if (goldMineral < silverMineral_1 && silverMineral_2 == -1) {
                        telemetry.addData("GOLD MINERAL", "1");
                        goldPosition = 1;
//                        goldMineral <- center
                    } else if (goldMineral > silverMineral_1 && silverMineral_2 == -1) {
                        telemetry.addData("GOLD MINERAL", "2");
                        goldPosition = 2;
//                          goldMineral <- right
//                    } else {
//                        goldPosition = "";
                    }
                    tensorFlow.tfod.shutdown();
                    telemetry.update();
                    return;
//                    if (tensorFlow.tfod != null) {
//                        tensorFlow.tfod.shutdown();
//                        tensorFlow.tfod = null;
//                        break;
//                    }
                }
                telemetry.update();
            }
        }
    }

    public void removeGoldLeft() {
        autoDrivetrain.rotateLeft(600);
        autoDrivetrain.waitToFinish();
        autoDrivetrain.moveForward(5000);
        autoDrivetrain.waitToFinish();
//        autoDrivetrain.left(2888);
//        autoDrivetrain.waitToFinish();
//        autoDrivetrain.moveForward(580);
//        autoDrivetrain.waitToFinish();
//        autoDrivetrain.moveBackward(580);
//        autoDrivetrain.waitToFinish();
//        autoDrivetrain.right(2888);
//        autoDrivetrain.waitToFinish();
    }

    public void removeGoldCenter() {
        autoDrivetrain.moveForward(5000);
        autoDrivetrain.waitToFinish();
//        autoDrivetrain.moveBackward(580);
//        autoDrivetrain.waitToFinish();
    }

    public void removeGoldRight() {
        autoDrivetrain.rotateRight(600);
        autoDrivetrain.waitToFinish();
        autoDrivetrain.moveForward(3000);
        autoDrivetrain.waitToFinish();
//        autoDrivetrain.moveBackward(580);
//        autoDrivetrain.waitToFinish();
//        autoDrivetrain.left(2888);
//        autoDrivetrain.waitToFinish();
    }

    public void dropMarker() {

    }


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        robot = new Robot();
        robot.init(hardwareMap);

        autoDrivetrain = new AutoDrivetrain(hardwareMap);
        autoDrivetrain.init(hardwareMap);

        autoDrivetrain.initialize();

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
            autoDrivetrain.moveLift();

            sleep(250);

            autoDrivetrain.right_lift.setPower(0.3);
            autoDrivetrain.left_lift.setPower(-0.3);
            sleep(2500);
            autoDrivetrain.right_lift.setPower(0);
            autoDrivetrain.left_lift.setPower(0);

            sleep(250);

            autoDrivetrain.left_back.setPower(0.3);
            autoDrivetrain.left_front.setPower(0.3);
            autoDrivetrain.right_front.setPower(0.3);
            autoDrivetrain.right_back.setPower(0.3);
            sleep(750);
            autoDrivetrain.left_back.setPower(0);
            autoDrivetrain.left_front.setPower(0);
            autoDrivetrain.right_front.setPower(0);
            autoDrivetrain.right_back.setPower(0);

            sleep(250);

            autoDrivetrain.left_back.setPower(0.3);
            autoDrivetrain.left_front.setPower(-0.3);
            autoDrivetrain.right_front.setPower(-0.3);
            autoDrivetrain.right_back.setPower(0.3);
            sleep(800);
            autoDrivetrain.left_back.setPower(0);
            autoDrivetrain.left_front.setPower(0);
            autoDrivetrain.right_front.setPower(0);
            autoDrivetrain.right_back.setPower(0);

            sleep(250);

            autoDrivetrain.left_front.setPower(-0.5);
            autoDrivetrain.left_back.setPower(-0.5);
            autoDrivetrain.right_front.setPower(0.5);
            autoDrivetrain.right_back.setPower(0.5);
            sleep(1000);
            autoDrivetrain.left_front.setPower(0);
            autoDrivetrain.left_back.setPower(0);
            autoDrivetrain.right_front.setPower(0);
            autoDrivetrain.right_back.setPower(0);





//          detecteaza minerale
//            mineralDetection();

//            while (opModeIsActive()) {
//                sleep(50);
//            }
//            return;
////          inlatura minerale
//
//            autoDrivetrain.moveForward(600);
//            autoDrivetrain.waitToFinish();
////            if (goldPosition == null) {
////                return;
////            }
//            switch(goldPosition) {
//                case 0:
//                    removeGoldLeft();
//                    telemetry.addData("Sampling", "done");
//                    break;
//                case 1:
//                    removeGoldCenter();
//                    telemetry.addData("Sampling", "done");
//                    break;
//                case 2:
//                    removeGoldRight();
//                    telemetry.addData("Sampling", "done");
//                    break;
//                default :
//                    telemetry.addData("Sampling", "failed");
//            }
//
////            autoDrivetrain.rotateLeft(1200);
////            autoDrivetrain.waitToFinish();
////            autoDrivetrain.moveForward(1244);
////            autoDrivetrain.waitToFinish();
////
//////          drop team marker
////            // dropMarker();
////
////            autoDrivetrain.rotateRight(1481);
////            autoDrivetrain.waitToFinish();
////            autoDrivetrain.moveForward(2444);
////            autoDrivetrain.waitToFinish();
////
//////            autoDrivetrain.rotateRight(241);
//////            autoDrivetrain.waitToFinish();
//////            autoDrivetrain.moveForward(1444);
//////            autoDrivetrain.waitToFinish();
//
            telemetry.addData("Status", "Finished");
            telemetry.update();
            sleep(500);
            break;
        }
    }
}