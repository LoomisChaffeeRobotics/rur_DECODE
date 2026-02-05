package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class LauncherVideoThingamajig extends OpMode {

    DcMotorEx launcher;
    DcMotorEx launcher2;
    double launcherspeed = 0.0;
    double launcher2speed = 0.0;
    LimeLightTurretSystem limelight;
    Servo flipper;
    public static double launcherF = 16.1;
    public static double launcherP = 60;
    public static double launcherI = 3;
    public static double launcherD = 1;


    @Override
    public void init() {
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        launcher2 = hardwareMap.get(DcMotorEx.class, "launcher2");
        flipper = hardwareMap.get(Servo.class, "flipper");
        limelight = new LimeLightTurretSystem();
        limelight.init(hardwareMap, telemetry);
        PIDFCoefficients launcherPIDF = new PIDFCoefficients(launcherP, launcherI, launcherD, launcherF);
        launcher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, launcherPIDF);
        launcher2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, launcherPIDF);

    }

    @Override
    public void loop() {

        limelight.update();
        if (gamepad1.dpad_up){
            launcherspeed -= 1;
        }
        if (gamepad1.dpad_down){
            launcherspeed += 1;
        }
        if (gamepad1.y){
            launcher2speed -= 1;
        }
        if (gamepad1.a){
            launcher2speed += 1;
        }
        if (gamepad1.right_trigger >0.2){
            flipper(true);
        }else{
            flipper(false);
        }

        launcher.setVelocity(launcherspeed);
        launcher2.setVelocity(launcher2speed);
        telemetry.addData("launcher set speed: ", launcherspeed* 15/7);
        telemetry.addData("launcher acutual speed: ", launcher.getVelocity()* 15/7);
        telemetry.addData("launcher2 set speed: ", launcher2speed * 15/7);
        telemetry.addData("launcher2 acutual speed: ", launcher2.getVelocity()* 15/7);
        telemetry.addData("limelight distance ", limelight.getDistance_from_apriltag(true) +.4);


    }
    public void flipper(boolean up){ // Done!
        //flip
        double flipDown = 0.3778d;
        double flipUP = 0.0d;
        flipper.setPosition(up? flipUP : flipDown);
        telemetry.addData("flipper upness: ", up);
    }
}