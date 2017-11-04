package chawks.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

/**
 * This class tests the functionality of the wheels encoders.  We control the wheels using the
 * RUN_TO_POSITION mode of the wheel encoders. In this mode, we set the "target position" of
 * the encoder, and the speed, and the motor will turn to the given position.
 * <p>
 * It's possible for RUN_TO_POSITION to appear not to work. This can be caused by:
 * - Incorrect wiring
 * - A bad encoder
 * <p>
 * In either case, the "current position" of the encoder will either read as "0", or once started,
 * will continue to return true for the "isBusy" method. NOTE: "isBusy" is not used in this program.
 * <p>
 * To use this class, run the program like a normal tele-op. The result should be four "true" values
 * in the telemetry. For any wheel which is "false" there is a problem. Check the wiring of the bot
 * against wiring diagrams. Also check the encoders on the motor themselves.
 *
 * @author joseph
 */

@Autonomous(name = "Test Wheels Strafe Right", group = "Autonomous")
@Disabled
public class TestWheelsStrafeRight extends TestWheelsTurnRightAut {
    public TestWheelsStrafeRight() {
        super(3, -3, -3, 3);
        new TestWheelsTurnRightAut(-3,3,-3,3);
    }
}
