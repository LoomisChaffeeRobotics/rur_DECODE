package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Config
@TeleOp
public class TurretTurningPIDFClass extends OpMode { // alliteration
    public static double Kf = 0;
    public static double Kp = 0 ;
    public static double Ki = 0;
    public static double Kd = 0;

    PIDFController turretControl;
    DcMotorEx encoder;
    CRServo turretSpin;
    double targetRotation = 50;
    double currentRotation;

    @Override
    public void init() {
        turretControl = new PIDFController(new PIDFCoefficients(Kf, Kp, Ki, Kd));
        turretControl.setTargetPosition(targetRotation);
    }

    @Override
    public void loop() {
        turretControl.setCoefficients(new PIDFCoefficients(Kf, Kp, Ki, Kd));
        double position = encoder.getCurrentPosition() * 114;
        turretControl.updatePosition(position);
        turretSpin.setPower(turretControl.run());

        if (gamepad1.a){
            targetRotation *= -1; //flips it

        }

    }

}
