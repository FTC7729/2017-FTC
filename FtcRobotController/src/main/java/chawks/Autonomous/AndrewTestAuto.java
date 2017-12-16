/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package chawks.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import chawks.hardware.*;

@Autonomous(name="AndrewTestAuto?", group ="Concept")
public class AndrewTestAuto extends LinearOpMode {

    public static final String TAG = "Vuforia VuMark Sample";

    OpenGLMatrix lastLocation = null;

    VuforiaLocalizer vuforia;

    KBot robot = new KBot();

    @Override public void runOpMode() {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "ASnFTXP/////AAAAGRItdPBpVU1Cpwf91MmRjZMgfHIOvk3JXTWDk0vMQBy7gb8tEtUF5L9G5d0CKMb8No6Y5ApK7vd+ROqL4fksIKpNGCyJwxc3vuVq3fUW13KV6mXl4/0/VmT/L03tOKjIds5v1ImaEz+7lQXqG0HCdcsDs5x0XtEBYKTgisFuzDZwmKTK3EXgRFl2kpf1ILJUEEbFMOskgRKUSTpXwWM3tDeix7B1Mu6fIafsL8VOvDuc1fzuJAHMO+rNL+yGjmrO2f421OzZgVYJxk6NbMJI5I6cbF12/L7LaTgMXnJ0oiKkJDc/QJY6m1u6/HNaP/kTOwqcT/mSRirXwZZUEx65qJ+x0/rOJa14+y5Zr5HutD7m";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();
        robot.init(hardwareMap);
        relicTrackables.activate();

        while (opModeIsActive()) {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                telemetry.addData("VuMark", "%s visible", vuMark);
                if (vuMark == RelicRecoveryVuMark.LEFT) {
                    robot.LFMotor.setPower(1);
                    robot.RFMotor.setPower(-1);
                    robot.LBMotor.setPower(-1);
                    robot.RBMotor.setPower(1);
                }
                if (vuMark == RelicRecoveryVuMark.RIGHT) {
                    robot.LFMotor.setPower(-1);
                    robot.RFMotor.setPower(1);
                    robot.LBMotor.setPower(1);
                    robot.RBMotor.setPower(-1);
                }
                if (vuMark == RelicRecoveryVuMark.CENTER) {
                    robot.LFMotor.setPower(-1);
                    robot.RFMotor.setPower(-1);
                    robot.LBMotor.setPower(-1);
                    robot.RBMotor.setPower(-1);
                }
            }
            else {
                telemetry.addData("VuMark", "not visible");
            }

            telemetry.update();
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
