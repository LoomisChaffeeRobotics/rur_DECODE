package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
@TeleOp
public class TurretTurningPIDFClass extends OpMode { // alliteration
    public static double Kf = 0;
    public static double Kp = 0.1;
    public static double Ki = 0;
    public static double Kd = 0.01;
    FtcDashboard dash = FtcDashboard.getInstance();
    Telemetry t2 = dash.getTelemetry();

    PIDFController turretControl;
    DcMotorEx encoder;
    CRServo turretSpin;
    double targetRotation = 35;

    @Override
    public void init() {
        turretControl = new PIDFController(new PIDFCoefficients(Kp, Ki, Kd, Kf));
        turretControl.setTargetPosition(targetRotation);
        turretSpin = hardwareMap.get(CRServo.class, "turretSpin");
        encoder = hardwareMap.get(DcMotorEx.class, "encoder");
        encoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void loop() {
        turretControl.setCoefficients(new PIDFCoefficients(Kp, Ki, Kd, Kf));

        double position = (double) encoder.getCurrentPosition() / 114.0;
        turretControl.updatePosition(position);
        turretControl.setTargetPosition(targetRotation);
        turretSpin.setPower(-turretControl.run());
        if (gamepad1.a){
            targetRotation *= -1; //flips it

        }
        t2.addData("encoder postiton", encoder.getCurrentPosition() / 114.0);
        t2.addData("turret power", turretSpin.getPower());
        t2.addData("target thihng", targetRotation);
        t2.addData("error", turretControl.getError());
        t2.addData("current thing", encoder.getCurrentPosition() / 114.0);
        t2.update();

    }

}
