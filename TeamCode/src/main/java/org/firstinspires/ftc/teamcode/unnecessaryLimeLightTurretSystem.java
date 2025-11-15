package org.firstinspires.ftc.teamcode;




//I've already moved all of the code into DriveClass - any changes that need to be made should be made there
//10-7-25

//to do uncomment otu the sv stuff




import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;

public class unnecessaryLimeLightTurretSystem{
    public double targetangle = 165;
    public Limelight3A limelight;
    public Pose3D botpose;
    public Position positionrelativetoapriltag;
    public double xrelativetoat;
    public double yrelativetoat;
    public LLResult result;
    public double botposeangle;
    public double angleerror = targetangle - botposeangle;
    public LLStatus status;

    public double distance_from_apriltag = 0; //meters
    public CRServo sv;

    public void init(HardwareMap hardwareMap, Telemetry telemetry) {
        sv = hardwareMap.get(CRServo.class, "sv");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        telemetry.setMsTransmissionInterval(11);
        limelight.pipelineSwitch(0);

        /*
         * Starts polling for data.  If you neglect to call start(), getLatestResult() will return null.
         */
        limelight.start();


    }
    public void turntoAT() {
        targetangle = 225;
        result = limelight.getLatestResult();
        botpose = (result != null) ? result.getBotpose() : botpose;
        botposeangle = botpose.getOrientation().getYaw(AngleUnit.DEGREES);
        angleerror = targetangle - botposeangle;
        if (angleerror < -1 || angleerror > 180) {
            sv.setPower(-0.1);
        } else if (angleerror > 1 && angleerror < 180) {
            sv.setPower(0.1);
        } else if (angleerror < 1 && angleerror > -1){
            sv.setPower(0);
        }
    }


    public double getDistance_from_apriltag(double value) {
        //BLUEEEEEEEEE
        result = limelight.getLatestResult();
        botpose = (result != null) ? result.getBotpose() : botpose;

        botposeangle = botpose.getOrientation().getYaw(AngleUnit.DEGREES);
        positionrelativetoapriltag = new Position(DistanceUnit.METER, botpose.getPosition().x + 1.482,botpose.getPosition().y + 1.413, 0, 0);
        if (value == 0) {
            distance_from_apriltag = Math.sqrt(Math.pow(positionrelativetoapriltag.x, 2)+Math.pow(positionrelativetoapriltag.y, 2));
        } else {
            distance_from_apriltag = value;
        }


        angleerror = targetangle - botposeangle;
//        if (angleerror < -1 || angleerror > 180) {
//            sv.setPower(-0.1);
//        } else if (angleerror > 1 && angleerror < 165) {
//            sv.setPower(0.1);
//        } else if (angleerror < 1 && angleerror > -1){
//            sv.setPower(0);
//        }
        status = limelight.getStatus();
        return distance_from_apriltag;
    }
}

