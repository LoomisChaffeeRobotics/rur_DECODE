package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class LauncherInterpolationTest extends OpMode {

    Launcher launcherClass;
    LimeLightTurretSystem limelight;
    double distance;

    @Override
    public void init() {
        launcherClass = new Launcher();
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
        telemetry.addData("Distance, meters", distance);
       telemetry.addData("botpose", limelight.botpose);

       telemetry.update();
    }

}
