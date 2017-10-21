package chawks.teleop;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
/**
 * Created by ibtro on 10/21/2017.
 */
@TeleOp(name= "NavX Navigation Test", group= "TeleOp")
//@Disabled //Comment this out to allow the opMode to be run on the bot
public class simpleNavTest extends LinearOpMode{
    IntegratingGyroscope gyro;
    NavxMicroNavigationSensor navxMicro;
    public double degrees;

    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        //The following lines are copied from the original NavX opMode file
        // which you can find at org.firstinspires.ftc.robotcontroller.external.samples.SensorKLNavxMicro
        navxMicro = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");
        gyro = (IntegratingGyroscope)navxMicro;
        telemetry.log().add("Gyro Calibrating. Do Not Move!");
        timer.reset();
        while (navxMicro.isCalibrating())  {
            telemetry.addData("calibrating", "%s", Math.round(timer.seconds())%2==0 ? "|.." : "..|");
            telemetry.update();
            Thread.sleep(50);
        }
        telemetry.log().clear(); telemetry.log().add("Gyro Calibrated. Press Start.");
        telemetry.clear(); telemetry.update();

        waitForStart();
        telemetry.log().clear();
        //Most of these lines are original, but a few will still be copied
        while (opModeIsActive()) {
            AngularVelocity rates = gyro.getAngularVelocity(AngleUnit.DEGREES);
            Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            //Add telemetry data
            //Tells DS to either turn left or right depending on bearing
            String heading = formatAngle(angles.angleUnit, angles.firstAngle);

            if (degrees >= -92 && degrees <= -88) {
                telemetry.addLine()
                        .addData("Stop Turning Right!", "");
                telemetry.update();
            } else if (degrees <= 92 && degrees >= 88) {
                telemetry.addLine()
                        .addData("Stop Turning Left!", "");
                telemetry.update();
            }else if (degrees > 92) {
                telemetry.addLine()
                        .addData("Too far left!", "");
                telemetry.update();
            } else if (degrees < -92) {
                telemetry.addLine()
                        .addData("Too far right!", "");
                telemetry.update();
            } else  {
                telemetry.addLine()
                        .addData("Keep Turning!","");
                telemetry.update();
            }
            idle();
        }
    }
    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees){
        getNumDegrees(degrees);
        return String.format("%.1f", AngleUnit.DEGREES.normalize(degrees));

    }
    void getNumDegrees(double stuff){
       degrees = stuff;
    }

}
