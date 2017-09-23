package chawks.hardware;

//import com.google.common.base.Preconditions;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Handles movement of robot using "dead reckoning", e.g. moving robot for specified distance.
 */
public class MovementController implements Runnable {
    private final static int THRESHOLD = 10;
    /**
     * Robot that we are controlling
     */
    private final Dutchess robot;
    /**
     * Telemetry used for debugging
     */
    private final Telemetry telemetry;
    /**
     * Power applied to wheels when moving in a forward direction. Should be a positive value.
     */
    private double wheelPower;
    /**
     * If true, we need to exit
     */
    private boolean stopped;
    /**
     * Driving direction
     **/
    private DrivingDirection drivingDirection = DrivingDirection.FORWARD;
    private int leftFrontTarget;
    private int leftBackTarget;
    private int rightFrontTarget;
    private int rightBackTarget;

    public void setState(State state) {
        this.state = state;
    }

    private State state;

    public MovementController(Dutchess robot, Telemetry telemetry) {
        this.robot = robot;
        this.telemetry = telemetry;
        setWheelPower(.6d); // default
        state = State.WaitUntilNewMovement;
    }

    public double getWheelPower() {
        return wheelPower;
    }

    public void setWheelPower(double wheelPower) {
        this.wheelPower = Range.clip(Math.abs(wheelPower), 0, 1);
        //Preconditions.checkState(this.wheelPower > 0, "wheelPower must be positive value greater than 0");
    }

    // Fix later
    private boolean isWheelsInPosition() {
        boolean inPosition = true;
       // for (DcMotor wheel : robot.getWheels()) {
        //   if (wheel.isBusy()) {
         //       telemetry.addData(robot.getNameOfWheel(wheel), "%7d to %7d", wheel.getCurrentPosition(), wheel.getTargetPosition());
          //     inPosition = false;
           // }
        //}
        telemetry.update();
        return inPosition;

    }

    public void run() {
        robot.setWheelsToRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        while (!stopped) {
            switch (state) {
                case ResetEncoders:

                    robot.setWheelsToRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                    state = State.initState;

                    break;
                case initState:
                    robot.stopAllWheels();
                    robot.setWheelsToRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    robot.lf.setTargetPosition(leftFrontTarget);
                    robot.lb.setTargetPosition(leftBackTarget);
                    robot.rf.setTargetPosition(rightFrontTarget);
                    robot.rb.setTargetPosition(rightBackTarget);

                    robot.setWheelsToRunMode(DcMotor.RunMode.RUN_TO_POSITION);

                    robot.setPowerAllWheels(wheelPower);

                    state = State.WaitUntilInPosition;
                    break;
                case WaitUntilInPosition:
                    int currLeftBackPos = robot.lb.getCurrentPosition();
                    int currRightBackPos = robot.rb.getCurrentPosition();
                    int currLeftFrontPos = robot.lf.getCurrentPosition();
                    int currRightFrontPos = robot.rf.getCurrentPosition();

                    //Check if the motors are close enough to being in position
                    boolean leftBackIsInPos = isAtTargetThreshold(leftBackTarget, currLeftBackPos, THRESHOLD);
                    boolean rightBackIsInPos = isAtTargetThreshold(rightBackTarget, currRightBackPos, THRESHOLD);
                    boolean leftFrontIsInPos = isAtTargetThreshold(leftFrontTarget, currLeftFrontPos, THRESHOLD);
                    boolean rightFrontIsInPos = isAtTargetThreshold(rightFrontTarget, currRightFrontPos, THRESHOLD);

                    //Display if each motor is in position
                    telemetry.addLine("leftBackIsInPos: " + leftBackIsInPos);
                    telemetry.addLine("rightBackIsInPos: " + rightBackIsInPos);
                    telemetry.addLine("leftFrontIsInPos: " + leftFrontIsInPos);
                    telemetry.addLine("rightFrontIsInPos: " + rightFrontIsInPos);

                    //If the motors are in position, transition to the next state
                    if (leftBackIsInPos && rightBackIsInPos && leftFrontIsInPos && rightFrontIsInPos) {
                        //Change this to State.OptionalStopMotors if you want the robot to
                        //just stop moving the motors instead of holding position
                        state = State.StopMotors;
                    }
                    break;
                case StopMotors:
                    robot.stopAllWheels();
                    state = State.WaitUntilNewMovement;
                    break;
            }
        }
        robot.stopAllWheels();
    }

    private boolean isAtTargetThreshold(int target, int current, int threshold) {
        int error = target - current;
        return Math.abs(error) < threshold;
    }

    public DrivingDirection getDrivingDirection() {
        return drivingDirection;
    }

