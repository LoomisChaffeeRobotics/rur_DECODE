package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
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

    Servo Flipper;
    @Override
    public void init() {
        Flipper = hardwareMap.get(Servo.class, "flipper");
//        leftFront=hardwareMap.get(DcMotor.class, "frontLeft");
//        leftRear=hardwareMap.get(DcMotor.class, "rearLeft");
//        rightFront=hardwareMap.get(DcMotor.class, "frontRight");
//        rightRear=hardwareMap.get(DcMotor.class, "rearRight");

    }

    @Override
    public void loop() {
        if(gamepad1.a){
            Flipper.setPosition(0.91);
//            leftFront.setPower(gamepad1.left_stick_y);
        }
        if(gamepad1.b){
            Flipper.setPosition(0.53);
//            leftRear.setPower(gamepad1.left_stick_y);
        }
        if(gamepad1.x){
//            rightFront.setPower(gamepad1.left_stick_y);
        }
        if(gamepad1.y){
//            rightRear.setPower(gamepad1.left_stick_y);
        }

//        telemetry.addData("leftRear: ", leftRear.getCurrentPosition());
//        telemetry.addData("leftFront: ", leftFront.getCurrentPosition());
//        telemetry.addData("rightRear: ", rightRear.getCurrentPosition());
//        telemetry.addData("rightFront: ", rightFront.getCurrentPosition());

        telemetry.addData("servo position: ",Flipper.getPosition());
        telemetry.update();


    }
}
