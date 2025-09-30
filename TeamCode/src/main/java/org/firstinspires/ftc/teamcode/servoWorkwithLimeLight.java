package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.LimeLightTesting.botposeangle;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp
public class servoWorkwithLimeLight extends OpMode {
    public double currentangle;
    public double targetangle = 180;
    CRServo sv;

    public void init() {
        LimeLightTesting ll;
        sv = hardwareMap.get(CRServo.class, "sv");
        currentangle = botposeangle;

    }

    public void loop() {
        if (botposeangle > targetangle) {
            sv.setPower(0.1);
        } else if (botposeangle < targetangle) {
            sv.setPower(-0.1);
        } else {
            sv.setPower(0);
        }
    }
}
