package chawks.Autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import chawks.hardware.Boxy;

/*
 * Created by joseph on 1/21/17.
 */
//@Autonomous(name = "BlueBoxyRight", group = "Autonomous")
@Autonomous(name = "BoxyBlueRightOfDoom", group = "Autonomous")
public class BoxyBlueRight extends AbstractDeadReckoningOpMode {

    private final Boxy robot = new Boxy();

    @Override
    public void runMovement() {
        //encoderDrive(double speed, double feetDistance, double timeoutS)
        encoderDrive(1.0, 15.0, 15.0);

        //robot.LFMotor.setPower(1.0);
       // robot.RFMotor.setPower(1.0);
        //robot.LBMotor.setPower(1.0);
        //robot.RBMotor.setPower(1.0);

        //encoderDriveDirect(double speed, double leftFeet, double rightFeet, double leftBackFeet, double rightBackFeet, double timeoutS)
        //encoderDriveDirect(.6, (3.4 * Math.PI / 12), -(3.4 * Math.PI / 12), (3.4 * Math.PI / 12), -(3.5 * Math.PI / 12), .95);
      //  sleep(4000);
        //encoderDrive(double speed, double feetDistance, double timeoutS)
        //encoderDrive(.6, 1.0, 1.3); // switched to 1 ft so robot can get a good look at the beacon

    }

}
