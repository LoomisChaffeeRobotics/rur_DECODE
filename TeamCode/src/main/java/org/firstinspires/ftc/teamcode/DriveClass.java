package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@TeleOp
public class DriveClass extends OpMode {
    NormalizedColorSensor colorSensor;
    float gain;
    final float[] hsvValues = new float[3];
    View relativeLayout;
    boolean xButtonCurrentlyPressed = gamepad1.x;
    boolean xButtonPreviouslyPressed;
    public enum SensedColor {
        PURPLE, GREEN, NEITHER
    }
    SensedColor CurrentColor = SensedColor.NEITHER;

    DcMotorEx launcher2;
    double MotorPower = 0.5;

    DcMotorEx launcher;
    DcMotorEx left_front;
    DcMotorEx right_front;
    DcMotorEx left_back;
    DcMotorEx right_back;

    IMU imu;
    double Yaw;
    double Pitch;
    double Roll;
    YawPitchRollAngles robotOrientation;

    double left_front_velocity = 0;
    double right_front_velocity = 0;
    double left_back_velocity = 0;
    double right_back_velocity = 0;


    double x;
    double y;
    double x_altered;
    double y_altered;
    public double targetangle = 165;
    public Limelight3A limelight;
    public Pose3D botpose;
    public double botposeangle;
    public double angleerror = targetangle - botposeangle;
    CRServo sv;
    @Override
    public void init() {
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        relativeLayout.post(() -> relativeLayout.setBackgroundColor(Color.WHITE));
        gain = 31;
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");

        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        launcher2 = hardwareMap.get(DcMotorEx.class, "launcher2");

        left_front = hardwareMap.get(DcMotorEx.class, "leftFront");
        right_front = hardwareMap.get(DcMotorEx.class, "rightFront");
        left_back = hardwareMap.get(DcMotorEx.class, "leftBack");
        right_back = hardwareMap.get(DcMotorEx.class, "rightBack");
        left_front.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        right_front.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        left_back.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        right_back.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        right_front.setDirection(DcMotorSimple.Direction.REVERSE);
        right_back.setDirection(DcMotorSimple.Direction.REVERSE);
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters myIMUparameter;
        myIMUparameter = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        );
        imu.initialize(myIMUparameter);
        robotOrientation = imu.getRobotYawPitchRollAngles();
        imu.resetYaw();
        Yaw = robotOrientation.getYaw();
        Pitch = robotOrientation.getPitch();
        Roll = robotOrientation.getRoll(); //field-centric setup

