package frc.robot.handlers;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.S_ClimbPivot;

public class ClimbPivot extends SubsystemBase implements StateSubsystem {
  private ClimbPivotStates desiredState, currentState = ClimbPivotStates.IDLE;

  private S_ClimbPivot climber = S_ClimbPivot.getInstance();

  private static ClimbPivot m_Instance;
  
  /** Creates a new Shooter. */
  private ClimbPivot() {}

  public static ClimbPivot getInstance() {
    if(m_Instance == null) {
      m_Instance = new ClimbPivot();
    }

    return m_Instance;
  }

  @Override
  public void setDesiredState(State state) {
    if(desiredState != state) {
      desiredState = (ClimbPivotStates) state;
      handleStateTransition();
    }
  }

  @Override
  public void handleStateTransition() {
    switch(desiredState) {
      case IDLE:
      case BROKEN:
        climber.stop();

        break;

      case CLIMBING:

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
      case CLIMBING:

        break;

      default:

        break;
    }

    if(!climber.checkSubsystem()) {
      setDesiredState(ClimbPivotStates.BROKEN);
    }
  }

  @Override
  public void periodic() {
    update();
  }

  public ClimbPivotStates getState() {
    return currentState;
  }

  public enum ClimbPivotStates implements State {
    IDLE,
    BROKEN,
    HOME,
    CLIMBING,
    RETURNING;
  }
}
