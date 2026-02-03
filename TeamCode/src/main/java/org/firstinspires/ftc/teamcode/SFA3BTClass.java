package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

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
    }


    @Override
    public void loop() {
        boolean ATSeen = false;
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
            roboPoseRelativeToAT = new SparkFunOTOS.Pose2D(ATSeenRoboPose.getX(DistanceUnit.INCH), ATSeenRoboPose.getY(DistanceUnit.INCH), ATSeenRoboPose.getHeading(AngleUnit.DEGREES) +90);
            sparkfun.myOtos.setPosition(roboPoseRelativeToAT); // the problem could be due to the turning
        }
        else {
            roboPoseRelativeToAT = sparkfun.myOtos.getPosition();
        }

        telemetry.addData("x",roboPoseRelativeToAT.x);
        telemetry.addData("y",roboPoseRelativeToAT.y);
        telemetry.addData("h",roboPoseRelativeToAT.h);

        if (!ATSeen){
            telemetry.addLine("NO AT SEEN");
        }
    }
}
