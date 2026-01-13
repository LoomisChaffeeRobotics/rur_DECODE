package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp // FINAL TELE-OP CLASS
public class CherryDrive extends OpMode { //this clas is called CherryDrive because Cherry is a placeholder robot name
    //RUN SENSECOLOR BEFORE EVERY TURNING THING

    //name of spinning turret is turretSpin
    //name of indexer is indexer
    //uncomment out the stuff later




    /** Class for detecting AT using limelight and turning turret to the AT also using limelight */
    LimeLightTurretSystem limeLightTurretSystem;

    /** Class for the Spnidexer and the color sensor */
    Indexer indexClass;

    /** we use this for finding motor speeds VERY IMPORTANT even tho only used once */
    Launcher launchClass;

    /** weather or not we turn the turret automatically (do we want ts?) */
    boolean autoTurn = true;
    /** if this isn't self-explanatory re-do you are 5th grade education */
    boolean flipperUp = false;
    // whether we're red alliance or not - can be set
    boolean isRed = false;

    /** List of Motors: */
    DcMotor intake;
    DcMotor launcher; // top
    DcMotor launcher2; // bottom (i think)
    // v drive motors v
    DcMotor left_front;
    DcMotor right_front;
    DcMotor left_back;
    DcMotor right_back;

    /** list of servos it is missing the spindexer servo because all that is dealt with in the 'indexer' class */
    CRServo indexer;
    Servo flipper;

    /** the imu we use */
    IMU imu;




