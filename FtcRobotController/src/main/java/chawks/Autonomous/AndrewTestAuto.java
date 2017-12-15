package chawks.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcontroller.external.samples.ConceptVuMarkIdentification;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import chawks.hardware.Boxy;
@Autonomous(name="AndrewTest?", group="Test")
//Disabled
public class AndrewTestAuto extends LinearOpMode {

    /* Declare OpMode members. */
    Boxy robot   = new Boxy();   // Use a Pushbot's hardware
    VuforiaLocalizer vuforia;
    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "ASnFTXP/////AAAAGRItdPBpVU1Cpwf91MmRjZMgfHIOvk3JXTWDk0vMQBy7gb8tEtUF5L9G5d0CKMb8No6Y5ApK7vd+ROqL4fksIKpNGCyJwxc3vuVq3fUW13KV6mXl4/0/VmT/L03tOKjIds5v1ImaEz+7lQXqG0HCdcsDs5x0XtEBYKTgisFuzDZwmKTK3EXgRFl2kpf1ILJUEEbFMOskgRKUSTpXwWM3tDeix7B1Mu6fIafsL8VOvDuc1fzuJAHMO+rNL+yGjmrO2f421OzZgVYJxk6NbMJI5I6cbF12/L7LaTgMXnJ0oiKkJDc/QJY6m1u6/HNaP/kTOwqcT/mSRirXwZZUEx65qJ+x0/rOJa14+y5Zr5HutD7m";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");
        relicTrackables.activate();
        waitForStart();
        robot.init(hardwareMap);


        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        while (opModeIsActive()) {
            if (vuMark == RelicRecoveryVuMark.LEFT) {
                DriveFowardTime(-1,1);
                StrafeLeftTime(1,1);
            }
            if (vuMark == RelicRecoveryVuMark.RIGHT) {
                DriveFowardTime(-1,1);
                StrafeRightTime(1,1);
            }
            if (vuMark == RelicRecoveryVuMark.CENTER) {
                DriveFowardTime(-1,1);
            }
        }

    }
    double DRIVE_POWER = 1;
    long SLEEP_TIME = 1000;
    public void StrafeLeftTime(double power, long time) throws InterruptedException
    {
        StrafeLeft(power);
        Thread.sleep(time);
        StopRobot();
    }
    public void StrafeLeft(double power)
    {
        robot.RFMotor.setPower(power);
        robot.RBMotor.setPower(-power);
        robot.LFMotor.setPower(-power);
        robot.LBMotor.setPower(power);
    }
    public void StrafeRightTime(double power, long time) throws InterruptedException
    {
        StrafeRight(power);
        Thread.sleep(time);
        StopRobot();
    }
    public void StrafeRight(double power)
    {
        robot.RFMotor.setPower(-power);
        robot.RBMotor.setPower(power);
        robot.LFMotor.setPower(power);
        robot.LBMotor.setPower(-power);
    }
    public void DriveFowardTime(double power, long time) throws InterruptedException
    {
        DriveFoward(power);
        Thread.sleep(time);
        StopRobot();
    }
    public void DriveFoward(double power)
    {
        robot.LFMotor.setPower(power);
        robot.RFMotor.setPower(power);
        robot.LBMotor.setPower(power);
        robot.RBMotor.setPower(power);
    }
    public void TurnLeftTime(double power,long time) throws InterruptedException
    {
        TurnLeft(power);
        Thread.sleep(time);
        StopRobot();
    }
    public void TurnLeft(double power)
    {
        robot.LFMotor.setPower(power);
        robot.LBMotor.setPower(power);
        robot.RFMotor.setPower(-power);
        robot.RBMotor.setPower(-power);
    }
    public void TurnRightTime(double power, long time) throws InterruptedException
    {
        TurnRight(power);
        Thread.sleep(time);
        StopRobot();
    }
    public void TurnRight(double power)
    {
        robot.RFMotor.setPower(power);
        robot.RBMotor.setPower(power);
        robot.LFMotor.setPower(-power);
        robot.LBMotor.setPower(-power);
    }

    public void StopRobot () {
        DriveFoward(0);
    }
}

