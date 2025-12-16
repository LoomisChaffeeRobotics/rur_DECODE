package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class CherryDrive extends OpMode { //this clas is called CherryDrive because the bot's name is Cherry that is final grrrrr


    //name of spinning turret is turretSpin
    //name of indexer is indexer
    //uncomment out the stuff later

    LimeLightTurretSystem limelightsystem;
    Indexer colorsensor;
//    CRServo indexer;

    boolean autoTurn = true;
    DcMotor intake;
    DcMotor turret1; // top
    DcMotor turret2; // bottom (i think)
    CRServo indexer;
    Servo flipper;

    DcMotor left_front;
    DcMotor right_front;
    DcMotor left_back;
    DcMotor right_back;
    IMU imu;



    @Override
    public void init() {
        limelightsystem = new LimeLightTurretSystem();
        limelightsystem.init(hardwareMap, telemetry);
//        TODO: FIX LIMELIGHT INIT!!!!!! NOW!!!!!!!!!! PELASE OMG I HATE LIMELIGHTG ADSLKFGHJGFDIPOK:L


        intake = hardwareMap.get(DcMotor.class,"intake");
        turret1 = hardwareMap.get(DcMotor.class,"launcher");
        turret2 = hardwareMap.get(DcMotor.class,"launcher2");
        indexer = hardwareMap.get(CRServo.class,"indexer");
        flipper = hardwareMap.get(Servo.class,"flipper");
//        colorsensor = new ColorTurningMechanismThing();
//        colorsensor.init(hardwareMap, telemetry);
//        indexer = hardwareMap.get(CRServo.class, "indexer");


        //FCD INIT

        left_front = hardwareMap.get(DcMotor.class, "leftFront");
        right_front = hardwareMap.get(DcMotor.class, "rightFront");
        left_back = hardwareMap.get(DcMotor.class, "leftBack");
        right_back = hardwareMap.get(DcMotor.class, "rightBack");

        left_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        left_front.setDirection(DcMotorSimple.Direction.REVERSE);
        left_back.setDirection(DcMotorSimple.Direction.REVERSE);

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

        //  INTAKE
        if (gamepad1.right_trigger > 0.2) {
            runIntake(1d);
        }
        else {
            runIntake(0d);
        }

        //  TURRET
        if (gamepad2.left_trigger > 0.2){
            startTurret(1d);
        } else {
            startTurret(0d);
        }

        //  FLIPPER
        if (gamepad2.right_trigger > 0.2){
            flipper(true);
        } else {
            flipper(false);
        }

//        if (gamepad2.x){
//            switchColor(ColorTurningMechanismThing.SensedColor.GREEN);
//        }
//        if (gamepad2.a){
//            switchColor(ColorTurningMechanismThing.SensedColor.NEITHER);
//        }
//        if (gamepad2.b){
//            switchColor(ColorTurningMechanismThing.SensedColor.PURPLE);
//        }

        //  TURNING THE HEAD
        if(autoTurn){
            autoTurn();
        }

        if (gamepad2.dpad_left){
            autoTurn = false;
            manuTurn(-1);
        } else if (gamepad2.dpad_right){
            autoTurn = false;
            manuTurn(1);
        } else if (!autoTurn){
            manuTurn(0);
        }
        if (gamepad1.dpad_up){
            autoTurn = true;
        }

        // Driving
        fieldCentricDriving();
        if (gamepad1.start){
            imu.resetYaw();
        }

    }


    public void fieldCentricDriving(){

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
//        telemetry.addData("yaw: ", Yaw);

    }
    public void runIntake(double power){
        //run Intake
        telemetry.addData("intake power: ",power);
        intake.setPower(-power);
    }
    public void startTurret(double power){
        // move,
        telemetry.addData("turret power: ", power);
    }
    public void flipper(boolean up){
        //flip
        double flipDown = 0.91d;
        double flipUP = 0.53d;
        flipper.setPosition(up? flipUP : flipDown);
        telemetry.addData("flipper upness: ", up);
    }
    public void switchColor(Indexer.SensedColor color){
       //COLOUR STUFFs
//        colorsensor.turnBasedOffColor(color);
        telemetry.addData("color switched: ", color);
    }
    public void autoTurn(){
        //turn automatically
//        limelightsystem.turntoAT();
//        autoTurn = true;
//        telemetry.addData("angel error", limelightsystem.angleerror);
    }
    public void manuTurn(double power){
        //turn manually
//        indexer.setPower(power);
        telemetry.addData("manuturn power", power);
    }
}
