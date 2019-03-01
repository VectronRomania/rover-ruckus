package org.firstinspires.ftc.teamcode.systems.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.systems.ar.TensorFlow;
import org.firstinspires.ftc.teamcode.systems.ar.Vuforia;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryGroup;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTask;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTaskRunnable;

import java.util.List;

/**
 * Component for detecting minerals.
 */
public class MineralDetector {

    /**
     * The gold mineral position reported by the detector.
     */
    public enum Position {
        NOT_DETECTED,
        LEFT,
        CENTER,
        RIGHT,
        DETECTED;

        @Override
        public String toString() {
            switch (this) {
                case NOT_DETECTED:
                    return "NOT_DETECTED";
                case LEFT:
                    return "LEFT";
                case CENTER:
                    return "CENTER";
                case RIGHT:
                    return "RIGHT";
                case DETECTED:
                    return "DETECTED";
                default:
                    return "NULL";
            }
        }

        public static Position get(int position) {
            switch (position) {
                case -1:
                    return Position.NOT_DETECTED;
                case 0:
                    return Position.DETECTED;
                case 1:
                    return Position.LEFT;
                case 2:
                    return Position.CENTER;
                case 3:
                    return Position.RIGHT;
                default:
                    return Position.NOT_DETECTED;
            }
        }
    }

    /**
     * The type of the detected mineral.
     */
    public enum MineralType {
        GOLD,
        SILVER,
        UNKNOWN;

        public static MineralType get(Recognition recognition) {
            switch (recognition.getLabel()) {
                case Constants.LABEL_GOLD_MINERAL:
                    return MineralType.GOLD;
                case Constants.LABEL_SILVER_MINERAL:
                    return MineralType.SILVER;
                default:
                    return MineralType.UNKNOWN;
            }
        }
    }

    private final TensorFlow tensorFlow;
    private final LinearOpMode opMode;
    private List<Recognition> updatedRecognitions = null;


    /**
     * Flag that changes where the sample will write detection results to.
     */
    private boolean deployed = false;


    /**
     * Detection during deployment.
     */
    private boolean deploymentGoldDetected = false;
    /**
     * The position if one mineral(gold) is detected
     */
    private Position deploymentGoldPosition = Position.NOT_DETECTED;
    /**
     * The position if two minerals are detected.
     */
    private Position deploymentGoldPosition2 = Position.NOT_DETECTED;
    /**
     * The position if three minerals are detected.
     */
    private Position deploymentGoldPosition3 = Position.NOT_DETECTED; // FIXME: 27/02/2019 buggy, crater holds more minerals


    /**
     * Detection during sampling.
     */
    private boolean samplingGoldDetected = false;
    /**
     * The position if one mineral(gold) is detected
     */
    private Position samplingGoldPosition = Position.NOT_DETECTED;
    /**
     * The position if two minerals are detected.
     */
    private Position samplingGoldPosition2 = Position.NOT_DETECTED;

    public MineralDetector(final HardwareMap hw, final LinearOpMode opMode) {
        tensorFlow = new TensorFlow(new Vuforia(), hw);
        this.opMode = opMode;
    }

    public BackgroundTask<String> getDetector() {
        return new BackgroundTask<>(new BackgroundTaskRunnable<String>() {
            @Override
            protected synchronized void initialize() {
                super.result = "";
                if (tensorFlow.tfod != null) {
                    tensorFlow.tfod.activate();
                }

                TelemetryGroup<String> preDeployment = new TelemetryGroup<String>("Pre-deployment") {}
                        .add(new TelemetryItem<String>("Detected") {
                            @Override
                            public void update() {
                                this.set(String.valueOf(deploymentGoldDetected));
                            }
                        })
                        .add(new TelemetryItem<String>("1 detected") {
                            @Override
                            public void update() {
                                this.set(deploymentGoldPosition.toString());
                            }
                        })
                        .add(new TelemetryItem<String>("2 detected") {
                            @Override
                            public void update() {
                                this.set(deploymentGoldPosition2.toString());
                            }
                        })
                        .add(new TelemetryItem<String>("3 detected") {
                            @Override
                            public void update() {
                                this.set(deploymentGoldPosition3.toString());
                            }
                        });

                TelemetryGroup<String> postDeployment = new TelemetryGroup<String>("Post-deployment") {}
                        .add(new TelemetryItem<String>("Detected") {
                            @Override
                            public void update() {
                                this.set(String.valueOf(samplingGoldDetected));
                            }
                        })
                        .add(new TelemetryItem<String>("1 detected") {
                            @Override
                            public void update() {
                                this.set(samplingGoldPosition.toString());
                            }
                        })
                        .add(new TelemetryItem<String>("2 detected") {
                            @Override
                            public void update() {
                                this.set(samplingGoldPosition2.toString());
                            }
                        });

                super.telemetryItem = new TelemetryGroup<String>("Detector readings") {}
                        .add(preDeployment)
                        .add(postDeployment);
            }

            @Override
            protected void shutdown() {
                super.telemetryItem.set(super.result);
                if (super.isStopRequested)
                    return;
                if (tensorFlow.tfod != null) {
                    tensorFlow.tfod.shutdown();
                }
            }

            @Override
            public void run() {

                if (isStopRequested) {
                    return;
                }

                if (!deployed) {
                    deploymentDetection();
                } else {
                    samplingDetection();
                }

            }
        }, "Detector", BackgroundTask.Type.LOOP, opMode);
    }

