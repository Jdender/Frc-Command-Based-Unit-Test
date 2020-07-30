package frc.robot.colorwheel;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ColorWheelConstants.ArmPosition;

public class ColorWheelSubsystem extends SubsystemBase {

    private final ColorWheelCore colorWheel;

    public ColorWheelSubsystem(final ColorWheelCore colorWheel) {
        this.colorWheel = colorWheel;

        final var tab = Shuffleboard.getTab("Driver");
        tab.addString("Color Sensed", () -> colorWheel.getColor().name());
        tab.addString("Color Required", () -> colorWheel.getRequiredColor().name());
    }

    public interface ArmState {

        public default ArmState execute(final ColorWheelCore colorWheel) {
            return this;
        }

        public ArmState toggle(final ColorWheelCore colorWheel);

        public class GoingUp implements ArmState {
            @Override
            public ArmState execute(final ColorWheelCore colorWheel) {
                if (colorWheel.getCurrentPosition() >= ArmPosition.UP) {
                    colorWheel.stopLiftMotor();
                    return new CurrentlyUp();
                } else {
                    return this;
                }
            }

            @Override
            public ArmState toggle(final ColorWheelCore colorWheel) {
                colorWheel.startLiftMotor();
                return new GoingDown();
            }
        }

        public class CurrentlyUp implements ArmState {
            @Override
            public ArmState toggle(final ColorWheelCore colorWheel) {
                colorWheel.startLiftMotor();
                return new GoingDown();
            }
        }

        public class GoingDown implements ArmState {
            @Override
            public ArmState execute(final ColorWheelCore colorWheel) {
                if (colorWheel.getCurrentPosition() <= ArmPosition.DOWN) {
                    colorWheel.stopLiftMotor();
                    return new CurrentlyDown();
                } else {
                    return this;
                }
            }

            @Override
            public ArmState toggle(final ColorWheelCore colorWheel) {
                colorWheel.startLiftMotor();
                return new GoingUp();
            }
        }

        public class CurrentlyDown implements ArmState {
            @Override
            public ArmState toggle(final ColorWheelCore colorWheel) {
                colorWheel.startLiftMotor();
                return new GoingUp();
            }
        }
    }

    public ArmState currentState = new ArmState.CurrentlyDown();

    public void execute() {
        currentState = currentState.execute(colorWheel);
    }

    public void toggle() {
        currentState = currentState.toggle(colorWheel);
    }
}
