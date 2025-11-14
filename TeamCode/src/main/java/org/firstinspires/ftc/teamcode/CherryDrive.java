package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class CherryDrive extends OpMode {

    enum Colors{GREEN, PUPRLE, EMPTY}; //TODO: fix spelling error in 'puprel'

    boolean autoTurn = true;

    @Override
    public void init() {

    }

    @Override
    public void loop() {

        fieldCentricDriving(gamepad1.left_stick_x,gamepad1.left_stick_y,
                gamepad1.right_stick_x,gamepad1.right_stick_y);


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
            switchColor(Colors.GREEN);
        }
        if (gamepad2.a){
            switchColor(Colors.EMPTY);
        }
        if (gamepad2.b){
            switchColor(Colors.PUPRLE);
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
        // move
        telemetry.addData("turret power: ", power);
    }
    void flipper(boolean up){
        //flip
        telemetry.addData("flipper upness: ", up);
    }
    void switchColor(Colors colors){
       //COLOUR STUFFER
        telemetry.addData("color switched: ", colors);
    }
    void autoTurn(){
        //turn automatically
        telemetry.addLine("auto Turning");
    }
    void manuTurn(double direction){
        //turn manually
        telemetry.addData("manuturn Direction:", direction);
    }
}
