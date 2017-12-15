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
import chawks.hardware.KBot;
import chawks.hardware.TurnType;

public class NavXMicro extends LinearOpMode{
    double degrees;
    //Needed to not make this class abstract
    public void runOpMode() {}
    NavxMicroNavigationSensor navxMicro;
    IntegratingGyroscope gyro;
    ElapsedTime timer = new ElapsedTime();
    public NavXMicro() {

    }
    public void initAndCalibrate(HardwareMap hardwareMap) throws InterruptedException {
        navxMicro = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");
        gyro = (IntegratingGyroscope)navxMicro;
        // Wait until the gyro calibration is complete
        timer.reset();
        while (navxMicro.isCalibrating())  {
            Thread.sleep(50);
        }
    }

    /**
     * Use the NavX Micro Navigation sensor to complete a turn by a set amount of degrees
     * @param target target angle, relative to heading on startup
     * @param speed turning speed, usually between 0.1 and 0.5
     */
    public void turn(double target, double speed, Boxy bot) {
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
            heading = formatAngle(angles.angleUnit, angles.firstAngle);
                if (degrees > target + 2) {
                    bot.RBMotor.setPower(-speed);
                    bot.RFMotor.setPower(-speed);
                    bot.LFMotor.setPower(speed);
                    bot.LBMotor.setPower(speed);
                } else if (degrees < target - 2) {
                    bot.RBMotor.setPower(speed);
                    bot.RFMotor.setPower(speed);
                    bot.LFMotor.setPower(-speed);
                    bot.LBMotor.setPower(-speed);
                } else {
                    bot.RBMotor.setPower(0);
                    bot.RFMotor.setPower(0);
                    bot.LFMotor.setPower(0);
                    bot.LBMotor.setPower(0);
                }
            idle();
        }
        sleep(1500);
    }

    public void turn(double target, double speed, KBot bot) {
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
            heading = formatAngle(angles.angleUnit, angles.firstAngle);
            if (degrees > target + 2) {
                bot.RBMotor.setPower(-speed);
                bot.RFMotor.setPower(-speed);
                bot.LFMotor.setPower(speed);
                bot.LBMotor.setPower(speed);
            } else if (degrees < target - 2) {
                bot.RBMotor.setPower(speed);
                bot.RFMotor.setPower(speed);
                bot.LFMotor.setPower(-speed);
                bot.LBMotor.setPower(-speed);
            } else {
                bot.RBMotor.setPower(0);
                bot.RFMotor.setPower(0);
                bot.LFMotor.setPower(0);
                bot.LBMotor.setPower(0);
            }
            idle();
        }
        sleep(1500);
    }

    /**
     * Slight modification of original two-parameter turn() method. Uses NavX Micro Navigation sensor
     * to turn a specified amount at a specified speed. Has an option to switch from default relative
     * to init heading to relative to heading at turn() method call.
     * @param target target angle, relative to heading when called (turnType = TurnType.RELATIVE), or
     *               relative to heading on opmode init (turnType = TurnType.ABSOLUTE)
     * @param speed turning speed, usually between 0.1 and 0.5
     * @param turnType set to either TurnType.RELATIVE or TurnType.ABSOLUTE
     */
    public void turn(double target, double speed, Boxy bot, TurnType turnType) {
        TurnType RELATIVE = turnType.RELATIVE;
        AngularVelocity rates = gyro.getAngularVelocity(AngleUnit.DEGREES);
        Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        String heading = formatAngle(angles.angleUnit, angles.firstAngle);
        if (turnType == RELATIVE) {
            target = target + degrees;
        }
        while (degrees > target + 2 || degrees < target - 2) {
            if (!opModeIsActive())
            {
                return;
            }
            rates = gyro.getAngularVelocity(AngleUnit.DEGREES);
            angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            heading = formatAngle(angles.angleUnit, angles.firstAngle);
                if (degrees > target + 2) {
                    bot.RBMotor.setPower(-speed);
                    bot.RFMotor.setPower(-speed);
                    bot.LFMotor.setPower(speed);
                    bot.LBMotor.setPower(speed);
                } else if (degrees < target - 2) {
                    bot.RBMotor.setPower(speed);
                    bot.RFMotor.setPower(speed);
                    bot.LFMotor.setPower(-speed);
                    bot.LBMotor.setPower(-speed);
                } else {
                    bot.RBMotor.setPower(0);
                    bot.RFMotor.setPower(0);
                    bot.LFMotor.setPower(0);
                    bot.LBMotor.setPower(0);
                }
            idle();
        }
        sleep(1500);
    }

    public void turn(double target, double speed, KBot bot, TurnType turnType) {
        TurnType RELATIVE = turnType.RELATIVE;
        AngularVelocity rates = gyro.getAngularVelocity(AngleUnit.DEGREES);
        Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        String heading = formatAngle(angles.angleUnit, angles.firstAngle);
        if (turnType == RELATIVE) {
            target = target + degrees;
        }
        while (degrees > target + 2 || degrees < target - 2) {
            if (!opModeIsActive())
            {
                return;
            }
            rates = gyro.getAngularVelocity(AngleUnit.DEGREES);
            angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            heading = formatAngle(angles.angleUnit, angles.firstAngle);
            if (degrees > target + 2) {
                bot.RBMotor.setPower(-speed);
                bot.RFMotor.setPower(-speed);
                bot.LFMotor.setPower(speed);
                bot.LBMotor.setPower(speed);
            } else if (degrees < target - 2) {
                bot.RBMotor.setPower(speed);
                bot.RFMotor.setPower(speed);
                bot.LFMotor.setPower(-speed);
                bot.LBMotor.setPower(-speed);
            } else {
                bot.RBMotor.setPower(0);
                bot.RFMotor.setPower(0);
                bot.LFMotor.setPower(0);
                bot.LBMotor.setPower(0);
            }
            idle();
        }
        sleep(1500);
    }

    //The following methods should not be called. They are used as part of the turn() method
    //to grab heading angle values

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
