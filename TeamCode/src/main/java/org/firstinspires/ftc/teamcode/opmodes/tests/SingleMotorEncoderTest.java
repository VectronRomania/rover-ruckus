package org.firstinspires.ftc.teamcode.opmodes.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Single motor encoder test", group="Tests")


public class SingleMotorEncoderTest extends LinearOpMode {

    public DcMotor motor;

    @Override
    public void runOpMode() {

        motor = hardwareMap.get(DcMotor.class, "motor");
        motor.setDirection(DcMotor.Direction.FORWARD);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        motor.setTargetPosition(2500);
        motor.setPower(0.5);

        while (opModeIsActive()) {
            if (!motor.isBusy())
                motor.setPower(0);

            telemetry.addData("Encoder", motor.getCurrentPosition());
            telemetry.update();
        }

    }
}
