/*
Modern Robotics Digital In Example
Created 7/25/2017 by Colton Mehlhoff of Modern Robotics using FTC SDK 3.10
Reuse permitted with credit where credit is due

Configuration: Digital Device named "limit"
In this example, we are using a limit switch but you could use any digital input like a touch sensor.

Core Device Interface named "Device Interface Module 1"

Support is available by emailing support@modernroboticsinc.com
*/

package chawks.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannel;


@TeleOp(name = "Digital In Example", group = "MRI")
//@Disabled
public class MRI_Digital_In_Example extends LinearOpMode {

    //A Digital Input.
    DigitalChannel MRLimitSwitch;

    //CDI. Using this, we can read any digital sensor on this CDI without creating an instance for each sensor.
    DeviceInterfaceModule cdi;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Link objects to configuration file
        MRLimitSwitch = hardwareMap.digitalChannel.get("limit");
        cdi = hardwareMap.deviceInterfaceModule.get("Device Interface Module 1");

        waitForStart();

        while (opModeIsActive()) {

            //Read the limit switch using the instance of DigitalChannel.
            //Value will be true or false
            telemetry.addData("light", MRLimitSwitch.getState());

            //Read each Digital Port of the CDI. 0-7
            for (int i = 0; i < 8; i++) {
                telemetry.addData("Digital " + i, cdi.getDigitalChannelState(i));
            }
            telemetry.update();
        }
    }
}
