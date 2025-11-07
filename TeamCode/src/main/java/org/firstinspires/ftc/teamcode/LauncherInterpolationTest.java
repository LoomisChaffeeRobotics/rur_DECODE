package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class LauncherInterpolationTest extends OpMode {

    Launcher launcherClass;
    unnecessaryLimeLightTurretSystem limelight;

    @Override
    public void init() {
        launcherClass = new Launcher();
        launcherClass.init(hardwareMap, telemetry);

    }

    @Override
    public void loop() {
        launcherClass.shoot();
        telemetry.addData("Distance from APrilag", limelight.distance_from_apriltag);
       telemetry.addData("botpose", limelight.botpose);
    }

}
