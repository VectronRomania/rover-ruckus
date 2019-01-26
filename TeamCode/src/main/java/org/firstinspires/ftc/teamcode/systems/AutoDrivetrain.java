package org.firstinspires.ftc.teamcode.systems;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.opmodes.tests.DistanceSensorTest;

public class AutoDrivetrain extends Robot {

    private Type type;

    public int currentDistance;
    public int left_front_encoder;
    public int left_back_encoder;
    public int right_front_encoder;
    public int right_back_encoder;

    private boolean lock;

    public AutoDrivetrain(HardwareMap hardwareMap) {

        super();
        super.init(hardwareMap);

        super.left_back.setDirection(DcMotor.Direction.FORWARD);
        super.left_front.setDirection(DcMotor.Direction.FORWARD);
        super.right_back.setDirection(DcMotor.Direction.FORWARD);
        super.right_front.setDirection(DcMotor.Direction.FORWARD);

        super.left_back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        super.left_front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        super.right_back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        super.right_front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        super.left_back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        super.left_front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        super.right_back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        super.right_front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void initialize(){
        left_front_encoder = super.left_front.getCurrentPosition();
        left_back_encoder = super.left_back.getCurrentPosition();
        right_front_encoder = super.right_front.getCurrentPosition();
        right_back_encoder = super.right_back.getCurrentPosition();

    }

    public void moveForward (int ticks){
        super.left_back.setPower(0.5f);
        super.left_front.setPower(0.5f);
        super.right_back.setPower(0.5f);
        super.right_front.setPower(0.5f);

        super.left_back.setTargetPosition(left_back_encoder - ticks);
        super.left_front.setTargetPosition(left_front_encoder - ticks);
        super.right_back.setTargetPosition(right_back_encoder + ticks);
        super.right_front.setTargetPosition(right_front_encoder + ticks);
    }

    public void moveBackward (int ticks){
        super.left_back.setPower(0.5f);
        super.left_front.setPower(0.5f);
        super.right_back.setPower(0.5f);
        super.right_front.setPower(0.5f);

        super.left_back.setTargetPosition(super.left_back.getCurrentPosition() + ticks);
        super.left_front.setTargetPosition(super.left_front.getCurrentPosition() + ticks);
        super.right_back.setTargetPosition(super.right_back.getCurrentPosition() - ticks);
        super.right_front.setTargetPosition(super.right_front.getCurrentPosition() - ticks);
    }

    public void diagonallyRight (int ticks){
        super.left_back.setPower(0.5f);
        super.left_front.setPower(0.5f);
        super.right_back.setPower(0.5f);
        super.right_front.setPower(0.5f);

        super.left_back.setTargetPosition(super.left_back.getCurrentPosition() + ticks);
        super.left_front.setTargetPosition(super.left_front.getCurrentPosition());
        super.right_back.setTargetPosition(super.right_back.getCurrentPosition());
        super.right_front.setTargetPosition(super.right_front.getCurrentPosition() - ticks);
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
        super.left_back.setPower(0.5f);
        super.left_front.setPower(0.5f);
        super.right_back.setPower(0.5f);
        super.right_front.setPower(0.5f);

        super.left_back.setTargetPosition(super.left_back.getCurrentPosition() + ticks);
        super.left_front.setTargetPosition(super.left_front.getCurrentPosition() + ticks);
        super.right_back.setTargetPosition(super.right_back.getCurrentPosition() + ticks);
        super.right_front.setTargetPosition(super.right_front.getCurrentPosition() + ticks);
    }

    public void rotateLeft (int ticks) {
        super.left_back.setPower(0.5f);
        super.left_front.setPower(0.5f);
        super.right_back.setPower(0.5f);
        super.right_front.setPower(0.5f);

        super.left_back.setTargetPosition(super.left_back.getCurrentPosition() - ticks);
        super.left_front.setTargetPosition(super.left_front.getCurrentPosition() - ticks);
        super.right_back.setTargetPosition(super.right_back.getCurrentPosition() - ticks);
        super.right_front.setTargetPosition(super.right_front.getCurrentPosition() - ticks);
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

    public void stop(){
        left_back.setPower(0f);
        left_front.setPower(0f);
        right_back.setPower(0f);
        right_front.setPower(0f);
    }

    public void waitToFinish() {
        while ( left_front.isBusy() && right_front.isBusy() && left_back.isBusy() && right_back.isBusy() ) {

        }
        stop();
    }

    public void moveLift() {
        super.right_lift.setPower(0.5f);
        super.left_lift.setPower(-0.5f);
        currentDistance = (int)(distanceSensor.getDistance(DistanceUnit.MM));
        while(currentDistance >= 95) {
            if (currentDistance <= 95) {
                super.right_lift.setPower(0f);
                super.left_lift.setPower(0f);
                break;
            }
            currentDistance = (int)(distanceSensor.getDistance(DistanceUnit.MM));
        }
    }



    public boolean getLockStatus() { return this.lock; }
    public void unlock() { this.lock = false; }
}


