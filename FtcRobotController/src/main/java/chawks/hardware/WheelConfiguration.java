package chawks.hardware;

// Can't find the Preconditions anymore - is this deprecated?
//import com.google.common.base.Preconditions;


/**
 * Encapsulates the wheel settings
 *
 * @author Joseph Arakelian
 */
public class WheelConfiguration {
    /**
     * Number of encoder counts per inch.  This varies based upon the encoder hardware that is used.
     *
     * @see "http://ftcforum.usfirst.org/showthread.php?7483-Encoder-count-maximum-value"
     */
    private final double countsPerInch;

    /**
     * Number of encoder counts per motor revolution.
     */
    private final int countsPerMotorRev;

    /**
     * When using gearing, the distance traveled can be scaled by this amount.
     */
    private final double driveGearReduction;

    /**
     * Wheel diameter in inches
     */
    private final double wheelDiameterInches;

    private final double radiusLeftFront;

    private final double radiusLeftBack;

    private final double radiusRightFront;

    private final double radiusRightBack;

    public WheelConfiguration(int countsPerMotorRev, double driveGearReduction, double wheelDiameterInches,
                              double radiusLeftFront, double radiusRightFront, double radiusLeftBack, double radiusRightBack) {
        // sanity checks
  /*
        Preconditions.checkArgument(countsPerMotorRev > 0, "countsPerMotorRev must be >0");
        Preconditions.checkArgument(driveGearReduction > 0, "driveGearReduction must be >0");
        Preconditions.checkArgument(wheelDiameterInches > 0, "wheelDiameterInches must be >0");
        Preconditions.checkArgument(radiusLeftFront > 0, "radiusLeftFront must be >0");
        Preconditions.checkArgument(radiusRightFront > 0, "radiusLeftFront must be >0");
        Preconditions.checkArgument(radiusLeftBack > 0, "radiusLeftFront must be >0");
        Preconditions.checkArgument(radiusRightBack > 0, "radiusLeftFront must be >0");
    */
        // we use these values to convert distances to/from encoder "counts"
        this.countsPerMotorRev = countsPerMotorRev;
        this.driveGearReduction = driveGearReduction;
        this.wheelDiameterInches = wheelDiameterInches;
        countsPerInch = (countsPerMotorRev * driveGearReduction) / (wheelDiameterInches * Math.PI);

        // store wheel radii (relative to center-point of wheels)
        this.radiusLeftFront = radiusLeftFront;
        this.radiusRightFront = radiusRightFront;
        this.radiusLeftBack = radiusLeftBack;
        this.radiusRightBack = radiusRightBack;
    }

    public double getRadiusLeftFront() {
        return radiusLeftFront;
    }

    public double getRadiusLeftBack() {
        return radiusLeftBack;
    }

    public double getRadiusRightFront() {
        return radiusRightFront;
    }

    public double getRadiusRightBack() {
        return radiusRightBack;
    }

    public int getCountsPerMotorRev() {
        return countsPerMotorRev;
    }

    public double getDriveGearReduction() {
        return driveGearReduction;
    }

    public double getWheelDiameterInches() {
        return wheelDiameterInches;
    }

    public double getCountsPerInch() {
        return countsPerInch;
    }
}
