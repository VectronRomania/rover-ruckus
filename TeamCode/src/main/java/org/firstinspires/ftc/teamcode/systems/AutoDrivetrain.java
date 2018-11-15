package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.Robot;

public class AutoDrivetrain extends Robot {

    private Type type;

    public AutoDrivetrain(HardwareMap hw, Type t) {

        super();
        super.init(hw);

        left_back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        left_front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right_front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        left_back.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        left_front.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right_back.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right_front.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void moveForward (int ticks){
        left_back.setPower(0.5f);
        left_front.setPower(0.5f);
        right_back.setPower(0.5f);
        right_front.setPower(0.5f);

        left_back.setTargetPosition(left_back.getCurrentPosition() + ticks);
        left_front.setTargetPosition(left_front.getCurrentPosition() + ticks);
        right_back.setTargetPosition(right_back.getCurrentPosition() - ticks);
        right_front.setTargetPosition(right_front.getCurrentPosition() - ticks);
    }

    public void moveBackward (int ticks){
        left_back.setPower(0.5f);
        left_front.setPower(0.5f);
        right_back.setPower(0.5f);
        right_front.setPower(0.5f);

        left_back.setTargetPosition(left_back.getCurrentPosition() - ticks);
        left_front.setTargetPosition(left_front.getCurrentPosition() - ticks);
        right_back.setTargetPosition(right_back.getCurrentPosition() + ticks);
        right_front.setTargetPosition(right_front.getCurrentPosition() + ticks);
    }

    public void diagonallyRight (int ticks){
        left_back.setPower(0.5f);
        left_front.setPower(0.5f);
        right_back.setPower(0.5f);
        right_front.setPower(0.5f);

        left_back.setTargetPosition(left_back.getCurrentPosition() + ticks);
        left_front.setTargetPosition(left_front.getCurrentPosition());
        right_back.setTargetPosition(right_back.getCurrentPosition());
        right_front.setTargetPosition(right_front.getCurrentPosition() - ticks);
    }

    public void diagonallyLeft (int ticks){
        left_back.setPower(0.5f);
        left_front.setPower(0.5f);
        right_back.setPower(0.5f);
        right_front.setPower(0.5f);

        left_back.setTargetPosition(left_back.getCurrentPosition());
        left_front.setTargetPosition(left_front.getCurrentPosition() + ticks);
        right_back.setTargetPosition(right_back.getCurrentPosition() - ticks);
        right_front.setTargetPosition(right_front.getCurrentPosition());
    }

    public void rotateRight (int ticks) {
        left_back.setPower(0.5f);
        left_front.setPower(0.5f);
        right_back.setPower(0.5f);
        right_front.setPower(0.5f);

        left_back.setTargetPosition(left_back.getCurrentPosition() + ticks);
        left_front.setTargetPosition(left_front.getCurrentPosition() + ticks);
        right_back.setTargetPosition(right_back.getCurrentPosition() + ticks);
        right_front.setTargetPosition(right_front.getCurrentPosition() + ticks);
    }

    public void rotateLeft (int ticks) {
        left_back.setPower(0.5f);
        left_front.setPower(0.5f);
        right_back.setPower(0.5f);
        right_front.setPower(0.5f);

        left_back.setTargetPosition(left_back.getCurrentPosition() - ticks);
        left_front.setTargetPosition(left_front.getCurrentPosition() - ticks);
        right_back.setTargetPosition(right_back.getCurrentPosition() - ticks);
        right_front.setTargetPosition(right_front.getCurrentPosition() - ticks);
    }

    public void right (int ticks) {
        left_back.setPower(0.5f);
        left_front.setPower(0.5f);
        right_back.setPower(0.5f);
        right_front.setPower(0.5f);

        left_back.setTargetPosition(left_back.getCurrentPosition() - ticks);
        left_front.setTargetPosition(left_front.getCurrentPosition() + ticks);
        right_back.setTargetPosition(right_back.getCurrentPosition() - ticks);
        right_front.setTargetPosition(right_front.getCurrentPosition() + ticks);
    }

    public void left (int ticks) {
        left_back.setPower(0.5f);
        left_front.setPower(0.5f);
        right_back.setPower(0.5f);
        right_front.setPower(0.5f);

        left_back.setTargetPosition(left_back.getCurrentPosition() + ticks);
        left_front.setTargetPosition(left_front.getCurrentPosition() - ticks);
        right_back.setTargetPosition(right_back.getCurrentPosition() + ticks);
        right_front.setTargetPosition(right_front.getCurrentPosition() - ticks);
    }
}
