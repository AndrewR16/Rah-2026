// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.OperatorConstants;
import frc.robot.subsystems.DriveSubsystem;

public class RobotContainer {
  // Robot subsystems
  private final DriveSubsystem m_robotDrive = new DriveSubsystem();

  // Operator controllers
  private final CommandXboxController m_pilotController = new CommandXboxController(0);
  // private final CommandXboxController m_gunnerController = new CommandXboxController(1);

  public RobotContainer() {
    configureBindings();

    m_robotDrive.setDefaultCommand(getDriveCommand());

  }

  private void configureBindings() {
    // Pilot bindings
    m_pilotController.x().whileTrue(m_robotDrive.run(m_robotDrive::setX)); // Stabalize robot

    // Gunner bindings
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
