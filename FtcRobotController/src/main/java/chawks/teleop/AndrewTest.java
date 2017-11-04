package chawks.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import chawks.hardware.DrivingDirection;
//import chawks.hardware.ShootingController;

//@TeleOp(name = "AndrewTest", group = "TeleOp")
public class AndrewTest extends AbstractTeleOpBoxy {


    public float STRAFE_SPEED = 1.0F; //setting to a higher speed

    @Override
    public void handleGamePad1(Gamepad gamepad) {
        // TODO: would be nice to use exponential scaling of the Y value so that as you move stick further,
        float leftStickY = Range.clip(-gamepad.left_stick_y, -1, 1);
        float rightStickY = Range.clip(-gamepad.right_stick_y, -1, 1);
        final boolean isButtonX = gamepad.x;
        final boolean isButtonY = gamepad.y;
        telemetry.addData("pad1", "left:%.2f, right:%.2f, dir:%s", leftStickY, rightStickY, drivingDirection.name());

        final boolean isDPADLeft = gamepad.dpad_left;
        final boolean isDPADRight = gamepad.dpad_right;
        final boolean isDPADUp = gamepad1.dpad_up;
        final boolean isDPADDown = gamepad1.dpad_down;
        final float isRightTrigger = gamepad1.right_trigger;
        final float isLeftTrigger = gamepad1.left_trigger;
        final boolean isRightBumper = gamepad1.right_bumper;
        final boolean isLeftBumper = gamepad1.left_bumper;

        // switch driving directions
        if (isButtonX) {
            drivingDirection = DrivingDirection.FORWARD;
        } else if (isButtonY) {
            drivingDirection = DrivingDirection.REVERSE;
        }

        // if either the DPAD left/right buttons are depressed, then we are strafing and setting the
        // wheel power to move left or right
        if (isDPADLeft) {
            float leftPower;
            float rightPower;
            switch (drivingDirection) {
                case FORWARD:
                default:
                    leftPower = -STRAFE_SPEED;
                    rightPower = STRAFE_SPEED;
                    break;
                case REVERSE:
                    leftPower = STRAFE_SPEED;
                    rightPower = -STRAFE_SPEED;
                    break;
            }
            robot.LFMotor.setPower(leftPower);
            robot.RFMotor.setPower(rightPower);
            robot.LBMotor.setPower(-leftPower);
            robot.RBMotor.setPower(-rightPower);
            return;
        } else {
            if (isDPADRight) {
                float leftPower;
                float rightPower;
                switch (drivingDirection) {
                    case FORWARD:
                    default:
                        leftPower = STRAFE_SPEED;
                        rightPower = -STRAFE_SPEED;
                        break;
                    case REVERSE:
                        leftPower = -STRAFE_SPEED;
                        rightPower = STRAFE_SPEED;
                        break;
                }
                robot.LFMotor.setPower(leftPower);
                robot.RFMotor.setPower(rightPower);
                robot.LBMotor.setPower(-leftPower);
                robot.RBMotor.setPower(-rightPower);
                return;
            }
            if ((isRightTrigger <= 1) && (isLeftTrigger > .2)) {
                robot.LFMotor.setPower(1);
                robot.RBMotor.setPower(1);
                robot.LBMotor.setPower(0);
                robot.RFMotor.setPower(0);
            } else if ((isLeftTrigger <= 1) && (isLeftTrigger > .2)) {
                robot.LFMotor.setPower(0);
                robot.RBMotor.setPower(0);
                robot.LBMotor.setPower(1);
                robot.RFMotor.setPower(1);
            } else if (isRightBumper) {
                robot.LFMotor.setPower(0);
                robot.RBMotor.setPower(0);
                robot.LBMotor.setPower(-1);
                robot.RFMotor.setPower(-1);
            } else if (isLeftBumper) {
                robot.LFMotor.setPower(-1);
                robot.RBMotor.setPower(-1);
                robot.LBMotor.setPower(0);
                robot.RFMotor.setPower(0);
            } else {
                robot.LFMotor.setPower(leftStickY);
                robot.RBMotor.setPower(rightStickY);
                robot.LBMotor.setPower(leftStickY);
                robot.RFMotor.setPower(rightStickY);
            }

            // the moment we take our finger off the DPAD, we are using the left and right stick values
            // to determine the power to apply to wheels.
            final float leftPower;
            final float rightPower;
            switch (drivingDirection) {
                case FORWARD:
                default:
                    leftPower = leftStickY;
                    rightPower = rightStickY;
                    break;
                case REVERSE:
                    leftPower = -rightStickY;
                    rightPower = -leftStickY;
                    break;
            }

            robot.LBMotor.setPower(leftPower);
            robot.LFMotor.setPower(leftPower);
            robot.RFMotor.setPower(rightPower);
            robot.RBMotor.setPower(rightPower);
        }
    }




    @Override

    public void handleGamePad2 (Gamepad gamepad){
    }
}
