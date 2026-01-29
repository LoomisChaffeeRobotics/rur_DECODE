package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;

import java.util.List;

public class LimeLightTurretSystem {
    public DcMotor encoder;
    sparkFunMethodsClass sparkfun;
    public double targetangle = 165; //chnsge
    public Limelight3A limelight;
    public Pose3D botpose;
    public Position positionrelativetoapriltag;
    public boolean yesorno = true;

    public LLResult result;
    public double botposeangle;
    public double angleerror = targetangle - botposeangle;
    public double velocityheading;
    public double kineticerror;
    public double distance_from_apriltag = 0; //meters
    public CRServo turretSpin;
    public List<LLResultTypes.FiducialResult> fiducialResults;

    public void init(HardwareMap hardwareMap, Telemetry telemetry) {
        turretSpin = hardwareMap.get(CRServo.class, "turretSpin");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        encoder = hardwareMap.get(DcMotor.class, "encoder");
        encoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telemetry.setMsTransmissionInterval(11);
        limelight.pipelineSwitch(0);
        sparkfun = new sparkFunMethodsClass();
        sparkfun.init(hardwareMap,telemetry);
        limelight.start();;


    }
    public void turnWithKinetic() { //this doesn't work we need to ask doc sbout this
        botpose = (result != null) ? result.getBotpose() : botpose;
        botposeangle = botpose.getOrientation().getYaw(AngleUnit.DEGREES);
        velocityheading = sparkfun.velocity.h; //i dont know what velocity.h is but im hoping it's the angle of the velocity
        kineticerror = targetangle-velocityheading;
        angleerror = (targetangle - botposeangle) + kineticerror; //you might need to add kineticerror or subtract (targetangle-botposeangle) from it
        if (angleerror < -1 || angleerror > 180) {
            turretSpin.setPower(-0.1);
        } else if (angleerror > 1 && angleerror < 180) {
            turretSpin.setPower(0.1);
        } else if (angleerror < 1 && angleerror > -1){
            turretSpin.setPower(0);
        }


    }
    public void turntoAT(double id) { //i think i got numbers right maybe

        //double encoderDegreesPerTick = 0.008772; //THIS IS THE CORRECT COEFFICIANT!!!!!!!!!
        result = limelight.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
        if (result == null || id != fiducialResults.get(0).getFiducialId()){
            turretSpin.setPower(0);
            yesorno = false;
            return;

        }
        yesorno = true;
        botpose = result.getBotpose();
        if (id == fiducialResults.get(0).getFiducialId()) {
            botposeangle = botpose.getOrientation().getYaw(AngleUnit.DEGREES);
            angleerror = result.getTx(); //targetangle - botposeangle;
            if (encoder.getCurrentPosition() < 18000 && encoder.getCurrentPosition() > -18000) { //change the big number
                if (angleerror < -1) {
                    turretSpin.setPower(0.2);
                } else if (angleerror > 1) {
                    turretSpin.setPower(-0.2);
                } else if (angleerror < 1 && angleerror > -1) {
                    turretSpin.setPower(0);
                } //TODO: Dylan Dorman wants a PID here but this is fine for now I GUESS>:c
            } else if (encoder.getCurrentPosition() < -18000 && angleerror > 0) { //so if the encoder is past a certain point either way turn back the other way
                turretSpin.setPower(-0.2); //idk which direction is right
                // noih
            } else if (encoder.getCurrentPosition() > 18000 && angleerror < 0) {
                turretSpin.setPower(0.2);
            } else {
                turretSpin.setPower(0);
            }

        }
    }

    public void turnToNotAT(double power){
        turretSpin.setPower(power);
    }


    public double getDistance_from_apriltag(double value /*im not sure what that does */, boolean isBlue) {
        //BLUEEEEEEEEE
        result = limelight.getLatestResult();
        botpose = (result != null) ? result.getBotpose() : botpose;
        botposeangle = botpose.getOrientation().getYaw(AngleUnit.DEGREES);
        positionrelativetoapriltag = new Position(DistanceUnit.METER, botpose.getPosition().x + 1.482,botpose.getPosition().y + (isBlue? 1.413: -1.413), 0, 0);
        if (value == 0) {
            distance_from_apriltag = Math.sqrt(Math.pow(positionrelativetoapriltag.x, 2)+Math.pow(positionrelativetoapriltag.y, 2));
        } else {
            return value;
        }

        angleerror = targetangle - botposeangle;
        return distance_from_apriltag;
    }

    /** returns degrees rotated clockwise in range [0,360).
     * 0 is forward (or starting pos)*/
    public double getTurretAngle(){
        double degrees = encoder.getCurrentPosition() * 0.008772;
        degrees %= 360;
        return degrees;
    }

    /** gives the position relative to the april tag */
    public Pose2D getPositionCenterRelative(boolean isBlue){
        result = limelight.getLatestResult();
        botpose = (result != null) ? result.getBotpose() : botpose;

        botposeangle = botpose.getOrientation().getYaw(AngleUnit.DEGREES);
        double robox = botpose.getPosition().x + 1.482 + Math.cos(botposeangle);
        double roboy = botpose.getPosition().y + (isBlue?1.413:-1.413) + Math.sin(botposeangle);
        return new Pose2D(DistanceUnit.METER, robox, roboy, AngleUnit.DEGREES, botposeangle);
    }
}


