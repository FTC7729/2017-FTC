package chawks.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.Gamepad;
//import chawks.hardware.ShootingController;

@TeleOp(name = "NextGenMakerFar", group = "StatesTeleOp")
//@Disabled
public class NextGenMakerFar extends AbstractTeleOpNextGen {
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
    public void handleGamePad2(Gamepad gamepad){

    }


}

