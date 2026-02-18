package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

//import com.pedropathing.util.Timer;

@TeleOp // FINAL TELE-OP CLASS
public class CherryDrive extends OpMode { //this clas is called CherryDrive because Cherry is a placeholder robot name
    //RUN SENSECOLOR BEFORE EVERY TURNING THING

    //name of spinning turret is turretSpin
    //name of indexer is indexer
    //uncomment out the stuff later

    FtcDashboard dash = FtcDashboard.getInstance();
    Telemetry t2 = dash.getTelemetry();




    /** This helps us send telemetry data to a thing called 'Panels' */
    private TelemetryManager panelsTelemetry;
    /** Class for detecting AT using limelight and turning turret to the AT also using limelight */
    LimeLightTurretSystem limeLightTurretSystem;
    ColorSensorAccuracyClass coloracc;
//    TelemetryManager panelsTelemetry;
    /** Class for the Spnidexer and the color sensor */
    Indexer indexClass;

    /** we use this for finding motor speeds VERY IMPORTANT even tho only used once */
    Launcher launchClass;
//    LauncherLegacy launcherLegacy;

    /** weather or not we turn the turret automatically (do we want ts?) */
    boolean autoTurn = true;

    /** if this isn't self-explanatory re-do you are 5th grade education */
    com.pedropathing.util.Timer flipperUp;
    // whether we're red alliance or not - can be set
    boolean isRed = false;


    /** List of Motors: */
    DcMotorEx launcher; // top
    DcMotorEx launcher2; // bottom (i think)
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

    /** depressed stuffs */
    boolean xdepressed = false;
    boolean bdepressed = false;
    boolean ydepressed = false;
    boolean adepressed = false;

    boolean startdepressed = false;



