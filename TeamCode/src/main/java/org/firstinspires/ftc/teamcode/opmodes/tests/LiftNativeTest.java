package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

//@Disabled
@Autonomous(name = "Lift Native Test", group = "test")
public class LiftNativeTest extends LinearOpMode {

    private DcMotor motor;

    @Override
    public void runOpMode() {

        motor = hardwareMap.get(DcMotor.class, "motor");
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();

        motor.setTargetPosition(-1000);

        motor.setPower(1.0);

        while (opModeIsActive()) {
            if (motor.getCurrentPosition() > 990) {
                motor.setPower(0.0);
                break;
            }
            idle();
        }
    }
}
