package org.firstinspires.ftc.teamcode.systems.autonomous;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.systems.ar.TensorFlow;
import org.firstinspires.ftc.teamcode.systems.ar.Vuforia;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTaskRunnable;

import java.util.List;

/**
 * Component for detecting minerals.
 */
public class MineralDetectionBackgroundRunnable extends BackgroundTaskRunnable<Integer> {

    private final TensorFlow tensorFlow;

    public MineralDetectionBackgroundRunnable(final HardwareMap hw) {
        tensorFlow = new TensorFlow(new Vuforia(), hw);
    }

    @Override
    protected synchronized void initialize() {
        if (tensorFlow.tfod != null) {
            tensorFlow.tfod.activate();
        }

        super.telemetryItem = new TelemetryItem<Integer>("Gold position") {
            @Override
            public void update() {
                        this.set(result);
            }
        };
    }


    @Override
    protected void shutdown() {
        if (super.isStopRequested)
            return;
        if (tensorFlow.tfod != null) {
            tensorFlow.tfod.shutdown();
        }
    }

    @Override
    public void run() {
        if (tensorFlow.tfod == null) {
            super.isStopRequested = true;
            return;
        }

        List<Recognition> updatedRecognitions = tensorFlow.tfod.getUpdatedRecognitions();

        if (updatedRecognitions != null) {
            if (updatedRecognitions.size() < 2)
                return;

            if (updatedRecognitions.size() == 2) {
                int goldMineral = -1;
                int silverMineral_1 = -1;
                int silverMineral_2 = -1;

                for (Recognition r : updatedRecognitions) {
                    if (r.getLabel().equals(Constants.LABEL_GOLD_MINERAL)) {
                        goldMineral = (int) (r.getLeft());
                    } else if (silverMineral_1 == -1) {
                                silverMineral_1 = (int) (r.getLeft());
                    } else {
                        silverMineral_2 = (int) (r.getLeft());
                    }
                }

                if (goldMineral == -1 && silverMineral_1 != -1 && silverMineral_2 != -1) {
                    result = 0;
                    finished = true;
//                  goldMineral <- servoLeft
                } else if (goldMineral < silverMineral_1 && silverMineral_2 == -1) {
                    result = 1;
                    finished = true;
//                  goldMineral <- center
                } else if (goldMineral > silverMineral_1 && silverMineral_2 == -1) {
                    result = 2;
                    finished = true;
//                  goldMineral <- servoRight
                }
            }
        }
    }
}
