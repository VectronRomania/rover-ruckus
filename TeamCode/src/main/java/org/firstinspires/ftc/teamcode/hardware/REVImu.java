package org.firstinspires.ftc.teamcode.hardware;

import android.support.annotation.NonNull;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Helper class for using the REV Expansion Hub integrated IMU.
 */
public class REVImu {

    private BNO055IMU sensor;

    public REVImu(){}

    /**
     * Static method similar to HardwareMap.get().
     * @param hw the hardware map.
     * @param name the device name.
     * @param calibrationDataFileName the file name of the calibration file.
     * @return the sensor.
     */
    @NonNull
    public static REVImu get(@NonNull HardwareMap hw, @NonNull String name, @NonNull String calibrationDataFileName) {
        REVImu imu = new REVImu();
        imu.sensor = hw.get(BNO055IMU.class, name);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = calibrationDataFileName;
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU-" + name;
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu.sensor.initialize(parameters);
        return imu;
    }

    /**
     * Get the heading(Z axis).
     * @return
     */
    public double getHeading() {
        return AngleUnit.normalizeDegrees(
                AngleUnit.DEGREES.fromUnit(
                        getOrientation().angleUnit,
                        getOrientation().firstAngle)
        );
    }

    /**
     * Get the roll(Y axis).
     * @return
     */
    public double getRoll() {
        return AngleUnit.normalizeDegrees(
                AngleUnit.DEGREES.fromUnit(
                        getOrientation().angleUnit,
                        getOrientation().secondAngle)
        );
    }

    /**
     * Get the pitch(X) axis.
     * @return
     */
    public double getPitch() {
        return AngleUnit.normalizeDegrees(
                AngleUnit.DEGREES.fromUnit(
                        getOrientation().angleUnit,
                        getOrientation().thirdAngle)
        );
    }

    /**
     * Get the orientation(internal use mostly).
     * @return
     */
    public Orientation getOrientation() {
        return sensor.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
    }

}
