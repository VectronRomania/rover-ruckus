package org.firstinspires.ftc.teamcode.systems.telemetry.items;

import org.firstinspires.ftc.teamcode.hardware.REVImu;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;

public final class RevImuOrientationTelemetryItem extends TelemetryItem<String> {

    private REVImu imu;

    public RevImuOrientationTelemetryItem(String tag, REVImu imu) {
        super(tag + "/Z-Y-X");
        this.imu = imu;
    }

    @Override
    public void update() {
        String builder = String.valueOf(imu.getHeading()) +
                "-" +
                imu.getRoll() +
                "-" +
                imu.getPitch();
        set(builder);
    }
}
