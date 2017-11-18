package chawks.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="RedAllianceStone1", group="safezone")
public class RedAllianceStone1 extends AbstractSafeZoneAutonomous {

    public void movement() {
        //Start Code after here
        encoderDrive(.5,.5,-.5,.5,-.5,4);
        //speed 5 is too fast, less than 7 dist is too short.
        encoderDrive(.5,-6,-6,-6,-6,4);
        navxTurn(-90.0);
    }
}