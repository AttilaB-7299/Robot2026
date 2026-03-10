// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.handlers;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.OI;
import frc.robot.Constants;
import frc.robot.Constants.DrivetrainConstants;
import frc.robot.subsystems.S_Drivetrain;
import frc.utils.LimelightHelpers;
import frc.utils.Utils;

public class Drivetrain extends SubsystemBase implements StateSubsystem {
  private DrivetrainStates desiredState, currentState = DrivetrainStates.IDLE;
  private S_Drivetrain Drivetrain = S_Drivetrain.getInstance();
  private static Drivetrain m_Instance;

  private PIDController rotController = new PIDController(0.03, 0.003, 0);
  
  /** Creates a new Drivetrain. */
  private Drivetrain() {
    rotController.setTolerance(1);
    rotController.setSetpoint(0);
  }

  public static Drivetrain getInstance() {
    if(m_Instance == null) {
      m_Instance = new Drivetrain();
    }

    return m_Instance;
  }

  @Override
  public void setDesiredState(State state) {
    if(desiredState != state) {
      desiredState = (DrivetrainStates) state;
      handleStateTransition();
    }
  }

  @Override
  public void handleStateTransition() {
    switch(desiredState) {
      case IDLE:
      case BROKEN:
        Drivetrain.stop();

        break;

      case DRIVE:
      case AIMING:

        break;

      case LOCKED:
        Drivetrain.setX();

        break;

      default:

        break;
    }

    currentState = desiredState;
  }

  @Override
  public void update() {
    switch(currentState) {
      case IDLE:
        setDesiredState(DrivetrainStates.DRIVE);

        break;
      
      case BROKEN:

        break;

      case DRIVE:
        if(DriverStation.isTeleopEnabled()) {
          drive();
        }

        break;

      case AIMING:
        if(LimelightHelpers.getTV(Constants.LIMELIGHT_NAME)) {
          double rotOffset = LimelightHelpers.getTX(Constants.LIMELIGHT_NAME);

          Drivetrain.drive(
            -MathUtil.applyDeadband(OI.driverController.getLeftY(), DrivetrainConstants.DRIVING_DEADBAND),
            -MathUtil.applyDeadband(OI.driverController.getLeftX(), DrivetrainConstants.DRIVING_DEADBAND),
            -Utils.normalize(rotController.calculate(rotOffset)),
            true, DrivetrainConstants.SPEED_SCALE
          );
        } else {
          drive();
        }
        
        break;

      case LOCKED:

        break;

      default:

        break;
    }
  }

  private void drive() {
    Drivetrain.drive(
      -MathUtil.applyDeadband(OI.driverController.getLeftY(), DrivetrainConstants.DRIVING_DEADBAND),
      -MathUtil.applyDeadband(OI.driverController.getLeftX(), DrivetrainConstants.DRIVING_DEADBAND),
      -MathUtil.applyDeadband(OI.driverController.getRightX(), DrivetrainConstants.DRIVING_DEADBAND),
      true, DrivetrainConstants.SPEED_SCALE
    );
  }

  @Override
  public void periodic() {
    update();
  }

  public Trigger bindState(Trigger button, DrivetrainStates onTrue, DrivetrainStates onFalse) {
    return button
      .onTrue(new InstantCommand(() -> setDesiredState(onTrue), m_Instance))
      .onFalse(new InstantCommand(() -> setDesiredState(onFalse), m_Instance));
  }

  public enum DrivetrainStates implements State {
    IDLE,
    BROKEN,
    DRIVE,
    AIMING,
    LOCKED;
  }
}
