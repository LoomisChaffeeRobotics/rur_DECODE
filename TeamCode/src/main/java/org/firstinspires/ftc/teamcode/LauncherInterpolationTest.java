package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class LauncherInterpolationTest extends OpMode {

    Launcher launcherClass;

    @Override
    public void init() {
        launcherClass = new Launcher();
        launcherClass.init(hardwareMap);
    }

    @Override
    public void loop() {
        launcherClass.shoot();
    }

}
