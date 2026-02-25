package frc.utils;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import edu.wpi.first.util.function.BooleanConsumer;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.utils.Elastic.Notification;
import frc.utils.Elastic.Notification.NotificationLevel;
import frc.robot.handlers.StateSubsystem;
import frc.robot.handlers.StateSubsystem.State;

public class Utils {
  private Utils() {}

  /**
   * All speeds that a motor is set to should be between -1 and 1.
   * This method ensures that all values will be safe for a motor to run at.
   * @param input The desired speed of a motor
   * @return The desired speed normalized between [-1, 1]
   */
  public static double normalize(double input) {
    if(input > 1) {
      return 1;
    } else if(input < -1) {
      return -1;
    } else if(Double.isNaN(input) || Double.isInfinite(input)) {
      return 0;
    }
    return input;
  }

  /**
   * Binds a state to a button. This method helps improve
   * readability it the code by hiding all of the stuff with
   * the InstantCommands and just passing in the needed arguments.
   * 
   * @param button The Trigger (usually a button) to bind the states to
   * @param onTrue The state to be active while the button is held down
   * @param onFalse The state to be active one the button is released
   * @return Returns the new Trigger for further method chaining
   */
  public static Trigger bindState(StateSubsystem subsystem, Trigger button, State onTrue, State onFalse) {
    return button
      .onTrue(new InstantCommand(() -> subsystem.setDesiredState(onTrue)))
      .onFalse(new InstantCommand(() -> subsystem.setDesiredState(onFalse)));
  }

  public static class ElasticUtil {

    static Notification defaultSetter = new Notification()
      .withTitle("Setter not configured")
      .withLevel(NotificationLevel.WARNING)
      .withDisplaySeconds(5);
    /**
     * This method automatically adds this value to the SmartDashboard.
     * **Important** to note: This does not add a setter to the value, so DO NOT
     * try to set the value of the variable through the dashboard.
     * 
     * @param key The name of the data
     * @param getter A method to get the value of the data
     */
    public static void putDouble(String key, DoubleSupplier getter) {
      SmartDashboard.putData(key, new Sendable() {
          @Override
          public void initSendable(SendableBuilder builder) {
            builder.addDoubleProperty(key, getter, x -> Elastic.sendNotification(defaultSetter));
          }
        }
      );
    }

    /**
     * This method automatically adds this value to the SmartDashboard.
     * 
     * @param key The name of the data
     * @param getter A method to get the value of the data
     * @param setter A method to set the value of the data
     */
    public static void putDouble(String key, DoubleSupplier getter, DoubleConsumer setter) {
      SmartDashboard.putData(key, new Sendable() {
          @Override
          public void initSendable(SendableBuilder builder) {
            builder.addDoubleProperty(key, getter, setter);
          }
        }
      );
    }

    /**
     * This method automatically adds this value to the SmartDashboard.
     * **Important** to note: This does not add a setter to the value, so DO NOT
     * try to set the value of the variable through the dashboard.
     * 
     * @param key The name of the data
     * @param getter A method to get the value of the data
     */
    public static void putBoolean(String key, BooleanSupplier getter) {
      SmartDashboard.putData(key, new Sendable() {
          @Override
          public void initSendable(SendableBuilder builder) {
            builder.addBooleanProperty(key, getter, x -> Elastic.sendNotification(defaultSetter));
          }
        }
      );
    }

    /**
     * This method automatically adds this value to the SmartDashboard.
     * 
     * @param key The name of the data
     * @param getter A method to get the value of the data
     * @param setter A method to set the value of the data
     */
    public static void putBoolean(String key, BooleanSupplier getter, BooleanConsumer setter) {
      SmartDashboard.putData(key, new Sendable() {
          @Override
          public void initSendable(SendableBuilder builder) {
            builder.addBooleanProperty(key, getter, setter);
          }
        }
      );
    }

    /**
     * This method automatically adds this value to the SmartDashboard.
     * **Important** to note: This does not add a setter to the value, so DO NOT
     * try to set the value of the variable through the dashboard.
     * 
     * @param key The name of the data
     * @param getter A method to get the value of the data
     */
    public static void putString(String key, Supplier<String> getter) {
      SmartDashboard.putData(key, new Sendable() {
          @Override
          public void initSendable(SendableBuilder builder) {
            builder.addStringProperty(key, getter, x -> Elastic.sendNotification(defaultSetter));
          }
        }
      );
    }

    /**
     * This method automatically adds this value to the SmartDashboard.
     * 
     * @param key The name of the data
     * @param getter A method to get the value of the data
     * @param setter A method to set the value of the data
     */
    public static void putString(String key, Supplier<String> getter, Consumer<String> setter) {
      SmartDashboard.putData(key, new Sendable() {
          @Override
          public void initSendable(SendableBuilder builder) {
            builder.addStringProperty(key, getter, setter);
          }
        }
      );
    }
  }
}
