package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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
    String teamColor = "none";
    int desiredID = 0;
    SparkFunOTOS.Pose2D roboPoseRelativeToAT;


    @Override
    public void init() {
        limelight = new LimeLightTurretSystem();
        limelight.init(hardwareMap,telemetry);
        sparkfun = new sparkFunMethodsClass();
        sparkfun.init(hardwareMap,telemetry);
    }


    @Override
    public void loop() {
        boolean ATSeen = false;
        limelight.update();

        if (gamepad1.a && Objects.equals(teamColor, "none")){ // Objects.equals(String, String) just sees if theyre equal
            teamColor = "red";
            desiredID = 24;
        }
        if (gamepad1.b && Objects.equals(teamColor, "none")){
            teamColor = "blue";
            desiredID = 20;
        }

///     commented out this section because it uses 'rsult' no longer a class variable
        //makes the results list
//        List<LLResultTypes.FiducialResult> results = limelight.result.getFiducialResults();


//        if (!limelight.result.getFiducialResults().isEmpty()){
//            ATSeen = true;
//        }

        // by NOW atseen is accurate

        if(ATSeen){
            Pose2D ATSeenRoboPose = limelight.getPositionCenterRelative(Objects.equals(teamColor, "blue"));
            roboPoseRelativeToAT = new SparkFunOTOS.Pose2D(ATSeenRoboPose.getX(DistanceUnit.METER), ATSeenRoboPose.getY(DistanceUnit.METER), ATSeenRoboPose.getHeading(AngleUnit.DEGREES));
            sparkfun.myOtos.setPosition(roboPoseRelativeToAT);
        }
        else {
            sparkfun.myOtos.getPosition();
        }

    }
}
