package chawks.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import chawks.hardware.DrivingDirection;

@TeleOp(name = "Holonomic Tank KBottt", group = "TeleOp")

public class HolonomicTankKBottt extends KBotTheTank {
    /**
     * The value "K" should never equal or be less than 0 as there will be no clockwise movement
     *
     * The value of "K" should never be greater than "1" as it will mess with values within the program, making them larger than the motors can handle
     */
    private final static float K = 0.5F;

    @Override
    public void handleGamePad1(Gamepad gamepad) {
        // TODO: would be nice to use exponential scaling of the Y value so that as you move stick further,
        float leftStickY = Range.clip(-gamepad.left_stick_y, -1, 1);
        float rightStickY = Range.clip(-gamepad.right_stick_y, -1, 1);
        final boolean isButtonX = gamepad.x;
        final boolean isButtonY = gamepad.y;
        double powerMod;
        telemetry.addData("pad1", "left:%.2f, right:%.2f, dir:%s", leftStickY, rightStickY, drivingDirection.name());

        final boolean isDPADLeft = gamepad.dpad_left;
        final boolean isDPADRight = gamepad.dpad_right;

        // switch driving directions
        if (isButtonY) {
            if (drivingDirection == DrivingDirection.FORWARD) {
                drivingDirection = DrivingDirection.REVERSE;
            } else if (drivingDirection == DrivingDirection.REVERSE) {
                drivingDirection = DrivingDirection.FORWARD;
            } else {
                drivingDirection = DrivingDirection.FORWARD;
            }
        }

        /**
         * The following switch statement handles the direction (Forward or Backwards
         */

        int directionControl = 1;
        switch (drivingDirection) {
            case FORWARD:
                directionControl = 1;
                break;
            case REVERSE:
                directionControl = -1;
                break;
        }

        // Set the power of forward, right, and clockwise motion in order to initiate drive system

        // push both joysticks forward to go forward, push both joysticks backwards to go backwards
        float forward = -directionControl * ((gamepad.right_stick_y + gamepad.left_stick_y) / 2);

        // push the joystick to the right to strafe right, in combination with the "forward" value the robot will be able to move holistically
        float right = directionControl * (gamepad.left_stick_x);

        // push left forward and right backwards to turn right, and vice versa to turn left :: This type is used to rotate the robot without conflict
        float clockwise = directionControl * ((gamepad.right_stick_y - gamepad.left_stick_y) / 2);
        /**
         * We multiply by "K" in order to manipulate the speed of turning
         * If turn speed is too high, go above and change the value of "K" to something lower
         * If turn speed is too low, go above and change the value of "K" to something higher
         */
        clockwise = K * clockwise;

        /**
         * Generate powers required to move each wheel in this type of movement
         * Each wheel must have its own generated type of power because if it didn't there wouldn't be mecanum movement
         */
        float lf_pow = forward + clockwise + right;
        float rf_pow = forward - clockwise - right;
        float lb_pow = forward + clockwise - right;
        float rb_pow = forward - clockwise + right;

        /**
         * Limits the movement powers to a range dependent upon the max
         * If the max exceeds "1", all of the powers are divided by the max in order to make them smaller and in doing such limiting them all to "-1" and "1"
         */
        float max = Math.abs(lf_pow);
        if (Math.abs(rf_pow) > max) max = Math.abs(rf_pow);
        if (Math.abs(lb_pow) > max) max = Math.abs(lb_pow);
        if (Math.abs(rb_pow) > max) max = Math.abs(rb_pow);
        if (max > 1) {
            lf_pow /= max;
            rf_pow /= max;
            lb_pow /= max;
            rb_pow /= max;
        }



        if(lb_pow == lf_pow){
            powerMod=0.5;
        }
        else{
            powerMod=1;
        }

        // Sets the power of each motor to their respective powers

        robot.LBMotor.setPower(lb_pow * powerMod);
        robot.RBMotor.setPower(rb_pow * powerMod);
        robot.RFMotor.setPower(rf_pow * powerMod);
        robot.LFMotor.setPower(lf_pow * powerMod);

        robot.LBMotor.setPower(lb_pow * .5);
        robot.RBMotor.setPower(rb_pow * .5);
        robot.RFMotor.setPower(rf_pow * .5);
        robot.LFMotor.setPower(lf_pow * .5);


    }
}
