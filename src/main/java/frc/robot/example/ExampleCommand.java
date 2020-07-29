/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.example;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ExampleCommand extends CommandBase {

    private final ExampleSubsystem example;

    public ExampleCommand(final ExampleSubsystem example) {
        addRequirements(example);
        this.example = example;
    }

    @Override
    public void execute() {
        if (!example.getSensor()) {
            return;
        }
        example.runMotorFromController();
    }
}
