package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.MotorCanIds;
import frc.robot.motorspeeds.IntakeSpeed;
import frc.robot.revconfigs.IntakeConfig;

public class IntakeSubsystem extends SubsystemBase {
    private final SparkMax m_intakeMotor = new SparkMax(MotorCanIds.kIntakeCanId, MotorType.kBrushless);

    public IntakeSubsystem() {
        m_intakeMotor.configure(IntakeConfig.intakeConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    /**
     * Run the intake mechanism. 
     * 
     * @param speed The speed to run the intake motor. Must be passed as a {@link IntakeSpeed}
     * @return A command to run the motor and stop it when interupted
     */
    public Command runIntake(IntakeSpeed speed) {
        return this.startEnd(
                () -> m_intakeMotor.set(speed.value()),
                () -> m_intakeMotor.set(0));
    }
    
}
