package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class LauncherInterpolationTest extends OpMode {

    LauncherLegacy launcherClass;
    LimeLightTurretSystem limelight;
    Servo flipper;
    double distance;

    @Override
    public void init() {
        flipper = hardwareMap.get(Servo.class, "flipper");
        launcherClass = new LauncherLegacy();
        launcherClass.init(hardwareMap, telemetry);
        limelight = new LimeLightTurretSystem();
        limelight.init(hardwareMap, telemetry);

    }

    @Override
    public void loop() {
        launcherClass.shoot(distance);
        if (gamepad1.a){
            distance += 0.01;
        } else if (gamepad1.b){
            distance -= 0.01;
        }
        if (gamepad1.right_trigger>0.2){
            flipper.setPosition(0);
        } else {
            flipper.setPosition(0.3578);
        }
        telemetry.addData("Distance, meters", distance);
//       telemetry.addData("botpose", limelight.botpose);

       telemetry.update();
    }

}
