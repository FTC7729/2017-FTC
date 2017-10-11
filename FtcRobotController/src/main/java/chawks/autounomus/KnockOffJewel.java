package chawks.autounomus;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by karahandy on 10/10/17.
 */
public class KnockOffJewel extends LinearOpMode {
//Public class means it can be accessed anywhere and private means it can only be acessed in that one class
    //
    public void runOpMode() {

       // if you don't have an OpMode active the varibles haven't been created, running without varibles will prevent
       // code from working

        while(opModeIsActive()) {
            //use while so the function inside only happens while you are using that OpMode
            if (isRed()) {

            }
        }
    }

    private boolean isRed(){
        int rand = (int) (Math.random() * 100);
        if (rand < 50) {
            return false;
        } else {
            return true;
        }
    }

}