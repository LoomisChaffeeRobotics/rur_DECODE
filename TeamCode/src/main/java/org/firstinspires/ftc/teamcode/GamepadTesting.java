package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class GamepadTesting extends OpMode {

    DcMotor intake;
    DcMotor launcher;
    DcMotor launcher2;
    CRServo indexer;
    Servo flipper;


    @Override
    public void init() {
        intake = hardwareMap.get(DcMotor.class,"intake");
        launcher = hardwareMap.get(DcMotor.class,"launcher");
        launcher2 = hardwareMap.get(DcMotor.class,"launcher2");
        indexer = hardwareMap.get(CRServo.class,"indexer");
        flipper = hardwareMap.get(Servo.class,"flipper");
//        flipper.setPosition(0.5);
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
            runIndexer(.2d);
        } else if (gamepad1.dpad_right) {
            runIndexer(-.2d);
        }
        else {
            runIndexer(0d);
        }
        if (gamepad1.dpad_up) {
            flip(0.565);
        } else if (gamepad1.dpad_down) {
            flip(0.91d);
        }
        if (gamepad1.left_trigger > 0.2){
            startTurret(-0.3d);
        } else {
            startTurret(0d);
        }

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
        launcher.setPower(power);
        launcher2.setPower(power);
        telemetry.addData("Flipper position: ",flipper.getPosition());
        telemetry.addData("turret power: ", power);
    }

}
//indexer v, flipper v, intake v, turret v