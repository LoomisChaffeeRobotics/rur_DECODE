package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class blahblahblah extends OpMode{

    DcMotor leftFront;

    DcMotor leftRear;

    DcMotor rightFront;

    DcMotor rightRear;
    @Override
    public void init() {
        leftFront=hardwareMap.get(DcMotor.class, "frontLeft");
        leftRear=hardwareMap.get(DcMotor.class, "rearLeft");
        rightFront=hardwareMap.get(DcMotor.class, "frontRight");
        rightRear=hardwareMap.get(DcMotor.class, "rearRight");

    }

    @Override
    public void loop() {
        if(gamepad1.a){
            leftFront.setPower(gamepad1.left_stick_y);
        }
        if(gamepad1.b){
            leftRear.setPower(gamepad1.left_stick_y);
        }
        if(gamepad1.x){
            rightFront.setPower(gamepad1.left_stick_y);
        }
        if(gamepad1.y){
            rightRear.setPower(gamepad1.left_stick_y);
        }

        telemetry.addData("leftRear: ", leftRear.getCurrentPosition());
        telemetry.addData("leftFront: ", leftFront.getCurrentPosition());
        telemetry.addData("rightRear: ", rightRear.getCurrentPosition());
        telemetry.addData("rightFront: ", rightFront.getCurrentPosition());

        telemetry.update();


    }
}
