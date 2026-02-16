package frc.robot.motorspeeds;

public enum FeederSpeed {
    /** Stop the motor */
    STOP(0),

    /** Spin towards the shooter */
    MAX_FORWARD(0.5),

    /** Spin towards the hopper */
    MAX_REVERSE(-0.2),
    ;

    private final double speed;

    FeederSpeed(double speed) {
        if (speed > 1 || speed < -1) {
            throw new IllegalArgumentException("Illegal motor speed created for feeder");
        }

        this.speed = speed;
    }

    public double value() {
        return speed;
    }
}
