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
public class TankTurnCourseCorrect extends LinearOpMode {
    NavxMicroNavigationSensor navxMicro;
    IntegratingGyroscope gyro;
    ElapsedTime timer;

    @Override
    public void runOpMode() throws InterruptedException {
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
        telemetry.log().clear();
        while (opModeIsActive()) {

        }
    }
}
