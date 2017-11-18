package chawks.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import chawks.hardware.DrivingDirection;
import chawks.hardware.KBot;
//@TeleOp(name = "Holonomic Tank KBottt", group = "TeleOp")
public class MrTHolonomi extends KBotTheTank {

    double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
    double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
    double rightX = gamepad1.right_stick_x;
    final double v1 = r * Math.cos(robotAngle) + rightX;
    final double v2 = r * Math.sin(robotAngle) - rightX;
    final double v3 = r * Math.sin(robotAngle) + rightX;
    final double v4 = r * Math.cos(robotAngle) - rightX;

    public final KBot robot = new KBot();
    public void handleGamePad1(Gamepad gamepad) {


    }

    public void handleGamePad2(Gamepad gamepad) {
    }


//LFM, RFM, LBM, RBM

}
