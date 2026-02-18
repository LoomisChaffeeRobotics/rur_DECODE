package org.firstinspires.ftc.teamcode.testingClasses;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Launcher;

@TeleOp
public class LauncherPIDTuningClass extends OpMode {
    FtcDashboard dash = FtcDashboard.getInstance();

    Telemetry t2 = dash.getTelemetry();
    DcMotorEx bottom_motor;
    DcMotorEx top_motor;
    public PIDFCoefficients launcherPIDF;
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
        launcherPIDF = new PIDFCoefficients(Launcher.launcherP, Launcher.launcherI, Launcher.launcherD, Launcher.launcherF);
        top_motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, launcherPIDF);
        bottom_motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, launcherPIDF);
        if (gamepad1.x) {
            flipper.setPosition(0.8117); // down
//            leftFront.setPower(gamepad1.left_stick_y);
        }
        if (gamepad1.b) {
            flipper.setPosition(0.4694); // up
//            leftRear.setPower(gamepad1.left_stick_y);
        }
        if (gamepad1.dpadUpWasPressed()) {
            top_velocity = 3000;
            top_motor.setVelocity(top_velocity * 7.0 / 15.0);
        }
        if (gamepad1.dpadDownWasPressed()) {
            top_velocity = 0;
            top_motor.setVelocity(top_velocity);

        }
        t2.addData("Upper set value", top_velocity);
        t2.addData("Lower set value", bottom_velocity);
        t2.addData("Upper current value", top_motor.getVelocity() * 15/7);
        t2.addData("Lower current value", bottom_motor.getVelocity() * 15/7);
        t2.update();

//        telemetry.addData("top: ",top_motor.getVelocity()*15/7);
//        telemetry.addData("bottom: ",bottom_motor.getVelocity()*15/7);
        telemetry.addData("servo pos", flipper.getPosition());
        telemetry.update();
    }
}