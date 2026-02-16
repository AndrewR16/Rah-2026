package frc.robot.motorspeeds;

public enum LauncherSpeed {
    /** Stop the motor */
    STOP(0),

    /** Spin away from the robot */
    MAX_FORWARD(0.5),
    
    /** Spin towards the robot */
    MAX_REVERSE(-0.2),

    /** Speed while running the intake routine */
    INTAKE_SPEED(0.2),
    ;

    private final double speed;

    LauncherSpeed(double speed) {
        if (speed > 1 || speed < -1) {
            throw new IllegalArgumentException("Illegal motor speed created for launcher");
        }
        
        this.speed = speed;
    }

    public double value() {
        return speed;
    }
}
