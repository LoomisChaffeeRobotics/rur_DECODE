package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class RGBIndicatorTesting extends OpMode {

    Servo light = null;

    @Override
    public void init(){
        light = hardwareMap.get(Servo.class,"light");
    }
    @Override
    public void loop(){
        if (gamepad1.a) {
            light.setPosition(0.722);
        } else if (gamepad1.b) {
            light.setPosition(0.500);
        } else {
            light.setPosition(0);
        }
    }

}

//Color Guide:
//Black – 0
//Red – 0.279
//Orange – 0.333
//Gold – 0.357
//Yellow – 0.388
//Sage – 0.444
//Green – 0.500
//Azure – 0.555
//Blue – 0.611
//Indigo – 0.666
//Violet – 0.722
//White – 1