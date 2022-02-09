// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SwerveDriveTrain;
import frc.robot.subsystems.SwerveModule;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.math.filter.SlewRateLimiter;
//import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwerveDriveCommand extends CommandBase {
  /** Creates a new SwerveDriveCommand. */

  private final SwerveDriveTrain  driveTrain;
  //private final XboxController controller;
  private final PS4Controller controller;

  // Slew rate limiters to make joystick inputs more gentle; 1/3 sec from 0 to 1.
  private final SlewRateLimiter xspeedLimiter = new SlewRateLimiter(6);
  private final SlewRateLimiter yspeedLimiter = new SlewRateLimiter(6);
  private final SlewRateLimiter rotLimiter = new SlewRateLimiter(6);

//here too
  public SwerveDriveCommand(SwerveDriveTrain  driveTrain, PS4Controller controller) {
    // Use addRequirements() here to declare subsystem dependencies.
    
    this.driveTrain = driveTrain;
    addRequirements(driveTrain);

    this.controller  = controller;
  }

  // Deleted in example from 1710
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Get the x speed.
    //this is inverted because Xbox controllers return negative values when we push forward.
    //Hand hand  = new Hand();

    final var xSpeed = -xspeedLimiter.calculate(controller.getLeftY()) * SwerveDriveTrain.kMaxSpeed;
    
    // Get the y speed or sideways/strafe speed. 
    //This is inverted to have a positive value when pulled to the left. 
    //Xbox controllers return positive values when you pull to the right by default.
    final var ySpeed = -yspeedLimiter.calculate(controller.getLeftX()) * SwerveDriveTrain.kMaxSpeed;
    
    // Get the rate of angular rotation. 
    // This is inverted to have a positive value when pulled to the left 
    // Xbox controllers return positive values when you pull to the right by default.
    final var rot = -rotLimiter.calculate(controller.getRightX()) * SwerveDriveTrain.kMaxAngularSpeed;

    //boolean calibrate = controller.getBumper(GenericHID.Hand.kLeft);
    boolean calibrate = controller.getL1Button();
  
    driveTrain.drive(xSpeed, ySpeed, rot,  true, calibrate);
  }

  // Deleted in example from 1710
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Deleted in example from 1710
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
