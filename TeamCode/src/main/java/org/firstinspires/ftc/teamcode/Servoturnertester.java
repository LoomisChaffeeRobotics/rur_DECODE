package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp
public class Servoturnertester extends OpMode {
    Servo supercoolservo;
    double number = 0d;

    @Override
    public void init() {
        supercoolservo = hardwareMap.get(Servo.class, "servo");
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_down){number = number + .01;}
        else if (gamepad1.dpad_up){number = number -.01;}

supercoolservo.setPosition(number);


telemetry.addData("position: ",
        supercoolservo.getPosition());
telemetry.update();

    }
}
