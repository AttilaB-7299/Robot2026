package frc.robot.handlers;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.OI;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.S_Drivetrain;
import frc.utils.Utils.ElasticUtil;

@SuppressWarnings("unused")
public class Drivetrain extends SubsystemBase implements StateSubsystem {

  private DrivetrainStates desiredState, currentState = DrivetrainStates.IDLE;
  private S_Drivetrain drivetrain = S_Drivetrain.getInstance();
  private static Drivetrain m_drivetrain;

  private Drivetrain() {
    ElasticUtil.putString("Drivetrain state", () -> getState().toString());
  }

  public static Drivetrain getInstance() {
    if(m_drivetrain == null) {
      m_drivetrain = new Drivetrain();
    }
    return m_drivetrain;
  }
  public Trigger bindState(Trigger button, DrivetrainStates onTrue, DrivetrainStates onFalse) {
    return button
      .onTrue(new InstantCommand(() -> setDesiredState(onTrue), this))
      .onFalse(new InstantCommand(() -> setDesiredState(onFalse), this));
  }
  @Override
  public void setDesiredState(State state) {
    if(this.desiredState != state) {
      desiredState = (DrivetrainStates) state;
      handleStateTransition();
    }
  }

  @Override
  public void handleStateTransition() {
    switch(desiredState) {
      case IDLE:
        drivetrain.stop();
        break;
      case BROKEN:
        drivetrain.stop();
        break;
      case DRIVE:
        break;
      case SLOW:
        break;
      case LOCKED:
        drivetrain.setX();

      default:
        break;
    }

    currentState = desiredState;
  }

  public void update() {
    switch(currentState) {
      case IDLE:
        break;
      case BROKEN:
        break;
      case DRIVE:
        drivetrain.drive(
          OI.getDriveLeftY(),
          OI.getDriveLeftX(),
          OI.getDriveRightX(),
          true, DriveConstants.SPEED_SCALE
        );
        break;
      case SLOW:
        drivetrain.drive(
          OI.getDriveLeftY() * DriveConstants.SLOW_SPEED,
          OI.getDriveRightX() * DriveConstants.SLOW_SPEED,
          OI.getDriveRightX() * DriveConstants.SLOW_SPEED,
          true, DriveConstants.SPEED_SCALE
          //how the FUCK do we take in 5 values when we only are supposed to give 3 
        );
        break;
      case REVERSE:
        drivetrain.drive(
          OI.getDriveLeftY() * -1,
          OI.getDriveRightX() * -1,
          OI.getDriveRightX() * -1,
          false, DriveConstants.SPEED_SCALE
        );
        break;
      case LOCKED:
          drivetrain.setX();
          break;
      //why is this not here but is in handleStateTransition i'm leaving it until someone explains
      default:
        break;
    }

    if(!drivetrain.checkSubsystem()) {
      setDesiredState(DrivetrainStates.BROKEN);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    update();
  }

  /**
   * @return The current state of the subsystem
   */
  public DrivetrainStates getState() {
    return currentState;
  }

  public enum DrivetrainStates implements State {
    IDLE,
    BROKEN,
    DRIVE,
    SLOW,
    REVERSE,
    LOCKED,
    AIMING,
    ALIGNING
  }
}
