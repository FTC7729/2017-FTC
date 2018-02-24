package chawks.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.Gamepad;

import chawks.teleop.AbstractTeleOpNextGen;
//import chawks.hardware.ShootingController;

@TeleOp(name = "nextgen with switchy lift test reset", group = "TeleOp")
//@Disabled
public class AndrewTest extends AbstractTeleOpNextGen {
    //A Digital Input.
    //DigitalChannel MRLimitSwitch;
    //CDI. Using this, we can read any digital sensor on this CDI without creating an instance for each sensor.

    DeviceInterfaceModule cdi;

    public float STRAFE_SPEED = 1.0F; //setting to a higher speed


    @Override
    public void handleGamePad1(Gamepad gamepad) {

    }

    @Override
    public void handleGamePad2(Gamepad gamepad) {//
        //  MRLimitSwitch = hardwareMap.digitalChannel.get("limit");
        telemetry.addData("ENcoder Locatron", robot.Elevate.getCurrentPosition());
        telemetry.addData("Switch State", robot.LimitSwitch.getState());
        telemetry.addData("Encoder Positron", robot.Lift_Tilt.getCurrentPosition());
        cdi = hardwareMap.deviceInterfaceModule.get("Device Interface Module 1");
        final boolean isDpadUp = gamepad.dpad_up;
        final boolean isDpadDown = gamepad.dpad_down;
        final boolean isButtonX = gamepad.x;
        final boolean isButtonB = gamepad.b;
        final boolean isButtonA = gamepad.a;
        final boolean isButtonY = gamepad.y;
        final boolean rightBumper = gamepad.right_bumper;
        final float rightTrigger = gamepad.right_trigger;
        final float leftTrigger = gamepad.left_trigger;
        //make sure to change lefty and righty values after confirming directions
        final boolean LimitSwitchElevate = robot.LimitSwitch.getState();
        final boolean LimitSwitchTilt = robot.LimitSwitchTilt.getState();
        final int ELEVATE_HIGH = 5010;
        final int ELEVATE_LOW = 50;
        final int LIFT_TILT_LOW = 9446;
        final int LIFT_TILT_MID = 6262;
        final int LIFT_TILT_HIGH = 50;
        if (isDpadUp) {
            robot.Elevate.setPower(0.5);
        }
        else if (isDpadDown) {
            robot.Elevate.setPower(-0.5);
        } else {
            robot.Elevate.setPower(0);
        }
        if (isButtonX) {
            robot.Lift_Tilt.setPower(0.5);
        } else if (isButtonB) {
            robot.Lift_Tilt.setPower(-0.5);
        } else {
            robot.Lift_Tilt.setPower(0);
        }

    }


}
