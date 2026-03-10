// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.handlers;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.handlers.Shooter.ShooterStates;
import frc.robot.handlers.Climber.ClimberStates;
import frc.robot.handlers.InPivot.InPivotStates;
import frc.robot.handlers.Intake.IntakeStates;
import frc.robot.handlers.Turret.TurretStates;
import frc.robot.handlers.Claw.ClawStates;
// import frc.robot.handlers.ClimbPivot.ClimbPivotStates;
import frc.robot.subsystems.S_Intake;
import frc.robot.subsystems.S_Shooter;
import frc.robot.subsystems.S_Turret;
import frc.robot.subsystems.S_InPivot;
import frc.robot.subsystems.S_Claw;
// import frc.robot.subsystems.S_ClimbPivot;
import frc.robot.subsystems.S_Climber;

@SuppressWarnings("unused")
public class Manager extends SubsystemBase implements CheckableSubsystem, StateSubsystem {
  private boolean initialized = false, status = false;

  private static Manager m_Instance;
  
  private ManagerStates desiredState, currentState = ManagerStates.IDLE;

  private S_Shooter shooter = S_Shooter.getInstance();
//   private S_Intake intake = S_Intake.getInstance();
//   private S_InPivot inPivot = S_InPivot.getInstance();
//   private S_Turret turret = S_Turret.getInstance();
  private S_Climber climber = S_Climber.getInstance();
  private S_Claw claw = S_Claw.getInstance();

  private Manager() {
	Shooter.getInstance();
	Climber.getInstance();
	Claw.getInstance();

    initialized = shooter.getInitialized();
    // initialized &= climbPivot.getInitialized();
    // initialized &= intake.getInitialized();
    // initialized &= inPivot.getInitialized();
    // initialized &= turret.getInitialized();
    initialized &= climber.getInitialized();
	initialized &= claw.getInitialized();
  }
  public static Manager getInstance() {
    if(m_Instance == null) {
      m_Instance = new Manager();
    }

    return m_Instance;
  }
  @Override
  public void stop() {
    shooter.stop();
    // climbPivot.stop();
    // intake.stop();
    // inPivot.stop();
    // turret.stop();
    climber.stop();
	claw.stop();
  }

  @Override
  public boolean getInitialized() {
    return initialized;
  }

  @Override
  public boolean checkSubsystem() {
    status = shooter.checkSubsystem();
    // status &= climbPivot.checkSubsystem();
    // status &= intake.checkSubsystem();
    // status &= inPivot.checkSubsystem();
    // status &= turret.checkSubsystem();
    status &= climber.checkSubsystem();
	status &= claw.checkSubsystem();

    return status;
  }

  @Override
  public void setDesiredState(State state) {
    if(desiredState != state) {
      desiredState = (ManagerStates) state;
      handleStateTransition();
    }
  }

