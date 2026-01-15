package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.ftc.localization.Encoder;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.security.KeyStore;
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
@Configurable
public class Indexer {

    public double error;
    float gain = 31;
    float[] hsvValues1 = new float[3];
    int canTurn = 0;
    double targetPosition=0;
    DcMotor intake;
    CRServo indexer;
    NormalizedColorSensor colorSensor1;
    public NormalizedRGBA colors1;
    public int unitsTraveled = 0;
    ///  I removed "currentColor" because it was useless. we only need one list.
    public List<SensedColor> SensedColorAll = new ArrayList<>(Arrays.asList(SensedColor.NEITHER, SensedColor.NEITHER, SensedColor.NEITHER));
    public enum SensedColor {
        PURPLE, GREEN, NEITHER
    }
    public void removefirst(List<SensedColor> l) {
        l.set(0, SensedColor.NEITHER);
        SensedColorAll = l;
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
    public void indexerUpdate(double indexerC, double indexerP){
        error = targetPosition - intake.getCurrentPosition();
        if (Math.abs(error) <0){
            indexer.setPower(0);
            return;
        }
        indexer.setPower(Math.signum(error)* indexerC + indexerP * error);
    }
    public void turn(boolean direction) { // true is right
//        if (canTurn == 0) {
//            return;
//        }
//
//        if (canTurn == 2) {
////            origin = intake.getCurrentPosition();
//            unitsTraveled = 0;
//            canTurn = 3;
//        }

//        distTravelled
//        if (Math.abs(encoder.getCurrentPosition()) < 0.67) {
//            return true;
//        }


        if (direction) {
            targetPosition += 8192/3;
            shift_list(SensedColorAll, direction);

        } else {
            targetPosition -= 8192/3;
            shift_list(SensedColorAll, direction);
        }
//        if (Math.abs((intake.getCurrentPosition() % 8192/3) - (8192/3)) > 50 || unitsTraveled < 30) {
//            unitsTraveled++;
//            indexer.setPower((direction ? 1 : -1) * 0.27);
//        }
//        else {
//            canTurn = 0;
//            indexer.setPower(0);
//
//
//        }
//
//        if (direction) {
//
//
//
//        } else {
//                if (intake.getCurrentPosition() % 8192/3 > -2730) {
//                    indexer.setPower(-0.27);
//                }
//                else {
//                    canTurn = 0;
//                    indexer.setPower(0);
//                    shift_list(SensedColorAll, direction);
//                }
//        }

    }
    public boolean turnRegardlessofColor() {
        if (SensedColorAll.get(0) != SensedColor.NEITHER) {
            turn(true); //idk what way this is bro
            return true;
        } else if (SensedColorAll.get(1) != SensedColor.NEITHER) {
            turn(true); //turn to slot 2 - change if needed
            return true;
        } else if (SensedColorAll.get(2) != SensedColor.NEITHER) {
            turn(false); //turn to slot 3 - change if needed
            return true;
        } else return false; //empty
    }
    public boolean turnBasedOfColor(SensedColor color) { //PURPLE, GREEN, or NEITHER
        //the boolean returned = whether there exists a color
        //to rotate to.
        if (SensedColorAll.get(0) == color) {
            return true;
        }
        else if (SensedColorAll.get(1) == color) {
            turn(false); //same as turnRegardlessOfColor
            shift_list(SensedColorAll,false);
            return true;
        } else if (SensedColorAll.get(2) == color) {
            turn(true);
            shift_list(SensedColorAll,true);
            return true;
        }
        else {
            return false;
        }
    }
    public void init(HardwareMap hardwareMap, Telemetry telemetry) {

        indexer = hardwareMap.get(CRServo.class, "indexer");
        colorSensor1 = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");
        intake = hardwareMap.get(DcMotor.class, "intake");
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telemetry.addData("Gain", gain);
        colorSensor1.setGain(gain);
        colors1 = colorSensor1.getNormalizedColors();
        Color.colorToHSV(colors1.toColor(), hsvValues1);

    }
    public void sensecolor() { //must be run at all times
        if (canTurn == 2) {
            return;
        }
//        if (canTurn != 1) {
//            canTurn = 0;
//        }
            if (hsvValues1[0] >= 163 && hsvValues1[0] <= 167) {
                SensedColorAll.set(0, SensedColor.GREEN);
            } else if (hsvValues1[0] >= 210 && hsvValues1[0] <= 230) {
                SensedColorAll.set(0, SensedColor.PURPLE);
            }
//            else {
//                CurrentColor2 = SensedColor.NEITHER;
//            }
//        } else if (canTurn == 0) {
//            canTurn = 1;
//        }
        canTurn = 2;

//        else if (canTurn == 1) {
//            canTurn = 2;
//        }
    }
}