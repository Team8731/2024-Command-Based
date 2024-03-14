package frc.robot.Subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import edu.wpi.first.wpilibj.*;

//Constants File Import
import frc.robot.Constants;

//Rev Colour Sensor Imports
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;

import com.revrobotics.ColorSensorV3;
//SparkFlex Imports
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class IntakeShooter extends SubsystemBase {
   //Create and name a xbox controller object
    XboxController intakeController = new XboxController(Constants.XboxControllerPort); 
    
    private final I2C.Port i2cPort = I2C.Port.kOnboard;
    private final ColorSensorV3 noteSensor = new ColorSensorV3(i2cPort);

    
    
    CANSparkFlex upperIntakeRollerMotor = new CANSparkFlex(Constants.upperIntakeRollerMotorCanID,MotorType.kBrushless);
    CANSparkFlex lowerIntakeRollerMotor = new CANSparkFlex(Constants.lowerIntakeRollerMotorCanID,MotorType.kBrushless);
    CANSparkFlex upperShooterMotor = new CANSparkFlex(Constants.lowerShooterMotorCanID,MotorType.kBrushless);
    CANSparkFlex lowerShooterMotor = new CANSparkFlex(Constants.upperShooterMotorCanID,MotorType.kBrushless);

    
   public IntakeShooter() {
//nothing in the constuctor
   }
     
    
    
    //Turn on the ground intake. Upper and lower intake
    public void groundIntakeOn(double speed) {
       
        upperIntakeRollerMotor.set(speed);
        lowerIntakeRollerMotor.set(speed);
    }

    //Turn on the ground intake. Upper and lower intake
    public void groundIntakeOff() {
       
        upperIntakeRollerMotor.set(0);
        lowerIntakeRollerMotor.set(0);
    }
    
    //Turn on the source intake, Upper intake and upper shooter
    public void sourceIntakePowerOn() {
        
        upperIntakeRollerMotor.set(Constants.sourceUpperIntakePower);
        upperShooterMotor.set(Constants.sourceIntakeShooterPower);
    }

    //Turn on the shooter for shooting or source intake
    public void shooterPowerOn(double speed) {
        
        upperShooterMotor.set(speed);
        lowerShooterMotor.set(speed);
    }

    //Turn off the shooter for shooting
    public void shooterPowerOff() {
        
        upperShooterMotor.set(0);
        lowerShooterMotor.set(0);
    }


    //Stop all shooter/intake motors
    public void stop(){
        upperShooterMotor.set(0);
        lowerShooterMotor.set(0);
        upperIntakeRollerMotor.set(0);
        upperShooterMotor.set(0);
    }

    //Check color sensor for a note returns true if a note was detected
    public boolean noteSensorStatus() {
    int proximity = noteSensor.getProximity();
    if (proximity > Constants.proximityLimit){
        return true;
    }
    else {
        return false;
    }
    }
   


    @Override
    public void periodic() {
    
    //Check Left and Right Bumper not pressed. Turn Off shooter if both are not pressed.
    if (intakeController.getRightBumper()&&!intakeController.getLeftBumper()&&intakeController.getLeftTriggerAxis()>-0.5&&intakeController.getRightTriggerAxis()>0.5){
        shooterPowerOff();
        groundIntakeOff();
    }

    //Check Right Bumper. Shoot Full Send! if pressed. 
    if (intakeController.getRightBumper()){
    shooterPowerOn(Constants.speakerShooterPower); 
    Timer.delay(1);
    groundIntakeOn(Constants.feedIntakeToShooterSpeed);
    }
        
    //Check Left Bumper. Shoot at amp scoreing speed. Turn off if not pressed
    if (intakeController.getLeftBumper()){
    shooterPowerOn(Constants.ampShooterPower); }
   

    if (intakeController.getRightTriggerAxis()>0.5){
        groundIntakeOn(Constants.groundIntakePower);}
        else {groundIntakeOff();}

    if (intakeController.getLeftTriggerAxis()<-0.5){
        sourceIntakePowerOn();
        }

    }


}

