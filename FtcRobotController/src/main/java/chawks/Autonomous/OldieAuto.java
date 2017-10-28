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
<<<<<<< HEAD
    Boxy robot   = new Boxy();   // Use a Pushbot's hardware
=======
    Boxy         robot   = new Boxy();   // Use a Pushbot's hardware
    private ElapsedTime     runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415926535897932384626433832795028);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;
    static final double     FORWARD_SPEED           = 0.6;
    static final double     BACKWARDS_SPEED         = -0.6;

>>>>>>> 18e5eb68f5c2d74bc33ad236f8e223f1fbe9e51f
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

<<<<<<< HEAD

        DriveFowardTime(DRIVE_POWER, SLEEP_TIME);
        StopRobot();

=======
         //ep through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        // test 1: Drive is too long - we went all the way from starting side to the the wall all the way across
        // Turning Right went to far, did not go 90 degrees that we expected
        encoderDrive(DRIVE_SPEED,  48,  48, 48,  48, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
        encoderDrive(TURN_SPEED,   12, -12, 12, -12, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
        encoderDrive(DRIVE_SPEED, -24, -24, -24, -24, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout

        // test 2: driving forward - it went forward but didnt' reach the big ball
        //for test 3 make the robot go farther and modify the angle at which the robot turns
        encoderDrive(DRIVE_SPEED,  12,  12, 12,  12, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
        encoderDrive(TURN_SPEED,   10, -10, 10, -10, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
        encoderDrive(DRIVE_SPEED, -12, -12, -12, -12, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout

        // test 3: it went forward turned and knocked off the big ball we took out the reversing because we didn't want it to do that and we want to focus on it going forward
        //for test 4 we will not rotate because we wnt the robot to go on the middle platform
        encoderDrive(DRIVE_SPEED,  24,  24, 24,  24, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
        encoderDrive(TURN_SPEED,   8, -8, 8, -8, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
        encoderDrive(DRIVE_SPEED, -12, -12, -12, -12, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout
        //test 4: it turned slightly to the right at the beggining instad of going straight and will look in code to see if it can be fixed and when we have the other robot this problem may not exist
        encoderDrive(DRIVE_SPEED,  30, 30, 30, 30, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
        encoderDrive(TURN_SPEED,   8, -8, 8, -8, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
        encoderDrive(DRIVE_SPEED, -12, -12, -12, -12, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout


        //     robot.crservo4.setPower(-0.5);

        //launchBall( -1.0, 0.8, 5.0); //servo speed, spiinner speed, timeout

        encoderDrive(DRIVE_SPEED,  30, 30, 30, 30, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
        encoderDrive(TURN_SPEED,   8, -8, 8, -8, 4.0);   // Turn Right to Position : newLeftTarget,  newRightTarget, newLeftBackTarget, newRightBackTarget
        encoderDrive(DRIVE_SPEED,  -8, 8, 8, -8, 5.0);  // Strafe Left/Right to get onto the Center Vortex?
        robot.LFMotor.setPower(BACKWARDS_SPEED);
        robot.RFMotor.setPower(FORWARD_SPEED);
        robot.LBMotor.setPower(FORWARD_SPEED);
        robot.RBMotor.setPower(BACKWARDS_SPEED);
        //




        sleep(1000);     // pause for servos to move

        telemetry.addData("Path", "Complete");
        telemetry.update();
>>>>>>> 18e5eb68f5c2d74bc33ad236f8e223f1fbe9e51f
    }
    double DRIVE_POWER = 1;
    long SLEEP_TIME = 1000;
    public void DriveFowardTime(double spower, long time) throws InterruptedException
    {
        DriveFoward(spower);
        Thread.sleep(time);
    }
    public void DriveFoward(double power)
    {
        robot.LFMotor.setPower(power);
        robot.RFMotor.setPower(power);
        robot.LBMotor.setPower(power);
        robot.RBMotor.setPower(power);
    }
    public void StopRobot () {
        DriveFoward(0);
    }
}

