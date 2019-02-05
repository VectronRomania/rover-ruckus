package org.firstinspires.ftc.teamcode.systems.telemetry.items;

import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryGroup;
import org.firstinspires.ftc.teamcode.systems.telemetry.TelemetryItem;

public class RevImuOrientationTelemetryGroup extends TelemetryGroup<String> {

    public RevImuOrientationTelemetryGroup(String tag) {
        super(tag);

        add(new RevImuOrientationTelemetryItem(
                "left_imu",
                Robot.Sensors.left_imu
        ));
        add(new RevImuOrientationTelemetryItem(
                "right_imu",
                Robot.Sensors.right_imu
        ));

    }
}
