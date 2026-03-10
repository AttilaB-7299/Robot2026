package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CANConstants;
import frc.robot.handlers.CheckableSubsystem;
import frc.utils.Utils;

public class S_Shooter extends SubsystemBase implements CheckableSubsystem {
  
  private boolean initialized = false, status = false;
  private SparkMax shooterMotor;
  
  private static S_Shooter m_Shooter;

  public S_Shooter() {
    shooterMotor = new SparkMax(CANConstants.SHOOTER_ID, MotorType.kBrushless);
    initialized = true;
  }

  public static S_Shooter getInstance() {
    if(m_Shooter == null) {
      m_Shooter = new S_Shooter();
    }

    return m_Shooter;
  }

  public void set(double speed) {
    shooterMotor.set(Utils.normalize(speed));
  }

  @Override
  public void stop(){
    shooterMotor.stopMotor();
  }

  @Override
  public boolean getInitialized() {
    return initialized;
  }

  @Override
  public boolean checkSubsystem() {
    status = getInitialized();
    status &= !shooterMotor.hasActiveFault();
    return status;
  }
  
}
