package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.handlers.CheckableSubsystem;
import frc.utils.Utils;

public class S_InPivot extends SubsystemBase implements CheckableSubsystem {
  
  private boolean initialized = false, status = false;
  private SparkMax inPivotMotor;
  
  private static S_InPivot m_InPivot;

  public S_InPivot() {
    inPivotMotor = new SparkMax(6, MotorType.kBrushless);
    initialized = true;
  }

  public static S_InPivot getInstance() {
    if(m_InPivot == null) {
      m_InPivot = new S_InPivot();
    }

    return m_InPivot;
  }

  public void set(double speed) {
    inPivotMotor.set(Utils.normalize(speed));
  }

  @Override
  public void stop(){
    inPivotMotor.stopMotor();
  }

  @Override
  public boolean getInitialized() {
    return initialized;
  }

  @Override
  public boolean checkSubsystem() {
    status = getInitialized();
    status &= !inPivotMotor.hasActiveFault();
    return status;
  }
  
}
