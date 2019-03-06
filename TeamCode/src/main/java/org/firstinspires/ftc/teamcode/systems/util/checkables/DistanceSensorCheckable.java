package org.firstinspires.ftc.teamcode.systems.util.checkables;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.systems.util.Checkable;
import org.firstinspires.ftc.teamcode.systems.util.CheckableGroup;

/**
 * Checkable for checking if the sensor is reporting a certain distance.
 */
public final class DistanceSensorCheckable implements Checkable {

    private final Rev2mDistanceSensor   sensor;

    private final DistanceUnit          distanceUnit;

    private final double                distance;

    private final double                bias;

    public DistanceSensorCheckable(final Rev2mDistanceSensor sensor,
                                   final DistanceUnit distanceUnit,
                                   final double distance,
                                   final double bias) {
        this.sensor = sensor;
        this.distanceUnit = distanceUnit;
        this.distance = distance;
        this.bias = bias;
    }

    @Override
    public synchronized Boolean check() {
        double reportedDistance;
        synchronized (sensor) {
            reportedDistance = sensor.getDistance(this.distanceUnit);
        }
        return reportedDistance < this.distance ?
                reportedDistance > this.distance - this.bias :
                reportedDistance < this.distance + this.bias;
    }

    public static Checkable getGroup(final Rev2mDistanceSensor sensor1,
                                     final Rev2mDistanceSensor sensor2,
                                     final DistanceUnit distanceUnit,
                                     final double distance,
                                     final double bias) {
        return new CheckableGroup()
                .add(new DistanceSensorCheckable(sensor1, distanceUnit, distance, bias), CheckableGroup.Operation.AND)
                .add(new DistanceSensorCheckable(sensor2, distanceUnit, distance, bias), CheckableGroup.Operation.AND);
    }
}
