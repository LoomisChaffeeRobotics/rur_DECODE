package org.firstinspires.ftc.teamcode.testingClasses;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class motortesting extends OpMode {
    DcMotorEx launcher;
    double power;
    @Override
    public void init() {
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");


    }
    @Override
    public void loop() {
        if (gamepad1.aWasPressed()) {
            power += 0.001;
            launcher.setPower(power);

        }
        else if (gamepad1.yWasPressed()) {
            power -= 0.001;
            launcher.setPower(power);
        }
        telemetry.addData("power", power);
        telemetry.update();
    }

}
