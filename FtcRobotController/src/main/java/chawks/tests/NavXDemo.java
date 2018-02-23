package chawks.tests;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.util.ElapsedTime;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import chawks.hardware.Boxy;

/**
 * Created by ibtro on 2/3/2018.
 */
@Autonomous(name="NavX Demo",group="Test")
public class NavXDemo extends LinearOpMode {
    Boxy robot = new Boxy();
    IntegratingGyroscope gyro;
    NavxMicroNavigationSensor navxMicro;
    private ElapsedTime     runtime = new ElapsedTime();
    static final double     BOT_SPEED = 0.2;
    double degrees = 0;
    // A timer helps provide feedback while calibration is taking place
    ElapsedTime timer = new ElapsedTime();
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

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

        navxMicro = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");
        gyro = (IntegratingGyroscope)navxMicro;
        telemetry.log().add("Gyro Calibrating. Do Not Move!");

        // Wait until the gyro calibration is complete
        timer.reset();
        while (navxMicro.isCalibrating())  {
            telemetry.addData("calibrating", "%s", Math.round(timer.seconds())%2==0 ? "|.." : "..|");
            telemetry.update();
            Thread.sleep(50);
        }
        telemetry.log().clear(); telemetry.log().add("Gyro Calibrated. Press Start.");
        telemetry.clear(); telemetry.update();
        waitForStart();
        telemetry.clear();
        telemetry.addLine("Begin Turning!"); telemetry.update();
        sleep(250);
        navxTurn(45);
        sleep(250);
        navxTurn(-45);
        sleep(500);
        navxTurn(90);
        sleep(250);
        navxTurn(0);
        telemetry.clear();telemetry.addLine("We're done!");telemetry.update();
        sleep(500);
    }
    void navxTurn(double target) {
        AngularVelocity rates = gyro.getAngularVelocity(AngleUnit.DEGREES);
        Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        String heading = formatAngle(angles.angleUnit, angles.firstAngle);

        while (degrees > target + 2 || degrees < target - 2 || ((target == 180 || target == -180) && (degrees > -178 || degrees < 178))) {
            if (!opModeIsActive())
            {
                return;
            }

            rates = gyro.getAngularVelocity(AngleUnit.DEGREES);
            angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            heading = formatAngle(angles.angleUnit, angles.firstAngle);
            if (degrees > target + 2 || ((target == 180 || target == -180) && degrees < 178)) {
                robot.RBMotor.setPower(-BOT_SPEED);
                robot.RFMotor.setPower(-BOT_SPEED);
                robot.LFMotor.setPower(BOT_SPEED);
                robot.LBMotor.setPower(BOT_SPEED);
            } else if (degrees < target - 2 || ((target == 180 || target == -180) && degrees > -178)) {
                robot.RBMotor.setPower(BOT_SPEED);
                robot.RFMotor.setPower(BOT_SPEED);
                robot.LFMotor.setPower(-BOT_SPEED);
                robot.LBMotor.setPower(-BOT_SPEED);
            } else {
                robot.RBMotor.setPower(0);
                robot.RFMotor.setPower(0);
                robot.LFMotor.setPower(0);
                robot.LBMotor.setPower(0);
            }
            idle();
        }
        telemetry.addData("Done Turning","");
        sleep(1500);
        telemetry.log().clear();
    }
    String formatRate(float rate) {
        return String.format("%.3f", rate);
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
