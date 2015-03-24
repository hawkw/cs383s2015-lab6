import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * Stores shared robot configuration.
 *
 * @author Hawk Weisman
 */
public class Robot {
    // -------------------------------------------------------------------------
    // PORT CONFIGURATION
    // -------------------------------------------------------------------------
    private static final Port SENSOR_COLOR = SensorPort.S1; // Change as appropriate
    private static final Port MOTOR_LEFT = MotorPort.B;
    private static final Port MOTOR_RIGHT = MotorPort.C;

    // -------------------------------------------------------------------------
    // WHEEL CONFIGURATION
    // -------------------------------------------------------------------------
    // These constants are based on some quick measurements from Lab 2 performed
    // using a plastic protractor with a precision of 1/16th inch. If the robot
    // doesn't turn accurately, please feel free to replace these w/ more
    // accurate measurements.
    private static final float WHEEL_DIAM = 2.0625f; // wheel diam ~= 2 1/6 in
    private static final float TRACK_WIDTH = 4.75f; // track width ~= 4 3/4 in

    // -------------------------------------------------------------------------
    // STATIC HARDWARE CLASSES
    // -------------------------------------------------------------------------
    public static final EV3ColorSensor colorSensor = new EV3ColorSensor(SENSOR_COLOR);
    private static final EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MOTOR_LEFT);
    private static final EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MOTOR_RIGHT);

    /**
     * Factory method for the color sensor color ID mode
     *
     * @return the color ID mode sensor provider
     */
    public static SampleProvider colorIDProvider() {
        return colorSensor.getColorIDMode();
    }

    /**
     * Factory method for a differential pilot with the robot's current
     * configuration.
     *
     * @return a DifferentialPilot configured for the robot's current
     *         configuration.
     */
    public static DifferentialPilot pilot() {
        return new DifferentialPilot(WHEEL_DIAM, TRACK_WIDTH, leftMotor,
                rightMotor);
    }

}