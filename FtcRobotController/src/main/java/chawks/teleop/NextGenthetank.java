package chawks.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import chawks.hardware.DrivingDirection;
//import chawks.hardware.ShootingController;

@TeleOp(name = "nextgen DA TANK", group = "TeleOp")
@Disabled
public class NextGenthetank extends AbstractTeleOpNextGen {
    //A Digital Input.
    //DigitalChannel MRLimitSwitch;

    //CDI. Using this, we can read any digital sensor on this CDI without creating an instance for each sensor.
    DeviceInterfaceModule cdi;

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
            robot.LFMotor.setPower(leftPower);
            robot.RFMotor.setPower(rightPower);
            robot.LBMotor.setPower(-leftPower);
            robot.RBMotor.setPower(-rightPower);
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

            robot.LFMotor.setPower(leftPower);
            robot.RFMotor.setPower(rightPower);
            robot.LBMotor.setPower(-leftPower);
            robot.RBMotor.setPower(-rightPower);
            return;

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

    @Override
    public void handleGamePad2(Gamepad gamepad) {//
      //  MRLimitSwitch = hardwareMap.digitalChannel.get("limit");
        cdi = hardwareMap.deviceInterfaceModule.get("Device Interface Module 1");
        final boolean isDpadUp = gamepad.dpad_up;
        final boolean isDpadDown = gamepad.dpad_down;
        final boolean isButtonA = gamepad.a;
        final boolean isButtonB = gamepad.b;
        final boolean isButtonX = gamepad.x;
        final boolean isButtonY = gamepad.y;
        final double rightTrigger = gamepad.right_trigger;
        final double leftTrigger = gamepad.left_trigger;
        //final double COLLECTOR_SPEED = 0.7;
        //final double LIFT_TILT_SPEED = 0.3;
        /*int toggle = 0;
        if (isButtonB) {
            if(toggle == 0) {
                toggle++;
            }
            if (toggle == 1) {
                toggle--;
            }
        }*/
        //if (toggle == 0) {


        /*}
        if (toggle == 1) {
            if (isButtonA){
                robot.RGServo.setPosition(.7);
                robot.LGServo.setPosition(.3);
            } else {
                robot.RGServo.setPosition(.4);
                robot.LGServo.setPosition(.6);
            }
            if (isButtonX) {
                robot.LGServo.setPosition(.3);
            } else {
                robot.LGServo.setPosition(.6);
            */

            //make sure to change lefty and righty values after confirming directions
            if (isButtonY) {
                robot.Intake.setPower(0.5);

            } else {
                robot.Intake.setPower(0);

            }
        if (isButtonX) {
            robot.Lift_Tilt.setPower(0.5);
        } else if (isButtonB) {
            robot.Lift_Tilt.setPower(-0.5);
        } else {
            robot.Lift_Tilt.setPower(0);
        }


        if (isDpadUp){
            robot.Elevate.setPower(0.5);
        }else if (isDpadDown){
            robot.Elevate.setPower(-0.5);
        }else{
            robot.Elevate.setPower(0);
        }

        /*if (isDpadUp && !MRLimitSwitch.getState()){
            robot.Elevate.setPower(.5);
        }else if (isDpadDown && !MRLimitSwitch.getState()){
                robot.Elevate.setPower(-.5);
        }else{
            robot.Elevate.setPower(0)0000000000000000000000000000000000000000000;
        }*/

    }//





}
