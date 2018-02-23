package chawks.Autonomous;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import chawks.hardware.Boxy;
import chawks.hardware.NavXMicro;
@Autonomous(name="BlueAllianceStone1", group="safezone")
@Disabled
public class BlueAllianceStone1 extends LinearOpMode {
    IntegratingGyroscope gyro;
    NavxMicroNavigationSensor navxMicro;
    public double degrees;
    ElapsedTime timer = new ElapsedTime();
    Boxy         robot   = new Boxy();
    NavXMicro Navx = new NavXMicro();
    private ElapsedTime     runtime = new ElapsedTime();
    static final double     BOT_SPEED = 0.1;
    static final double     COUNTS_PER_MOTOR_REV    = 280 ;    // eg: NEVEREST 40 Motor Encoder
    static final double     ROTATIONS_PER_MINUTE    = 160 ;
    static final double     DRIVE_GEAR_REDUCTION    = 1.5 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.93701 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI);


    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;
    static final double     FORWARD_SPEED           = 0.6;
    static final double     BACKWARDS_SPEED         = -0.6;
        @Override
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

        robot.LFMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.RFMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.LBMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.RBMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        idle();

        robot.LFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.RFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.LBMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.RBMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Path0",  "Starting at %7d :%7d :%7d :%7d",
                robot.LFMotor.getCurrentPosition(),
                robot.RFMotor.getCurrentPosition(),
                robot.LBMotor.getCurrentPosition(),
                robot.RBMotor.getCurrentPosition()
        );
        telemetry.update();
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

      // Navx.initAndCalibrate(hardwareMap);
        //ON KBOT WAAAAAAAAAAY TOO SPEEDY
        waitForStart();
        telemetry.log().clear();
        //Start Code after here
        encoderDrive(.5,-.5,.5,-.5,.5,4);
        //speed 5 is too fast, less than 7 dist is too short.
        encoderDrive(.5,-7,-7,-7,-7,4);
       // Navx.turn(90.0, 0.1, robot);
        navxTurn(90.0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    public void encoderDrive(double speed, double leftInches, double rightInches, double leftBackInches, double rightBackInches, double timeoutS) {
        int newLeftTarget;
        int newRightTarget;
        int newLeftBackTarget;
        int newRightBackTarget;

        if (opModeIsActive()) {

            newLeftTarget = robot.LFMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.RFMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            newLeftBackTarget = robot.LBMotor.getCurrentPosition() + (int)(leftBackInches * COUNTS_PER_INCH);
            newRightBackTarget = robot.RBMotor.getCurrentPosition() + (int)(rightBackInches * COUNTS_PER_INCH);
            robot.LFMotor.setTargetPosition(newLeftTarget);
            robot.RFMotor.setTargetPosition(newRightTarget);
            robot.LBMotor.setTargetPosition(newLeftBackTarget);
            robot.RBMotor.setTargetPosition(newRightBackTarget);

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

        }
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


   void navxTurn(double target) {
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
                robot.RBMotor.setPower(-BOT_SPEED);
                robot.RFMotor.setPower(-BOT_SPEED);
                robot.LFMotor.setPower(BOT_SPEED);
                robot.LBMotor.setPower(BOT_SPEED);
            } else if (degrees < target - 2) {
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

}