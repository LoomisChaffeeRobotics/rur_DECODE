package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class servoWork{
    CRServo sv;
    public void init(HardwareMap hardwareMap) {
        sv = hardwareMap.get(CRServo.class, "sv");


    }
    public void function() {
        //do something
    }
}
