package chawks.teleop;

import chawks.hardware.DrivingDirection;

public abstract class AbstractTeleOpWithSpinner extends AbstractTeleOpMode {
    /**
     * Arm controller
     */
    protected ArmController armController;

    /**
     * Spin motor controller
     */
    protected ShootingController shootingController;

    /**
     * Driving direction
     **/
    protected DrivingDirection drivingDirection = DrivingDirection.FORWARD;

    /**
     * Spin motor thread
     **/
    private Thread shootingThread;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        super.init();

        // arm
        this.armController = new ArmController(robot,telemetry);

        // shooting
        boolean sleepingRoommate = false;
        this.shootingController = new ShootingController(robot, telemetry, sleepingRoommate, 0);
        this.shootingThread = new Thread(shootingController);
        shootingThread.start();
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        super.start();
        shootingController.setTargetPower(ShootingController.MAX_SPIN_MOTOR_SPEED);
    }

    @Override
    public void stop() {
        super.stop();
        shootingController.stop();
    }
}
