package chawks.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import chawks.hardware.Boxy;
/**
 * Created by katie on 10/24/2017.
 */
@Autonomous(name = "DeadReckonAndewTest", group = "Autonomous")
public abstract class DeadReckonAndew extends LinearOpMode {

    private final Boxy robot = new Boxy();
    public void main() throws InterruptedException {
     waitForStart();


     DriveFowardTime(DRIVE_POWER, SLEEP_TIME);
     StopRobot();

}
    double DRIVE_POWER = 1;
    long SLEEP_TIME = 1000;
    public void DriveFowardTime(double power, long time) throws InterruptedException
    {
        DriveFoward(power);
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
