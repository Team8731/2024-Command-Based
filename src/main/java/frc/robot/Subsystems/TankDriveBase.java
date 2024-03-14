package frc.robot.Subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//Import CAN controller library for VictorSPX
import edu.wpi.first.wpilibj.XboxController;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import Smart Dashboard library
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//Constants File Import
import frc.robot.Constants;

public class TankDriveBase extends SubsystemBase {
    //Create and name motor objects
    
    WPI_TalonSRX leftFrontDriveMotor = new WPI_TalonSRX(Constants.leftFrontDriveMotorCanID);
    WPI_TalonSRX leftRearDriveMotor = new WPI_TalonSRX(Constants.leftRearDriveMotorCanID); 
    WPI_TalonSRX rightFrontDriveMotor = new WPI_TalonSRX(Constants.rightFrontDriveMotorCanID); 
    WPI_TalonSRX rightRearDriveMotor = new WPI_TalonSRX(Constants.rightRearDriveMotorCanID);
    //Create and name  a differential drive object
    DifferentialDrive diffdrive = new DifferentialDrive(leftFrontDriveMotor, rightFrontDriveMotor);
    //Create and name a xbox controller object
    XboxController driverController = new XboxController(Constants.XboxControllerPort); 
    //Create a PDM Object to read current values of channels
    PowerDistribution revPDM = new PowerDistribution(1, ModuleType.kRev);
    public int leftStickButtonState; // 0 State is normal speed mode, 1 is reduced speed mode
    
    public TankDriveBase(){
        leftFrontDriveMotor.configFactoryDefault();
        leftRearDriveMotor.configFactoryDefault();
        rightFrontDriveMotor.configFactoryDefault();
        rightRearDriveMotor.configFactoryDefault();

        leftRearDriveMotor.setInverted(Constants.leftMotorInverted);
        leftFrontDriveMotor.setInverted(Constants.leftMotorInverted);
        rightRearDriveMotor.setInverted(Constants.rightMotorInverted);
        rightFrontDriveMotor.setInverted(Constants.rightMotorInverted);

        leftRearDriveMotor.setNeutralMode(NeutralMode.Brake);
        leftFrontDriveMotor.setNeutralMode(NeutralMode.Brake);
        rightRearDriveMotor.setNeutralMode(NeutralMode.Brake);
        rightFrontDriveMotor.setNeutralMode(NeutralMode.Brake);

        leftRearDriveMotor.follow(leftFrontDriveMotor); 
        rightRearDriveMotor.follow(rightFrontDriveMotor);

        leftStickButtonState = 0;

        
}
    
public void Drive(double fwdSpeed, double turnSpeed){
    if (Math.abs(fwdSpeed)<0.1) {
        fwdSpeed = 0;
    }
    if (Math.abs(turnSpeed)<0.1){
        turnSpeed = 0;
    }
    
    //Use a state machine for the right stick button. 0 will be used for normal drive speed. 1 will be a reduced speed state
if (driverController.getLeftStickButtonPressed()){
    if(leftStickButtonState==1){
        leftStickButtonState=0;
    }
    if(leftStickButtonState==0){
        leftStickButtonState=1;
    }
}
// End of state machine check

    if (leftStickButtonState==1){
        fwdSpeed = fwdSpeed * Constants.fwdCreepScaler;
        turnSpeed = turnSpeed * Constants.turnCreepScaler;
        diffdrive.arcadeDrive(fwdSpeed, turnSpeed);
    }
    if(leftStickButtonState==0){
        diffdrive.arcadeDrive(fwdSpeed*Constants.fwdScaler, turnSpeed*Constants.turnScaler);
    }
}

public void driveAsTank(double leftSpeed, double rightSpeed){
diffdrive.tankDrive(leftSpeed, rightSpeed);
 } 

public void Stop(){
   leftRearDriveMotor.set(0);
   rightRearDriveMotor.set(0);
   leftFrontDriveMotor.set(0);
   rightFrontDriveMotor.set(0);

}

@Override
  public void periodic() {
    Drive(driverController.getLeftX(),driverController.getRightY());
    
    //display right and left encoder values
    SmartDashboard.putNumber("Left Drive Positin",leftFrontDriveMotor.getSelectedSensorPosition());
    SmartDashboard.putNumber("Right Drive Positin",rightFrontDriveMotor.getSelectedSensorPosition());
    //display drive motor current drawtyfghgjujujhhuujjjjuujjuk
    SmartDashboard.putNumber("LF Drive_M Current", revPDM.getCurrent(Constants.leftFrontDriveMotorPDMChannel));
    SmartDashboard.putNumber("LF Drive_M Current", revPDM.getCurrent(Constants.leftReartDriveMotorPDMChannel));
    SmartDashboard.putNumber("LF Drive_M Current", revPDM.getCurrent(Constants.rightFrontDriveMotorPDMChannel));
    SmartDashboard.putNumber("LF Drive_M Current", revPDM.getCurrent(Constants.leftReartDriveMotorPDMChannel));


  }


}
