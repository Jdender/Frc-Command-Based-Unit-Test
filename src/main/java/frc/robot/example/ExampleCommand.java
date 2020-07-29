/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.example;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ExampleCommand extends CommandBase {

    private final ExampleSubsystem pewpew;

    public ExampleCommand(final ExampleSubsystem pewpew) {
        addRequirements(pewpew);
        this.pewpew = pewpew;
    }

    @Override
    public void execute() {
        if (!pewpew.getSensor()) {
            return;
        }
        pewpew.runMotorFromController();
    }
}