    @Override
    public void init() {
        //class initializations
        panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();
        panelsTelemetry.update(telemetry);
        limeLightTurretSystem = new LimeLightTurretSystem();
        limeLightTurretSystem.init(hardwareMap, telemetry);
        indexClass = new Indexer();
        indexClass.init(hardwareMap, telemetry);
        launchClass = new Launcher();
        launchClass.init(hardwareMap, telemetry);
        coloracc = new ColorSensorAccuracyClass();
        coloracc.init(hardwareMap,telemetry);
//        launcherLegacy = new LauncherLegacy();
//        launcherLegacy.init(hardwareMap, telemetry);
        //Setting the motors
//        intake = hardwareMap.get(DcMotor.class,"intake");
        launcher = hardwareMap.get(DcMotorEx.class,"launcher");
        launcher2 = hardwareMap.get(DcMotorEx.class,"launcher2");
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
        left_front.setDirection(DcMotorSimple.Direction.FORWARD);
        right_front.setDirection(DcMotorSimple.Direction.FORWARD);
        left_back.setDirection(DcMotorSimple.Direction.FORWARD);
        right_back.setDirection(DcMotorSimple.Direction.FORWARD);
        indexClass.SensedColorAll.set(0, Indexer.SensedColor.NEITHER);
        indexClass.SensedColorAll.set(1, Indexer.SensedColor.NEITHER);
        indexClass.SensedColorAll.set(2, Indexer.SensedColor.NEITHER);


        /* imu starting stuff */
        imu = hardwareMap.get(IMU.class, "imu2");
        IMU.Parameters myIMUparameter;

        myIMUparameter = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
                        RevHubOrientationOnRobot.UsbFacingDirection.RIGHT
                )
        );

        imu.initialize(myIMUparameter);
        imu.resetYaw();



        flipperUp = new com.pedropathing.util.Timer();
        flipperUp.resetTimer();

    }
    @Override
    public void start() {
        indexClass.intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    @Override
    public void loop() {
        if (gamepad2.start && !startdepressed){
            isRed = !isRed;
        }
        startdepressed = gamepad2.start;
        limeLightTurretSystem.update(!isRed);
        if (!indexClass.indexer_is_moving) {
            indexClass.sensecolor();
            coloracc.update();
        }

//        telemetry.addData("motor up speed", launcher2.getVelocity());
//        telemetry.addData("motor down speed", launcher.getVelocity());
//        telemetry.addData("is indexer moving", indexClass.indexer_is_moving);
        telemetry.addData("sensed color", indexClass.hsvValues1[0]);
        telemetry.addData("SensedColorAll", indexClass.SensedColorAll);
        telemetry.addData("NEITHER sense confidence %", coloracc.emptyAccuracy);
        telemetry.addData("NEITHER sense count", coloracc.wrongCount);
        telemetry.addData("oldest color accuracy val", coloracc.lastWrongArray[0]);
        telemetry.addData("newest color accuracy val", coloracc.lastWrongArray[124]);
        telemetry.addData("reset times", indexClass.reset_times);
//        telemetry.addData("is list not neither", indexClass.SensedColorAll.get(0) != Indexer.SensedColor.NEITHER);
        telemetry.addData("Atseen", limeLightTurretSystem.ATSeen);
        telemetry.addData("index", limeLightTurretSystem.index);
//        telemetry.addData("flipper", flipperUp.getElapsedTime());
//        telemetry.addData("resuurretSystem.getDistance_from_apriltag(true))[0]);
//        telemetry.addData("result 1",launchClass.find_closest_x(limeLightTurretSystem.getDistance_from_apriltag(true))[1]);
//        telemetry.addData("upper motor speed 0", launchClass.upper_motor_value_0*(7.0 / 15.0));
//        telemetry.addData("upper motor speed 1", launchClass.upper_motor_value_1*(7.0 / 15.0));
//        telemetry.addData("lower motor speed 0", launchClass.lower_motor_value_0*(7.0 / 15.0));
//        telemetry.addData("lowerpower", launchClass.lower_motor_interporation_result*(15.0/7.0));
//        telemetry.addData("higherpower", launchClass.upper_motor_interporation_result*(15.0/7.0));

//        telemetry.addData("distance", limeLightTurretSystem.getDistance_from_apriltag(!isRed) + 0.4);
//        telemetry.addData("current distance", limeLightTurretSystem.getDistance_from_apriltag(!isRed) + 0.9)


        //  INTAKE - gp1 RT, gp2 RB
        if ((gamepad1.right_trigger > 0.2) || gamepad2.right_bumper /*this is for testing maybe will keep*/ /*nevermind keep this*/) {
            runIntake(1d);
        }
        else if (gamepad2.left_bumper || gamepad1.left_trigger > 0.2){
            runIntake(-1);
        } else {
            runIntake(0d);
        }

        if (gamepad2.dpad_up){ //reverse launcher gp2 DIP-DUP
            launcher.setPower(-0.2);
            launcher2.setPower(-0.2);
        } else



        //  TURRET - gp2 LT
        if (gamepad2.left_trigger > 0.2) {
            startTurret(1);
        } else {
            startTurret(0);
        }

        //  FLIPPER - gp2 RT
        if (gamepad2.right_trigger > 0.2 && Math.abs(indexClass.error) < 200 && launchClass.checkIfSpunUp()){
            flipperUp.resetTimer();
            flipper(true);
            indexClass.removefirst(indexClass.SensedColorAll);
//            flipperUp = true;
        } else {
            flipper(false);
//            flipperUp = false;
        }


        // SPINDEXER - gp2 X and B
        if (gamepad2.x && !xdepressed) {
            turnSpinner(true);
        }else if (gamepad2.b && !bdepressed) {
            turnSpinner(false);
        }
        xdepressed = gamepad2.x;
        bdepressed = gamepad2.b;

        indexClass.errorCalc(); // claculates error ~ALWAYS RUNNING~
        if (flipperUp.getElapsedTime() <= 700) {
            indexer.setPower(0);
        } else {
            indexClass.indexerUpdate(); // updates position ~NOT ALWAYS RUNNING~
        }







//        telemetry.update();


//         Driving
        autoTurn();
        fieldCentricDriving();
        if (gamepad1.start){
            imu.resetYaw();
        }
        indexClass.updateIndicator(launchClass.checkIfSpunUp());
        if (gamepad2.start){
            if (isRed){
                indexClass.light.setPosition(0.279);
            } else{
                indexClass.light.setPosition(0.611);
            }
        }
        telemetry.addData("error", indexClass.error);

        t2.addData("inner cur velo", launchClass.launcher.getVelocity());
        t2.addData("outer cur velo", launchClass.launcher2.getVelocity());
        t2.addData("inner targ velo", launchClass.lower_motor_interporation_result * (7.0/15.0));
        t2.addData("outer targ velo", launchClass.upper_motor_interporation_result* (7.0/15.0));
        t2.addData("indexerpos", indexClass.intake.getCurrentPosition());
        t2.addData("indexertargpos", indexClass.targetPosition);
        t2.update();
        telemetry.update();

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
        if (gamepad1.a){
            denominator *= 3;
        }

        right_front.setPower(right_front_velocity/denominator);
        right_back.setPower(right_back_velocity/denominator);
        left_front.setPower(left_front_velocity/denominator);
        left_back.setPower(left_back_velocity/denominator);


    }

    /** runs the intake at power power. 0 for stop 1 for go .5 for 50% speed*/
    public void runIntake(double power){ // Done!

        indexClass.spinIn(power);
    }
    public void startTurret(double power){ // Done?
        if (power == 0) {
            launcher.setPower(0);
            launcher2.setPower(0);
        } else {
            double distance = limeLightTurretSystem.getDistance_from_apriltag(!isRed);
            launchClass.shoot( distance+ (distance>2? 0.5:0));/*0.17 to get distance to center of turret + 0.23 to get to the center of the goal*/
        }
    }
    public void flipper(boolean up){ // Done!
        //flip

        double flipDown = 0.8117;
        double flipUP = 0.42; //monday feb 16th values
        flipper.setPosition(up? flipUP : flipDown);
        telemetry.addData("flipper upness: ", up);
    }
    public void turnSpinner(boolean direction) {
        indexClass.sensecolor();
        indexClass.turn(direction);

    }
    public boolean switchColor(Indexer.SensedColor color) { // NOT DONE
        //COLOUR STUFFs
        indexClass.sensecolor();
        if (true) {
            return false;
        }
        else {
            indexClass.turnBasedOffColor(color);
        }
        telemetry.addData("color switched: ", color);
        return false;
    }
    public void autoTurn(){ // Done?
        if (gamepad2.dpadDownWasPressed()){
            autoTurn = !autoTurn;
        }
        if (autoTurn) {
            double tx = limeLightTurretSystem.turntoAT(!isRed);
            telemetry.addData("tx", tx);
        } else{
            limeLightTurretSystem.turnToNotAT(0);
        }
    }
    public void turretTurnTo(double angle){ //not using
        //turns to angle TODO: turn
    }
    public void manuTurn(double power){ // Done?
        limeLightTurretSystem.turnToNotAT(power);
    }
}
