package chawks.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import chawks.hardware.Dutchess;

public abstract class AbstractDeadReckoningOpMode extends LinearOpMode {
    /**
     * Robot configuration
     **/

    private final Dutchess robot = new Dutchess();

    private ElapsedTime elapsedTime = new ElapsedTime();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        // this.setCamera(Cameras.PRIMARY);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        robot.lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        robot.lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0", "Starting at %7d :%7d :%7d :%7d",
                robot.lf.getCurrentPosition(),
                robot.rf.getCurrentPosition(),
                robot.lb.getCurrentPosition(),
                robot.rb.getCurrentPosition()
        );
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        initMovement();

        shoot();

        runMovement();


        //colorLook();


        // pause for servos to move
        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    public void initMovement() {

    }

    public void shoot() {
        robot.spin.setPower(0.1);
        sleep(250);

        robot.spin.setPower(0.2);
        sleep(250);

        robot.spin.setPower(0.27);
        sleep(250);


        robot.spin.setPower(0.5);
        sleep(250);

        robot.spin.setPower(0.7);
        sleep(500);


        launchBall(-1.0, 0.7, 5.0); //servo speed, spin speed, timeout

    }

    public void launchBall(double speedServo, double speedSpinner, double timeoutS) {
        robot.s4.setDirection(DcMotorSimple.Direction.REVERSE);
        robot.s3.setDirection(DcMotorSimple.Direction.REVERSE);
        robot.s2.setDirection(DcMotorSimple.Direction.REVERSE);

        if (opModeIsActive()) {
            elapsedTime.reset();

            robot.spin.setPower(speedSpinner);

            try {
                sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }

            while (opModeIsActive() &&
                    (elapsedTime.seconds() < timeoutS)) {
                robot.s4.setPower(speedServo);
                robot.s3.setPower(speedServo);
                robot.s2.setPower(speedServo);

                robot.s2.setDirection(DcMotorSimple.Direction.REVERSE);
                try {
                    sleep(1500);
                    robot.s4.setPower(0);
                    robot.s3.setPower(-.05);
                    robot.s2.setPower(0);
                    Thread.sleep(750);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            robot.spin.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            robot.s4.setPower(0);
            robot.s3.setPower(0);
            robot.s2.setPower(0);
        }
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
            newLeftTarget = robot.lf.getCurrentPosition() + (int) (feetDistance * countsPerInch);
            newRightTarget = robot.rf.getCurrentPosition() + (int) (feetDistance * countsPerInch);
            newLeftBackTarget = robot.lb.getCurrentPosition() + (int) (feetDistance * countsPerInch);
            newRightBackTarget = robot.rb.getCurrentPosition() + (int) (feetDistance * countsPerInch);

            robot.lf.setTargetPosition(newLeftTarget);
            robot.rf.setTargetPosition(newRightTarget);
            robot.lb.setTargetPosition(newLeftBackTarget);
            robot.rb.setTargetPosition(newRightBackTarget);

            elapsedTime.reset();
            robot.lf.setPower(Math.abs(speed));
            robot.rf.setPower(Math.abs(speed) + .05);
            robot.lb.setPower(Math.abs(speed));
            robot.rb.setPower(Math.abs(speed) + .05);
            while (opModeIsActive() &&
                    (elapsedTime.seconds() < timeoutS) && (robot.lf.isBusy() && robot.rf.isBusy()) && !isWheelsInPosition()) {

                // Display it for the driver.		                 // Display it for the driver.
                telemetry.addData("Path1", "Going to %7d :%7d :%7d :%7d", newLeftTarget, newRightTarget, newLeftBackTarget, newRightBackTarget);
                telemetry.addData("Path1", "Going to %7d :%7d :%7d :%7d", newLeftTarget, newRightTarget, newLeftBackTarget, newRightBackTarget);
                telemetry.addData("Path2", "Currently at %7d :%7d :%7d :%7d",
                        robot.lf.getCurrentPosition(),
                        robot.rf.getCurrentPosition(),
                        robot.lb.getCurrentPosition(),
                        robot.rb.getCurrentPosition()
                );
                telemetry.update();
            }

            // Stop all motion;		             // Stop all motion;
            robot.stopAllWheels();
            robot.setWheelsToRunMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);
        }
    }

    public boolean isWheelsInPosition() {
        for (DcMotor wheel : robot.getWheels()) {
            if (wheel.getCurrentPosition() < wheel.getTargetPosition()) {
                return false;
            }
        }
        return true;
    }

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
            newLeftTarget = robot.lf.getCurrentPosition() + (int) (leftFeet * countsPerInch);
            newRightTarget = robot.rf.getCurrentPosition() + (int) (rightFeet * countsPerInch);
            newLeftBackTarget = robot.lb.getCurrentPosition() + (int) (leftBackFeet * countsPerInch);
            newRightBackTarget = robot.rb.getCurrentPosition() + (int) (rightBackFeet * countsPerInch);

            // SetTarget
            robot.lf.setTargetPosition(newLeftTarget);
            robot.rf.setTargetPosition(newRightTarget);
            robot.lb.setTargetPosition(newLeftBackTarget);
            robot.rb.setTargetPosition(newRightBackTarget);

            elapsedTime.reset();
            robot.lf.setPower(Math.abs(speed));
            robot.rf.setPower(Math.abs(speed));
            robot.lb.setPower(Math.abs(speed));
            robot.rb.setPower(Math.abs(speed));
            while (opModeIsActive() &&
                    (elapsedTime.seconds() < timeoutS) && (robot.lf.isBusy() && robot.rf.isBusy()) && !isWheelsInPosition()) {

                // Display it for the driver.		                 // Display it for the driver.
                telemetry.addData("Path1", "Going to %7d :%7d :%7d :%7d", newLeftTarget, newRightTarget, newLeftBackTarget, newRightBackTarget);
                telemetry.addData("Path1", "Going to %7d :%7d :%7d :%7d", newLeftTarget, newRightTarget, newLeftBackTarget, newRightBackTarget);
                telemetry.addData("Path2", "Currently at %7d :%7d :%7d :%7d",
                        robot.lf.getCurrentPosition(),
                        robot.rf.getCurrentPosition(),
                        robot.lb.getCurrentPosition(),
                        robot.rb.getCurrentPosition()
                );
                telemetry.update();
            }

            robot.stopAllWheels();
            robot.setWheelsToRunMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);
        }
    }


    public abstract void runMovement();

}
