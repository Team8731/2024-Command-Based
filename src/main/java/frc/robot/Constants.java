package frc.robot;



public final class Constants {
    
    //CAN ID's for Tank Drive Motors
    public static final int leftFrontDriveMotorCanID = 99;
    public static final int leftRearDriveMotorCanID = 99;
    public static final int rightFrontDriveMotorCanID = 99;
    public static final int rightRearDriveMotorCanID = 99;

    //Rev PDM Channels for reading current values
    //Drive Motors
    public static final int leftFrontDriveMotorPDMChannel = 18;
    public static final int leftReartDriveMotorPDMChannel = 19;
    public static final int rightFrontDriveMotorPDMChannel = 17;
    public static final int rightRearDriveMotorPDMChannel = 16;
    //Arm Motors
    public static final int armMotorTopPDMChannel = 14;
    public static final int armMotorBottomRearPDMChannel = 15;
    public static final int armMotorBottomFrontPDMChannel = 13;
   
    //Inverted Drive Motor settings
    public static final boolean leftMotorInverted = true;
    public static final boolean rightMotorInverted = false;


    //CAN ID's for Shooter/Intake Motors
    public static final int upperIntakeRollerMotorCanID = 99;
    public static final int lowerIntakeRollerMotorCanID = 99;
    public static final int upperShooterMotorCanID = 99;
    public static final int lowerShooterMotorCanID = 99;

    //CAN ID for Arm Motors
    public static final int armMotorTopCanID = 99;
    public static final int armMotorBottomRearCanID = 99;
    public static final int armMotorBottomFrontCanID = 99;

    //Limit Switch DIO
    public static final int armLimitSwitchDIO = 0;

    //Intake Motor Speeds
    public static final double groundIntakePower = 0.25; //used for both upper and lower motors
    
    //Shooter Speed for Speaker Scoring
    public static final double speakerShooterPower = 0.25;
    public static final double feedIntakeToShooterSpeed = 1;
    public static final double speakerShooterDelayTime = 1.0;


    //Shooter speed for Amp Scoring
    public static final double ampShooterPower = 0.25;
    public static final double ampShooterDelayTime = 0.5;

    //Source Intake Speeds
    public static final double sourceIntakeShooterPower = 0.25;
    public static final double sourceUpperIntakePower = 0.25;

    //Colour Sensor Proxmimity Threshold
    public static final int proximityLimit = 100;

    //Xbox Controller Port
    public static final int XboxControllerPort = 0;
    
    //Arcade Drive Limiting Speed Scalar Multipliers
    public static double fwdCreepScaler = 0.35;
    public static double turnCreepScaler = 0.35;
    public static double fwdScaler = 0.85;
    public static double turnScaler = 0.85;

    //Arm Positions in revolutions
    //1 motor rotation = 2.31 degrees of arm rotation
    //Units below are in motor rotations
    public static double ground = 0.0;
    public static double amp = 45.0;
    public static double source = 45.0;
    public static double shootFromSpeaker = 5.5;

    //Arm manual adjust angle rate of change while pressing dpad
    public static double adjustArmAngle = 3;
    
    //Arm Soft Limits
    //Units below are in motor rotations
    public static float armUpSoftLimit = 20.0f;
    public static Float armDownSoftLimit = 0.0f;

}
