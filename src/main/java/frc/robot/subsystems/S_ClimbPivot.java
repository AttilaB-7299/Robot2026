package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.handlers.CheckableSubsystem;
import frc.utils.Utils;

public class S_ClimbPivot extends SubsystemBase implements CheckableSubsystem {
  
  private boolean initialized = false, status = false;
  private SparkMax climbPivotMotor;
  
  private static S_ClimbPivot m_ClimbPivot;

  public S_ClimbPivot() {
    climbPivotMotor = new SparkMax(6, MotorType.kBrushless);
    initialized = true;
  }

  public static S_ClimbPivot getInstance() {
    if(m_ClimbPivot == null) {
      m_ClimbPivot = new S_ClimbPivot();
    }

    return m_ClimbPivot;
  }

  public void set(double speed) {
    climbPivotMotor.set(Utils.normalize(speed));
  }

  @Override
  public void stop(){
    climbPivotMotor.stopMotor();
  }

  @Override
  public boolean getInitialized() {
    return initialized;
  }

  @Override
  public boolean checkSubsystem() {
    status = getInitialized();
    status &= !climbPivotMotor.hasActiveFault();
    return status;
  }
  
}
