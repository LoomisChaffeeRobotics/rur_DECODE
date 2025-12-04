package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class CherryDrive extends OpMode {


    //uncomment out the stuff later

    unnecessaryLimeLightTurretSystem limelightsystem;
    ColorTurningMechanismThing colorsensor;
//    CRServo sv;

    boolean autoTurn = true;
    DcMotor intake;

    @Override
    public void init() {
        limelightsystem = new unnecessaryLimeLightTurretSystem();
        limelightsystem.init(hardwareMap, telemetry);
        intake = hardwareMap.get(DcMotor.class,"intake");
//        colorsensor = new ColorTurningMechanismThing();
//        colorsensor.init(hardwareMap, telemetry);
//        sv = hardwareMap.get(CRServo.class, "sv");

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
        intake.setPower(power);
    }
    public void startTurret(double power){
        // move,
        telemetry.addData("turret power: ", power);
    }
    public void flipper(boolean up){
        //flip
        telemetry.addData("flipper upness: ", up);
    }
    public void switchColor(ColorTurningMechanismThing.SensedColor color){
       //COLOUR STUFFEs
//        colorsensor.turnBasedOffColor(color);
        telemetry.addData("color switched: ", color);
    }
    public void autoTurn(){
        //turn automatically
        limelightsystem.turntoAT();
        autoTurn = true;
        telemetry.addData("auto Turning", limelightsystem.angleerror);
    }
    public void manuTurn(double power){
        //turn manually
//        sv.setPower(power);
        telemetry.addData("manuturn Direction:", power);
    }
}
