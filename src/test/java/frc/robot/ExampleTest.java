package frc.robot;

import static org.mockito.Mockito.*;
import edu.wpi.first.wpilibj.XboxController;
import org.junit.Test;
import frc.robot.example.*;

public class ExampleTest {

    @Test
    public void testExampleSubsystem() {
        final var core = mock(ExampleCore.class);
        final var controller = mock(XboxController.class);

        when(controller.getRawAxis(0)).thenReturn(10.0, 20.0);

        final var example = new ExampleSubsystem(core, controller);

        example.runMotorFromController();
        verify(core).setMotor(20.0);

        example.runMotorFromController();
        verify(core).setMotor(40.0);
    }

    @Test
    public void testExampleCommand() {
        final var example = mock(ExampleSubsystem.class);

        when(example.getSensor()).thenReturn(false, false, true);

        final var command = new ExampleCommand(example);

        command.execute();
        command.execute();
        verify(example, times(0)).runMotorFromController();

        command.execute();
        verify(example, times(1)).runMotorFromController();
    }
}
