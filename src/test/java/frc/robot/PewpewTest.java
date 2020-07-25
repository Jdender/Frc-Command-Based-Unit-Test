package frc.robot;

import static org.mockito.Mockito.*;
import edu.wpi.first.wpilibj.XboxController;
import org.junit.Test;
import frc.robot.pewpew.*;

public class PewpewTest {

    @Test
    public void testPewpewSubsystem() {
        final var core = mock(PewpewCore.class);
        final var controller = mock(XboxController.class);

        when(controller.getRawAxis(0)).thenReturn(10.0, 20.0);

        final var pewpew = new PewpewSubsystem(core, controller);

        pewpew.runMotorFromController();
        verify(core).setMotor(20.0);

        pewpew.runMotorFromController();
        verify(core).setMotor(40.0);
    }

    @Test
    public void testPewpewCommand() {
        final var pewpew = mock(PewpewSubsystem.class);

        when(pewpew.getSensor()).thenReturn(false, false, true);

        final var command = new PewpewCommand(pewpew);

        command.execute();
        command.execute();
        verify(pewpew, times(0)).runMotorFromController();

        command.execute();
        verify(pewpew, times(1)).runMotorFromController();
    }
}
