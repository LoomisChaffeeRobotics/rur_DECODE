package org.firstinspires.ftc.teamcode.testingClasses;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class FlipperTestingClass extends OpMode{

//    DcMotor leftFront;
//
//    DcMotor leftRear;
//
//    DcMotor rightFront;
//
//    DcMotor rightRear;

    Servo turretSpin;
    @Override
    public void init() {
        turretSpin = hardwareMap.get(Servo.class, "flipper");

    }

    @Override
    public void loop() {
        if(gamepad1.a){
            turretSpin.setPosition(turretSpin.getPosition() +0.001);
        } else if(gamepad1.b){
            turretSpin.setPosition(turretSpin.getPosition() -0.001);
        } else {turretSpin.setPosition(turretSpin.getPosition());}

        telemetry.addData("servo posiwer: ",turretSpin.getPosition());
        telemetry.update();


    }
}
