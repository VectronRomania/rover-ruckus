package org.firstinspires.ftc.teamcode.systems;

import android.support.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.util.CheckableGroup;
import org.firstinspires.ftc.teamcode.systems.util.CheckableLogicalOperation;
import org.firstinspires.ftc.teamcode.systems.util.checkables.MotorEncoderCheckable;


public class Lift {

    private Double lastPower = 0.0;

    public enum Direction {
        UP,
        DOWN
    }

    public Lift() {
        Robot.Lift.setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE);

        Robot.Lift.setDirection(DcMotor.Direction.FORWARD);

        Robot.Lift.setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Robot.Lift.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Control the lift manually.
     * @param gamepad the gamepad used for extracting the controls.
     */
    public void manual(@NonNull Gamepad gamepad) {
        if (gamepad.left_trigger > 0) {
            Robot.Lift.setPower(gamepad.left_trigger, -gamepad.left_trigger);
            return;
        }
        if (gamepad.right_trigger > 0) {
            Robot.Lift.setPower(-gamepad.right_trigger, gamepad.right_trigger);
            return;
        }
        Robot.Lift.setPower(0.0);
    }

    /**
     * Control the lift in run to position mode.
     * @param direction
     * @param ticks
     * @param power
     */
    public CheckableGroup move(@NonNull Direction direction, @NonNull Integer ticks, @NonNull Double power) {
        switch (direction) {
            case UP:
                Robot.Lift.setTargetPosition(
                        Robot.Lift.left_lift.getCurrentPosition()   + ticks,
                        Robot.Lift.right_lift.getCurrentPosition() - ticks
                );
                break;
            case DOWN:
                Robot.Lift.setTargetPosition(
                        Robot.Lift.left_lift.getCurrentPosition()   - ticks,
                        Robot.Lift.right_lift.getCurrentPosition() + ticks
                );
                break;
        }
        Robot.Lift.setPower(power);
        CheckableGroup group = new CheckableGroup();
        group.add(new MotorEncoderCheckable(Robot.Lift.left_lift, Robot.Lift.left_lift.getTargetPosition(), 5), CheckableLogicalOperation.AND);
        group.add(new MotorEncoderCheckable(Robot.Lift.right_lift, Robot.Lift.right_lift.getTargetPosition(), 5), CheckableLogicalOperation.AND);
        return group;
    }

    /**
     * Control the lift in run using encoder mode.
     * @param direction
     * @param power
     */
    public void move(@NonNull Direction direction, @NonNull Double power) {
        switch (direction) {
            case UP:
                Robot.Lift.setPower(
                        power,
                        -power
                );
                break;
            case DOWN:
                Robot.Lift.setPower(
                        -power,
                        power
                );
                break;
        }
    }

    public void stop() {
        Robot.Lift.setTargetPosition(
                Robot.Lift.left_lift.getCurrentPosition(),
                Robot.Lift.right_lift.getCurrentPosition()
        );
        Robot.Lift.setPower(0.0);
    }

    public void brake() {
        lastPower = (Robot.Lift.left_lift.getPower() + Robot.Lift.right_lift.getPower()) / 2; // FIXME: 22/02/2019 not good

        Robot.Lift.setPower(0.0);
    }

    public void resume() {
        Robot.Lift.setPower(
                lastPower, -lastPower
        ); // FIXME: 22/02/2019 operators
    }
}
