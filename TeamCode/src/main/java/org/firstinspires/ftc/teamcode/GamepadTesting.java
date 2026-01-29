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
    DcMotor left_front;
    DcMotor right_front;
    DcMotor left_back;
    DcMotor right_back;
    CRServo indexer;
    Servo flipper;


    @Override
    public void init() {
        intake = hardwareMap.get(DcMotor.class,"intake");
        launcher = hardwareMap.get(DcMotor.class,"launcher");
        launcher2 = hardwareMap.get(DcMotor.class,"launcher2");
        indexer = hardwareMap.get(CRServo.class,"indexer");
        flipper = hardwareMap.get(Servo.class,"flipper");
        left_front = hardwareMap.get(DcMotor.class, "leftFront");
        right_front = hardwareMap.get(DcMotor.class, "rightFront");
        left_back = hardwareMap.get(DcMotor.class, "leftBack");
        right_back = hardwareMap.get(DcMotor.class, "rightBack");

        //Setting the servos (not spindexer)

        //setting up the drive motor behavior
        left_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
        if (gamepad1.dpadUpWasPressed()) {
            flip(0.01); // up 0.1889
        } else if (gamepad1.dpadDownWasPressed()) {
            flip(-.01); // down .51
        } else {
            flip(0);
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
        flipper.setPosition(flipper.getPosition()+power);
    }
    public void startTurret(double power){
        launcher.setPower(power);
        launcher2.setPower(power);
        telemetry.addData("Flipper position: ",flipper.getPosition());
        telemetry.addData("turret power: ", power);
    }

}
//indexer v, flipper v, intake v, turret v