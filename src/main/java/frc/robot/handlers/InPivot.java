// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.handlers;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.S_InPivot;

public class InPivot extends SubsystemBase implements StateSubsystem {
  private InPivotStates desiredState, currentState = InPivotStates.IDLE;
  private S_InPivot inPivot = S_InPivot.getInstance();

  private static InPivot m_Instance;
  
  /** Creates a new InPivot. */
  private InPivot() {}

  public static InPivot getInstance() {
    if(m_Instance == null) {
      m_Instance = new InPivot();
    }

    return m_Instance;
  }

  @Override
  public void setDesiredState(State state) {
    if(desiredState != state) {
      desiredState = (InPivotStates) state;
      handleStateTransition();
    }
  }

  @Override
  public void handleStateTransition() {
    switch(desiredState) {
      case IDLE:
      case BROKEN:
        inPivot.stop();

        break;

      case HOME:
      case INTAKING:

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
      case BROKEN:
      case HOME:
      case INTAKING:

        break;

      default:

        break;
    }

    if(!inPivot.checkSubsystem()) {
      setDesiredState(InPivotStates.BROKEN);
    }
  }

  @Override
  public void periodic() {
    update();
  }

  public InPivotStates getState() {
    return currentState;
  }

  public enum InPivotStates implements State {
    IDLE,
    BROKEN,
    HOME,
    INTAKING;
  }
}
