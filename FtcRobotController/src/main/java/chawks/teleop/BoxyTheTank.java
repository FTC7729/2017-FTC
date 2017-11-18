package chawks.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;
import chawks.hardware.Robot;
import chawks.hardware.DrivingDirection;

@TeleOp(name = "BOXY DA TANK", group = "TeleOp")
public class BoxyTheTank extends AbstractTeleOp {

    public BoxyTheTank(Robot robot) {
        super(robot);
    }

    public float STRAFE_SPEED = 1.0F; //setting to a higher speed

    public void handleGamePad1(Gamepad gamepad) {
        // TODO: would be nice to use exponential scaling of the Y value so that as you move stick further,
        double y_axis = -Range.clip(-gamepad.left_stick_y, -1, 1);
        double x_axis = Range.clip(-gamepad.left_stick_x, -1, 1);
        double clockwise = Range.clip(-gamepad.right_stick_x, -1, 1);

        double K = 0.4;
        clockwise = K * clockwise;

        double front_left = y_axis + clockwise + x_axis;
        double front_right = y_axis - clockwise - x_axis;
        double rear_left = y_axis + clockwise - x_axis;
        double rear_right = y_axis - clockwise + x_axis;

        double max = Math.abs(front_left);
        if (Math.abs(front_right)>max) max = Math.abs(front_right);
        if (Math.abs(rear_left)>max) max = Math.abs(rear_left);
        if (Math.abs(rear_right)>max) max = Math.abs(rear_right);
        if (max>1)
        {front_left/=max; front_right/=max; rear_left/=max; rear_right/=max;}

        robot.LFMotor.setPower(front_left);
        robot.LBMotor.setPower(rear_left);
        robot.RFMotor.setPower(front_right);
        robot.LBMotor.setPower(rear_right);

    }

    @Override
    public void handleGamePad2(Gamepad gamepad) {}

}
