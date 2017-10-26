package chawks.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import chawks.hardware.Boxy;

/**
 *
 * This class tests the functionality of the wheels encoders.  We control the wheels using the
 * RUN_TO_POSITION mode of the wheel encoders. In this mode, we set the "target position" of
 * the encoder, and the speed, and the motor will turn to the given position.
 *
 * It's possible for RUN_TO_POSITION to appear not to work. This can be caused by:
 * - Incorrect wiring
 * - A bad encoder
 *
 * In either case, the "current position" of the encoder will either read as "0", or once started,
 * will continue to return
 * true for the "isBusy" method. NOTE: "isBusy" is not used in this program.
 *
 * To use this class, run the program like a normal tele-op. The result should be four "true" values
 * in the telemetry. For any wheel which is "false" there is a problem. Check the wiring of the bot
 * against wiring diagrams. Also check the encoders on the motor themselves.
 *
 * @author joseph
 *
 */

@Autonomous(name = "Please Move Roreward Robot", group = "Autonomous")
public class PleaseMoveForewardRobot extends OpMode {

    enum State {
        ResetEncoders, StartEncoders, WaitUntilInPosition, StopMotors, Done
    }

    //Is not quite a dutchess
    private Boxy robot = new Boxy();

    private final static int THRESHOLD = 10;

    private final int leftFrontTarget;
    private final int leftBackTarget;
    private final int rightFrontTarget;
    private final int rightBackTarget;

    public PleaseMoveForewardRobot() {
        this(3,3);
    }


    public PleaseMoveForewardRobot(int leftTarget, int rightTarget) {
        this(leftTarget,leftTarget, rightTarget, rightTarget);
    }



    public PleaseMoveForewardRobot(int leftFrontTarget, int leftBackTarget, int rightFrontTarget, int rightBackTarget) {
        int encoderCpr = robot.getWheelConfiguration().getCountsPerMotorRev();
        this.leftFrontTarget = leftFrontTarget * encoderCpr;
        this.leftBackTarget = leftBackTarget * encoderCpr;
        this.rightFrontTarget = rightFrontTarget * encoderCpr;
        this.rightBackTarget = rightBackTarget * encoderCpr;
    }

    public PleaseMoveForewardRobot(int leftFrontTarget, int leftBackTarget, int rightFrontTarget, int rightBackTarget, double strafeFactor) {
        int encoderCpr = robot.getWheelConfiguration().getCountsPerMotorRev();
        this.leftFrontTarget = (int) (leftFrontTarget * encoderCpr * strafeFactor);
        this.leftBackTarget = (int) (leftBackTarget * encoderCpr * strafeFactor);
        this.rightFrontTarget = (int) (rightFrontTarget * encoderCpr * strafeFactor);
        this.rightBackTarget = (int) (rightBackTarget * encoderCpr * strafeFactor);
    }

    private State state;

    public void init() {
        robot.init(hardwareMap);
        state = State.ResetEncoders;
    }

    public void loop() {
        switch (state) {
            case ResetEncoders:

                robot.setWheelsToRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                state = State.StartEncoders;

                break;
            case StartEncoders:

                robot.LFMotor.setTargetPosition(leftFrontTarget);
                robot.LBMotor.setTargetPosition(leftBackTarget);
                robot.RFMotor.setTargetPosition(rightFrontTarget);
                robot.RBMotor.setTargetPosition(rightBackTarget);

                robot.setWheelsToRunMode(DcMotor.RunMode.RUN_TO_POSITION);

                robot.setPowerAllWheels(0.4D);

                state = State.WaitUntilInPosition;

                break;
            case WaitUntilInPosition:
                int currLeftBackPos = robot.LBMotor.getCurrentPosition();
                int currRightBackPos = robot.RBMotor.getCurrentPosition();
                int currLeftFrontPos = robot.LFMotor.getCurrentPosition();
                int currRightFrontPos = robot.RFMotor.getCurrentPosition();

                //Check if the motors are close enough to being in position
                boolean leftBackIsInPos = isAtTargetThreshold(leftBackTarget, currLeftBackPos, THRESHOLD);
                boolean rightBackIsInPos = isAtTargetThreshold(rightBackTarget, currRightBackPos, THRESHOLD);
                boolean leftFrontIsInPos = isAtTargetThreshold(leftFrontTarget, currLeftFrontPos, THRESHOLD);
                boolean rightFrontIsInPos = isAtTargetThreshold(rightFrontTarget, currRightFrontPos, THRESHOLD);

                //Display if each motor is in position
                telemetry.addLine("leftBackIsInPos: " + leftBackIsInPos + " (" + robot.LBMotor.isBusy() + ")");
                telemetry.addLine("rightBackIsInPos: " + rightBackIsInPos + " (" + robot.RBMotor.isBusy() + ")");
                telemetry.addLine("leftFrontIsInPos: " + leftFrontIsInPos + " (" + robot.LFMotor.isBusy() + ")");
                telemetry.addLine("rightFrontIsInPos: " + rightFrontIsInPos + " (" + robot.RFMotor.isBusy() + ")");

                //If the motors are in position, transition to the next state
                if(!robot.LBMotor.isBusy() && !robot.RBMotor.isBusy() && !robot.LFMotor.isBusy() && !robot.RFMotor.isBusy()) {
                    //Change this to State.OptionalStopMotors if you want the robot to
                    //just stop moving the motors instead of holding position
                    state = State.StopMotors;
                }
                break;
            case StopMotors:
                robot.stopAllWheels();
                state = State.Done;
                break;
        }
    }

    private boolean isAtTargetThreshold(int target, int current, int threshold) {
        int error = target - current;
        return Math.abs(error) < threshold;
    }

}
