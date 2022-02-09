// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

// For setting kinematics 
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;

// For gyro in example 
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SPI;

// other
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class SwerveDriveTrain extends SubsystemBase {
  // Limmits to set
  public static final double kMaxSpeed = Units.feetToMeters(13.6); // 20 feet per second
  public static final double kMaxAngularSpeed = Math.PI; // 1/2 rotation per second
  public static double feildCalibration = 0;

  // Set angle offsets from the smart dashboard
  public static double frontLeftOffset = 0;
  public static double frontRightOffset = 0;
  public static double backLeftOffset = 0;
  public static double backRightOffset = 0;

  //motor and encoder ids
  public static final int frontLeftDriveId = 8; 
  public static final int frontLeftCANCoderId = 6; 
  public static final int frontLeftSteerId = 7;
  
  public static final int frontRightDriveId = 14; 
  public static final int frontRightCANCoderId = 3; 
  public static final int frontRightSteerId = 13; 

  public static final int backLeftDriveId = 10; 
  public static final int backLeftCANCoderId = 5; 
  public static final int backLeftSteerId = 9;

  public static final int backRightDriveId = 12; 
  public static final int backRightCANCoderId = 4; 
  public static final int backRightSteerId = 11;   
  
  // Gyro from exampl 
  public static AHRS gyro = new AHRS(SPI.Port.kMXP);

  private SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
    new Translation2d(
      Units.inchesToMeters(10), //.254 meters 
      Units.inchesToMeters(10)
    ),
    new Translation2d(
      Units.inchesToMeters(10),
      Units.inchesToMeters(-10)
    ),
    new Translation2d(
      Units.inchesToMeters(-10),
      Units.inchesToMeters(10)
    ),
    new Translation2d(
      Units.inchesToMeters(-10),
      Units.inchesToMeters(-10)
    )
  );

  private SwerveModule[] modules = new SwerveModule[] {
    new SwerveModule(new TalonFX(frontLeftDriveId), new TalonFX(frontLeftSteerId), new CANCoder(frontLeftCANCoderId), Rotation2d.fromDegrees(frontLeftOffset)), // Front Left
    new SwerveModule(new TalonFX(frontRightDriveId), new TalonFX(frontRightSteerId), new CANCoder(frontRightCANCoderId), Rotation2d.fromDegrees(frontRightOffset)), // Front Right
    new SwerveModule(new TalonFX(backLeftDriveId), new TalonFX(backLeftSteerId), new CANCoder(backLeftCANCoderId), Rotation2d.fromDegrees(backLeftOffset)), // Back Left
    new SwerveModule(new TalonFX(backRightDriveId), new TalonFX(backRightSteerId), new CANCoder(backRightCANCoderId), Rotation2d.fromDegrees(backRightOffset))  // Back Right

  };

  
  /** Creates a new SwerveDriveTrain. */
  public SwerveDriveTrain() {
    // This was commented out in example
    //gyro.reset();
  }

  public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative, boolean calibrateGyro) {
    if(calibrateGyro){
      gyro.reset(); //recalibrates gyro offset
    }

    SwerveModuleState[] states = kinematics.toSwerveModuleStates( fieldRelative ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, Rotation2d.fromDegrees(-gyro.getAngle()) ) : new ChassisSpeeds(xSpeed, ySpeed, rot) );
    SwerveDriveKinematics.desaturateWheelSpeeds​(states, kMaxSpeed);
  
    for (int i = 0; i < states.length; i++) {
      SwerveModule module = modules[i];
      SwerveModuleState state = states[i];
      SmartDashboard.putNumber(String.valueOf(i), module.getRawAngle());
      
      //below is a line to comment out from step 5
      //module.setDesiredState(state);
      SmartDashboard.putNumber("gyro Angle", gyro.getAngle());
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
