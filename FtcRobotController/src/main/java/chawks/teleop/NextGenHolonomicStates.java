package chawks.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.Gamepad;
//import chawks.hardware.ShootingController;

@TeleOp(name = "NextGen Holonomic States", group = "StatesTeleOp")
//@Disabled
public class NextGenHolonomicStates extends AbstractTeleOpNextGen {
    //A Digital Input.
    //DigitalChannel MRLimitSwitch;
    //CDI. Using this, we can read any digital sensor on this CDI without creating an instance for each sensor.

    DeviceInterfaceModule cdi;

    public void init() {
        //telemetry.addData("Entering Init","YAy");
        //telemetry.update();
        super.init();
        while (robot.LimitSwitch.getState() == true) {
            //telemetry.addData("Resetting:","Encoder");
            //telemetry.update();
            robot.Elevate.setPower(-0.175);
            /*if (opModeIsActive() == false) {
                robot.Elevate.setPower(0);
                return;
            }*/
        }
        while (robot.LimitSwitchTilt.getState() == true) {
            robot.Lift_Tilt.setPower(-0.4);
        }
        telemetry.addData("Switch touched Tilt", "o");
        robot.Elevate.setPower(0);
        robot.Lift_Tilt.setPower(0);
        robot.Lift_Tilt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Lift_Tilt.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.Elevate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Elevate.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public float STRAFE_SPEED = 1.0F; //setting to a higher speed


    @Override
    public void handleGamePad1(Gamepad gamepad) {
        double r = Math.hypot(gamepad.left_stick_x, gamepad.left_stick_y);
        double robotAngle = Math.atan2(gamepad.left_stick_y, gamepad.left_stick_x) - Math.PI / 4;
        double rightX = gamepad.right_stick_x;
        final double v1 = r * Math.cos(robotAngle) - rightX;
        final double v2 = r * Math.sin(robotAngle) + rightX;
        final double v3 = r * Math.sin(robotAngle) - rightX;
        final double v4 = r * Math.cos(robotAngle) + rightX;


        robot.LFMotor.setPower(v3);
        robot.RFMotor.setPower(v4);
        robot.LBMotor.setPower(v1);
        robot.RBMotor.setPower(v2);
    }

    @Override
    public void handleGamePad2(Gamepad gamepad) {//
        //  MRLimitSwitch = hardwareMap.digitalChannel.get("limit");
        telemetry.addData("ENcoder Locatron", robot.Elevate.getCurrentPosition());
        telemetry.addData("Switch State", robot.LimitSwitch.getState());
        telemetry.addData("Encoder Positron", robot.Lift_Tilt.getCurrentPosition());
        cdi = hardwareMap.deviceInterfaceModule.get("Device Interface Module 1");
        final boolean isDpadUp = gamepad.dpad_up;
        final boolean isDpadDown = gamepad.dpad_down;
        final boolean isButtonX = gamepad.x;
        final boolean isButtonB = gamepad.b;
        final boolean isButtonA = gamepad.a;
        final boolean isButtonY = gamepad.y;
        final boolean rightBumper = gamepad.right_bumper;
        final float rightTrigger = gamepad.right_trigger;
        final float leftTrigger = gamepad.left_trigger;
        //make sure to change lefty and righty values after confirming directions
        final boolean LimitSwitchElevate = robot.LimitSwitch.getState();
        final boolean LimitSwitchTilt = robot.LimitSwitchTilt.getState();
        final int ELEVATE_HIGH = 5010;
        final int ELEVATE_LOW = 50;
        final int LIFT_TILT_LOW = 9446;
        final int LIFT_TILT_MID = 6262;
        final int LIFT_TILT_HIGH = 50;
        if (leftTrigger > .2) {
            robot.Intake.setPower(0.5);

        } else if (rightTrigger > .2) {
            robot.Intake.setPower(-0.5);
        } else {
            robot.Intake.setPower(0);
        }
        if (isButtonB) {
            robot.Lift_Tilt.setTargetPosition(LIFT_TILT_MID);
            robot.Lift_Tilt.setPower(-0.5);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {

            }
            robot.Elevate.setTargetPosition(ELEVATE_HIGH);
            robot.Elevate.setPower(-0.5);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {

            }
            robot.Lift_Tilt.setTargetPosition(LIFT_TILT_HIGH);
            robot.Lift_Tilt.setPower(0.5);
        }
        else if (isButtonA) {
            robot.Elevate.setTargetPosition(ELEVATE_LOW);
            robot.Elevate.setPower(-0.5);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {

            }
            robot.Lift_Tilt.setTargetPosition(LIFT_TILT_LOW);
            robot.Lift_Tilt.setPower(0.5);
        }
        else if (isButtonX){
            robot.Elevate.setTargetPosition(ELEVATE_LOW);
            robot.Elevate.setPower(-0.5);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            robot.Lift_Tilt.setTargetPosition(LIFT_TILT_MID);
            robot.Lift_Tilt.setPower(-0.5);
        }
        else if (isButtonY) {
            robot.Elevate.setTargetPosition(ELEVATE_LOW);
            robot.Elevate.setPower(-0.5);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            robot.Lift_Tilt.setTargetPosition(LIFT_TILT_HIGH);
            robot.Lift_Tilt.setPower(-0.5);
        }


        if (isDpadUp) {
            robot.Elevate.setTargetPosition(ELEVATE_HIGH);
            robot.Elevate.setPower(0.5);
        }
        else if (isDpadDown) {
                robot.Elevate.setTargetPosition(ELEVATE_LOW);
                robot.Elevate.setPower(-0.5);
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
