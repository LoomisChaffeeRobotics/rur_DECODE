package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp

public class servoWork extends OpMode {
    CRServo sv;
    @Override
    public void init() {
        sv = hardwareMap.get(CRServo.class, "sv");


    }

    @Override
    public void loop() {
        if (gamepad1.dpad_up) {
            sv.setPower(1);

        }
        else if (gamepad1.dpad_down) {
            sv.setPower(-1);
        }
        else{
            sv.setPower(0);
        }



    }
}
