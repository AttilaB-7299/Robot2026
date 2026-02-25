package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.handlers.CheckableSubsystem;
import frc.utils.Utils;

public class S_Climber extends SubsystemBase implements CheckableSubsystem {
  
  private boolean initialized = false, status = false;
  private SparkMax climberMotor;
  
  private static S_Climber m_Climber;

  public S_Climber() {
    climberMotor = new SparkMax(6, MotorType.kBrushless);
    initialized = true;
  }

  public static S_Climber getInstance() {
    if(m_Climber == null) {
      m_Climber = new S_Climber();
    }

    return m_Climber;
  }

  public void set(double speed) {
    climberMotor.set(Utils.normalize(speed));
  }

  @Override
  public void stop(){
    climberMotor.stopMotor();
  }

  @Override
  public boolean getInitialized() {
    return initialized;
  }

  @Override
  public boolean checkSubsystem() {
    status = getInitialized();
    status &= !climberMotor.hasActiveFault();
    return status;
  }
  
}
