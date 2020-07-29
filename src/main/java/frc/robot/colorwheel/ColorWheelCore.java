package frc.robot.colorwheel;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Counter.Mode;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.ColorSensorV3;
// import com.revrobotics.CANSparkMaxLowLevel.MotorType;
// import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorMatch;

import static frc.robot.Constants.ColorWheelConstants.*;

public class ColorWheelCore extends SubsystemBase {

    private final Counter encoder = new Counter(Mode.kSemiperiod);
    private final TalonSRX liftMotor = new TalonSRX(LIFT_MOTOR_ID);
    // private final CANSparkMax spinMotor = new CANSparkMax(SPIN_MOTOR_ID,
    // MotorType.kBrushless);

    public double getCurrentPosition() {
        return encoder.getPeriod() * ENCODER_RATIO;
    }

    public void setLiftMotorSpeed(final double speed) {
        liftMotor.set(ControlMode.PercentOutput, speed);
    }

    private final ColorSensorV3 colorSensor = new ColorSensorV3(I2C.Port.kOnboard);

    private final ColorMatch colorMatcher = new ColorMatch();

    // These require a ColorMatch instance so they're not in Constants
    private final Color redTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color greenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color blueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color yellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    public ColorWheelCore() {
        colorMatcher.addColorMatch(redTarget);
        colorMatcher.addColorMatch(greenTarget);
        colorMatcher.addColorMatch(blueTarget);
        colorMatcher.addColorMatch(yellowTarget);
    }

    public enum ColorResult {
        Red, Green, Blue, Yellow, Unknown,
    }

    public ColorResult getColor() {
        final var detectedColor = colorSensor.getColor();
        final var match = colorMatcher.matchClosestColor(detectedColor);

        // Match the resulting color to the static matches to return it's enum value
        if (match.color == redTarget) {
            return ColorResult.Red;
        } else if (match.color == greenTarget) {
            return ColorResult.Green;
        } else if (match.color == blueTarget) {
            return ColorResult.Blue;
        } else if (match.color == yellowTarget) {
            return ColorResult.Yellow;
        } else {
            return ColorResult.Unknown;
        }
    }

    public ColorResult getRequiredColor() {
        final var gameData = DriverStation.getInstance().getGameSpecificMessage();

        if (gameData.length() == 0) {
            return ColorResult.Unknown;
        }

        switch (gameData.charAt(0)) {
        case 'R':
            return ColorResult.Red;
        case 'G':
            return ColorResult.Green;
        case 'B':
            return ColorResult.Blue;
        case 'Y':
            return ColorResult.Yellow;
        default:
            return ColorResult.Unknown;
        }
    }
}
