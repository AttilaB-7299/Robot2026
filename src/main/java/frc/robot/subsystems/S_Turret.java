package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.handlers.CheckableSubsystem;
import frc.utils.Utils;

public class S_Turret extends SubsystemBase implements CheckableSubsystem {
  private boolean initialized = false, status = false;
  private SparkMax turretMotor;
  
  private static S_Turret m_Turret;

  public S_Turret() {
    turretMotor = new SparkMax(6, MotorType.kBrushless);
    initialized = true;
  }

  public static S_Turret getInstance() {
    if(m_Turret == null) {
      m_Turret = new S_Turret();
    }

    return m_Turret;
  }

  public void set(double speed) {
    turretMotor.set(Utils.normalize(speed));
  }

  @Override
  public void stop(){
    turretMotor.stopMotor();
  }

  @Override
  public boolean getInitialized() {
    return initialized;
  }

  @Override
  public boolean checkSubsystem() {
    status = getInitialized();
    status &= !turretMotor.hasActiveFault();
    return status;
  }




}
