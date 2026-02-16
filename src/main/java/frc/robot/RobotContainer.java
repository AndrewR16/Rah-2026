// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.OperatorConstants;
import frc.robot.motorspeeds.FeederSpeed;
import frc.robot.motorspeeds.IntakeSpeed;
import frc.robot.motorspeeds.LauncherSpeed;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class RobotContainer {
  // Robot subsystems
  private final DriveSubsystem m_drive = new DriveSubsystem();
  private final ShooterSubsystem m_shooter = new ShooterSubsystem();
  private final IntakeSubsystem m_intake = new IntakeSubsystem();

  // Operator controllers
  private final CommandXboxController m_pilot = new CommandXboxController(0);
  private final CommandXboxController m_gunner = new CommandXboxController(1);

  public RobotContainer() {
    configureBindings();

    m_drive.setDefaultCommand(getDriveCommand());

  }

  private void configureBindings() {
    // Pilot bindings
    // Set wheels to X formation (X)
    m_pilot.x().whileTrue(
      m_drive.run(m_drive::setX)); 

    // Gunner bindings
    // *Shoot fuel (Right Trigger)
    m_gunner.rightTrigger().whileTrue(
      shootFuel());

    // *Reverse shooter and intake (Left Trigger)
    m_gunner.leftTrigger().whileTrue(
      reverseShooterAndIntake());

    // *Start intake routine (Right Bumper)
    m_gunner.rightBumper().onTrue(
      startIntakeRoutine());

    // *Stop intake routine (Left Bumper)
    m_gunner.leftBumper().onTrue(
      stopIntakeRoutine());
  }

  private Command shootFuel() {
    return new ParallelCommandGroup(
        m_shooter.runLauncher(LauncherSpeed.MAX_FORWARD),
        m_shooter.runFeeder(FeederSpeed.MAX_FORWARD));
  }

  private Command reverseShooterAndIntake() {
    return new ParallelCommandGroup(
        m_shooter.runLauncher(LauncherSpeed.MAX_REVERSE),
        m_shooter.runFeeder(FeederSpeed.MAX_REVERSE),
        m_intake.runIntake(IntakeSpeed.MAX_REVERSE));
  }

  private Command startIntakeRoutine() {
    return new ParallelCommandGroup(
        m_shooter.runLauncher(LauncherSpeed.INTAKE_SPEED),
        m_intake.runIntake(IntakeSpeed.MAX_FORWARD));
  }

  private Command stopIntakeRoutine() {
    return new ParallelCommandGroup(
        m_shooter.runLauncher(LauncherSpeed.STOP),
        m_intake.runIntake(IntakeSpeed.STOP));
  }

  public Command getDriveCommand() {
    // The left stick controls translation of the robot.
    // Turning is controlled by the X axis of the right stick.
    return new RunCommand(
        () -> m_drive.drive(
            -MathUtil.applyDeadband(m_pilot.getLeftY(), OperatorConstants.kPilotControllerDeadband),
            -MathUtil.applyDeadband(m_pilot.getLeftX(), OperatorConstants.kPilotControllerDeadband),
            -MathUtil.applyDeadband(m_pilot.getRightX(), OperatorConstants.kPilotControllerDeadband),
            true),
        m_drive);
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
