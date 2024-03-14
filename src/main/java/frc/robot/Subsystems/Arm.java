package frc.robot.Subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//Constants File Import
import frc.robot.Constants;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.XboxController;
//import Smart Dashboard library
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.SparkPIDController;

//SparkMax Imports
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class Arm extends SubsystemBase {
   XboxController armController = new XboxController(Constants.XboxControllerPort);   
  //Create Motor Objects
    CANSparkMax armMotorTop = new CANSparkMax(Constants.armMotorTopCanID,MotorType.kBrushless);
    CANSparkMax armMotorBottomRear = new CANSparkMax(Constants.armMotorBottomRearCanID,MotorType.kBrushless);
    CANSparkMax armMotorBottomFront = new CANSparkMax(Constants.armMotorBottomFrontCanID,MotorType.kBrushless);
    //Create a PDM object so we can display current values of PDM channels
    PowerDistribution revPDM = new PowerDistribution(1, ModuleType.kRev);
    //Create Limit Switch Object
    DigitalInput armLimitSwitch = new DigitalInput(Constants.armLimitSwitchDIO);
    public RelativeEncoder armEncoder;
    public SparkPIDController armPidController;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
    public double headingTo;

    public Arm(){
         
        //Restore Factory Defaults
        armMotorTop.restoreFactoryDefaults();
        armMotorBottomRear.restoreFactoryDefaults();
        armMotorBottomFront.restoreFactoryDefaults();
        //Set Current Limits
        armMotorTop.setSmartCurrentLimit(35);
        armMotorBottomRear.setSmartCurrentLimit(35);
        armMotorBottomFront.setSmartCurrentLimit(35);
        //Set Idle Modes
        armMotorTop.setIdleMode(IdleMode.kBrake);
        armMotorBottomRear.setIdleMode(IdleMode.kBrake);
        armMotorBottomFront.setIdleMode(IdleMode.kBrake);

        //Make armMotorTop the leader of the group
        armMotorBottomFront.follow(armMotorTop);
        armMotorBottomRear.follow(armMotorTop);

        //Create an Encoder Object to keep track of the arm position
        armEncoder = armMotorTop.getEncoder(); 
        
        //Set the armEncoder to 0
        armEncoder.setPosition(0);

        armMotorTop.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        armMotorTop.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
    
        armMotorTop.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, Constants.armUpSoftLimit);
        armMotorTop.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, Constants.armDownSoftLimit);

        //Create a PID object to control the Arm
        armPidController = armMotorTop.getPIDController();

        kP = 0.1; 
        kI = 1e-4;
        kD = 1; 
        kIz = 0; 
        kFF = 0; 
        kMaxOutput = 1; 
        kMinOutput = -1;
        
        armPidController.setP(kP);
        armPidController.setI(kI);
        armPidController.setD(kD);
        armPidController.setIZone(kIz);
        armPidController.setFF(kFF);
        armPidController.setOutputRange(kMinOutput, kMaxOutput);

        //Display Arm Start Position Should be "0"
        SmartDashboard.putNumber("Arm Encoder Starting Pos", armEncoder.getPosition());        
}

public void armMove(double goToPosition) {

  headingTo = goToPosition;
  armPidController.setReference(goToPosition, CANSparkMax.ControlType.kPosition);
  //Try this smart motion instead of the line above
  //armPidController.setReference(goToPosition, CANSparkMax.ControlType.kSmartMotion);

}

public void stopArm(){
  armMotorTop.set(0);
}


@Override
  public void periodic() {
    //Always check if moving down and if limit switch is made
    if ((headingTo < armEncoder.getPosition()&&(armLimitSwitch.get()))){
    armMotorTop.set(0);
  }
    //display right and left encoder values
    SmartDashboard.putNumber("Arm Top Motor Current", revPDM.getCurrent(Constants.armMotorTopPDMChannel));
    SmartDashboard.putNumber("Arm Top Motor Current", revPDM.getCurrent(Constants.armMotorBottomFrontPDMChannel));
    SmartDashboard.putNumber("Arm Top Motor Current", revPDM.getCurrent(Constants.armMotorBottomRearPDMChannel));
    SmartDashboard.putBoolean("Arm Down Limit Switch",armLimitSwitch.get());
    SmartDashboard.putNumber("Arm Encoder Position", armEncoder.getPosition());
    SmartDashboard.putNumber("Arm Going To Position", headingTo);

  if(armController.getYButton()){
    armMove(Constants.amp);
  }

  if(armController.getBButton()){
    armMove(Constants.shootFromSpeaker);
  }
  
  if(armController.getAButton()){
    armMove(Constants.ground);
  }

  if(armController.getXButton()){
    armMove(Constants.source);
  }

  if(armController.getPOV()==0){
    headingTo = headingTo + Constants.adjustArmAngle;
    armMove(headingTo);
  }
  if(armController.getPOV()==180){
    headingTo = headingTo - Constants.adjustArmAngle;
    armMove(headingTo);
  }


  
  }




    
}
