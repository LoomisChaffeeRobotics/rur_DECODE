package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;

public class LimeLightTestingClass extends OpMode {
    Pose3D botpose;
    Limelight3A limelight;
    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0);
        limelight.start();

    }

    @Override
    public void loop() {
        LLResult result = limelight.getLatestResult();
         botpose = (result != null) ? result.getBotpose() : botpose;

        double botposeangle = botpose.getOrientation().getYaw(AngleUnit.DEGREES);
        positionrelativetoapriltag = new Position(DistanceUnit.METER, botpose.getPosition().x + 1.482,botpose.getPosition().y + 1.413, 0, 0);
        if (value == 0) {
            distance_from_apriltag = Math.sqrt(Math.pow(positionrelativetoapriltag.x, 2)+Math.pow(positionrelativetoapriltag.y, 2));
        } else {
            distance_from_apriltag = value;
        }


        angleerror = targetangle - botposeangle;
        status = limelight.getStatus();
        return distance_from_apriltag;
    }
}
