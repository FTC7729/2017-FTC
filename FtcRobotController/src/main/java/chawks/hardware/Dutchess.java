package chawks.hardware;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.List;

public class Dutchess {
    private final WheelConfiguration wheelConfiguration;
    private final ElapsedTime period = new ElapsedTime();
    public DcMotor lb;
    public DcMotor rb;
    public DcMotor lf;
    public DcMotor rf;
    public DcMotor spin;
    public Servo arm;
    public CRServo s2;
    public CRServo s3;
    public CRServo s4;

    public Dutchess() {
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
    public List<DcMotor> getWheelsAndSpinner() {
        return Lists.newArrayList(lb, rb, lf, rf, spin);
    }

    /**
     * Returns a list of wheel motors
     *
     * @return list of wheel motors
     */
    public List<DcMotor> getWheels() {
        return Lists.newArrayList(lf, rf, lb, rb);
    }

    public String getNameOfWheel(DcMotor motor) {
        if(motor==lb) {
            return "lb";
        } else if(motor==rb) {
            return "rb";
        } else if(motor==lf) {
            return "lf";
        } else if(motor==rf) {
            return "rf";
        } else {
            return "";
        }
    }

    /**
     * Returns a list of continuous rotation servos
     *
     * @return list of continuous rotation servos
     */
    public List<CRServo> getCRServos() {
        return Lists.newArrayList(s4, s3, s2);
    }

    /**
     * Initialize the hardware
     *
     * @param hardwareMap configuration from FTC application
     */
    public void init(HardwareMap hardwareMap) {
        // grab wheels
        lb = hardwareMap.dcMotor.get("lB");
        rb = hardwareMap.dcMotor.get("rB");
        lf = hardwareMap.dcMotor.get("lF");
        rf = hardwareMap.dcMotor.get("rF");

        // configure spin
        spin = hardwareMap.dcMotor.get("spin");
        spin.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        spin.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // Set all motors to zero power
        stopWheelsAndSpinner();

        // configure the wheels so that if we apply +1.0 power to all of the wheels,
        // the robot moves forward
        lf.setDirection(DcMotor.Direction.FORWARD);
        lb.setDirection(DcMotor.Direction.FORWARD);
        rf.setDirection(DcMotor.Direction.REVERSE);
        rb.setDirection(DcMotor.Direction.REVERSE);
        setWheelsToRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // beacon arm - port 3
        arm = hardwareMap.servo.get("s1");
        arm.setPosition(0);

        //back most servo - port 6
        s4 = hardwareMap.crservo.get("s4");

        // middle servo - port 2
        s3 = hardwareMap.crservo.get("s3");

        // launch servo - port 4
        s2 = hardwareMap.crservo.get("s2");
        stopAllServos();
    }

    public final void setWheelsToRunMode(DcMotor.RunMode runMode) {
        Preconditions.checkState(isWheelsStopped(), "Should not change run mode without stopping wheels first");
        for (DcMotor motor : getWheels()) {
            motor.setMode(runMode);
        }
    }

    public final void stopAllServos() {
        // we have three servos: s2, s3, and s4
        for (CRServo servo : getCRServos()) {
            servo.setPower(0);
            servo.setDirection(DcMotorSimple.Direction.REVERSE);
        }
    }

    public final void stopAllWheels() {
        setPowerAllWheels(0);
    }

    public final void setPowerAllWheels(double power) {
        for (DcMotor motor : getWheels()) {
            motor.setPower(power);
        }
    }

    public final void stopWheelsAndSpinner() {
        for (DcMotor motor : getWheelsAndSpinner()) {
            motor.setPower(0);
        }
    }

    public final boolean isWheelsStopped() {
        for (DcMotor motor : getWheels()) {
            if (motor.getPower() != 0) {
                return false;
            }
        }
        return true;
    }
}

