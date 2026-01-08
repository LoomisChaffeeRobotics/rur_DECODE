package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import org.opencv.core.Mat;

@TeleOp
public class colorSensorTestingApple extends OpMode {

    ColorSensor sensor;

    float[] hsv = new float[3];

    @Override
    public void init() {
        sensor = hardwareMap.get(ColorSensor.class, "sensor_color");
    }

    @Override
    public void loop() {
        Color.colorToHSV(sensor.argb(),hsv);
        telemetry.addData("hue", hsv[0]);
        telemetry.update();
    }

}


/*
colorSensorTestingApple
@Autonomous
public class
 */