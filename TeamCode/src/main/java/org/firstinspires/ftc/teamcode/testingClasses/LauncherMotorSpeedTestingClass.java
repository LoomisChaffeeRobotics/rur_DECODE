package org.firstinspires.ftc.teamcode.testingClasses;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class LauncherMotorSpeedTestingClass extends OpMode {

    DcMotorEx bottom_motor;
    DcMotorEx top_motor;
    Servo flipper;
    double bottom_velocity = 0;
    double top_velocity = 0;

    ElapsedTime elapsedTime;

    @Override
    public void init() {
        flipper = hardwareMap.get(Servo.class, "flipper");
        bottom_motor = hardwareMap.get(DcMotorEx.class, "launcher");
        top_motor = hardwareMap.get(DcMotorEx.class, "launcher2"); //
        elapsedTime = new ElapsedTime();
    }

    @Override
    public void loop() {
        if(gamepad1.x){
            flipper.setPosition(0); // down //OFFICIAL THINGS SINCE JAN 8TH!!!!!!!
//            leftFront.setPower(gamepad1.left_stick_y);
        }
        if(gamepad1.b){
            flipper.setPosition(0.3578); // up
//            leftRear.setPower(gamepad1.left_stick_y);
        }
        if (gamepad1.dpad_up) {
            bottom_velocity += 0.01;
            bottom_motor.setPower(bottom_velocity);
        }
        if (gamepad1.dpad_down) {
            bottom_velocity -= 0.01;
            bottom_motor.setPower(bottom_velocity);
        }
        if (gamepad1.y) {
            top_velocity += 0.01;
            top_motor.setPower(top_velocity);
        }
        if (gamepad1.a) {
            top_velocity -= 0.01;
            top_motor.setPower(top_velocity);
        }

        telemetry.addData("top: ",top_motor.getVelocity()*15/7);
        telemetry.addData("bottom: ",bottom_motor.getVelocity()*15/7);
        telemetry.addData("servo pos", flipper.getPosition());
        telemetry.update();
    }
}
