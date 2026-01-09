/* Copyright (c) 2017-2020 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//I've already moved all of the code into DriveClass - any changes that need to be made should be made there
//10-7-25
/*
 * This OpMode shows how to use a color sensor in a generic
 * way, regardless of which particular make or model of color sensor is used. The OpMode
 * assumes that the color sensor is configured with a name of "sensor_color".
 *
 * There will be some variation in the values measured depending on the specific sensor you are using.
 *
 * If the color sensor supports adjusting the gain, you can increase the gain (a multiplier to make
 * the sensor report higher values) by holding down the A button on the gamepad, and decrease the
 * gain by holding down the B button on the gamepad. The AndyMark Proximity & Color Sensor does not
 * support this.
 *
 * If the color sensor has a light which is controllable from software, you can use the X button on
 * the gamepad to toggle the light on and off. The REV sensors don't support this, but instead have
 * a physical switch on them to turn the light on and off, beginning with REV Color Sensor V2. The
 * AndyMark Proximity & Color Sensor does not support this.
 *
 * If the color sensor also supports short-range distance measurements (usually via an infrared
 * proximity sensor), the reported distance will be written to telemetry. As of September 2025,
 * the only color sensors that support this are the ones from REV Robotics and the AndyMark
 * Proximity & Color Sensor. These infrared proximity sensor measurements are only useful at very
 * small distances, and are sensitive to ambient light and surface reflectivity. You should use a
 * different sensor if you need precise distance measurements.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

public class Indexer {
    float gain = 31;
    float[] hsvValues1 = new float[3];
    int canTurn = 0;
    DcMotor encoder;
    ElapsedTime timer;
    CRServo indexer;
    NormalizedColorSensor colorSensor1;
    public SensedColor CurrentColor = SensedColor.NEITHER;
    public SensedColor CurrentColor2 = SensedColor.NEITHER;
    public SensedColor CurrentColor3 = SensedColor.NEITHER;
    public List<SensedColor> SensedColorAll = new ArrayList<>(Arrays.asList(CurrentColor, CurrentColor2, CurrentColor3));
//    NormalizedColorSensor colorSensor2;
//    NormalizedColorSensor colorSensor3;

    View relativeLayout;
    public enum SensedColor {
        PURPLE, GREEN, NEITHER
    }
//    public enum SensedColor2 {
//        PURPLE, GREEN, NEITHER;
//    }
//    public enum SensedColor3 {
//        PURPLE, GREEN, NEITHER;
//    }
    public void waitAuto(double seconds) {
        timer.reset();
        while (timer.seconds() < seconds ) {}

    }
    public List<SensedColor> removefirst(List<SensedColor> l) {
        l.set(0, SensedColor.NEITHER);
        return l;
    }
    public List<SensedColor> shift_list(List<SensedColor> l, boolean direction) { //true is right, must be same as turn()

        SensedColor element_0 = l.get(0);
        SensedColor element_1 = l.get(1);
        SensedColor element_2 = l.get(2);

        if (direction) { //everything direction-wise is dep[endent on this

            l.set(1, element_0);
            l.set(2, element_1);
            l.set(0, element_2);
        }
        else {
            l.set(1, element_2);
            l.set(2, element_0);
            l.set(0, element_1);
        }
        SensedColorAll = l;
        return l;
    }
    public void turn(boolean direction) { // true is right

//        if (Math.abs(encoder.getCurrentPosition()) < 0.67) {
////            return true;
//        }
//
        if (direction) {
//                if (encoder.getCurrentPosition()  < 120) {
//                    indexer.setPower(0.67);
//                }
//                else {
                    canTurn = 0;
                    indexer.setPower(0);
//                    encoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    shift_list(SensedColorAll, direction);
//                }


        } else {
//                if (encoder.getCurrentPosition() > -120) {
//                    indexer.setPower(-0.67);
//                }
//                else {
                    canTurn = 0;
                    indexer.setPower(0);
//                    encoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    shift_list(SensedColorAll, direction);
//                }
        }

    }
    public boolean turnRegardlessofColor() {
        if (CurrentColor != SensedColor.NEITHER) {
            turn(true); //idk what way this is bro
            return true;
        } else if (CurrentColor2 != SensedColor.NEITHER) {
            turn(true); //turn to slot 2 - change if needed
            return true;
        } else if (CurrentColor3 != SensedColor.NEITHER) {
            turn(false); //turn to slot 3 - change if needed
            return true;
        } else return false; //empty
    }
    public boolean turnBasedOfColor(SensedColor color) { //PURPLE, GREEN, or NEITHER
        //the boolean returned = whether there exists a color
        //to rotate to.
        if (CurrentColor == color) {
            return true;
        }
        else if (CurrentColor2 == color) {
            turn(true); //same as turnRegardlessOfColor
            return true;
        } else if (CurrentColor3 == color) {
            turn(false);
            return true;
        }
        else {
            return false;
        }
    }
    public void init(HardwareMap hardwareMap, Telemetry telemetry) {
        timer = new ElapsedTime();
        timer.reset();
        indexer = hardwareMap.get(CRServo.class, "indexer");
        colorSensor1 = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");
        encoder = hardwareMap.get(DcMotor.class, "encoder");
        SensedColorAll = shift_list(SensedColorAll, true);

        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId); //idk if these two lines are needed

        telemetry.addData("Gain", gain);
        colorSensor1.setGain(gain);
//            colorSensor2.setGain(gain);
//            colorSensor3.setGain(gain);
        NormalizedRGBA colors1 = colorSensor1.getNormalizedColors();
        Color.colorToHSV(colors1.toColor(), hsvValues1);
    }
    public void sensecolor() { //must be run at all times
//<<<<<<< HEADS
        if (canTurn != 1) {
            canTurn = 0;
        }
        if (encoder.getCurrentPosition() < 60 && canTurn == 0) {


            indexer.setPower(0.5);

            if (hsvValues1[0] >= 90 && hsvValues1[0] <= 180) {
                CurrentColor = SensedColor.GREEN;
            } else if (hsvValues1[0] >= 270 && hsvValues1[0] <= 330) {
                CurrentColor = SensedColor.PURPLE;
            } else {
                CurrentColor = SensedColor.NEITHER;
            }
        } else if (canTurn == 0) {
            canTurn = 1;
        }

//114 = tick per tick


        if (encoder.getCurrentPosition() > 0 && canTurn == 1) {
            indexer.setPower(-0.5);
        }
        else if (canTurn == 1) {
            canTurn = 2;
        }
        SensedColorAll = (Arrays.asList(CurrentColor, CurrentColor2, CurrentColor3));
    }
}

