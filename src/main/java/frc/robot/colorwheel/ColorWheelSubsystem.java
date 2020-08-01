package frc.robot.colorwheel;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ColorWheelConstants.ArmLimits;
import frc.robot.Constants.ColorWheelConstants.ArmTargets;

public class ColorWheelSubsystem extends SubsystemBase {

    private final ColorWheelCore colorWheel;
    public ArmState currentState;

    public ColorWheelSubsystem(final ColorWheelCore colorWheel) {
        this.colorWheel = colorWheel;

        currentState = new ArmState.CurrentlyDown(colorWheel);

        final var tab = Shuffleboard.getTab("Driver");
        tab.addString("Color Sensed", () -> colorWheel.getColor().name());
        tab.addString("Color Required", () -> colorWheel.getRequiredColor().name());

        final var debugTab = Shuffleboard.getTab("Debug");
        debugTab.addString("Current State", () -> this.currentState.getClass().getSimpleName());
    }

    public interface ArmState {

        public default ArmState execute(final ColorWheelCore colorWheel) {
            return this;
        }

        public ArmState toggle();

        public class CurrentlyDown implements ArmState {
            public CurrentlyDown(final ColorWheelCore colorWheel) {
                colorWheel.stopLiftMotor();
            }

            @Override
            public ArmState toggle() {
                return new GoingUp();
            }
        }

        public class GoingUp implements ArmState {
            @Override
            public ArmState execute(final ColorWheelCore colorWheel) {
                if (colorWheel.getCurrentPosition() <= ArmLimits.UP) {
                    return new CurrentlyUp(colorWheel);
                } else {
                    colorWheel.updateLiftMotor(ArmTargets.UP);
                    return this;
                }
            }

            @Override
            public ArmState toggle() {
                return new GoingDown();
            }
        }

        public class CurrentlyUp implements ArmState {
            public CurrentlyUp(final ColorWheelCore colorWheel) {
                colorWheel.stopLiftMotor();
            }

            @Override
            public ArmState toggle() {
                return new GoingDown();
            }
        }

        public class GoingDown implements ArmState {
            @Override
            public ArmState execute(final ColorWheelCore colorWheel) {
                if (colorWheel.getCurrentPosition() >= ArmLimits.DOWN) {
                    return new CurrentlyDown(colorWheel);
                } else {
                    colorWheel.updateLiftMotor(ArmTargets.DOWN);
                    return this;
                }
            }

            @Override
            public ArmState toggle() {
                return new GoingUp();
            }
        }
    }

    @Override
    public void periodic() {
        currentState = currentState.execute(colorWheel);
    }

    public void toggle() {
        currentState = currentState.toggle();
    }
}