    public void setDrivingDirection(DrivingDirection drivingDirection) {
        this.drivingDirection = drivingDirection;
    }

    public void stop() {
        stopped = true;
    }

    public void stopMoving() {
        stopped = true;
        telemetry.addLine("stopped moving!");
        robot.stopAllWheels();
    }

    public void turn(double distanceInches, boolean rightTurn) {
        int sign = rightTurn ? 1 : -1;
        double leftDistanceInches = distanceInches * sign;
        double rightDistanceInches = distanceInches * -sign;
        move(leftDistanceInches, -rightDistanceInches, leftDistanceInches, -rightDistanceInches);
    }

    /**
     * Turn the robot the specified number of degrees.
     *
     * @param angleDegrees The number of degrees to turn. A positive number turns the robot to the right;
     *                     a negative value turns the robot to the left.
     */
    public void turn(double angleDegrees) {
        double angleRadians = angleDegrees * Math.PI / 180;

        // when making a right-hand turn, we reverse the sign of the right wheel distances
        // to emulate tank driving style
        WheelConfiguration config = robot.getWheelConfiguration();
        double leftFrontDistanceInches = angleRadians * config.getRadiusLeftFront();
        double rightFrontDistanceInches = -angleRadians * config.getRadiusRightFront();
        double leftBackDistanceInches = angleRadians * config.getRadiusLeftBack();
        double rightBackDistanceInches = -angleRadians * config.getRadiusRightBack();

        move(leftFrontDistanceInches, rightFrontDistanceInches, leftBackDistanceInches, rightBackDistanceInches);
    }

    public void strafeLeft(double wheelRevolutions) {
        // This multiplication is to contain the
        double distanceInches = wheelRevolutions * (4.0/50);

        final double leftDistanceInches;
        final double rightDistanceInches;
        switch (drivingDirection) {
            case FORWARD:
            default:
                leftDistanceInches = -distanceInches;
                rightDistanceInches = distanceInches;
                break;
            case REVERSE:
                leftDistanceInches = distanceInches;
                rightDistanceInches = -distanceInches;
                break;
        }
        move(-leftDistanceInches, -rightDistanceInches, leftDistanceInches, rightDistanceInches);
    }

    public void strafeRight(double wheelRevolutions) {
        // This multiplication is to contain the
        double distanceInches = wheelRevolutions * (4.0/50);

        final double leftDistanceInches;
        final double rightDistanceInches;
        switch (drivingDirection) {
            case FORWARD:
            default:
                leftDistanceInches = distanceInches;
                rightDistanceInches = -distanceInches;
                break;
            case REVERSE:
                leftDistanceInches = -distanceInches;
                rightDistanceInches = distanceInches;
                break;
        }
        move(leftDistanceInches, rightDistanceInches, -leftDistanceInches, -rightDistanceInches);
    }

    public void move(double distanceInches) {
        move(distanceInches, distanceInches, distanceInches, distanceInches);
    }

    public void move(double leftDistanceInches, double rightDistanceInches) {
        move(leftDistanceInches, rightDistanceInches, leftDistanceInches, rightDistanceInches);
    }

    /**
     * Move the robot based upon encoder counts. Caller specifies the distances to be moved in inches,
     * and the speed with which the wheels are supposed to move.
     *
     * @param leftFrontDistanceInches  distance to move left-front wheel, in inches (may be negative!)
     * @param rightFrontDistanceInches distance to move right-front wheel, in inches (may be negative!)
     * @param leftBackDistanceInches   distance to move left-back wheel, in inches (may be negative!)
     * @param rightBackDistanceInches  distance to move right-back wheel, in inches (may be negative!)
     */
    private void move(double leftFrontDistanceInches, double rightFrontDistanceInches, double leftBackDistanceInches, double rightBackDistanceInches) {
        if (stopped) {
            // ignore any attempt to move once stopped
            return;
        }

        // convert the distance that we want to travel into encoder "counts"
        final double countsPerRev = robot.getWheelConfiguration().getCountsPerMotorRev();

        leftFrontTarget = (int) (leftFrontDistanceInches * countsPerRev);
        rightFrontTarget = (int) (rightFrontDistanceInches * countsPerRev);
        leftBackTarget = (int) (leftBackDistanceInches * countsPerRev);
        rightBackTarget = (int) (rightBackDistanceInches * countsPerRev);

        state = State.initState;
    }

    @Override
    public String toString() {
        return "MovementController{" +
                "wheelPower=" + wheelPower +
                '}';
    }

    public enum State {
        ResetEncoders, initState, WaitUntilInPosition, StopMotors, WaitUntilNewMovement
    }
}
