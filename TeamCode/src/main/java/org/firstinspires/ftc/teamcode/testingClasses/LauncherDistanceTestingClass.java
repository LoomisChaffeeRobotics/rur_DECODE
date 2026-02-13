package org.firstinspires.ftc.teamcode.testingClasses;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Launcher;
import org.firstinspires.ftc.teamcode.LimeLightTurretSystem;

@TeleOp
public class LauncherDistanceTestingClass extends OpMode {
    FtcDashboard dash = FtcDashboard.getInstance();
    Telemetry t2 = dash.getTelemetry();
    Launcher launcherClass;
    LimeLightTurretSystem limelight;
    Servo flipper;
    double distance;

    @Override
    public void init() {
        flipper = hardwareMap.get(Servo.class, "flipper");
        launcherClass = new Launcher();
        launcherClass.init(hardwareMap, telemetry);
        limelight = new LimeLightTurretSystem();
        limelight.init(hardwareMap, telemetry);

    }

    @Override
    public void loop() {
        launcherClass.shoot(distance);
        if (gamepad1.aWasPressed()){
            distance += 0.2;
        } else if (gamepad1.bWasPressed()){
            distance -= 0.2;
        }
        if (gamepad1.right_trigger>0.2){
            flipper.setPosition(0);
        } else {
            flipper.setPosition(0.3578);
        }
        telemetry.addData("Distance, meters", distance);
//       telemetry.addData("botpose", limelight.botpose);
        telemetry.addData("Interoplation lower motor", launcherClass.lower_motor_interporation_result);
        telemetry.addData("upper bound for lower motor", launcherClass.lower_motor_value_0);
        telemetry.addData("lower bound for lower motor", launcherClass.lower_motor_value_1);
        telemetry.addData("Interoplation upper motor", launcherClass.upper_motor_interporation_result);
        telemetry.addData("upper bound for upper motor", launcherClass.upper_motor_value_0);
        telemetry.addData("lower bound for upper motor", launcherClass.upper_motor_value_1);
//        telemetry.addData("Interoplation upper motor", launcherClass.upper_motor_interporation_result);
        telemetry.addData("inner cur velo", launcherClass.launcher.getVelocity());
        telemetry.addData("outer cur velo", launcherClass.launcher2.getVelocity());
        telemetry.addData("inner targ velo", launcherClass.lower_motor_interporation_result * (7.0/15.0));
        telemetry.addData("outer targ velo", launcherClass.upper_motor_interporation_result * (7.0/15.0));
      
       telemetry.update();
    }

}
