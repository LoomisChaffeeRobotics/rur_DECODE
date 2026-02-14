package org.firstinspires.ftc.teamcode.testingClasses;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Launcher;

@TeleOp
public class LauncherMotorSpeedTestingClass extends OpMode {
    FtcDashboard dash;

    Telemetry t2 = dash.getTelemetry();
    DcMotorEx bottom_motor;
    DcMotorEx top_motor;
    Servo flipper;
    double bottom_velocity = 0;
    double top_velocity = 0;

    ElapsedTime elapsedTime;
    Launcher launcher;

    @Override
    public void init() {
        flipper = hardwareMap.get(Servo.class, "flipper");
        bottom_motor = hardwareMap.get(DcMotorEx.class, "launcher");
        top_motor = hardwareMap.get(DcMotorEx.class, "launcher2"); //
        elapsedTime = new ElapsedTime();
        launcher = new Launcher();
        launcher.init(hardwareMap,telemetry);
    }

    @Override
    public void loop() {
        top_motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, launcher.launcherPIDF);
        bottom_motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, launcher.launcherPIDF);
        if (gamepad1.x) {
            flipper.setPosition(0); // down
//            leftFront.setPower(gamepad1.left_stick_y);
        }
        if (gamepad1.b) {
            flipper.setPosition(0.3578); // up
//            leftRear.setPower(gamepad1.left_stick_y);
        }
        if (gamepad1.dpadUpWasPressed()) {
            bottom_velocity = 3000;
            bottom_motor.setVelocity(bottom_velocity * 7.0 / 15.0);
            top_velocity = 500;
            top_motor.setVelocity(top_velocity * 7.0 / 15.0);
        }
        if (gamepad1.dpadDownWasPressed()) {
            bottom_velocity = 0;
            bottom_motor.setVelocity(bottom_velocity);
            top_velocity = 0;
            top_motor.setVelocity(top_velocity);


            t2.addData("Upper set value", top_velocity * 7.0 / 15.0);
            t2.addData("Lower set value", bottom_velocity * 7.0 / 15.0);
            t2.addData("Upper current value", top_motor.getVelocity());
            t2.addData("Lower current value", bottom_motor.getVelocity());
            t2.update();

//        telemetry.addData("top: ",top_motor.getVelocity()*15/7);
//        telemetry.addData("bottom: ",bottom_motor.getVelocity()*15/7);
            telemetry.addData("servo pos", flipper.getPosition());
            telemetry.update();
        }
    }
    }
