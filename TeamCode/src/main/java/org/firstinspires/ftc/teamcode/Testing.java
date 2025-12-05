package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp
public class Testing extends OpMode {

    DcMotor intake;
    DcMotor turret1;
    DcMotor turret2;
    CRServo indexer;
    Servo flipper;


    @Override
    public void init() {
        intake = hardwareMap.get(DcMotor.class,"intake");
        turret1 = hardwareMap.get(DcMotor.class,"Turret1");
        turret2 = hardwareMap.get(DcMotor.class,"Turret2");
        indexer = hardwareMap.get(CRServo.class,"indexer");
        flipper = hardwareMap.get(Servo.class,"flipper");
    }

    @Override
    public void loop() {

        if (gamepad1.a) {
            runIntake(1d);
        }
        else {
            runIntake(0d);
        }
        if (gamepad1.dpad_left) {
            runIndexer(0.2d);
        } else if (gamepad1.dpad_right) {
            runIndexer(-0.2d);
        }
        else {
            runIndexer(0d);
        }
        if (gamepad1.dpad_up) {
            flip(0.0001d);
            telemetry.addData("Flipper position: ",flipper.getPosition());
        } else if (gamepad1.dpad_down) {
            flip(-0.0001d);
            telemetry.addData("Flipper position: ",flipper.getPosition());
        }
        if (gamepad2.left_trigger > 0.2){
            startTurret(-1d);
        } else {
            startTurret(0d);
        }

    telemetry.update();
    }

    public void runIntake(double power) {
        intake.setPower(power);
    }
    public void runIndexer(double power){
        indexer.setPower(power);
    }
    public void flip(double power){
        flipper.setPosition(flipper.getPosition() + power);
    }
    public void startTurret(double power){
        turret1.setPower(power);
        turret2.setPower(power);
        telemetry.addData("turret power: ", power);
    }

}
//indexer v, flipper v, intake v, turret v