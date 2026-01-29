package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class LauncherRotateTest extends OpMode {

    LimeLightTurretSystem limelight_thing;

    @Override
    public void init() {
        limelight_thing.init(hardwareMap, telemetry);
    }

    @Override
    public void loop() {
        if (gamepad1.aWasPressed()) {
            limelight_thing.turntoAT(24);
        }
        else {
            limelight_thing.turnToNotAT(0);
        }
    }

}
