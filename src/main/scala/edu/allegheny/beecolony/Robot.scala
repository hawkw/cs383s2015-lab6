package edu.allegheny.beecolony

import java.net.Socket

import lejos.hardware.motor.EV3LargeRegulatedMotor
import lejos.hardware.port.{MotorPort, SensorPort}
import lejos.hardware.sensor.EV3ColorSensor
import lejos.robotics.navigation.{DifferentialPilot,Navigator}

import scala.annotation.tailrec
import scala.language.postfixOps
import scala.util.{Try,Success,Failure}

/**
 * Stores shared robot configuration.
 *
 * @author Hawk Weisman
 */
trait Robot {

  type Coordinate = Tuple2[Float,Float]
  type Color = Int

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

  // -------------------------------------------------------------------------
  // LeJOS CLASSES
  // -------------------------------------------------------------------------
  private val colorSensor = new EV3ColorSensor(SENSOR_COLOR)
  private val leftMotor   = new EV3LargeRegulatedMotor(MOTOR_LEFT)
  private val rightMotor  = new EV3LargeRegulatedMotor(MOTOR_RIGHT)
  private val colorIDProvider = colorSensor.getColorIDMode
  private val pilot           = new DifferentialPilot(WHEEL_DIAM, TRACK_WIDTH, leftMotor, rightMotor)
  private val nav             = new Navigator(pilot)
  private val poseProvider    = nav getPoseProvider

  private val sample = new Array[Float](1) // this is because the LeJOS api is awful
  /**
   * Move the robot to a coordinate pair
   * @param where the coordinate to move to
   */
  def goTo(where: Coordinate): Unit = { (x: Float,y: Float) => nav goTo (x,y) }

  /**
   * @return the current coordinates
   */
  def location: Coordinate  = (poseProvider.getPose getX, poseProvider.getPose getY)

  /**
   * @return the color ID currently under the color sensor
   */
  def checkColor: Color = {
    colorIDProvider fetchSample (sample, 0) // ugh, I had to write an impure function
    sample(0) toInt
  }

}

