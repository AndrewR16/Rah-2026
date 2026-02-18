package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.MotorCanIds;
import frc.robot.motorspeeds.FeederSpeed;
import frc.robot.motorspeeds.LauncherSpeed;
import frc.robot.revconfigs.ShooterConfig;

public class ShooterSubsystem extends SubsystemBase {
    // Motors
    private final SparkMax m_launcherMotor = new SparkMax(MotorCanIds.kShooterCanId, MotorType.kBrushless);
    private final SparkMax m_feederMotor = new SparkMax(MotorCanIds.kHopperCanId, MotorType.kBrushless);

    public ShooterSubsystem() {
        m_launcherMotor.configure(ShooterConfig.launcherConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        m_feederMotor.configure(ShooterConfig.feederConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
    }

    /**
     * Run the launcher mechanism. 
     * 
     * @param speed The speed to run the launcher motor. Must be passed as a {@link LauncherSpeed}
     * @return A command to run the motor and stop it when interupted
     */
    public Command runLauncher(LauncherSpeed speed) {
        return this.startEnd(
                () -> m_launcherMotor.set(speed.value()),
                () -> m_launcherMotor.set(0));
    }

    /**
     * Run the feeder mechanism. 
     * 
     * @param speed The speed to run the feeder motor. Must be passed as a {@link FeederSpeed}
     * @return A command to run the motor and stop it when interupted
     */
    public Command runFeeder(FeederSpeed speed) {
        return this.startEnd(
                () -> m_feederMotor.set(speed.value()),
                () -> m_feederMotor.set(0));

    }
}
