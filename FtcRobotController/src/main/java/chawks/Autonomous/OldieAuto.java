package chawks.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import chawks.hardware.Boxy;

/**
 * This file illustrates the concept of driving a path based on encoder counts.
 * It uses the common Pushbot hardware class to define the drive on the robot.
 * The code is structured as a LinearOpMode
 *
 * The code REQUIRES that you DO have encoders on the wheels,
 *   otherwise you would use: PushbotAutoDriveByTime;
 *
 *  This code ALSO requires that the drive Motors have been configured such that a positive
 *  power command moves them forwards, and causes the encoders to count UP.
 *
 *   The desired path in this example is:
 *   - Drive forward for 48 inches
 *   - Spin right for 12 Inches
 *   - Drive Backwards for 24 inches
 *   - Stop and close the claw.
 *
 *  The code is written using a method called: encoderDrive(speed, leftInches, rightInches, timeoutS)
 *  that performs the actual movement.
 *  This methods assumes that each movement is relative to the last stopping place.
 *  There are other ways to perform encoder based moves, but this method is probably the simplest.
 *  This code uses the RUN_TO_POSITION mode to enable the Motor controllers to generate the run profile
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="Oldie Auto", group="Pushbot")
//@Disabled
public class OldieAuto extends LinearOpMode {

    /* Declare OpMode members. */
    Boxy         robot   = new Boxy();   // Use a Pushbot's hardware
    private ElapsedTime     runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;
    static final double     FORWARD_SPEED           = 0.6;
    static final double     BACKWARDS_SPEED         = -0.6;

    @Override
    public void runOpMode() {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        //  robot.LFMotor.setPower(left);
        //  robot.RFMotor.setPower(right);
        //  robot.LBMotor.setPower(left);
        //  robot.RBMotor.setPower(right);

        robot.LFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.RFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LBMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.RBMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        idle();

        robot.LFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.RFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.LBMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.RBMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d :%7d :%7d",
                robot.LFMotor.getCurrentPosition(),
                robot.RFMotor.getCurrentPosition(),
                robot.LBMotor.getCurrentPosition(),
                robot.RBMotor.getCurrentPosition()
        );
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        // test 1: Drive is too long - we went all the way from starting side to the the wall all the way across
        // Turning Right went to far, did not go 90 degrees that we expected
        //encoderDrive(DRIVE_SPEED,  48,  48, 48,  48, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
        //encoderDrive(TURN_SPEED,   12, -12, 12, -12, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
        // encoderDrive(DRIVE_SPEED, -24, -24, -24, -24, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout

        // test 2: driving forward - it went forward but didnt' reach the big ball
        //for test 3 make the robot go farther and modify the angle at which the robot turns
        //encoderDrive(DRIVE_SPEED,  12,  12, 12,  12, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
        // encoderDrive(TURN_SPEED,   10, -10, 10, -10, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
        //encoderDrive(DRIVE_SPEED, -12, -12, -12, -12, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout

        // test 3: it went forward turned and knocked off the big ball we took out the reversing because we didn't want it to do that and we want to focus on it going forward
        //for test 4 we will not rotate because we wnt the robot to go on the middle platform
        //encoderDrive(DRIVE_SPEED,  24,  24, 24,  24, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
        //encoderDrive(TURN_SPEED,   8, -8, 8, -8, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
        //encoderDrive(DRIVE_SPEED, -12, -12, -12, -12, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout
        //test 4: it turned slightly to the right at the beggining instad of going straight and will look in code to see if it can be fixed and when we have the other robot this problem may not exist
        //encoderDrive(DRIVE_SPEED,  30, 30, 30, 30, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
        //encoderDrive(TURN_SPEED,   8, -8, 8, -8, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
        //encoderDrive(DRIVE_SPEED, -12, -12, -12, -12, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout


        //     robot.crservo4.setPower(-0.5);

        //launchBall( -1.0, 0.8, 5.0); //servo speed, spiinner speed, timeout

        //encoderDrive(DRIVE_SPEED,  30, 30, 30, 30, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
        // encoderDrive(TURN_SPEED,   8, -8, 8, -8, 4.0);   // Turn Right to Position : newLeftTarget,  newRightTarget, newLeftBackTarget, newRightBackTarget
        // encoderDrive(DRIVE_SPEED,  -8, 8, 8, -8, 5.0);  // Strafe Left/Right to get onto the Center Vortex?
        //       robot.LFMotor.setPower(BACKWARDS_SPEED);
        //  robot.RFMotor.setPower(FORWARD_SPEED);
        //    robot.LBMotor.setPower(FORWARD_SPEED);
        // robot.RBMotor.setPower(BACKWARDS_SPEED);
        //




        sleep(1000);     // pause for servos to move

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    /*public void launchBall(double speedServo, double speedSpinner, double timeoutS) {

        robot.crservo2.setDirection(DcMotorSimple.Direction.REVERSE);
        robot.crservo3.setDirection(DcMotorSimple.Direction.REVERSE);

        if (opModeIsActive()) {

            runtime.reset();
            robot.crservo2.setPower(Math.abs(speedServo));
            robot.crservo3.setPower(Math.abs(speedServo));

            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS)) {
                robot.crservo2.setPower(speedServo);
                robot.crservo3.setPower(speedServo);
                robot.spinMotor.setPower(speedSpinner);

            }
            robot.spinMotor.setPower(0);
            robot.crservo2.setPower(0);
            robot.crservo3.setPower(0);

        }
    }*/

    /*
     *  Method to perfmorm a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    public void encoderDrive(double speed,
                             double leftInches, double rightInches, double leftBackInches, double rightBackInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;
        int newLeftBackTarget;
        int newRightBackTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.LFMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.RFMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            newLeftBackTarget = robot.LBMotor.getCurrentPosition() + (int)(leftBackInches * COUNTS_PER_INCH);
            newRightBackTarget = robot.RBMotor.getCurrentPosition() + (int)(rightBackInches * COUNTS_PER_INCH);
            robot.LFMotor.setTargetPosition(newLeftTarget);
            robot.RFMotor.setTargetPosition(newRightTarget);
            robot.LBMotor.setTargetPosition(newLeftBackTarget);
            robot.RBMotor.setTargetPosition(newRightBackTarget);

            // Turn On RUN_TO_POSITION
            robot.LFMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.RFMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.LBMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.RBMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.LFMotor.setPower(Math.abs(speed));
            robot.RFMotor.setPower(Math.abs(speed));
            robot.LBMotor.setPower(Math.abs(speed));
            robot.RBMotor.setPower(Math.abs(speed));


            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.LFMotor.isBusy() && robot.RFMotor.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Going to %7d :%7d :%7d :%7d", newLeftTarget,  newRightTarget, newLeftBackTarget, newRightBackTarget);
                telemetry.addData("Path2",  "Currently at %7d :%7d :%7d :%7d",
                        robot.LFMotor.getCurrentPosition(),
                        robot.RFMotor.getCurrentPosition(),
                        robot.LBMotor.getCurrentPosition(),
                        robot.RBMotor.getCurrentPosition()
                );
                telemetry.update();
            }

            // Stop all motion;
            robot.LFMotor.setPower(0);
            robot.RFMotor.setPower(0);
            robot.LBMotor.setPower(0);
            robot.RBMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.LFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.RFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.LBMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.RBMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);




            //  sleep(250);   // optional pause after each move
        }
    }
}
