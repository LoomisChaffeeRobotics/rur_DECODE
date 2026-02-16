package org.firstinspires.ftc.teamcode.testingClasses;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Launcher;

@TeleOp
public class LauncherFudgeNumber extends OpMode {
    Launcher launcher;
    Servo flipper;
    public static double fudge = 0.0;
    public static double fudge2 = 0.0;
    public static double distance = 0.0;

    @Override
    public void init() {
//        launcher = new Launcher();
//        launcher.init(hardwareMap, telemetry);
        flipper = hardwareMap.get(Servo.class,"flipper");
        flipper(false);
    }

    @Override
    public void loop() {
        launcher.shootFudged(distance, fudge, fudge2);

        flipper(gamepad1.a);

        if (gamepad1.dpadUpWasPressed()){

        }
    }

    public void flipper(boolean up){ // Done!
        //flip
        double flipDown = 0.8117;
        double flipUP = 0.4694;
        flipper.setPosition((up? flipUP : flipDown));
        telemetry.addData("flipper upness: ", up);
    }
}
