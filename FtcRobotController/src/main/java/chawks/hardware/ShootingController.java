package chawks.hardware;

//import com.google.common.base.Preconditions;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static java.lang.Thread.sleep;

/**
 * Created by joseph on 2/5/17.
 */

// No shooting for the new challenge Relic Recovery - keeping code as a reference

public class ShootingController implements Runnable {

    /**
     * Maximum speed of motor
     **/
    public static final double MAX_SPIN_MOTOR_SPEED = 0.7;

    /**
     * Maximum amount we are willing to change motor speed at-a-time
     **/
    public static final double MAX_SPIN_MOTOR_POWER_DELTA = MAX_SPIN_MOTOR_SPEED / 4;

    /**
     * Robot that we are controlling
     */
    private final Dutchess robot;

    /**
     * Current telemetry (used for logging)
     */
    private final Telemetry telemetry;

    /**
     * True if spin motor should be stopped. The {@link #run()} method will exit when this is true
     * and the {@link #targetPower} has been reached.
     **/
    boolean stopped;

    /**
     * Target spin motor speed. This background thread will continously work to reach
     * this speed, at a safe pace.
     */
    private double targetPower;

    public ShootingController(Dutchess robot, Telemetry telemetry, boolean stopped, double power) {
        // Commenting out the Preconditions - not using them for right now

        // Preconditions.checkArgument(robot != null, "robot must be non-null");
        //Preconditions.checkArgument((power >= -1.0) && (power <= 1.0), "power must be between -1 and 1, inclusive");
        this.robot = robot;
        this.telemetry = telemetry;
        this.stopped = stopped;
      //  setTargetPower(power);
    }

    public void setTargetPower(double power) {
        this.targetPower = stopped ? 0 : clipSpinMotorPower(power);
    }

    public void stop() {
        telemetry.addData("spin", "currentPower:%s", robot.spin.getPower());
        //setTargetPower(0);
        this.stopped = true;
    }

    private double clipSpinMotorPower(double power) {
        if (power < 0) {
            return 0;
        } else if (power > MAX_SPIN_MOTOR_SPEED) {
            return MAX_SPIN_MOTOR_SPEED;
        } else {
            return power;
        }
    }

    public void run() {
        // make sure we are using encoder
        robot.spin.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("spin", "started");
        try {
            managePower();
        } finally {
            telemetry.addData("spin", "stopped");
        }
    }

    public void incrementUpSpinner() {
        targetPower += .01;
    }

    public void incrementDownSpinner() {
        targetPower -= .01;
    }

    /**
     * Continuously adjusts the robot.spin motor speed until it reaches a target value.
     * <p>
     * This method will exit when the power setting has been reached, and our management has been stopped.
     */
    private void managePower() {
        while (true) {
            // make sure spin motor is going expected direction
            DcMotorSimple.Direction direction = robot.spin.getDirection();
            if (direction != DcMotorSimple.Direction.FORWARD) {
                telemetry.addData("spin", "directionWas:%s", direction.name());
                robot.spin.setDirection(DcMotorSimple.Direction.FORWARD);
            }

            // we can burn the motors if we change the speed too quickly
            final double currentPower = clipSpinMotorPower(robot.spin.getPower());

            // figure out what new power settings can be for this iteration
            final double newPower;
            double delta = targetPower - currentPower;
            if (delta > 0) {
                newPower = clipSpinMotorPower(currentPower + Math.min(delta, MAX_SPIN_MOTOR_POWER_DELTA));
            } else {
                newPower = clipSpinMotorPower(currentPower - Math.min(-delta, MAX_SPIN_MOTOR_POWER_DELTA));
            }

            telemetry.addData("spin", "from:%.2f, to:%.2f", currentPower, newPower);

            robot.spin.setPower(newPower);
            if (newPower <= 0.0 && stopped) {
                // exit thread when stopped and target power reached
                return;
            }

            try {
                // give motor time to adjust
                sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
