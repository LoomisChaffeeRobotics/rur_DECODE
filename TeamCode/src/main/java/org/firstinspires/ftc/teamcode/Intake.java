package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class Intake extends OpMode {

    DcMotor intake_motor;

    @Override
    public void init() {
        intake_motor = hardwareMap.get(DcMotor.class, "intake");
    }

    @Override
    public void loop() {
        intake_motor.setPower(gamepad1.left_stick_y);
        telemetry.addData("power: ", intake_motor.getPower());

        if(gamepad1.right_trigger > 0.){}

    }
}
