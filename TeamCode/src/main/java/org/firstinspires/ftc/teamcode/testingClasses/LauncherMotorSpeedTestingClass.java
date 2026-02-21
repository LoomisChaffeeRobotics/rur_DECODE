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
import org.firstinspires.ftc.teamcode.ColorSensorAccuracyClass;
import org.firstinspires.ftc.teamcode.Indexer;
import org.firstinspires.ftc.teamcode.Launcher;

@TeleOp
public class LauncherMotorSpeedTestingClass extends OpMode {
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

    ColorSensorAccuracyClass coloracc;
    Indexer indexClass;

    @Override
    public void init() {
        coloracc = new ColorSensorAccuracyClass();
//        coloracc.init(hardwareMap, telemetry);
        indexClass = new Indexer();
        indexClass.init(hardwareMap, telemetry);
        flipper = hardwareMap.get(Servo.class, "flipper");
        bottom_motor = hardwareMap.get(DcMotorEx.class, "launcher");
        top_motor = hardwareMap.get(DcMotorEx.class, "launcher2"); //
        elapsedTime = new ElapsedTime();
        launcher = new Launcher();
        launcher.init(hardwareMap,telemetry);
    }

    @Override
    public void loop() {

        indexClass.sensecolor();

//        coloracc.update();

        launcherPIDF = new PIDFCoefficients(Launcher.launcherP, Launcher.launcherI, Launcher.launcherD, Launcher.launcherF);
        top_motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, launcherPIDF);
        bottom_motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, launcherPIDF);
        if (gamepad1.x) {
            coloracc.reset();
            indexClass.removefirst(indexClass.SensedColorAll);
            flipper.setPosition(0.8117); // down
//            leftFront.setPower(gamepad1.left_stick_y);
        }
        if (gamepad1.b) {
            flipper.setPosition(0.42); // up
//            leftRear.setPower(gamepad1.left_stick_y);
        }
        if (gamepad1.dpad_up) {
            bottom_velocity += 10;
            bottom_motor.setVelocity(bottom_velocity * 7/15);
        }
        if (gamepad1.dpad_down) {
            bottom_velocity -= 10;
            bottom_motor.setVelocity(bottom_velocity * 7/15);
        }
        if (gamepad1.y) {
            top_velocity += 10;
            top_motor.setVelocity(top_velocity * 7/15);
        }
        if (gamepad1.a) {
            top_velocity -= 10;
            top_motor.setVelocity(top_velocity * 7/15);
        }


            telemetry.addData("Upper set value", top_velocity);
            telemetry.addData("Lower set value", bottom_velocity);
            telemetry.addData("Upper current value", top_motor.getVelocity() * 15/7);
            telemetry.addData("Lower current value", bottom_motor.getVelocity() * 15/7);
            telemetry.addData("sensed color", indexClass.hsvValues1[0]);
            telemetry.addData("SensedColorAll", indexClass.SensedColorAll);
            telemetry.addData("NEITHER sense confidence %", coloracc.emptyAccuracy);
            telemetry.addData("NEITHER sense count", coloracc.wrongCount);
            telemetry.addData("is list not neither", indexClass.SensedColorAll.get(0) != Indexer.SensedColor.NEITHER);

//        telemetry.addData("top: ",top_motor.getVelocity()*15/7);
//        telemetry.addData("bottom: ",bottom_motor.getVelocity()*15/7);
            telemetry.addData("servo pos", flipper.getPosition());
            telemetry.update();
        }
    }

