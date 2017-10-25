package chawks.teleop;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by karahandy on 10/24/17.
 */

public class WheelConfig {
    private DcMotor motor;
    public void setMotor(DcMotor motor) {
        this.motor = motor;
    }
    public DcMotor getDcMotor() {
        return motor;
    }

    private final int radius;
    public WheelConfig(int radius) {
        this.radius = radius;
    }
    public void move(double degrees){
        double distance = radius * ((degrees * Math.PI) / 180.0);
    }

}
