package chawks.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by joseph on 1/11/18.
 */

@TeleOp(name="TestWheels")
public class WheelTestTeleOp extends AbstractTeleOp {

    @Override
    public void handleGamePad1(Gamepad gamepad) {
        boolean a = gamepad1.a;
        boolean b = gamepad1.b;
        boolean x = gamepad1.x;
        boolean y = gamepad1.y;

        if (a) {
            robot.RFMotor.setPower(100);
        } else if (b) {
            robot.RBMotor.setPower(100);
        } else if (x) {
            robot.LFMotor.setPower(100);
        } else if (y) {
            robot.LBMotor.setPower(100);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void handleGamePad2(Gamepad gamepad) {

    }
}
