package chawks.autounomus;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
/**
 * Created by karahandy on 10/10/17.
 */
public class KnockOffJewel extends LinearOpMode {
//Public class means it can be accessed anywhere and private means it can only be acessed in that one class
    //
ColorSensor colorSensor;

    public void runOpMode() {
        colorSensor = hardwareMap.get(ColorSensor.class, "color_sensor");
       // if you don't have an OpMode active the varibles haven't been created, running without varibles will prevent
       // code from working
        colorSensor.enableLed(true);
        waitForStart();

        while(opModeIsActive()) {
            //use while so the function inside only happens while you are using that OpMode
            if (isRed()) {

            }
        }
    }

    private boolean isRed(){
       if (colorSensor.red() >= 100 && colorSensor.blue() <= 50) {
           return true;
       } else {
           return false;
       }

    }

}