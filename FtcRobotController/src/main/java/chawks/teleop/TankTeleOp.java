package chawks.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import chawks.hardware.DrivingDirection;
import chawks.hardware.ShootingController;

@TeleOp(name = "Tank", group = "TeleOp")
public class
TankTeleOp extends AbstractTeleOpWithSpinner {

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
            robot.lf.setPower(leftPower);
            robot.rf.setPower(rightPower);
            robot.lb.setPower(-leftPower);
            robot.rb.setPower(-rightPower);
            return;
        } else if (isDPADRight) {
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
            robot.lf.setPower(leftPower);
            robot.rf.setPower(rightPower);
            robot.lb.setPower(-leftPower);
            robot.rb.setPower(-rightPower);
            return;

        } else if (isDPADUp) {
            shootingController.incrementUpSpinner();
        } else if (isDPADDown) {
            shootingController.incrementDownSpinner();
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

        robot.lb.setPower(leftPower);
        robot.lf.setPower(leftPower);
        robot.rf.setPower(rightPower);
        robot.rb.setPower(rightPower);
    }

    @Override
    public void handleGamePad2(Gamepad gamepad) {
        boolean isButtonA = gamepad.a;
        boolean isButtonB = gamepad.b;
        boolean isButtonX = gamepad.x;
        boolean isDirectionUp = gamepad.dpad_up;
        boolean isDirectionDown = gamepad.dpad_down;
        final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle

        final double MAX_POS     =  1.0;     // Maximum rotational position
        final double MIN_POS     =  0.0;     // Minimum rotational position

        // Define class members

        telemetry.addData("pad2", "a:%s, b:%s, up:%s, down:%s, lb:%s, rb%s",
                isButtonA, isButtonB, isDirectionUp, isDirectionDown, gamepad.left_bumper, gamepad.right_bumper);

        if (gamepad.left_bumper) {
            shootingController.setTargetPower(0);
        } else if (gamepad.right_bumper) {
            shootingController.setTargetPower(ShootingController.MAX_SPIN_MOTOR_SPEED);
        }

        if (isButtonB) {
            // Launch a ball
            // arm collector is the ball colector which controls cont. servos in middle
            double armSpeed = armController.getArmSpeed();
            robot.s4.setPower(armSpeed);
            robot.s3.setPower(armSpeed);
            robot.s2.setPower(armSpeed);

            robot.s4.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.s3.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.s2.setDirection(DcMotorSimple.Direction.REVERSE);
        }

        if (isButtonA) {
            // collect a ball and move 2 servos
            // arm collector is the ball colector which controls cont. servos in middle
            double armSpeed = armController.getArmSpeed();
            robot.s2.setPower(armSpeed);
            robot.s3.setPower(armSpeed);
            robot.s2.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.s3.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        if (isButtonX) {
            // reject ball collect and move 2 servos
            // this is an emergency reject if wrong color ball is taken in
            double armSpeed = armController.getArmSpeed();
            robot.s2.setPower(armSpeed);
            robot.s3.setPower(armSpeed);
            robot.s4.setPower(armSpeed);
            robot.s2.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.s3.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.s4.setDirection(DcMotorSimple.Direction.FORWARD);
        }


        if (!isButtonB && !isButtonA) {
            //back most servo - port 6
            robot.s4.setPower(0);
            // middle servo - port 2
            robot.s3.setPower(0);
            // launch servo - port 4
            robot.s2.setPower(0);
        }

        if (isDirectionUp) {
            double position = robot.arm.getPosition() + INCREMENT;
            robot.arm.setPosition(position);
           // armController.setPosition(position);
          //  armController.adjustPosition(armController.getIncrement());
        } else if (isDirectionDown) {
            double position = robot.arm.getPosition() - INCREMENT;
            robot.arm.setPosition(position);
            //armController.setPosition(position);
          //armController.adjustPosition(-armController.getIncrement());
        }
        telemetry.addData("Servo Actual Position = %.2f", robot.arm.getPosition());
    }

}
