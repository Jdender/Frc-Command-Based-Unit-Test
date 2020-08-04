package frc.robot.colorwheel;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ColorWheelConstants.ArmLimits;
import frc.robot.Constants.ColorWheelConstants.ArmTargets;

public class ColorWheelSubsystem extends SubsystemBase {

    // Requires colorWheel so must be init delayed
    public ArmState currentState;

    private final ColorWheelCore colorWheel;

    public ColorWheelSubsystem(final ColorWheelCore colorWheel) {
        this.colorWheel = colorWheel;
        this.currentState = new CurrentlyDown();

        final var tab = Shuffleboard.getTab("Driver");
        tab.addString("Color Sensed", () -> colorWheel.getColor().name());
        tab.addString("Color Required", () -> colorWheel.getRequiredColor().name());

        final var debugTab = Shuffleboard.getTab("Debug");
        debugTab.addString("Current State", () -> this.currentState.getClass().getSimpleName());
    }

    private interface ArmState {

        public default ArmState execute() {
            return this;
        }

        public ArmState toggle();
    }

    // #region State Machine Impl
    public class CurrentlyDown implements ArmState {
        public CurrentlyDown() {
            colorWheel.stopLiftMotor();
        }

        @Override
        public ArmState toggle() {
            return new GoingUp();
        }
    }

    public class GoingUp implements ArmState {
        @Override
        public ArmState execute() {
            if (colorWheel.getCurrentPosition() <= ArmLimits.UP) {
                return new CurrentlyUp();
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
        public CurrentlyUp() {
            colorWheel.stopLiftMotor();
        }

        @Override
        public ArmState toggle() {
            return new GoingDown();
        }
    }

    public class GoingDown implements ArmState {
        @Override
        public ArmState execute() {
            if (colorWheel.getCurrentPosition() >= ArmLimits.DOWN) {
                return new CurrentlyDown();
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
    // #endregion

    @Override
    public void periodic() {
        currentState = currentState.execute();
    }

    public void toggle() {
        currentState = currentState.toggle();
    }
}
