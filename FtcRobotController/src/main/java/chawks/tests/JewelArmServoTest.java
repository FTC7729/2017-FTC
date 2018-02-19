package chawks.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by ibtro on 1/11/2018.
 */

@Autonomous(name="Jewel Arm Test",group="Tests")
public class JewelArmServoTest extends LinearOpMode {
    Servo pivot;
    Servo arm;
    double INCREMENT = 0.009;
    double PIVOT_START = 0.39;
    double ARM_DROP = 0.05;
    double ARM_START = 1.0;
    double arm_pos;

    public void runOpMode() {
        //connects to actual servos
        pivot = hardwareMap.get(Servo.class, "pivot");
        arm = hardwareMap.get(Servo.class, "arm");
        waitForStart();
        //put the arm down
        pivot.setPosition(PIVOT_START);
        arm_pos = ARM_START;
        while (arm_pos > ARM_DROP) {
            telemetry.addData("Lat position", arm.getPosition());
            telemetry.addData("vert positron", pivot.getPosition());
            telemetry.update();
            if(!opModeIsActive()){ break; }
            arm_pos -= INCREMENT;
            arm.setPosition(arm_pos);
            sleep(45);
            idle();
        }

    }

}
