package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class CherryDrive extends OpMode {

    enum Colors{GREEN, PUPRLE, EMPTY} //TODO: fix spelling error in 'puprel'

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


    }


    void fieldCentricDriving(double leftX, double leftY, double rightX, double rightY){
        // FCD and turning
    }
    void runIntake(double power){
        //run Intake
    }
    void startTurret(double power){
        // move
    }
    void flipper(boolean up){
        //flip
    }
    void switchColor(Colors colors){
       //COLOUR STUFFER
    }
}
