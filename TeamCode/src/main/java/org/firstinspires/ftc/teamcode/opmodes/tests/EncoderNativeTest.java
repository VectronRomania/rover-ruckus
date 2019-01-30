package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Encoder Native Test", group = "test")
public class EncoderNativeTest extends LinearOpMode {

    private DcMotor a;
    private DcMotor b;
    private DcMotor c;
    private DcMotor d;

    @Override
    public void runOpMode() {
        a = hardwareMap.get(DcMotor.class, "left_front");
        b = hardwareMap.get(DcMotor.class, "right_front");
        c = hardwareMap.get(DcMotor.class, "right_back");
        d = hardwareMap.get(DcMotor.class, "left_back");

        a.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        a.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        b.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        b.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        c.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        c.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        d.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        d.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();

        a.setTargetPosition(-1000);
        b.setTargetPosition(1000);
        c.setTargetPosition(1000);
        d.setTargetPosition(-1000);

        a.setPower(0.25);
        b.setPower(0.25);
        c.setPower(0.25);
        d.setPower(0.25);

        while (opModeIsActive()) {
            if (a.getCurrentPosition() < -990 &&
                    b.getCurrentPosition() > 990 &&
                    c.getCurrentPosition() > 990 &&
                    d.getCurrentPosition() < -990
                    ) {
                a.setPower(0.0);
                b.setPower(0.0);
                c.setPower(0.0);
                d.setPower(0.0);

                break;
            }

        }
    }
}
