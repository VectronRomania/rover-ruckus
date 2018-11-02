package org.firstinspires.ftc.teamcode.systems.drivetrain;

public class DriveTrainUtils {

    // TODO: 11/2/2018 create drivetrain interfaces that allow using encoders or not
    // TODO: 11/2/2018 drivetrain should be an interface

    public enum Type {
        TANK,
        OMNI,
        MECANUM
    }

    public interface DrivetrainDriver {
        void drive();
    }

    private DrivetrainDriver tankDriver = new DrivetrainDriver() {
        @Override
        public void drive() {

        }
    };

    private DrivetrainDriver omniDriver = new DrivetrainDriver() {
        @Override
        public void drive() {

        }
    };

    private DrivetrainDriver mecanumDriver = new DrivetrainDriver() {
        @Override
        public void drive() {

        }
    };
}
