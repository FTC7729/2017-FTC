package chawks.hardware;
//NOTE: no more com.google.common
//import com.google.common.base.Preconditions;
//import com.google.common.collect.Lists;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Robot {
    private final WheelConfiguration wheelConfiguration;
    private final ElapsedTime period = new ElapsedTime();
    public DcMotor LFMotor;
    public DcMotor LBMotor;
    public DcMotor RFMotor;
    public DcMotor RBMotor;

    public Robot() {
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
        LBMotor = hardwareMap.dcMotor.get("Luke");
        RBMotor = hardwareMap.dcMotor.get("Leia");
        LFMotor = hardwareMap.dcMotor.get("R2-D2");
        RFMotor = hardwareMap.dcMotor.get("C3PO");

        stopAllWheels();
        LFMotor.setDirection(DcMotor.Direction.FORWARD);
        LBMotor.setDirection(DcMotor.Direction.FORWARD);
        RFMotor.setDirection(DcMotor.Direction.FORWARD);
        RBMotor.setDirection(DcMotor.Direction.FORWARD);

        //I think it need to be run with encoder
        setWheelsToRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public final void setWheelsToRunMode(DcMotor.RunMode runMode) {
        LBMotor.setMode(runMode);
        RBMotor.setMode(runMode);
        LFMotor.setMode(runMode);
        RFMotor.setMode(runMode);
    }



    public final void stopAllWheels() {
        setPowerAllWheels(0);
    }

    public final void setPowerAllWheels(double power) {
            LBMotor.setPower(power);
            RBMotor.setPower(power);
            LFMotor.setPower(power);
            RFMotor.setPower(power);
    }

    public final void stopWheelsAndLift() {
        LBMotor.setPower(0);
        RBMotor.setPower(0);
        LFMotor.setPower(0);
        RFMotor.setPower(0);
    }

    public final void StopEverything(){
        LBMotor.setPower(0);
        RBMotor.setPower(0);
        LFMotor.setPower(0);
        RFMotor.setPower(0);
    }

    public final boolean areWheelsStopped() {
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

        return true;
    }
}

