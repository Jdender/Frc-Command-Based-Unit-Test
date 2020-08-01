/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final int CONTROLLER_ID = 0;

    public static final class ColorWheelConstants {

        public static final int LIFT_MOTOR_ID = 10;
        public static final int SPIN_MOTOR_ID = 11;

        // Ratio to convert seconds/tick to degrees
        public static final double ENCODER_RATIO = (360.0 / 1024.0) * 1000000;

        // We want the limit to have a ~10 degree buffer from the target
        public static final class ArmLimits {

            public static final int UP = 98;
            public static final int DOWN = 175;
        }

        public static final class ArmTargets {

            public static final int UP = 88;
            public static final int DOWN = 180;
        }
    }
}
