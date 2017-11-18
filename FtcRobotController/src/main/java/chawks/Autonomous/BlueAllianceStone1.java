package chawks.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="BlueAllianceStone1", group="safezone")
public class BlueAllianceStone1 extends AbstractSafeZoneAutonomous {

    public void movement() {
        //Start Code after here
        encoderDrive(.5,-.5,.5,-.5,.5,4);
        //speed 5 is too fast, less than 7 dist is too short.
        encoderDrive(.5,-7,-7,-7,-7,4);
        navxTurn(90.0);
    }

}