package chawks.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import chawks.hardware.Boxy;

import chawks.hardware.Dutchess;
@Autonomous(name = "AutonomousBoxy", group = "Autonomous")
@Disabled
public abstract class AutonomousBoxy extends LinearOpMode {
    /**
     * Robot configuration
     **/

    private final Boxy robot = new Boxy();

    private ElapsedTime elapsedTime = new ElapsedTime();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        int LOGANISSLEEPING = 1000;
        int LOGANISSLEEPINGLONG =2000;
        // Initialization of motors?
        robot.RBMotor.setPower(0);
        robot.RFMotor.setPower(0);
        robot.LBMotor.setPower(0);
        robot.LFMotor.setPower(0);
        sleep(LOGANISSLEEPING);
        robot.RBMotor.setPower(1);
        robot.RFMotor.setPower(-1);
        robot.LBMotor.setPower(-1);
        robot.LFMotor.setPower(1);
        sleep(LOGANISSLEEPINGLONG);
        robot.RBMotor.setPower(0);
        robot.RFMotor.setPower(0);
        robot.LBMotor.setPower(0);
        robot.LFMotor.setPower(0);        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        initMovement();



        runMovement();

      //colorLook();
        }

        // pause for servos to move
   public void initMovement() {

    }





    public void encoderDrive(double speed, double feetDistance, double timeoutS) {
    // }
    }


    public abstract void runMovement();

}