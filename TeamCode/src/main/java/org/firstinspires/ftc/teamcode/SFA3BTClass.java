package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;

import java.util.List;
import java.util.Objects;

@TeleOp
public class SFA3BTClass extends OpMode {
    /** SPARK FUN AS AN APRILTAG BACKUP TESTING CLASS */
    sparkFunMethodsClass sparkfun;
    LimeLightTurretSystem limelight;
    String teamColor = "none";
    int desiredID = 0;
    SparkFunOTOS.Pose2D roboPoseRelativeToAprilTag;


    @Override
    public void init() {
        limelight = new LimeLightTurretSystem();
        limelight.init(hardwareMap,telemetry);
        sparkfun = new sparkFunMethodsClass();
        sparkfun.init(hardwareMap,telemetry);
    }


    @Override
    public void loop() {
        boolean ATSeen = true;

        if (gamepad1.a && Objects.equals(teamColor, "none")){ // Objects.equals(String, String) just sees if theyre equal
            teamColor = "red";
            desiredID = 24;
        }
        if (gamepad1.b && Objects.equals(teamColor, "none")){
            teamColor = "blue";
            desiredID = 20;
        }

        if (limelight.result.getFiducialResults() == null)
        {
            ATSeen = false;
        }
        //makes the results list
        List<LLResultTypes.FiducialResult> results = limelight.result.getFiducialResults();

        int goodIndex = 10; //no way it sees 10 AT's like no nuh uh i dont belive it.

        if (ATSeen){
            for (int i = 0; i < limelight.result.getFiducialResults().size(); i++){
                if (results.get(i).getFiducialId() == desiredID){
                    goodIndex = i;
                    limelight.turntoAT(desiredID);
                    Position pos = results.get(i).getCameraPoseTargetSpace().getPosition();
                    double head = results.get(i).getCameraPoseTargetSpace().getOrientation().getYaw();
                    roboPoseRelativeToAprilTag = new SparkFunOTOS.Pose2D(pos.x, pos.y,head);
                    break;
                }
            }
            if (goodIndex == 10) {
                ATSeen = false;
            }
        }
        // by NOW atseen is accurate

        if(ATSeen){


        }
        else {
            sparkfun.myOtos.getPosition();
        }

    }
}
