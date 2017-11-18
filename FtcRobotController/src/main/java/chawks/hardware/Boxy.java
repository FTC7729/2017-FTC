package chawks.hardware;
//NOTE: no more com.google.common
//import com.google.common.base.Preconditions;
//import com.google.common.collect.Lists;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorColor;

public class Boxy {
    private final WheelConfiguration wheelConfiguration;
    private final ElapsedTime period = new ElapsedTime();
    public DcMotor LFMotor;
    public DcMotor LBMotor;
    public DcMotor RFMotor;
    public DcMotor RBMotor;
    public SensorColor sensor_color;


    public Boxy() {
        // these settings are for AndyMark Motor Encoder with Mecanum wheels
        // see: http://www.handhgraphicsorlando.com/STEM/BabyBot_DriveEncoderB.pdf
        final int countsPerMotorRev = 1120;
        final double wheelDiameterInches = 4.0;
        final double driveGearReduction = 1.0;

        // computed by Joseph, do not change without discussion
        final double radiusLeftFront = 9.0676d;
        final double radiusRightFront = 9.0683d;
        final double radiusLeftBack = 8.6226d;
        final double radiusRightBack = 8.6245d;

        // store the wheel configuration
        this.wheelConfiguration = new WheelConfiguration(countsPerMotorRev, driveGearReduction, wheelDiameterInches,
                radiusLeftFront, radiusRightFront, radiusLeftBack, radiusRightBack);
    }

    public WheelConfiguration getWheelConfiguration() {
        return wheelConfiguration;
    }

    /**
     * Returns a list of motors that we have configured.
     *
     * @return list of motors that we have configured.
     */


  /*  public List<DcMotor> getWheelsAndSpinner() {
        return Lists.newArrayList(lb, rb, lf, rf, spin);
    }
    */

    /**
     * Returns a list of wheel motors
     *
     * @return list of wheel motors
     */
    /* public List<DcMotor> getWheels() {
        return Lists.newArrayList(lf, rf, lb, rb);
    }
    */

    public String getNameOfWheel(DcMotor motor) {
        if(motor==LBMotor) {
            return "LBMotor";
        } else if(motor==RBMotor) {
            return "RBMotor";
        } else if(motor==LFMotor) {
            return "LFMotor";
        } else if(motor==RFMotor) {
            return "RFMotor";
        } else {
            return "";
        }
    }

    /**
     * Returns a list of continuous rotation servos
     *
     * @return list of continuous rotation servos
     */
    /*
    public List<CRServo> getCRServos() {

        return Lists.newArrayList(s4, s3, s2);
    }
    */

    /**
     * Initialize the hardware
     *
     * @param hardwareMap configuration from FTC application
     */
    public void init(HardwareMap hardwareMap) {
        // grab wheels
        LBMotor = hardwareMap.dcMotor.get("LBMotor");
        RBMotor = hardwareMap.dcMotor.get("RBMotor");
        LFMotor = hardwareMap.dcMotor.get("LFMotor");
        RFMotor = hardwareMap.dcMotor.get("RFMotor");


        // Set all motors to zero power
        stopWheelsAndSpinner();

        // configure the wheels so that if we apply +1.0 power to all of the wheels,
        // the robot moves forward
        LFMotor.setDirection(DcMotor.Direction.FORWARD);
        LBMotor.setDirection(DcMotor.Direction.FORWARD);
        RFMotor.setDirection(DcMotor.Direction.REVERSE);
        RBMotor.setDirection(DcMotor.Direction.REVERSE);
        setWheelsToRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);



    }

    public final void setWheelsToRunMode(DcMotor.RunMode runMode) {
      //  Preconditions.checkState(isWheelsStopped(), "Should not change run mode without stopping wheels first");
      //  for (DcMotor motor : geheels()) {
        LBMotor.setMode(runMode);
        RBMotor.setMode(runMode);
        LFMotor.setMode(runMode);
        RFMotor.setMode(runMode);

      //  }
    }



    public final void stopAllWheels() {
        setPowerAllWheels(0);
    }

    public final void setPowerAllWheels(double power) {
        //for (DcMotor motor : getWheels()) {
            LBMotor.setPower(power);
            RBMotor.setPower(power);
            LFMotor.setPower(power);
            RFMotor.setPower(power);

        //}
    }

    public final void stopWheelsAndSpinner() {
        //for (DcMotor motor : getWheelsAndSpinner()) {
            LBMotor.setPower(0);
            RBMotor.setPower(0);
            LFMotor.setPower(0);
            RFMotor.setPower(0);
        //}
    }

    public final boolean isWheelsStopped() {
        //for (DcMotor motor : getWheels()) {
            if (LBMotor.getPower() != 0) {
                return false;
            }
            if (RBMotor.getPower() != 0) {
                return false;
            }
            if (LFMotor.getPower() != 0) {
                return false;
            }
            if (RFMotor.getPower() != 0) {
                return false;
            }

        //}
        return true;
    }
}

