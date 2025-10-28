package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class LauncherTuningTest extends OpMode {

    DcMotor bottom_motor;
    DcMotor top_motor;

    double bottom_power = 0;
    double top_power = 0;

    ElapsedTime elapsedTime;

    @Override
    public void init() {
        bottom_motor = hardwareMap.get(DcMotor.class, "launcher1");
        top_motor = hardwareMap.get(DcMotor.class, "launcher2;");
        elapsedTime = new ElapsedTime();
    }

    @Override
    public void loop() {
        if (gamepad1.a && elapsedTime.time() > 0.2) {
            bottom_power += 0.1;
            bottom_motor.setPower(bottom_power);
            elapsedTime.reset();
        }
        if (gamepad1.b && elapsedTime.time() > 0.2) {
            top_power += 0.1;
            bottom_motor.setPower(top_power);
            elapsedTime.reset();
        }
    }
}
