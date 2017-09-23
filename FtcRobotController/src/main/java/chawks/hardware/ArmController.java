package chawks.hardware;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Handles movement of robot arm
 */
public class ArmController {
    /**
     * Robot that we are controlling
     */
    private final Dutchess robot;

    /**
     * Telemetry used for debugging
     */
    private final Telemetry telemetry;

    /**
     * Current position
     */
    public double position;

    /**
     * Speed at which we move the arm
     */
    private double armSpeed = -1.0;

    /**
     * How quickly do we move the arm
     */
    private double increment = 0.01d;

    /**
     * Maximum arm position
     */
    private double maxPosition = 1.0d;

    /**
     * Minimum arm position
     */
    private double minPosition = 0.5d;

    /**
     * Constructi an arm controller
     *
     * @param robot     robot
     * @param telemetry telemetry for logging
     */
    public ArmController(Dutchess robot, Telemetry telemetry) {
        this.robot = robot;
        this.telemetry = telemetry;
        this.position = (maxPosition - minPosition) / 2;
       // setPosition(this.position);
    }

    public double getArmSpeed() {
        return armSpeed;
    }

    public void setArmSpeed(double armSpeed) {
        this.armSpeed = armSpeed;
    }

    public double getIncrement() {
        return increment;
    }

    public void setIncrement(double increment) {
        this.increment = increment;
    }

    public double getPosition() {
        return position;
    }

   /* public void setPosition(double position) {
        this.position = Range.clip(position, minPosition, maxPosition);
        robot.arm.setPosition(this.position);
    }*/

  //  public void adjustPosition(double deltaPosition) {
   //     setPosition(getPosition() + deltaPosition);
  //  }

    public double getMaxPosition() {
        return maxPosition;
    }

    public void setMaxPosition(double maxPosition) {
        this.maxPosition = maxPosition;
    }

    public double getMinPosition() {
        return minPosition;
    }

    public void setMinPosition(double minPosition) {
        this.minPosition = minPosition;
    }
}
