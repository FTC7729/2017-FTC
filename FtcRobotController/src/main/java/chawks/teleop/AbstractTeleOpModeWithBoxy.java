package chawks.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import chawks.hardware.Boxy;

public abstract class AbstractTeleOpModeWithBoxy extends OpMode {
    /**
     * Robot hardware configuration
     */
    protected final Boxy robot = new Boxy();

    /**
     * This code is run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        robot.init(hardwareMap);
        telemetry.addData("Say", "Let's Go C-HAWKS!");
    }

    /**
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        handleGamePad1(gamepad1);
        handleGamePad2(gamepad2);
    }

    /**
     * Handle all Game Pad 1 controller input
     * When creating a new Tele-Op user must extend this class then add in custom gamepad1 code
     */
    public abstract void handleGamePad1(Gamepad gamepad);

    /**
     * Handle all Game Pad 2 controller input
     * When creating a new Tele-Op user must extend this class then add in custom gamepad2 code
     */
    public abstract void handleGamePad2(Gamepad gamepad);
}
