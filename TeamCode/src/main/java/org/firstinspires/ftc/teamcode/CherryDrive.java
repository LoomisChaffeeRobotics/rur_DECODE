package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;

public class CherryDrive extends OpMode {


    //uncomment out the stuff later

    unnecessaryLimeLightTurretSystem limelightsystem;
    ColorTurningMechanismThing colorsensor;
//    CRServo sv;

    boolean autoTurn = true;

    @Override
    public void init() {
        limelightsystem = new unnecessaryLimeLightTurretSystem();
        limelightsystem.init(hardwareMap, telemetry);
//        colorsensor = new ColorTurningMechanismThing();
//        colorsensor.init(hardwareMap, telemetry);
//        sv = hardwareMap.get(CRServo.class, "sv");
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

        if (gamepad2.x){
            switchColor(ColorTurningMechanismThing.SensedColor.GREEN);
        }
        if (gamepad2.a){
            switchColor(ColorTurningMechanismThing.SensedColor.NEITHER);
        }
        if (gamepad2.b){
            switchColor(ColorTurningMechanismThing.SensedColor.PURPLE);
        }

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


    void fieldCentricDriving(double leftX, double leftY, double rightX, double rightY){
        // FCD and turning
        telemetry.addData("leftX: ", leftX);
        telemetry.addData("leftY: ", leftY);
        telemetry.addData("rightX: ", rightX);
        telemetry.addData("rightY: ", rightY);
    }
    void runIntake(double power){
        //run Intake
        telemetry.addData("intake power: ",power);
    }
    void startTurret(double power){
        // move,
        telemetry.addData("turret power: ", power);
    }
    void flipper(boolean up){
        //flip
        telemetry.addData("flipper upness: ", up);
    }
    void switchColor(ColorTurningMechanismThing.SensedColor color){
       //COLOUR STUFFEs
//        colorsensor.turnBasedOffColor(color);
        telemetry.addData("color switched: ", color);
    }
    void autoTurn(){
        //turn automatically
        limelightsystem.turntoAT();
        autoTurn = true;
        telemetry.addLine("auto Turning");
    }
    void manuTurn(double power){
        //turn manually
//        sv.setPower(power);
        telemetry.addData("manuturn Direction:", power);
    }
}
