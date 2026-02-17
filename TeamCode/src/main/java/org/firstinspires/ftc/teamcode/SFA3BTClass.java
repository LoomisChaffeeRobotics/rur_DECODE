package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;

import java.util.List;
import java.util.Objects;
import java.util.Vector;

@TeleOp

@Config
public class SFA3BTClass extends OpMode {
    /** SPARK FUN AS AN APRILTAG BACKUP TESTING CLASS */
    public static double SFKf = 0.00;
    public static double SFKp = 0.02;
    public static double SFKi = 0;
    public static double SFKd = 0.0;
    public static double TXKf = 0;
    public static double TXKp = 0.05;
    public static double TXKi = 0;
    public static double TXKd = 0.0002;
    FtcDashboard dash = FtcDashboard.getInstance();
    Telemetry t2 = dash.getTelemetry();

    LimeLightTurretSystem limelight;

    @Override
    public void init() {
        limelight = new LimeLightTurretSystem();
        limelight.init(hardwareMap,telemetry);
    }


    @Override
    public void loop() {

        limelight.update(true);
        limelight.turretControl.setCoefficients(new PIDFCoefficients(SFKp, SFKi, SFKd, SFKf));
        limelight.turretFineControl.setCoefficients(new PIDFCoefficients(TXKp, TXKi, TXKd, TXKf));
        limelight.turntoAT(true);

        t2.addData("encoder postiton", limelight.encoder.getCurrentPosition());
        t2.addData("turret power", limelight.turretSpin.getPower());
        t2.addData("target thihng", 0);
        t2.addData("error", limelight.turretFineControl.getError());
        t2.addData("current thing", limelight.limelight.getLatestResult().getTx());
        t2.addData("turret pos", limelight.turretPosition);
        t2.addData("heading", -limelight.roboPoseRelativeToAT.h - Math.toDegrees(Math.atan(limelight.roboPoseRelativeToAT.x/limelight.roboPoseRelativeToAT.y)));
        telemetry.addData("atseen", limelight.ATSeen);
        t2.update();

    }
}
