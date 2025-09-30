package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Launcher extends OpMode {
    DcMotor launcher2;

    DcMotor launcher;
    @Override
    public void init() {
        launcher = hardwareMap.get(DcMotor.class, "launcher");
        launcher2 = hardwareMap.get(DcMotor.class, "launcher2");
    }

    @Override
    public void loop() {
        if(gamepad1.a) {
            launcher.setPower(1);
        }
        if(gamepad1.b) {
            launcher2.setPower(1);
        }
        else if(gamepad1.y) {
            launcher.setPower(-1);

            }
        else if (gamepad1.x) {
            launcher2.setPower(-1);
        }
        else {
            launcher.setPower(0);
            launcher2.setPower(0);
        }
    }
}
