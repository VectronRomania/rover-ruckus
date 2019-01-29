package org.firstinspires.ftc.teamcode.old.systems.ar;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

public class TensorFlow {

    public TFObjectDetector tfod;

    public TensorFlow(Vuforia vuforia, HardwareMap hardwareMap) {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia.vuforiaLocalizer);
        tfod.loadModelFromAsset(org.firstinspires.ftc.teamcode.old.constants.TensorFlow.TFOD_MODEL_ASSET, org.firstinspires.ftc.teamcode.old.constants.TensorFlow.LABEL_GOLD_MINERAL, org.firstinspires.ftc.teamcode.old.constants.TensorFlow.LABEL_SILVER_MINERAL);
    }
}
