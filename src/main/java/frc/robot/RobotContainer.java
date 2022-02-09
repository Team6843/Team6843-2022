// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PS4Controller;
import frc.robot.commands.SwerveDriveCommand;
import frc.robot.subsystems.SwerveDriveTrain;

/** The structure of the robot (including subsystems, commands, and button mappings) should be declared here */
public class RobotContainer 
{
    //private final XboxController controller = new XboxController(0);
    private final PS4Controller controller = new PS4Controller(0);

    private final SwerveDriveTrain driveTrain = new SwerveDriveTrain();

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() 
    {
        driveTrain.setDefaultCommand(new SwerveDriveCommand(driveTrain, controller));
    }
}
