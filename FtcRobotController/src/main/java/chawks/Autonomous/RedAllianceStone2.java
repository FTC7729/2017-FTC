package chawks.Autonomous;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import chawks.hardware.Boxy;
@Autonomous(name="RedAllianceStone2", group="safezone")
public class RedAllianceStone2 extends AbstractSafeZoneAutonomous {

    public void movement() {
        //Start Code after here
        encoderDrive(.75,-.5,.5,-.5,.5,4);
        //speed 5 is too fast, less than 7 dist is too short.
        encoderDrive(.5,-5,-5,-5,-5,4);
        navxTurn(0.0);
    }
}