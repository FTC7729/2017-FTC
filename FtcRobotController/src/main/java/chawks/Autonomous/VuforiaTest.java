package chawks.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous(name = "Vuforia Demo Test", group = "Test")
@Disabled //comment this out to enable the opmode
public class VuforiaTest extends LinearOpMode {
    OpenGLMatrix lastlocation = null;
    VuforiaLocalizer vuforia;
    @Override
    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "ASnFTXP/////AAAAGRItdPBpVU1Cpwf91MmRjZMgfHIOvk3JXTWDk0vMQBy7gb8tEtUF5L9G5d0CKMb8No6Y5ApK7vd+ROqL4fksIKpNGCyJwxc3vuVq3fUW13KV6mXl4/0/VmT/L03tOKjIds5v1ImaEz+7lQXqG0HCdcsDs5x0XtEBYKTgisFuzDZwmKTK3EXgRFl2kpf1ILJUEEbFMOskgRKUSTpXwWM3tDeix7B1Mu6fIafsL8VOvDuc1fzuJAHMO+rNL+yGjmrO2f421OzZgVYJxk6NbMJI5I6cbF12/L7LaTgMXnJ0oiKkJDc/QJY6m1u6/HNaP/kTOwqcT/mSRirXwZZUEx65qJ+x0/rOJa14+y5Zr5HutD7m";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);

        waitForStart();

        relicTrackables.activate();

        while (opModeIsActive()) {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

            if(vuMark != RelicRecoveryVuMark.UNKNOWN) {
                telemetry.addData("Vuforia","Put the glyph in the %s Column", vuMark);
            } else {
                telemetry.addData("Vuforia","No Target Found!");
            }
            telemetry.update();
        }
    }
}