  @Override
  public void handleStateTransition() {
    switch(desiredState) {
      case IDLE:
        Shooter.getInstance().setDesiredState(ShooterStates.IDLE);
        // ClimbPivot.getInstance().setDesiredState(ClimbPivot.ClimbPivotStates.IDLE);
        Climber.getInstance().setDesiredState(ClimberStates.IDLE);
        // Intake.getInstance().setDesiredState(IntakeStates.IDLE);
        // InPivot.getInstance().setDesiredState(InPivotStates.IDLE);
        // Turret.getInstance().setDesiredState(TurretStates.IDLE);
		Claw.getInstance().setDesiredState(ClawStates.IDLE);

        break;

      case DRIVE:
        Shooter.getInstance().setDesiredState(ShooterStates.IDLE);
        // ClimbPivot.getInstance().setDesiredState(ClimbPivot.ClimbPivotStates.HOME);
        Climber.getInstance().setDesiredState(ClimberStates.HOME);
        // Intake.getInstance().setDesiredState(IntakeStates.IDLE);
        // InPivot.getInstance().setDesiredState(InPivotStates.HOME);
        // Turret.getInstance().setDesiredState(TurretStates.IDLE);
		Claw.getInstance().setDesiredState(ClawStates.IDLE);

        break;

      case SHOOTING:
        Shooter.getInstance().setDesiredState(ShooterStates.SHOOTING);
        // ClimbPivot.getInstance().setDesiredState(ClimbPivot.ClimbPivotStates.HOME);
        Climber.getInstance().setDesiredState(ClimberStates.HOME);
        // Intake.getInstance().setDesiredState(IntakeStates.IDLE);
        // InPivot.getInstance().setDesiredState(InPivotStates.HOME);
        // Turret.getInstance().setDesiredState(TurretStates.IDLE);
		Claw.getInstance().setDesiredState(ClawStates.HOME);

        break;

      case PASSING:
        Shooter.getInstance().setDesiredState(ShooterStates.SHOOTING); // Subject to change
        // ClimbPivot.getInstance().setDesiredState(ClimbPivot.ClimbPivotStates.HOME);
        Climber.getInstance().setDesiredState(ClimberStates.HOME);
        // Intake.getInstance().setDesiredState(IntakeStates.IDLE);
        // InPivot.getInstance().setDesiredState(InPivotStates.HOME);
        // Turret.getInstance().setDesiredState(TurretStates.PASSING);
		Claw.getInstance().setDesiredState(ClawStates.IDLE);

        break;

      case INTAKING:

        Shooter.getInstance().setDesiredState(ShooterStates.IDLE);
        // ClimbPivot.getInstance().setDesiredState(ClimbPivot.ClimbPivotStates.HOME);
        Climber.getInstance().setDesiredState(ClimberStates.HOME);
        // Intake.getInstance().setDesiredState(IntakeStates.INTAKING);
        // InPivot.getInstance().setDesiredState(InPivotStates.INTAKING);
        // Turret.getInstance().setDesiredState(TurretStates.IDLE);
		Claw.getInstance().setDesiredState(ClawStates.IDLE);

        break;

      case OUTPUTTING:
        Shooter.getInstance().setDesiredState(ShooterStates.IDLE);
        Climber.getInstance().setDesiredState(ClimberStates.HOME);
        // ClimbPivot.getInstance().setDesiredState(ClimbPivotStates.HOME);
        // Intake.getInstance().setDesiredState(IntakeStates.OUTPUTTING);
        // InPivot.getInstance().setDesiredState(InPivotStates.INTAKING);
        // Turret.getInstance().setDesiredState(TurretStates.IDLE);
		Claw.getInstance().setDesiredState(ClawStates.IDLE);

        break;

      case CLIMBING:
        Shooter.getInstance().setDesiredState(ShooterStates.IDLE);
        Climber.getInstance().setDesiredState(ClimberStates.CLIMBING);
        // ClimbPivot.getInstance().setDesiredState(ClimbPivotStates.CLIMBING);
        // Intake.getInstance().setDesiredState(IntakeStates.IDLE);
        // InPivot.getInstance().setDesiredState(InPivotStates.HOME);
        // Turret.getInstance().setDesiredState(TurretStates.IDLE);
		Claw.getInstance().setDesiredState(ClawStates.HOLDING);

        break;

      case LOWERING:
        Shooter.getInstance().setDesiredState(ShooterStates.IDLE);
        Climber.getInstance().setDesiredState(ClimberStates.RETURNING);
		Claw.getInstance().setDesiredState(ClawStates.HOLDING);
        // ClimbPivot.getInstance().setDesiredState(ClimbPivotStates.RETURNING);
        // Intake.getInstance().setDesiredState(IntakeStates.IDLE);
        // InPivot.getInstance().setDesiredState(InPivotStates.HOME);
        // Turret.getInstance().setDesiredState(TurretStates.IDLE);

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
        setDesiredState(ManagerStates.DRIVE);

        break;

      case DRIVE:
      case SHOOTING:
      case PASSING:
      case INTAKING:
      case OUTPUTTING:
      case CLIMBING:
      case LOWERING:
      
        break;

      default:
    
        break;
    }
  }

  @Override
  public void periodic() {
    update();
  }

  public ManagerStates getState() {
    return currentState;
  }

  public Trigger bindState(Trigger button, ManagerStates onTrue, ManagerStates onFalse) {
    return button
      .onTrue(new InstantCommand(() -> setDesiredState(onTrue), this))
      .onFalse(new InstantCommand(() -> setDesiredState(onFalse), this));
  }

  public enum ManagerStates implements State {
    IDLE,
    DRIVE,
    SHOOTING,
    PASSING,
    INTAKING,
    OUTPUTTING,
    CLIMBING,
    LOWERING;
  }
}
