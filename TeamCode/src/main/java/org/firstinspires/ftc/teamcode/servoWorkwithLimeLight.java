package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp
public class servoWorkwithLimeLight extends OpMode {
    public double currentangle;
    public double targetangle = 135;
    public double angleerror = targetangle - currentangle;


    CRServo sv;
    LimeLightTesting ll;
    public void init() {
        sv = hardwareMap.get(CRServo.class, "sv");
        ll = new LimeLightTesting();

    }

    public void loop() {
        if (angleerror < 180) {
            sv.setPower(0.1);
        } else if (angleerror > 180) {
            sv.setPower(-0.1);
        } else {
            sv.setPower(0);
        }
        currentangle = ll.botposeangle;
        telemetry.addData("angle", ll.botposeangle);
        telemetry.addData("power",sv.getPower());
        telemetry.update();

    }
}

