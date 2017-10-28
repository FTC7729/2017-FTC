package chawks.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
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

@Autonomous(name="AndrewTest?", group="Pushbot")
//@Disabled
public class AndrewTestAuto extends LinearOpMode {

    /* Declare OpMode members. */
    Boxy robot   = new Boxy();   // Use a Pushbot's hardware
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        robot.init(hardwareMap);
        TurnLeftTime(DRIVE_POWER,1150);
        DriveFowardTime(DRIVE_POWER,1500);
        /*//For facing the jewel, red alliance
        StrafeLeftTime(DRIVE_POWER,2000);
        Thread.sleep(1000);
        //For facing jewel, blue alliance
        StrafeRightTime(DRIVE_POWER,2000);
        /*DriveFowardTime(DRIVE_POWER, SLEEP_TIME);
        //1000 is
        TurnRightTime(DRIVE_POWER,SLEEP_TIME);
        StopRobot();*/

    }
    double DRIVE_POWER = 1;
    long SLEEP_TIME = 1000;
    public void StrafeLeftTime(double power, long time) throws InterruptedException
    {
        StrafeLeft(power);
        Thread.sleep(time);
        StopRobot();
    }
    public void StrafeLeft(double power)
    {
        robot.RFMotor.setPower(power);
        robot.RBMotor.setPower(-power);
        robot.LFMotor.setPower(-power);
        robot.LBMotor.setPower(power);
    }
    public void StrafeRightTime(double power, long time) throws InterruptedException
    {
        StrafeRight(power);
        Thread.sleep(time);
        StopRobot();
    }
    public void StrafeRight(double power)
    {
        robot.RFMotor.setPower(-power);
        robot.RBMotor.setPower(power);
        robot.LFMotor.setPower(power);
        robot.LBMotor.setPower(-power);
    }
    public void DriveFowardTime(double power, long time) throws InterruptedException
    {
        DriveFoward(power);
        Thread.sleep(time);
        StopRobot();
    }
    public void DriveFoward(double power)
    {
        robot.LFMotor.setPower(power);
        robot.RFMotor.setPower(power);
        robot.LBMotor.setPower(power);
        robot.RBMotor.setPower(power);
    }
    public void TurnLeftTime(double power,long time) throws InterruptedException
    {
        TurnLeft(power);
        Thread.sleep(time);
        StopRobot();
    }
    public void TurnLeft(double power)
    {
        robot.LFMotor.setPower(power);
        robot.LBMotor.setPower(power);
        robot.RFMotor.setPower(-power);
        robot.RBMotor.setPower(-power);
    }
    public void TurnRightTime(double power, long time) throws InterruptedException
    {
        TurnRight(power);
        Thread.sleep(time);
        StopRobot();
    }
    public void TurnRight(double power)
    {
        robot.RFMotor.setPower(power);
        robot.RBMotor.setPower(power);
        robot.LFMotor.setPower(-power);
        robot.LBMotor.setPower(-power);
    }

    public void StopRobot () {
        DriveFoward(0);
    }
}

