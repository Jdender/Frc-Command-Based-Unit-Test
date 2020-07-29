package frc.robot;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static java.util.stream.IntStream.range;
import org.junit.Test;
import frc.robot.colorwheel.*;
import frc.robot.colorwheel.ColorWheelSubsystem.ArmState;

public class ColorWheelTest {

    @Test
    public void testColorWheelArm() {
        final var core = mock(ColorWheelCore.class);

        when(core.getCurrentPosition()).thenReturn(0.0, 40.0, 80.0, 100.0, 100.0, 80.0, 40.0, 0.0);

        final var colorWheel = new ColorWheelSubsystem(core);

        // Going Up
        {
            colorWheel.toggle();

            range(0, 3).forEach((i) -> {
                colorWheel.execute();
                assertTrue(colorWheel.currentState instanceof ArmState.GoingUp);
            });

            colorWheel.execute();
            assertTrue(colorWheel.currentState instanceof ArmState.CurrentlyUp);
        }

        // Going Down
        {
            colorWheel.toggle();

            range(0, 3).forEach((i) -> {
                colorWheel.execute();
                assertTrue(colorWheel.currentState instanceof ArmState.GoingDown);
            });

            colorWheel.execute();
            assertTrue(colorWheel.currentState instanceof ArmState.CurrentlyDown);
        }
    }
}
