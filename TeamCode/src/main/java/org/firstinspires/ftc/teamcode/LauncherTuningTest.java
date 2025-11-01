package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class LauncherTuningTest extends OpMode {

    DcMotorEx bottom_motor;
    DcMotorEx top_motor;
    double bottom_power = 0;
    double top_power = 0;

    ElapsedTime elapsedTime;

    @Override
    public void init() {
        bottom_motor = hardwareMap.get(DcMotorEx.class, "launcher1"); // port 2
        top_motor = hardwareMap.get(DcMotorEx.class, "launcher2"); // port 0
        elapsedTime = new ElapsedTime();
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up && elapsedTime.time() > 0.2) {
            bottom_power += 0.1;
            bottom_motor.setPower(bottom_power);
            elapsedTime.reset();
        }
        if (gamepad1.dpad_down && elapsedTime.time() > 0.2) {
            bottom_power -= 0.1;
            bottom_motor.setPower(top_power);
            elapsedTime.reset();
        }
        if (gamepad1.y && elapsedTime.time() > 0.2) {
            top_power += 0.1;
            top_motor.setPower(bottom_power);
            elapsedTime.reset();
        }
        if (gamepad1.a && elapsedTime.time() > 0.2) {
            top_power -= 0.1;
            top_motor.setPower(top_power);
            elapsedTime.reset();
        }

        telemetry.addData("top: ",top_motor.getVelocity());
        telemetry.addData("bottom: ",bottom_motor.getVelocity());
        telemetry.update();
    }
}
