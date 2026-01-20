package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import org.opencv.core.Mat;

@TeleOp
public class colorSensorTestingApple extends OpMode {
    DcMotor intake;
    NormalizedColorSensor sensor;
    int hsvvalue;
    float[] hsv = new float[3];
    float gain = 31;

    @Override
    public void init() {
        intake = hardwareMap.get(DcMotor.class, "intake");
        sensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");

        sensor.setGain(gain);
    }

    @Override
    public void loop() {
        if (gamepad1.x) {
            intake.setPower(-1);
        } else {
            intake.setPower(0);
        }
        if (gamepad1.dpad_up) {
            gain += 0.1;
        } else if (gamepad1.dpad_down) {
            gain -= 0.1;
        }
        sensor.setGain(gain);
        Color.colorToHSV(sensor.getNormalizedColors().toColor(), hsv);
        if (hsv[0] >= 157 && hsv[0] <= 167) {
            telemetry.addLine("GREEN");
        } else if (hsv[0] >= 238 && hsv[0] <= 242) {
            telemetry.addLine("PUJPEL");
        }
        telemetry.addData("hue", hsv[0]);
        telemetry.update();

    }

}


/*
colorSensorTestingApple
@Autonomous
public class
 */