package org.firstinspires.ftc.teamcode.systems.autonomous;

import android.util.Pair;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.Lift;
import org.firstinspires.ftc.teamcode.systems.util.checkables.MotorEncoderCheckable;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;
import org.firstinspires.ftc.teamcode.systems.util.BackgroundTaskRunnable;
import org.firstinspires.ftc.teamcode.systems.util.Checkable;
import org.firstinspires.ftc.teamcode.systems.util.CheckableGroup;
import org.firstinspires.ftc.teamcode.systems.util.CheckableLogicalOperation;

public class LiftDeploy {

    /**
     * Checkable for checking the height reported by the distance sensors
     */
    public class LiftHeightCheckable implements Checkable {

        private final Integer minHeightMM;

        public LiftHeightCheckable(Integer minHeightMM) {
            this.minHeightMM = minHeightMM;
        }

        @Override
        public Boolean check() {
            return Robot.Lift.distanceSensor.getDistance(DistanceUnit.MM) < minHeightMM;
        }
    }

    /**
     * CheckableGroup for checking encoder values in the lift
     */
    public class LiftEncoderCheckableGroup extends CheckableGroup {
        public LiftEncoderCheckableGroup(Integer ticksLeft, Integer ticksRight, Integer bias) {
            items.add(
                    new Pair<Checkable, CheckableLogicalOperation>(
                        new MotorEncoderCheckable(Robot.Lift.left_lift, ticksLeft, bias),
                        CheckableLogicalOperation.AND
                    )
            );
            items.add(
                    new Pair<Checkable, CheckableLogicalOperation>(
                            new MotorEncoderCheckable(Robot.Lift.right_lift, ticksRight, bias),
                            CheckableLogicalOperation.AND
                    )
            );
        }
    }

    /**
     * BackgroundTaskRunnable for pulling down the lift after deployment
     */
    public class LiftDropDownRunnable extends BackgroundTaskRunnable<Boolean> {

        private final Lift lift;

        public LiftDropDownRunnable(final Lift lift) {
            this.lift = lift;
        }

        @Override
        protected void initialize() {
            Robot.Lift.setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
            this.telemetryItem = new TelemetryItem<Boolean>("Lift pulling down status") {
                @Override
                public void update() {
                    set(result);
                }
            };
        }

        @Override
        protected void shutdown() {
            lift.stop();
        }

        @Override
        public void run() {
            lift.move(Lift.Direction.DOWN, 100, 1.0);
        }
    }

    private final Lift lift;

    public LiftDeploy(final Lift lift) {
        this.lift = lift;
    }

    /**
     * Stage one of deployment moves the robot down based on the distance sensors' readings.
     */
    public void stageOne() {
        Robot.Lift.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.move(Lift.Direction.DOWN, 0.75);
    }

    /**
     * Stage two of deployment completes the touchdown process using predefined encoder ticks.
     */
    public void stageTwo() {
        Robot.Lift.setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.move(Lift.Direction.DOWN, Robot.ENCODER_TICKS_PER_FULL_ROTATION, 0.75);
    }

    /**
     * Stage three deployment unlatches the robot from the lander.
     */
    public void stageThree() {
        Robot.Lift.setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.move(Lift.Direction.UP, Robot.ENCODER_TICKS_PER_FULL_ROTATION * 3/2, 0.75);
        // TODO: 31/01/2019 write unlatching
    }
}
