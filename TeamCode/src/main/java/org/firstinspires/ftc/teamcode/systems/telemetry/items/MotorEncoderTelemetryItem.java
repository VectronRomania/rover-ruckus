package org.firstinspires.ftc.teamcode.systems.telemetry.items;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;

public final class DrivetrainEncoderTelemetryItem extends TelemetryItem<Integer> {

    private final DcMotor motor;

    /**
     * The costructor.
     * @param tag the item identifier.
     */
    public DrivetrainEncoderTelemetryItem(String tag, DcMotor motor) {
        super(tag);
        this.motor = motor;
    }

    @Override
    public void update() {
        super.set(motor.getCurrentPosition());
    }
}
