package org.firstinspires.ftc.teamcode.systems.ar;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.Constants;

public class Vuforia {

    public VuforiaLocalizer vuforiaLocalizer;

    public Vuforia() {

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = Constants.VUFORIA_KEY;
        parameters.cameraDirection = Constants.CAMERA_DIRECTION;

        vuforiaLocalizer = ClassFactory.getInstance().createVuforia(parameters);

    }
}
