package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class LauncherInterpolationTest extends OpMode {

    Launcher launcherClass;
    LimeLightTurretSystem limelight;

    @Override
    public void init() {
        launcherClass = new Launcher();
        launcherClass.init(hardwareMap, telemetry);
        limelight = new LimeLightTurretSystem();
        limelight.init(hardwareMap, telemetry);

    }

    @Override
    public void loop() {
        launcherClass.shoot(limelight.getDistance_from_apriltag(0));
        telemetry.addData("Distance from APrilag", limelight.getDistance_from_apriltag(0));
       telemetry.addData("botpose", limelight.botpose);
       telemetry.update();
    }

}
