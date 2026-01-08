package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class flippertester extends OpMode{

//    DcMotor leftFront;d
//
//    DcMotor leftRear;
//
//    DcMotor rightFront;
//
//    DcMotor rightRear;

    Servo Flipper;
    @Override
    public void init() {
        Flipper = hardwareMap.get(Servo.class, "flipper");

    }

    @Override
    public void loop() {
        if(gamepad1.a){
            Flipper.setPosition(Flipper.getPosition() + 0.001); // up
        }
        if(gamepad1.b){
            Flipper.setPosition(Flipper.getPosition() - 0.001); // down
        }

        telemetry.addData("servo position: ",Flipper.getPosition());
        telemetry.update();


    }
}
