package org.firstinspires.ftc.teamcode.systems.util.checkables;

import org.firstinspires.ftc.teamcode.hardware.REVImu;
import org.firstinspires.ftc.teamcode.systems.util.Checkable;
import org.firstinspires.ftc.teamcode.systems.util.CheckableGroup;

/**
 * Checkable for checking a value reported by an IMU.
 */
public final class ImuAxisCheckable implements Checkable {

    public enum Axis {
        X,
        Y,
        Z
    }

    private final REVImu    sensor;

    private final Axis      axis;

    private final double    angle;

    private final double    bias;

    public ImuAxisCheckable(final REVImu sensor,
                            final Axis axis,
                            final double angle,
                            final double bias) {
        this.sensor = sensor;
        this.axis = axis;
        this.angle = angle;
        this.bias = bias;
    }

    @Override
    public Boolean check() {
        Double reportedAngle = null;
        synchronized (sensor) {
            switch (axis) {
                case X:
                    reportedAngle = sensor.getPitch();
                    break;
                case Y:
                    reportedAngle = sensor.getRoll();
                    break;
                case Z:
                    reportedAngle = sensor.getHeading();
                    break;
            }
        }
        return reportedAngle < this.angle ?
                reportedAngle > this.angle - this.bias :
                reportedAngle < this.angle + this.bias;
    }

    public static Checkable getGroup(final REVImu sensor1,
                                     final REVImu sensor2,
                                     final Axis axis,
                                     final double angle,
                                     final double bias) {
        return new CheckableGroup()
                .add(new ImuAxisCheckable(sensor1, axis, angle, bias), CheckableGroup.Operation.AND)
                .add(new ImuAxisCheckable(sensor2, axis, angle, bias), CheckableGroup.Operation.AND);
    }
}
