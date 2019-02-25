package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Lift Native Test", group = "test")
public class LiftNativeTest extends LinearOpMode {

    private DcMotor left;
    private DcMotor right;

    @Override
    public void runOpMode() {

        left = hardwareMap.get(DcMotor.class, "left_lift");
        right = hardwareMap.get(DcMotor.class, "right_lift");

        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();

        left.setTargetPosition(-1000);
        right.setTargetPosition(1000);

        left.setPower(1.0);
        right.setPower(1.0);

        while (opModeIsActive()) {
            if (left.getCurrentPosition() > 990 && right.getCurrentPosition() > 990) {
                left.setPower(0.0);
                right.setPower(0.0);
                break;
            }
            idle();
        }
    }
}