        sv = hardwareMap.get(CRServo.class, "sv");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        telemetry.setMsTransmissionInterval(11);
        limelight.pipelineSwitch(0);
        limelight.start();
    }

    @Override
    public void loop() {
        telemetry.addLine("Hold the A button on gamepad 1 to increase gain, or B to decrease it.\n");
        telemetry.addLine("Higher gain values mean that the sensor will report larger numbers for Red, Green, and Blue, and Value\n");

        // Update the gain value if either of the A or B gamepad buttons is being held
        if (gamepad1.a) {
            // Only increase the gain by a small amount, since this loop will occur multiple times per second.
            gain += 0.05F;
        } else if (gamepad1.b && gain > 1) { // A gain of less than 1 will make the values smaller, which is not helpful.
            gain -= 0.05F;
        }

        // Show the gain value via telemetry
        telemetry.addData("Gain", gain);

        // Tell the sensor our desired gain value (normally you would do this during initialization,
        // not during the loop)
        colorSensor.setGain(gain);

        // Check the status of the X button on the gamepad
        xButtonCurrentlyPressed = gamepad1.x;

        // If the button state is different than what it was, then act
        if (xButtonCurrentlyPressed != xButtonPreviouslyPressed) {
            // If the button is (now) down, then toggle the light
            if (xButtonCurrentlyPressed) {
                if (colorSensor instanceof SwitchableLight) {
                    SwitchableLight light = (SwitchableLight)colorSensor;
                    light.enableLight(!light.isLightOn());
                }
            }
            xButtonPreviouslyPressed = xButtonCurrentlyPressed;
        }


        // Get the normalized colors from the sensor
        NormalizedRGBA colors = colorSensor.getNormalizedColors();

        /* Use telemetry to display feedback on the driver station. We show the red, green, and blue
         * normalized values from the sensor (in the range of 0 to 1), as well as the equivalent
         * HSV (hue, saturation and value) values. See http://web.archive.org/web/20190311170843/https://infohost.nmt.edu/tcc/help/pubs/colortheory/web/hsv.html
         * for an explanation of HSV color. */

        // Update the hsvValues array by passing it to Color.colorToHSV()
        Color.colorToHSV(colors.toColor(), hsvValues);

//            telemetry.addLine()
//                    .addData("Red", "%.3f", colors.red)
//                    .addData("Green", "%.3f", colors.green)
//                    .addData("Blue", "%.3f", colors.blue);
        telemetry.addLine()
                .addData("Hue", "%.3f", hsvValues[0]);
//                    .addData("Saturation", "%.3f", hsvValues[1])
//                    .addData("Value", "%.3f", hsvValues[2]);
//            telemetry.addData("Alpha", "%.3f", colors.alpha);
        if (hsvValues[0] >= 100 && hsvValues[0] <= 180) {
            CurrentColor = SensedColor.GREEN;
        } else if (hsvValues[0] >= 270 && hsvValues[0] <= 330) {
            CurrentColor = SensedColor.PURPLE;
        } else {
            CurrentColor = SensedColor.NEITHER;
        }

        /* If this color sensor also has a distance sensor, display the measured distance.
         * Note that the reported distance is only useful at very close range, and is impacted by
         * ambient light and surface reflectivity. */
        if (colorSensor instanceof DistanceSensor) {
            telemetry.addData("Distance (cm)", "%.3f", ((DistanceSensor) colorSensor).getDistance(DistanceUnit.CM));
        }
        telemetry.addData("CurrentColor", CurrentColor);
        telemetry.update();

        // Change the Robot Controller's background color to match the color detected by the color sensor.
        relativeLayout.post(() -> relativeLayout.setBackgroundColor(Color.HSVToColor(hsvValues))); //color sensor running thing
        if(gamepad1.a) {
            launcher.setVelocity(200);
            launcher2.setVelocity(200);
        }
        else if(gamepad1.y) {
            launcher.setVelocity(-200);
            launcher2.setVelocity(-200);

        }
        else {
            launcher.setVelocity(0);
            launcher2.setVelocity(0);
        } //launcher thing
        angleerror = targetangle - botposeangle;
        if (angleerror < -1 || angleerror > 180) {
            sv.setPower(-0.1);
        } else if (angleerror > 1 && angleerror < 165) {
            sv.setPower(0.1);
        } else if (angleerror < 1 && angleerror > -1){
            sv.setPower(0);
        }
        if (gamepad1.right_bumper) {
            sv.setPower(0);
        } // thtey wouldh ave to hold down the button - might not be necessary if a better solution is found or something
        telemetry.addData("angle error", angleerror);
        telemetry.addData("power",sv.getPower());
        telemetry.addData("angle", botposeangle);
        LLResult result = limelight.getLatestResult();
        // Access general information
        botpose = result.getBotpose();
        botposeangle = botpose.getOrientation().getYaw(AngleUnit.DEGREES);
        telemetry.addData("Botpose", botpose.toString());
        telemetry.addData("botposeangle", botposeangle);
        robotOrientation = imu.getRobotYawPitchRollAngles();
        Yaw = robotOrientation.getYaw();
        Pitch = robotOrientation.getPitch();
        Roll = robotOrientation.getRoll(); //limelight turret function
        x = gamepad1.left_stick_x;
        y = gamepad1.left_stick_y;

        x_altered = (x * Math.cos(Math.toRadians(Yaw))) - (y * Math.sin(Math.toRadians(Yaw)));
        y_altered = (y * Math.cos(Math.toRadians(Yaw))) + (x * Math.sin(Math.toRadians(Yaw)));

        right_front_velocity = y_altered + x_altered;
        left_front_velocity = y_altered - x_altered;

        left_back_velocity = y_altered + x_altered;
        right_back_velocity = y_altered - x_altered;

        //rotation

        right_front_velocity += gamepad1.right_stick_x;
        right_back_velocity += gamepad1.right_stick_x;

        left_front_velocity += -gamepad1.right_stick_x;
        left_back_velocity += -gamepad1.right_stick_x;


        right_front.setPower(right_front_velocity);
        right_back.setPower(right_back_velocity);
        left_front.setPower(left_front_velocity);
        left_back.setPower(left_back_velocity);

        if (gamepad1.start) {
            imu.resetYaw();
        }

        telemetry.addData("Yaw", robotOrientation.getYaw());
        telemetry.update(); //field centric operation field centric operation



    }



}
