package chawks.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import chawks.hardware.Boxy;
@Disabled
public abstract class AutonomousTestThingy extends LinearOpMode {

    /**
     * Robot configuration
     **/

    private final Boxy robot = new Boxy();

    private ElapsedTime elapsedTime = new ElapsedTime();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        // this.setCamera(Cameras.PRIMARY);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        robot.LFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.RFMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LBMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.RBMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        robot.LFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.RFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.LBMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.RBMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0", "Starting at %7d :%7d :%7d :%7d",
                robot.LFMotor.getCurrentPosition(),
                robot.RFMotor.getCurrentPosition(),
                robot.LBMotor.getCurrentPosition(),
                robot.RBMotor.getCurrentPosition()
        );
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        initMovement();



        runMovement();


        //colorLook();


        // pause for servos to move
        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    public void initMovement() {

    }





    public void encoderDrive(double speed, double feetDistance, double timeoutS) {
        feetDistance /= 5;
        int newLeftTarget;
        int newRightTarget;
        int newLeftBackTarget;
        int newRightBackTarget;

        if (opModeIsActive()) {
            robot.setWheelsToRunMode(DcMotor.RunMode.RUN_TO_POSITION);

            final double countsPerInch = robot.getWheelConfiguration().getCountsPerInch();
            newLeftTarget = robot.LFMotor.getCurrentPosition() + (int) (feetDistance * countsPerInch);
            newRightTarget = robot.RFMotor.getCurrentPosition() + (int) (feetDistance * countsPerInch);
            newLeftBackTarget = robot.LBMotor.getCurrentPosition() + (int) (feetDistance * countsPerInch);
            newRightBackTarget = robot.RBMotor.getCurrentPosition() + (int) (feetDistance * countsPerInch);

            robot.LFMotor.setTargetPosition(newLeftTarget);
            robot.RFMotor.setTargetPosition(newRightTarget);
            robot.LBMotor.setTargetPosition(newLeftBackTarget);
            robot.RBMotor.setTargetPosition(newRightBackTarget);

            elapsedTime.reset();
            robot.LFMotor.setPower(Math.abs(speed));
            robot.RFMotor.setPower(Math.abs(speed) + .05);
            robot.LBMotor.setPower(Math.abs(speed));
            robot.RBMotor.setPower(Math.abs(speed) + .05);

            /*while (opModeIsActive() &&
                    (elapsedTime.seconds() < timeoutS) && (robot.LFMotor.isBusy() && robot.RFMotor.isBusy()) && !isWheelsInPosition()) {

                // Display it for the driver.		                 // Display it for the driver.
                telemetry.addData("Path1", "Going to %7d :%7d :%7d :%7d", newLeftTarget, newRightTarget, newLeftBackTarget, newRightBackTarget);
                telemetry.addData("Path1", "Going to %7d :%7d :%7d :%7d", newLeftTarget, newRightTarget, newLeftBackTarget, newRightBackTarget);
                telemetry.addData("Path2", "Currently at %7d :%7d :%7d :%7d",
                        robot.LFMotor.getCurrentPosition(),
                        robot.RFMotor.getCurrentPosition(),
                        robot.LBMotor.getCurrentPosition(),
                        robot.RBMotor.getCurrentPosition()
                );
                telemetry.update();
            }*/

            // Stop all motion;		             // Stop all motion;
            robot.stopAllWheels();
            robot.setWheelsToRunMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);
        }
    }

    //PLEASE ADD LATER PLEASE

    // public boolean isWheelsInPosition() {
    //    for (DcMotor wheel : robot.getWheels()) {
    //        if (wheel.getCurrentPosition() < wheel.getTargetPosition()) {
    //           return false;
    //         }
    //     }
    //      return true;
    // }

    public void encoderDriveDirect(double speed, double leftFeet, double rightFeet, double leftBackFeet, double rightBackFeet, double timeoutS) {
        leftFeet /= 5;
        rightFeet /= 5;
        leftBackFeet /= 5;
        rightBackFeet /= 5;

        int newLeftTarget;
        int newRightTarget;
        int newLeftBackTarget;
        int newRightBackTarget;

        if (opModeIsActive()) {
            robot.setWheelsToRunMode(DcMotor.RunMode.RUN_TO_POSITION);

            final double countsPerInch = robot.getWheelConfiguration().getCountsPerInch();
            newLeftTarget = robot.LFMotor.getCurrentPosition() + (int) (leftFeet * countsPerInch);
            newRightTarget = robot.RFMotor.getCurrentPosition() + (int) (rightFeet * countsPerInch);
            newLeftBackTarget = robot.LBMotor.getCurrentPosition() + (int) (leftBackFeet * countsPerInch);
            newRightBackTarget = robot.RBMotor.getCurrentPosition() + (int) (rightBackFeet * countsPerInch);

            // SetTarget
            robot.LFMotor.setTargetPosition(newLeftTarget);
            robot.RFMotor.setTargetPosition(newRightTarget);
            robot.LBMotor.setTargetPosition(newLeftBackTarget);
            robot.RBMotor.setTargetPosition(newRightBackTarget);

            elapsedTime.reset();
            robot.LFMotor.setPower(Math.abs(speed));
            robot.RFMotor.setPower(Math.abs(speed));
            robot.LBMotor.setPower(Math.abs(speed));
            robot.RBMotor.setPower(Math.abs(speed));

            /*while (opModeIsActive() &&
                    (elapsedTime.seconds() < timeoutS) && (robot.LFMotor.isBusy() && robot.RFMotor.isBusy()) && !isWheelsInPosition()) {

                // Display it for the driver.		                 // Display it for the driver.
                telemetry.addData("Path1", "Going to %7d :%7d :%7d :%7d", newLeftTarget, newRightTarget, newLeftBackTarget, newRightBackTarget);
                telemetry.addData("Path1", "Going to %7d :%7d :%7d :%7d", newLeftTarget, newRightTarget, newLeftBackTarget, newRightBackTarget);
                telemetry.addData("Path2", "Currently at %7d :%7d :%7d :%7d",
                        robot.LFMotor.getCurrentPosition(),
                        robot.RFMotor.getCurrentPosition(),
                        robot.LBMotor.getCurrentPosition(),
                        robot.RBMotor.getCurrentPosition()
                );
                telemetry.update();
            }*/

            robot.stopAllWheels();
            robot.setWheelsToRunMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);
        }
    }


    public abstract void runMovement();

}