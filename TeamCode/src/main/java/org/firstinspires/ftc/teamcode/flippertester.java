package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class flippertester extends OpMode{

//    DcMotor leftFront;
//
//    DcMotor leftRear;
//
//    DcMotor rightFront;
//
//    DcMotor rightRear;

    CRServo turretSpin;
    @Override
    public void init() {
        turretSpin = hardwareMap.get(CRServo.class, "turretSpin");

    }

    @Override
    public void loop() {
        if(gamepad1.a){
            turretSpin.setPower(0.1);
        } else if(gamepad1.b){
            turretSpin.setPower(-0.1);
        } else {turretSpin.setPower(0);}

        telemetry.addData("servo posiwer: ",turretSpin.getPower());
        telemetry.update();


    }
}
