package org.firstinspires.ftc.teamcode;

import com.pedropathing.ftc.localization.Encoder;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Base64;

@TeleOp
public class Testing extends OpMode {

    DcMotor intake;
    DcMotorEx turret1;
    DcMotorEx turret2;
    DcMotor turretSpinEncoder;
    CRServo indexer;
    Servo flipper;

    double bottomSpeed = 0;
    double topSpeed = 0;



    @Override
    public void init() {
        intake = hardwareMap.get(DcMotor.class,"intake");
        turret1 = hardwareMap.get(DcMotorEx.class,"Bottom");
        turret2 = hardwareMap.get(DcMotorEx.class,"Top");
        indexer = hardwareMap.get(CRServo.class,"indexer");
        flipper = hardwareMap.get(Servo.class,"flipper");
        turretSpinEncoder = hardwareMap.get(DcMotor.class, "turretSpinning");
        flipper.setPosition(0.3661);
    }

    @Override
    public void loop() {

        if (gamepad1.a) {
           bottomSpeed += 1;
        }
        if (gamepad1.b) {
            bottomSpeed -= 1;
        }
        if (gamepad1.x) {
            topSpeed += 1;
        }
        if (gamepad1.y) {
            topSpeed -= 1;
        }
        turret1.setVelocity(bottomSpeed);
        turret2.setVelocity(topSpeed);
        runIntake(1);



        if (gamepad1.dpad_left) {
            runIndexer(.2d);
        } else if (gamepad1.dpad_right) {
            runIndexer(-.2d);
        }
        else {
            runIndexer(0d);
        }
        if (gamepad1.dpad_up) {
            flip(0);
        } else if (gamepad1.dpad_down) {
            flip(0.3661);
        }
        if (gamepad1.left_trigger > 0.2){
            startTurret(-1d);
        } else {
            startTurret(0d);
        }

        telemetry.addData("Target top: ", topSpeed);
        telemetry.addData("Target bottom: ", bottomSpeed);
        telemetry.addLine();
        telemetry.addData("top: ", turret2.getVelocity());
        telemetry.addData("bottom: ", turret1.getVelocity());
        telemetry.update();
    }

    public void runIntake(double power) {
        intake.setPower(-power);
    }
    public void runIndexer(double power){
        indexer.setPower(power);
    }
    public void flip(double power){
        flipper.setPosition(power);
    }
    public void startTurret(double power){
//        turret1.setPower(power);
//        turret2.setPower(power);

//        telemetry.addData("turret power: ", power);
    }

}
//indexer v, flipper v, intake v, turret v

//0.113 top
//0.3661 bottom