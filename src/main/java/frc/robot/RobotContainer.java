// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.handlers.Drivetrain;
import frc.robot.handlers.Drivetrain.DrivetrainStates;
import frc.robot.handlers.Manager;
import frc.robot.handlers.Manager.ManagerStates;
import frc.robot.subsystems.S_Drivetrain;
import frc.utils.Utils;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
@SuppressWarnings("unused")
public class RobotContainer {

  private SendableChooser<Command> autoChooser = new SendableChooser<Command>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configures all the autos
    configureAutos();
    // Configure the trigger bindings
    configureBindings();
  }

  private void configureAutos() {
    autoChooser.addOption("Nothing", new InstantCommand());
    autoChooser.addOption("Leave", new RunCommand(() -> S_Drivetrain.getInstance().drive(0.5, 0, 0, false), Drivetrain.getInstance()));
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    Manager.getInstance().bindState(OI.auxController.a(), ManagerStates.SHOOTING, ManagerStates.DRIVE);

    Manager.getInstance().bindState(OI.auxController.b(), ManagerStates.OUTPUTTING, ManagerStates.DRIVE);

    Manager.getInstance().bindState(OI.auxController.rightBumper(), ManagerStates.PASSING, ManagerStates.DRIVE);

    Manager.getInstance().bindState(OI.auxController.leftBumper(), ManagerStates.INTAKING, ManagerStates.DRIVE);

    // Stops movement by setting the wheels in an X formation
    Drivetrain.getInstance().bindState(OI.driverController.x(), DrivetrainStates.LOCKED, DrivetrainStates.DRIVE);
    // // Removes yaw control and aligns to April tags
    Drivetrain.getInstance().bindState(OI.driverController.a(), DrivetrainStates.AIMING, DrivetrainStates.DRIVE);

    // Intaking
    //do we need this, we're in drive normally so like can we just delete this
    Drivetrain.getInstance().bindState(OI.driverController.leftBumper(), DrivetrainStates.DRIVE, DrivetrainStates.DRIVE);
    
    // Zeroes out the gyro
    OI.driverController.start()
      .onTrue(new InstantCommand(() -> S_Drivetrain.getInstance().setHeading(0), Drivetrain.getInstance()));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return autoChooser.getSelected();
  }
}