    @Override
    public void init() {
        //class initializations
        limeLightTurretSystem = new LimeLightTurretSystem();
        limeLightTurretSystem.init(hardwareMap, telemetry);
        indexClass = new Indexer();
        indexClass.init(hardwareMap, telemetry);
        launchClass = new Launcher();
        launchClass.init(hardwareMap, telemetry);
        telemetry.addLine("If red hit start if not don't ");
        if (gamepad1.start) {
            isRed = true;
        }
        //Setting the motors
        intake = hardwareMap.get(DcMotor.class,"intake");
        launcher = hardwareMap.get(DcMotor.class,"launcher");
        launcher2 = hardwareMap.get(DcMotor.class,"launcher2");
        left_front = hardwareMap.get(DcMotor.class, "leftFront");
        right_front = hardwareMap.get(DcMotor.class, "rightFront");
        left_back = hardwareMap.get(DcMotor.class, "leftBack");
        right_back = hardwareMap.get(DcMotor.class, "rightBack");

        //Setting the servos (not spindexer)
        indexer = hardwareMap.get(CRServo.class,"indexer");
        flipper = hardwareMap.get(Servo.class,"flipper");

        //setting up the drive motor behavior
        left_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        left_front.setDirection(DcMotorSimple.Direction.REVERSE);
        left_back.setDirection(DcMotorSimple.Direction.REVERSE);

        /* imu starting stuff */
        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters myIMUparameter;

        myIMUparameter = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
                        RevHubOrientationOnRobot.UsbFacingDirection.LEFT
                )
        );

        imu.initialize(myIMUparameter);
        imu.resetYaw();



        flipper(false);
    }

    @Override
    public void loop() {
//        colorsensor.sensecolor(telemetry);

        //  INTAKE - gp1 RT
        if (gamepad1.right_trigger > 0.2) {
            runIntake(1d);
        }
        else {
            runIntake(0d);
        }

        //  TURRET - gp2 LT
        if (gamepad2.left_trigger > 0.2){
            startTurret(1d);
        } else {
            startTurret(0d);
        }

        //  FLIPPER - gp2 RT
        if (gamepad2.right_trigger > 0.2){
            flipper(true);
        } else {
            flipper(false);
        }

        // SPINDEXER - gp2 x,a,b (green,none,purple)
//        if (gamepad2.x){
//            switchColor(Indexer.SensedColor.GREEN);
//        }
//        if (gamepad2.a){
//            switchColor(Indexer.SensedColor.NEITHER);
//        }
//        if (gamepad2.b){
//            switchColor(Indexer.SensedColor.PURPLE);
//        }

        //  TURNING THE HEAD - gp2
//        if(autoTurn){
//            autoTurn();
//        }
//        if (gamepad2.dpad_left){
//            autoTurn = false;
//            manuTurn(-0.2);
//        } else if (gamepad2.dpad_right){
//            autoTurn = false;
//            manuTurn(0.2);
//        } else if (!autoTurn){
//            manuTurn(0);
//        }
//        if (gamepad1.dpad_up){
//            autoTurn = true;
//        }

//         Driving
        fieldCentricDriving();
        if (gamepad1.start){
            imu.resetYaw();
        }

    }


    /** Moves the Drive motors in a field-centric way.
     * x and y altered are the x and y components of the input vector rotated
     * "denominator" is the denominator used to clamo out the values in [-1,1] without changing the ratio */
    public void fieldCentricDriving(){ // Done!

        //inputs
        double x = gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;
        double rx = gamepad1.right_stick_x;
        double Yaw = imu.getRobotYawPitchRollAngles().getYaw();

        //rotation
        double x_altered = (x * Math.cos(Math.toRadians(Yaw))) + (y * Math.sin(Math.toRadians(Yaw)));
        double y_altered = (y * Math.cos(Math.toRadians(Yaw))) - (x * Math.sin(Math.toRadians(Yaw)));

        //adding it all together
        double right_front_velocity = y_altered - x_altered - rx;
        double left_front_velocity = y_altered + x_altered + rx;
        double left_back_velocity = y_altered - x_altered + rx;
        double right_back_velocity = y_altered + x_altered - rx;

        //denominator (keep ratio of speeds)
        double denominator = 1d;
        double max = Math.max(Math.max(Math.abs(right_back_velocity),Math.abs(right_front_velocity)), Math.max(Math.abs(left_back_velocity),Math.abs(left_front_velocity)));

        if(max > 1){
            denominator = max;
        }

        right_front.setPower(right_front_velocity/denominator);
        right_back.setPower(right_back_velocity/denominator);
        left_front.setPower(left_front_velocity/denominator);
        left_back.setPower(left_back_velocity/denominator);

        telemetry.addData("leftstickx", gamepad1.left_stick_x);
        telemetry.addData("denominator", denominator);
//        telemetry.addData("yaw: ", Yaw);

    }

    /** runs the intake at power power. 0 for stop 1 for go .5 for 50% speed*/
    public void runIntake(double power){ // Done!

        telemetry.addData("intake power: ",power);
        intake.setPower(-power);
    }
    public void startTurret(double power){ // Done?
        if (power == 0) {
            launcher.setPower(0);
            launcher2.setPower(0);
            return;
        }
        launchClass.shoot(limeLightTurretSystem.getDistance_from_apriltag(0));
        telemetry.addData("turret power: ", power);
    }
    public void flipper(boolean up){ // Done!
        //flip
        double flipDown = 0.91d;
        double flipUP = 0.53d;
        flipper.setPosition(up? flipUP : flipDown);
        telemetry.addData("flipper upness: ", up);
        flipperUp = up;
    }
    public boolean switchColor(Indexer.SensedColor color){ // NOT DONE
       //COLOUR STUFFs

        if (flipperUp || indexClass.canTurn == 0) {return false;}

        if (indexClass.canTurn == 1) {
            indexClass.sensecolor();
        }
        else {
            indexClass.turnBasedOfColor(color);
//            if (indexclass.canTurn == 0) {
//                return true;
//            }
        }
        telemetry.addData("color switched: ", color);
        return false;
    }
    public void autoTurn(){ // Done?
        if (isRed) {
            limeLightTurretSystem.turntoAT(24);
        } else {
            limeLightTurretSystem.turntoAT(20);
        }

    }
    public void turretTurnTo(double angle){ //not using
        //turns to angle TODO: turn
    }
    public void manuTurn(double power){ // Done?
        limeLightTurretSystem.turnToNotAT(power);
    }
}
