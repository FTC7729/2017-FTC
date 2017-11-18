package chawks.hardware;
//NOTE: no more com.google.common
//import com.google.common.base.Preconditions;
//import com.google.common.collect.Lists;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class KBot extends Robot {

    public void setupServos(HardwareMap hardwareMap) {
        LGServo = hardwareMap.servo.get("LGServo");
        RGServo = hardwareMap.servo.get("RGServo");

        stopWheelsAndLift();
        ZeroServos();
    }

}

