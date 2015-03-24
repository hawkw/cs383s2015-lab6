package edu.allegheny.beecolony

import lejos.hardware.motor.EV3LargeRegulatedMotor
import lejos.hardware.port.{MotorPort, SensorPort, Port}
import lejos.hardware.sensor.EV3ColorSensor
import lejos.robotics.SampleProvider
import lejos.robotics.navigation.DifferentialPilot

/**
 * Stores shared robot configuration.
 *
 * @author Hawk Weisman
 */
object Robot {
  // -------------------------------------------------------------------------
  // PORT CONFIGURATION
  // -------------------------------------------------------------------------
  private val SENSOR_COLOR  = SensorPort.S1; // Change as appropriate
  private val MOTOR_LEFT    = MotorPort.B
  private val MOTOR_RIGHT   = MotorPort.C

  // -------------------------------------------------------------------------
  // WHEEL CONFIGURATION
  // -------------------------------------------------------------------------
  // These constants are based on some quick measurements from Lab 2 performed
  // using a plastic protractor with a precision of 1/16th inch. If the robot
  // doesn't turn accurately, please feel free to replace these w/ more
  // accurate measurements.
  private val WHEEL_DIAM  = 2.0625f
  private val TRACK_WIDTH = 4.75f

  private val colorSensor = new EV3ColorSensor(SENSOR_COLOR)
  private val leftMotor   = new EV3LargeRegulatedMotor(MOTOR_LEFT)
  private val rightMotor  = new EV3LargeRegulatedMotor(MOTOR_RIGHT)

  /**
   * Factory method for the color sensor color ID mode
   *
   * @return the color ID mode sensor provider
   */
  def colorIDProvider: SampleProvider = colorSensor.getColorIDMode

  /**
   * Factory method for a differential pilot with the robot's current
   * configuration.
   *
   * @return a DifferentialPilot configured for the robot's current
   *         configuration.
   */
  def pilot: DifferentialPilot = new DifferentialPilot(
    WHEEL_DIAM, TRACK_WIDTH, leftMotor, rightMotor)
}

