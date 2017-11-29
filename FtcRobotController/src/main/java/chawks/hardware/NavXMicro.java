package chawks.hardware;


import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import chawks.hardware.Boxy;

public class NavXMicro extends LinearOpMode{
    boolean isBoxy;
    Boxy boxy;
    KBot kbot;
    double degrees;
    public void runOpMode() {}
    public NavXMicro(Boxy bot) {
        boxy = bot;
        isBoxy = true;
    }
    public NavXMicro(KBot bot) {
        kbot = bot;
        isBoxy = false;
    }

    NavxMicroNavigationSensor navxMicro;
    IntegratingGyroscope gyro;
    ElapsedTime timer = new ElapsedTime();
    public void initAndCalibrate(HardwareMap hardwareMap) throws InterruptedException {
        navxMicro = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");
        gyro = (IntegratingGyroscope)navxMicro;
        // Wait until the gyro calibration is complete
        timer.reset();
        while (navxMicro.isCalibrating())  {
            telemetry.addData("calibrating", "%s", Math.round(timer.seconds())%2==0 ? "|.." : "..|");
            telemetry.update();
            Thread.sleep(50);
        }

    }
    public void turn(double target, double speed) {
        AngularVelocity rates = gyro.getAngularVelocity(AngleUnit.DEGREES);
        Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        String heading = formatAngle(angles.angleUnit, angles.firstAngle);

        while (degrees > target + 2 || degrees < target - 2) {
            if (!opModeIsActive())
            {
                return;
            }
            rates = gyro.getAngularVelocity(AngleUnit.DEGREES);
            angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            telemetry.addData("Heading","%s degrees", degrees);
            telemetry.addData("Target", "%s degrees", target);
            heading = formatAngle(angles.angleUnit, angles.firstAngle);
            if (isBoxy) {
                if (degrees > target + 2) {
                    boxy.RBMotor.setPower(-speed);
                    boxy.RFMotor.setPower(-speed);
                    boxy.LFMotor.setPower(speed);
                    boxy.LBMotor.setPower(speed);
                    telemetry.addLine("Turning Right");
                } else if (degrees < target - 2) {
                    boxy.RBMotor.setPower(speed);
                    boxy.RFMotor.setPower(speed);
                    boxy.LFMotor.setPower(-speed);
                    boxy.LBMotor.setPower(-speed);
                    telemetry.addLine("Turning Left");
                } else {
                    boxy.RBMotor.setPower(0);
                    boxy.RFMotor.setPower(0);
                    boxy.LFMotor.setPower(0);
                    boxy.LBMotor.setPower(0);
                }
            } else {
                if (degrees > target + 2) {
                    kbot.RBMotor.setPower(-speed);
                    kbot.RFMotor.setPower(-speed);
                    kbot.LFMotor.setPower(speed);
                    kbot.LBMotor.setPower(speed);
                    telemetry.addLine("Turning Right");
                } else if (degrees < target - 2) {
                    kbot.RBMotor.setPower(speed);
                    kbot.RFMotor.setPower(speed);
                    kbot.LFMotor.setPower(-speed);
                    kbot.LBMotor.setPower(-speed);
                    telemetry.addLine("Turning Left");
                } else {
                    kbot.RBMotor.setPower(0);
                    kbot.RFMotor.setPower(0);
                    kbot.LFMotor.setPower(0);
                    kbot.LBMotor.setPower(0);
                }
            }
            telemetry.update();
            idle();
        }
        sleep(1500);
    }
    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }
    String formatDegrees(double degrees){
        getNumDegrees(degrees);
        return String.format("%.1f", AngleUnit.DEGREES.normalize(degrees));
    }
    void getNumDegrees(double stuff) {
        degrees = stuff;
    }
}
