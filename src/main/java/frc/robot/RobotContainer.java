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
  private final DriveSubsystem m_robotDrive = new DriveSubsystem();
  private final ShooterSubsystem m_robotShooter = new ShooterSubsystem();
  private final IntakeSubsystem m_robotIntake = new IntakeSubsystem();

  // Operator controllers
  private final CommandXboxController m_pilotController = new CommandXboxController(0);
  private final CommandXboxController m_gunnerController = new CommandXboxController(1);

  public RobotContainer() {
    configureBindings();

    m_robotDrive.setDefaultCommand(getDriveCommand());

  }

  private void configureBindings() {
    // Pilot bindings
    m_pilotController.x().whileTrue(
      m_robotDrive.run(m_robotDrive::setX)); // Stabalize robot

    // Gunner bindings
    // *Shoot fuel (Right Trigger)
    m_gunnerController.rightTrigger().whileTrue(
      shootFuel());

    // *Reverse shooter and intake (Left Trigger)
    m_gunnerController.leftTrigger().whileTrue(
      reverseShooterAndIntake());

    // *Start intake routine (Right Bumper)
    m_gunnerController.rightBumper().onTrue(
      startIntakeRoutine());

    // *Stop intake routine (Left Bumper)
    m_gunnerController.leftBumper().onTrue(
      stopIntakeRoutine());
  }

  private Command shootFuel() {
    return new ParallelCommandGroup(
        m_robotShooter.runLauncher(LauncherSpeed.MAX_FORWARD),
        m_robotShooter.runFeeder(FeederSpeed.MAX_FORWARD));
  }

  private Command reverseShooterAndIntake() {
    return new ParallelCommandGroup(
        m_robotShooter.runLauncher(LauncherSpeed.MAX_REVERSE),
        m_robotShooter.runFeeder(FeederSpeed.MAX_REVERSE),
        m_robotIntake.runIntake(IntakeSpeed.MAX_REVERSE));
  }

  private Command startIntakeRoutine() {
    return new ParallelCommandGroup(
        m_robotShooter.runLauncher(LauncherSpeed.INTAKE_SPEED),
        m_robotIntake.runIntake(IntakeSpeed.MAX_FORWARD));
  }

  private Command stopIntakeRoutine() {
    return new ParallelCommandGroup(
        m_robotShooter.runLauncher(LauncherSpeed.STOP),
        m_robotIntake.runIntake(IntakeSpeed.STOP));
  }

  public Command getDriveCommand() {
    // The left stick controls translation of the robot.
    // Turning is controlled by the X axis of the right stick.
    return new RunCommand(
        () -> m_robotDrive.drive(
            -MathUtil.applyDeadband(m_pilotController.getLeftY(), OperatorConstants.kPilotControllerDeadband),
            -MathUtil.applyDeadband(m_pilotController.getLeftX(), OperatorConstants.kPilotControllerDeadband),
            -MathUtil.applyDeadband(m_pilotController.getRightX(), OperatorConstants.kPilotControllerDeadband),
            true),
        m_robotDrive);
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
