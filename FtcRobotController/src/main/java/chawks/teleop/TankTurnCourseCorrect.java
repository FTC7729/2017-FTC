package chawks.teleop;
import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import chawks.hardware.Boxy;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
/**
 * Created by ibtro on 10/24/2017.
 */
@TeleOp(name = "Course Correction: Tank Turn", group = "TeleOp")
public abstract class TankTurnCourseCorrect extends LinearOpMode {

    public double degrees;
    NavxMicroNavigationSensor navxMicro;
    IntegratingGyroscope gyro;
    ElapsedTime timer = new ElapsedTime();
    private final Boxy robot = new Boxy();




    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        navxMicro = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");
        gyro = (IntegratingGyroscope) navxMicro;
        telemetry.log().add("Gyro Calibrating. Do Not Move!");
        timer.reset();
        while (navxMicro.isCalibrating()) {
            telemetry.addData("calibrating", "%s", Math.round(timer.seconds()) % 2 == 0 ? "|.." : "..|");
            telemetry.update();
            Thread.sleep(50);
        }
        telemetry.log().clear();
        telemetry.log().add("Gyro Calibrated. Press Start.");
        telemetry.clear();
        telemetry.update();

        waitForStart();

        start();
        telemetry.log().clear();
        while (opModeIsActive()) {
            AngularVelocity rates = gyro.getAngularVelocity(AngleUnit.DEGREES);
            Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            String heading = formatAngle(angles.angleUnit, angles.firstAngle);

            if (degrees > 5) {
                robot.LBMotor.setPower(1.0);
                robot.LFMotor.setPower(1.0);
                robot.RFMotor.setPower(-1.0);
                robot.RBMotor.setPower(-1.0);

            } else if (degrees < -5) {
                robot.LBMotor.setPower(-1.0);
                robot.LFMotor.setPower(-1.0);
                robot.RFMotor.setPower(1.0);
                robot.RBMotor.setPower(1.0);
            } else if (degrees > -5 && degrees < 5) {
                telemetry.addLine()
                        .addData("On Course! Bearing: ",formatAngle(angles.angleUnit, angles.firstAngle));
                telemetry.update();
            }
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
