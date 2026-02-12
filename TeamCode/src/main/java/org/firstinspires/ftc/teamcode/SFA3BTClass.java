package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
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
public class SFA3BTClass extends OpMode {
    /** SPARK FUN AS AN APRILTAG BACKUP TESTING CLASS */
    public static double Kf = 0;
    public static double Kp = 0.1;
    public static double Ki = 0;
    public static double Kd = 0.01;
    FtcDashboard dash = FtcDashboard.getInstance();
    Telemetry t2 = dash.getTelemetry();
    CRServo turretSpin;
    double targetRotation = 0.0;

    PIDFController turretControl;
    sparkFunMethodsClass sparkfun;
    LimeLightTurretSystem limelight;
    String teamColor = "blue";
    int desiredID = 0;
    SparkFunOTOS.Pose2D roboPoseRelativeToAT;
    DcMotorEx encoder;


    @Override
    public void init() {
        limelight = new LimeLightTurretSystem();
        limelight.init(hardwareMap,telemetry);
        sparkfun = new sparkFunMethodsClass();
        sparkfun.init(hardwareMap,telemetry);
        encoder = hardwareMap.get(DcMotorEx.class, "encoder");
        roboPoseRelativeToAT = new SparkFunOTOS.Pose2D(0,0,0);

        turretControl = new PIDFController(new PIDFCoefficients(Kp, Ki, Kd, Kf));
        turretControl.setTargetPosition(targetRotation);
        turretSpin = hardwareMap.get(CRServo.class, "turretSpin");
        encoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }


    @Override
    public void loop() {
        boolean ATSeen = false;
        double turretPosition = encoder.getCurrentPosition() * -0.00877192982456;
        ATSeen = limelight.update();


        if (gamepad1.a && Objects.equals(teamColor, "none")){ // Objects.equals(String, String) just sees if theyre equal
            teamColor = "red";
            desiredID = 24;
        }
        if (gamepad1.b && Objects.equals(teamColor, "none")){
            teamColor = "blue";
            desiredID = 20;
        }


        if(ATSeen){
            Pose2D ATSeenRoboPose = limelight.getPositionCenterRelative(Objects.equals(teamColor, "blue"));
            roboPoseRelativeToAT = new SparkFunOTOS.Pose2D(ATSeenRoboPose.getX(DistanceUnit.INCH), ATSeenRoboPose.getY(DistanceUnit.INCH), ATSeenRoboPose.getHeading(AngleUnit.DEGREES) - turretPosition);
            sparkfun.myOtos.setPosition(roboPoseRelativeToAT); // the problem could be due to the turning
        }
        else {
            roboPoseRelativeToAT = sparkfun.myOtos.getPosition();
        }

        telemetry.addData("x",roboPoseRelativeToAT.x);
        telemetry.addData("y",roboPoseRelativeToAT.y);
        telemetry.addData("h",roboPoseRelativeToAT.h);
        telemetry.addData("spin", turretPosition);

        if (!ATSeen){
            telemetry.addLine("NO AT SEEN");
        }

        turretControl.setCoefficients(new PIDFCoefficients(Kp, Ki, Kd, Kf));

        turretControl.updatePosition(turretPosition);
        if (Math.abs(-roboPoseRelativeToAT.h) < 30 ) {turretControl.setTargetPosition(-roboPoseRelativeToAT.h);}
        turretSpin.setPower(turretControl.run());

        t2.addData("encoder postiton", turretPosition);
        t2.addData("turret power", turretSpin.getPower());
        t2.addData("target thihng", -roboPoseRelativeToAT.h);
        t2.addData("error", turretControl.getError());
        t2.addData("current thing", turretPosition);
        t2.update();

    }
}
