package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.handlers.CheckableSubsystem;
import frc.utils.Utils;

public class S_Intake extends SubsystemBase implements CheckableSubsystem {
  
  private boolean initialized = false, status = false;
  private SparkMax intakeMotor;
  
  private static S_Intake m_Intake;

  public S_Intake() {
    intakeMotor = new SparkMax(99997, MotorType.kBrushless);
    initialized = true;
  }

  public static S_Intake getInstance() {
    if(m_Intake == null) {
      m_Intake = new S_Intake();
    }

    return m_Intake;
  }

  public void set(double speed) {
    intakeMotor.set(Utils.normalize(speed));
  }

  @Override
  public void stop(){
    intakeMotor.stopMotor();
  }

  @Override
  public boolean getInitialized() {
    return initialized;
  }

  @Override
  public boolean checkSubsystem() {
    status = getInitialized();
    status &= !intakeMotor.hasActiveFault();
    return status;
  }
  
}
