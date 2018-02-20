package chawks.tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import chawks.teleop.AbstractTeleOpNextGen;

import chawks.hardware.DrivingDirection;
//import chawks.hardware.ShootingController;

@TeleOp(name = "nextgen with switchy lift test reset", group = "TeleOp")
public class AndrewTest extends AbstractTeleOpNextGen  {

    //A Digital Input.
    //DigitalChannel MRLimitSwitch;

    //CDI. Using this, we can read any digital sensor on this CDI without creating an instance for each sensor.
    DeviceInterfaceModule cdi;
    public void init() {
        super.init();
        boolean LimitSwitchElevate = robot.LimitSwitch.getState();
        if (LimitSwitchElevate == true) {
            robot.Elevate.setPower(-0.15);
        } else {
            robot.Elevate.setPower(0);
        }
        if (LimitSwitchElevate == false) {
            robot.Elevate.setPower(0);
            robot.Elevate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }
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
        telemetry.addData("ENcoder Locatron", robot.Elevate.getCurrentPosition());
        cdi = hardwareMap.deviceInterfaceModule.get("Device Interface Module 1");
        final boolean isDpadUp = gamepad.dpad_up;
        final boolean isDpadDown = gamepad.dpad_down;
        final boolean isButtonA = gamepad.a;
        final boolean isButtonB = gamepad.b;
        final boolean isButtonX = gamepad.x;
        final boolean isButtonY = gamepad.y;
        //final double rightTrigger = gamepad.right_trigger;
        //final double leftTrigger = gamepad.left_trigger;
        //make sure to change lefty and righty values after confirming directions
        final boolean LimitSwitchElevate = robot.LimitSwitch.getState();
        final boolean LimitSwitchTilt = robot.LimitSwitchTilt.getState();

        if (isButtonY) {
            robot.Intake.setPower(0.5);

        }
        else if(isButtonA) {
            robot.Intake.setPower(-0.5);
        }
        else {
            robot.Intake.setPower(0);
        }


        if (isButtonX) {
            robot.Lift_Tilt.setPower( 0.5);
        } else if (LimitSwitchTilt == false) {
            telemetry.addData("Tilt switch triggered", "hi");
        } else if (LimitSwitchTilt == true) {
            if (isButtonB) {
                robot.Lift_Tilt.setPower(-0.5);
            } else {
                robot.Lift_Tilt.setPower(0);
            }
        }


        if (isDpadUp) {
            robot.Elevate.setPower(0.5);
        } else if (LimitSwitchElevate == false) {
            telemetry.addData("YOU TOUCHDED MA SWITCHED. TRIGGERED","COOL");
        } else if (LimitSwitchElevate == true) {
            if (isDpadDown) {
                robot.Elevate.setPower(-0.5);
            }
            } else {
            robot.Elevate.setPower(0);
        }



        /*if (isDpadUp && !MRLimitSwitch.getState()){
            robot.Elevate.setPower(.5);
        }else if (isDpadDown && !MRLimitSwitch.getState()){
                robot.Elevate.setPower(-.5);
        }else{
            robot.Elevate.setPower(0)0000000000000000000000000000000000000000000;
        }*/

    }





}
