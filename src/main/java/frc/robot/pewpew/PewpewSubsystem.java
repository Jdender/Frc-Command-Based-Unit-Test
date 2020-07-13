/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.pewpew;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PewpewSubsystem extends SubsystemBase {

    private final PewpewCore core;
    private final XboxController controller;

    public PewpewSubsystem(PewpewCore core, XboxController controller) {
        this.core = core;
        this.controller = controller;
    }

    public double someHelperMethod(double input) {
        return input * 2;
    }

    public void runMotorFromController() {
        core.setMotor(someHelperMethod(controller.getRawAxis(0)));
    }

    public boolean getSensor() {
        return core.getSensor();
    }
}
