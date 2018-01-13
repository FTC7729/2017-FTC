package chawks.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="DriveSystem")
public class DrivingTeleOp extends AbstractTeleOp {

    @Override
    public void handleGamePad1(Gamepad gamepad) {
        double C1LX = Range.clip(gamepad1.left_stick_x, -1, 1);
        double C1LY = Range.clip(gamepad1.left_stick_y, -1, 1);
        double C1RX = Range.clip(gamepad1.right_stick_x, -1, 1);

        robot.LFMotor.setPower(-C1LY - C1LX - C1RX);
        robot.RFMotor.setPower(C1LY - C1LX - C1RX);
        robot.RBMotor.setPower(C1LY + C1LX - C1RX);
        robot.LBMotor.setPower(-C1LY + C1LX - C1RX);
    }

    @Override
    public void handleGamePad2(Gamepad gamepad) {

    }
}
