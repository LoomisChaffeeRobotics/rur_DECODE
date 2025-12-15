package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
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

    @Override
    public void init() {
        limelightsystem = new LimeLightTurretSystem();
        limelightsystem.init(hardwareMap, telemetry);


        intake = hardwareMap.get(DcMotor.class,"intake");
        turret1 = hardwareMap.get(DcMotor.class,"Turret1");
        turret2 = hardwareMap.get(DcMotor.class,"Turret2");
        indexer = hardwareMap.get(CRServo.class,"indexer");
        flipper = hardwareMap.get(Servo.class,"flipper");
//        colorsensor = new ColorTurningMechanismThing();
//        colorsensor.init(hardwareMap, telemetry);
//        indexer = hardwareMap.get(CRServo.class, "indexer");

        /*
        //FCD INIT

        left_front = hardwareMap.get(DcMotor.class, "leftFront");
        right_front = hardwareMap.get(DcMotor.class, "rightFront");
        left_back = hardwareMap.get(DcMotor.class, "leftBack");
        right_back = hardwareMap.get(DcMotor.class, "rightBack");

        left_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        right_front.setDirection(DcMotorSimple.Direction.REVERSE);
        right_back.setDirection(DcMotorSimple.Direction.REVERSE);

        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters myIMUparameter;

        myIMUparameter = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
                        RevHubOrientationOnRobot.UsbFacingDirection.LEFT
//                )
//        );

        imu.initialize(myIMUparameter);

        robotOrientation = imu.getRobotYawPitchRollAngles();
        imu.resetYaw();
        Yaw = robotOrientation.getYaw();
        Pitch = robotOrientation.getPitch();
        Roll = robotOrientation.getRoll();

         */

        flipper(false);
    }

    @Override
    public void loop() {
//        colorsensor.sensecolor(telemetry);
//        fieldCentricDriving(gamepad1.left_stick_x,gamepad1.left_stick_y,
//                gamepad1.right_stick_x,gamepad1.right_stick_y);


        if (gamepad1.right_trigger > 0.2) {
            runIntake(1d);
        }
        else {
            runIntake(0d);
        }


        if (gamepad2.left_trigger > 0.2){
            startTurret(1d);
        } else {
            startTurret(0d);
        }


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


    }


    public void fieldCentricDriving(double leftX, double leftY, double rightX, double rightY){
        // FCD and turning
        telemetry.addData("leftX: ", leftX);
        telemetry.addData("leftY: ", leftY);
        telemetry.addData("rightX: ", rightX);
        telemetry.addData("rightY: ", rightY);


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
        double flipDown = 0.3661d;
        double flipUP = 0d;
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
        limelightsystem.turntoAT();
        autoTurn = true;
        telemetry.addData("angel error", limelightsystem.angleerror);
    }
    public void manuTurn(double power){
        //turn manually
//        indexer.setPower(power);
        telemetry.addData("manuturn power", power);
    }
}
