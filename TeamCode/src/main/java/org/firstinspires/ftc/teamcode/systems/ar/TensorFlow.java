package org.firstinspires.ftc.teamcode.systems.ar;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.Constants;

public class TensorFlow {

    public TFObjectDetector tfod;

    public TensorFlow(Vuforia vuforia, HardwareMap hardwareMap) {

        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);

        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia.vuforiaLocalizer);

        tfod.loadModelFromAsset(Constants.TFOD_MODEL_ASSET,
                Constants.LABEL_GOLD_MINERAL,
                Constants.LABEL_SILVER_MINERAL);
    }

}
