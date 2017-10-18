package chawks.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import chawks.hardware.ArmController;
import chawks.hardware.Boxy;
import chawks.hardware.DrivingDirection;
import chawks.hardware.ShootingController;
@TeleOp(name = "GrabberTest")
public class TeleOpGrabber extends AbstractTeleOpModeWithBoxy {

    public void handleGamePad1(Gamepad gamepad) {
        robot.rGrabberServo.setPosition(0);
        robot.lGrabberServo.setPosition(0);
        if (gamepad.a) {
            robot.rGrabberServo.setPosition(-0.1);
            robot.lGrabberServo.setPosition(0.1);
        } else if (gamepad.b) {
            robot.rGrabberServo.setPosition(0);
            robot.lGrabberServo.setPosition(0);
        } else {

        }
    }

    public void handleGamePad2(Gamepad gamepad) {

    }
}
