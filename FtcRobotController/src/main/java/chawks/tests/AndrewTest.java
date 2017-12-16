package chawks.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.ClassFactory;

import chawks.hardware.Boxy;
@Autonomous(name="AndrewTest?", group="Test")
//Disabled
public class AndrewTest extends LinearOpMode {

    /* Declare OpMode members. */
    Boxy robot   = new Boxy();   // Use a Pushbot's hardware
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();
        robot.init(hardwareMap);


        DriveFowardTime(.5,1);
        StrafeLeftTime(.5,1);
        DriveFowardTime(.5,1);
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

