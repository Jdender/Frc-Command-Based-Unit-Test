package frc.robot.pewpew;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import static frc.robot.Constants.PewpewConstants.*;

public class PewpewCore {

    private final Spark motor = new Spark(MOTOR_ID);

    private final DigitalInput sensor = new DigitalInput(SENSOR_ID);

    public void setMotor(final double speed) {
        motor.set(speed);
    }

    public boolean getSensor() {
        return sensor.get();
    }
}
