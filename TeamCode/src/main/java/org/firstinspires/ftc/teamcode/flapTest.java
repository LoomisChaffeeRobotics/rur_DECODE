package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class flapTest extends OpMode {
    CRServo indexer;
    Servo flap;

    @Override
    public void init() {
        indexer = hardwareMap.get(CRServo.class, "indexer");
        flap = hardwareMap.get(Servo.class, "flap");
    }

    @Override
    public void loop() {
        if (gamepad1.dpad_down) {
            flap.setPosition(0);
        } else if (gamepad1.dpad_up) {
            flap.setPosition(0.5);
        }
        if (gamepad1.a) {
            indexer.setPower(0.5);
        } else if (gamepad1.x) {
            indexer.setPower(-0.5);
        } else {
            indexer.setPower(0);
        }
    }
}