    public synchronized void switchToDeployed() {
        synchronized ((Object) this.deployed) {
            this.deployed = true;
        }
    }

    /**
     * Load data from TensorFlow.
     * @return the size of the list.
     */
    private int getUpdatedRecognitions() {
        updatedRecognitions = tensorFlow.tfod.getUpdatedRecognitions();

        if (updatedRecognitions == null) {
            return 0;
        }
        return updatedRecognitions.size();
    }

    private void deploymentDetection() {
        int size = getUpdatedRecognitions();

        if (size < 1 || size > 3) {
            return;
        }

//        One mineral detected
        if (size == 1) {
            if (MineralType.get(updatedRecognitions.get(0)) != MineralType.GOLD) {
                return;
            }
            this.deploymentGoldDetected = true;
            this.deploymentGoldPosition = Position.DETECTED;
            return;
        }

//        Two minerals detected
        if (size == 2) {
            int k = 2;
            int silverDetected = 0;
            for (Recognition recognition : updatedRecognitions) {
                if (MineralType.get(recognition) == MineralType.GOLD) {
                    this.deploymentGoldDetected = true;
                    this.deploymentGoldPosition2 = Position.get(k);
                    continue;
                }
                if (MineralType.get(recognition) == MineralType.SILVER) {
                    silverDetected++;
                }
//                if 2 silver were detected then the gold mineral is in the left position
                if (silverDetected == 2) {
                    this.deploymentGoldDetected = true;
                    this.deploymentGoldPosition2 = Position.LEFT;
                }
                k++;
            }
//            return;
        }

////        Three minerals detected
//        int k = 1;
//        for (Recognition recognition : updatedRecognitions) {
//            if (MineralType.get(recognition) != MineralType.GOLD) {
//                k++;
//                continue;
//            }
//            this.deploymentGoldDetected = true;
//            this.deploymentGoldPosition2 = Position.get(k);
//        }

//        More objects detected

    }

    public synchronized boolean isDeploymentGoldDetected() {
        return this.deploymentGoldDetected;
    }

    public synchronized Position getDeploymentGoldPosition() {
        return this.deploymentGoldPosition;
    }

    public synchronized Position getDeploymentGoldPosition2() {
        return this.deploymentGoldPosition2;
    }

    public synchronized Position getDeploymentGoldPosition3() {
        return this.deploymentGoldPosition3;
    }


    private void samplingDetection() {
        int size = getUpdatedRecognitions();

        if (size < 0 || size > 2) {
            return;
        }

//        One mineral detected
        if (size == 1) {
            if (MineralType.get(updatedRecognitions.get(0)) != MineralType.GOLD) {
                return;
            }
            this.samplingGoldDetected = true;
            this.samplingGoldPosition = Position.DETECTED;
            return;
        }

//        two minerals detected
        int k = 0;
        int silverCount = 0;
        for (Recognition recognition : this.updatedRecognitions) {
            if (MineralType.get(recognition) == MineralType.GOLD) {
                this.samplingGoldDetected = true;
                this.samplingGoldPosition2 = Position.get(k);
            }
            if (MineralType.get(recognition) == MineralType.SILVER) {
                silverCount++;
            }
            if (silverCount == 2) {
                this.samplingGoldDetected = true;
                this.samplingGoldPosition2 = Position.RIGHT;
            }
            k++;
            continue;

        }
    }

    public synchronized boolean isSamplingGoldDetected() {
        return this.samplingGoldDetected;
    }

    public synchronized Position getSamplingGoldPosition() {
        return this.samplingGoldPosition;
    }

    public synchronized Position getSamplingtGoldPosition2() {
        return this.samplingGoldPosition2;
    }
}
