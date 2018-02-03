package chawks.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import chawks.hardware.nextgen;

public abstract class AbstractTeleOpModeWithNextGen extends OpMode {
    /**
     * Robot hardware configuration
     */
    protected final nextgen robot = new nextgen();
    private ElapsedTime runtime = new ElapsedTime();
    static final double     BOT_SPEED = 0.1;
    static final double     COUNTS_PER_MOTOR_REV    = 280 ;    // eg: NEVEREST 40 Motor Encoder
    static final double     ROTATIONS_PER_MINUTE    = 160 ;
    static final double     DRIVE_GEAR_REDUCTION    = 1.5 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.93701 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;
    static final double     FORWARD_SPEED           = 0.6;
    static final double     BACKWARDS_SPEED         = -0.6;
    boolean IsSwitch;
    double LiftZero;

    /**
     * This code is run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        robot.init(hardwareMap);
        telemetry.addData("Say", "Let's Go C-HAWKS!");

        telemetry.addData("Calibrating", "The Lifteroni");

        //READ
        //      Assuming a postive
        //READ
        while(IsSwitch = false){
            robot.Elevate.setPower(0.1);
            IsSwitch = robot.LimitSwitch.getState();
        }
        robot.Elevate.setPower(0);
        robot.Elevate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LFMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.LFMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LiftZero = robot.Elevate.getCurrentPosition();

        //READ
        //      Assuming down increases incoder. Also 10 is a made up number
        //      also fix the bootleg encoder drive later. the standard one is just for wheels!
        //READ
        while(robot.Elevate.getCurrentPosition()<= (LiftZero - 10)){
            robot.Elevate.setPower(-0.1);
        }

        //the real product here is the LiftZero variable
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
