package frc.robot;
/*
    Original code by Tim Wescott
    Source: https://www.wescottdesign.com/articles/pid/pidWithoutAPhd.pdf
    Modifications by Ty Silva
    Modified to work in Java and match previous PID code.
*/

import edu.wpi.first.wpilibj.Timer;
import frc.robot.PIDParameters;

public class PID {
    public PIDParameters[] optionSets;
    private double lastTime;
    private double lastPosition; // Last position input
    public double sigma; // Integrator state
    double Highestvelocity = 0;// find highest velocity

    /**
     * Object to caculate PID
     *
     * @param optionSets a array of PIDParameters
     */
    public PID(final PIDParameters[] optionSets) {
        this.optionSets = optionSets;
        this.lastTime = Timer.getFPGATimestamp();
        this.lastPosition = 0;
        this.sigma = 0;
    }

    /**
     * Overload for backwords compatability Check PIDParameters for usage of these
     * params
     */
    public PID(final double kP, final double kI, final double kD, final double rangeIStart, final double rangeIEnd,
            final double rangeDStart, final double rangeDEnd, final double integratMax, final double integratMin) {
        this.optionSets = new PIDParameters[] { new PIDParameters(kP, kI, kD, rangeIStart, rangeIEnd, rangeDStart,
                rangeDEnd, integratMax, integratMin), };
        this.lastTime = Timer.getFPGATimestamp();
        this.lastPosition = 0;
        this.sigma = 0;
    }

    /**
     * Overload for backwords compatability If no optionIndex is passed assume it's
     * 0
     *
     * @param desiredPosition Where you want to go
     * @param currentPosition Where you are now
     * @return Calculated power output
     */
    public double pidCalculate(final double desiredPosition, final double currentPosition) {
        return pidCalculate(desiredPosition, currentPosition, 0);
    }

    /**
     * Calculate a power output using pid
     *
     * @param desiredPosition Where you want to go
     * @param currentPosition Where you are now
     * @param optionIndex     Which option set to use
     * @return Calculated power output
     */
    public double pidCalculate(final double desiredPosition, final double CurrentPosition, final int optionIndex) {
        final double kP = optionSets[optionIndex].kP; // LEAVE HERE, DISCUSS WITH STUDENTS
        final double kI = optionSets[optionIndex].kI;
        final double kD = optionSets[optionIndex].kD;
        final double integratMin = optionSets[optionIndex].minI;
        final double integratMax = optionSets[optionIndex].maxI;

        final double currentTime = Timer.getFPGATimestamp();
        final double deltaTime = currentTime - lastTime;
        lastTime = currentTime;
        final double distanceLeft = desiredPosition - CurrentPosition;

        double pTerm, dTerm, iTerm;

        double powerOutput;

        pTerm = kP * distanceLeft; // calculate the proportional term
        // calculate the integral state with appropriate limiting
        sigma += distanceLeft;
        // Limit the integrator state if necessary
        if (sigma < integratMin) {
            sigma = integratMin;
        } else if (sigma > integratMax) {
            sigma = integratMax;
        }
        // calculate the integral term
        iTerm = kI * sigma;
        // calculate the derivative
        dTerm = kD * ((CurrentPosition - lastPosition) / deltaTime);

        lastPosition = CurrentPosition;
        powerOutput = pTerm + iTerm - dTerm;

        if (powerOutput > 1)
            powerOutput = 1;
        else if (powerOutput < -1)
            powerOutput = -1;

        return powerOutput;
    }

}
