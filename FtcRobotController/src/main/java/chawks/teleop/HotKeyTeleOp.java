package chawks.teleop;


/**
 * Created by karahandy on 10/21/17.
 */
    import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
    import com.qualcomm.robotcore.hardware.Gamepad;
    import com.qualcomm.robotcore.util.Range;

import chawks.hardware.DrivingDirection;

/**
 public class HotKeyTeleOp {
    @Override
    public void handleGamePad2(Gamepad gamepad){
        //for testing purposes it is on GamePad2
        if (gamepad.left_bumper){
            //turns robot left by putting left side negative and right side positive
            robot.LFMotor.setPower(-1.00);
            robot.LBMotor.setPower(-1.00);
            robot.RFMotor.setPower(1.00);
            robot.RBMotor.setPower(1.00);

 *       } else if (gamepad.right_bumper){
            //turns robot right by putting left side positive and right side negative
            robot.LFMotor.setPower(1.00);
            robot.LBMotor.setPower(1.00);
            robot.RFMotor.setPower(-1.00);
            robot.RBMotor.setPower(-1.00);

        }

}


*/

