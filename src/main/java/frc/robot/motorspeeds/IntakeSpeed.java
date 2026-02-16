package frc.robot.motorspeeds;

public enum IntakeSpeed {
    /** Stop the motor */
    STOP(0),

    /** Spin towards the robot */
    MAX_FORWARD(0.5),

    /** Spin away from the robot */
    MAX_REVERSE(-0.5),
    ;

    private final double speed;

    IntakeSpeed(double speed) {
        if (speed > 1 || speed < -1) {
            throw new IllegalArgumentException("Illegal motor speed created for intake");
        }
        
        this.speed = speed;
    }

    public double value() {
        return speed;
    }
}
