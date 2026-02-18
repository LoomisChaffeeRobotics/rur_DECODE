package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.acmerobotics.dashboard.config.Config;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

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
@Config
public class Indexer {
//    TelemetryManager panelsTelemetry;
    public static double indexerP = 0.0002;
    public static double indexerF = 0.03;
    public static double indexerI = 0.000000;
    public static double indexerD = 0.00003;
    public LimeLightTurretSystem limelightclass;
    public ColorSensorAccuracyClass coloracc;
    public IMU imu;
    public float gain = 8.4F;
    public float[] hsvValues1 = new float[3];
    public double targetPosition=0;
    public DcMotor intake;
    public CRServo indexer;
    public NormalizedColorSensor colorSensor1;
    public NormalizedRGBA colors1;
    public TelemetryManager panlesTelem;
    public double curPose;
    public double error;
    public int unitsTraveled = 0;
    public double sum;
    public double prevPosition = 0;
    public double prevError = 0;
    public double prevRotationRate = 0;
    public double power;

    public Servo light = null;

    public boolean indexer_is_moving = false;
    ElapsedTime PIDTimer = new ElapsedTime();
    ///  I removed "currentColor" because it was useless. we only need one list.
    public List<SensedColor> SensedColorAll = new ArrayList<>(Arrays.asList(SensedColor.NEITHER, SensedColor.NEITHER, SensedColor.NEITHER));

    public int reset_times = 0;

    public enum SensedColor {
        PURPLE, GREEN, NEITHER
    }
    public void removefirst(List<SensedColor> l) {
//        panelsTelemetry = ftcTelemetry;
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

    public void indexerUpdate(){
        error = targetPosition - intake.getCurrentPosition();
        curPose = intake.getCurrentPosition();
        double absError = Math.abs(error);

        if ((targetPosition - prevPosition) * error < 0){
            panlesTelem.addLine("switch");
            sum = 0;
        }

        double product = indexerP * error;
        double derivative = (error - prevError)/PIDTimer.seconds();
        double rotationRate = imu.getRobotAngularVelocity(AngleUnit.DEGREES).yRotationRate;
//        double rotationAccel = (rotationRate - prevRotationRate)/PIDTimer.seconds(); - ????????? wut
//        if (absError< 100){
//            indexer.setPower(0);
//            sum = 0;
//        } else
        {
            sum += error * indexerI;
            if (Math.abs(sum) > 0.5) {sum = Math.signum(sum)*0.5;}

            power = Math.signum(error) * indexerF + product + sum + (indexerD * derivative);
//            power = Math.max(Math.min(power,indexerSpeedCap), -indexerSpeedCap);
            indexer.setPower(power);

        }
        prevPosition = intake.getCurrentPosition();
        prevError = error;
        prevRotationRate = rotationRate;
        PIDTimer.reset();

        indexer_is_moving = absError >= 100;


        panlesTelem.addData("product: ", indexerP);
        panlesTelem.addData("sum: ", indexerI);
        panlesTelem.addData("derivitive: ", indexerD);
        panlesTelem.addData("position: ", intake.getCurrentPosition());
        panlesTelem.addData("error: ", error);
        panlesTelem.addData("target: ", targetPosition);
        panlesTelem.addData("power: ", power);
        panlesTelem.update();


    }
    public void errorCalc(){
        error = targetPosition - intake.getCurrentPosition();
    }

    public void turn(boolean direction) { // true is right

        coloracc.reset();

//        reset_times++;

//        sensecolor();

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
            shift_list(SensedColorAll, !direction);

        } else {
            targetPosition -= 8192/3;
            shift_list(SensedColorAll, !direction);
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


    public boolean turnBasedOffColor(SensedColor color) { //PURPLE, GREEN, or NEITHER
        //the boolean returned = whether there exists a color
        //to rotate to.
        if (SensedColorAll.get(0) == color) {
            return true;
        }
        else if (SensedColorAll.get(1) == color) {
            turn(true); //same as turnRegardlessOfColor

            return true;
        } else if (SensedColorAll.get(2) == color) {
            turn(false);

            return true;
        }
        else {
            return false;
        }
    }
    public void init(HardwareMap hardwareMap, Telemetry telemetry) {
        limelightclass = new LimeLightTurretSystem();
        limelightclass.init(hardwareMap, telemetry);
        imu = hardwareMap.get(IMU.class, "imu2");
        coloracc = new ColorSensorAccuracyClass();

        indexer = hardwareMap.get(CRServo.class, "indexer");
        colorSensor1 = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");
        intake = hardwareMap.get(DcMotor.class, "intake");
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        colorSensor1.setGain(gain);
        colors1 = colorSensor1.getNormalizedColors();
        Color.colorToHSV(colors1.toColor(), hsvValues1);
        panlesTelem = PanelsTelemetry.INSTANCE.getTelemetry();
        PIDTimer.reset();

        light = hardwareMap.get(Servo.class,"light");

    }
    public SensedColor sensecolor() { //must be run at all times. Senses the color
        colors1 = colorSensor1.getNormalizedColors();

        colorSensor1.setGain(gain);
        Color.colorToHSV(colors1.toColor(), hsvValues1);
            if (hsvValues1[0] >= 151 && hsvValues1[0] <= 170) {

                if (SensedColorAll.get(0) != SensedColor.GREEN) {
                    coloracc.reset();
                    reset_times++;
                }

                SensedColorAll.set(0, SensedColor.GREEN);
                return SensedColor.GREEN;
            } else if (hsvValues1[0] >= 200 && hsvValues1[0] <= 290) { //MUST BE CHANGED ASAPPPPPPPPP // UPDATED 1/29 TO EMMA'S NUMBERS

                if (SensedColorAll.get(0) != SensedColor.PURPLE) {
                    coloracc.reset();
                    reset_times++;
                }

                SensedColorAll.set(0, SensedColor.PURPLE);


                return SensedColor.PURPLE;
            }
            else {

//                if (SensedColorAll.get(0) != SensedColor.NEITHER) {
//                    coloracc.reset();
//                }

                // CHECK TO MAKE SURE THE FOLLOWING WORKS:

                if (coloracc.emptyAccuracy > 0.8 && coloracc.accCount > 100) {
                    coloracc.reset();
                    reset_times++;
                    SensedColorAll.set(0, SensedColor.NEITHER);
                }

                return SensedColor.NEITHER;
            }

//            } else {
//                SensedColorAll.set(0, SensedColor.NEITHER);
//            }
//            else {
//                CurrentColor2 = SensedColor.NEITHER;
//            }
    }
//**
    public void spinIn(double power){ // I thought having 2 indexer motors would be bad but the probelm is still there.
        intake.setPower(-power);
    }

    public void updateIndicator(boolean turretGood){
        if (SensedColorAll.get(0) == SensedColor.PURPLE){
            if (turretGood && Math.abs(error) < 100){
                light.setPosition(0.722);
            } else {
                light.setPosition(0.666);
            }
        } else if(SensedColorAll.get(0) == SensedColor.GREEN){
            if (turretGood && Math.abs(error) < 100){
                light.setPosition(0.388);
            } else {
                light.setPosition(0.500);
            }
        } else {
            if (turretGood && Math.abs(error) < 100){
                light.setPosition(0.333);
            } else {
                light.setPosition(0);
            }
        }

    }
}