package chawks.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp(name = "MrTBoxy ", group = "TeleOp")
@Disabled
public class BoxyMrTHolonomi extends BoxyTheTank {

//Hello, How Are You

    public void handleGamePad1(Gamepad gamepad) {
        double r = Math.hypot(gamepad.left_stick_x, gamepad.left_stick_y);
        double robotAngle = Math.atan2(gamepad.left_stick_y, gamepad.left_stick_x) - Math.PI / 4;
        double rightX = gamepad.right_stick_x;
        final double v1 = r * Math.cos(robotAngle) - rightX;
        final double v2 = r * Math.sin(robotAngle) + rightX;
        final double v3 = r * Math.sin(robotAngle) - rightX;
        final double v4 = r * Math.cos(robotAngle) + rightX;


        robot.LFMotor.setPower((-v3)/2);
        robot.RFMotor.setPower((-v4)/2);
        robot.LBMotor.setPower((-v1)/2);
        robot.RBMotor.setPower((-v2)/2);
    }

    public void handleGamePad2(Gamepad gamepad) {
    }


//LFM, RFM, LBM, RBM

}
