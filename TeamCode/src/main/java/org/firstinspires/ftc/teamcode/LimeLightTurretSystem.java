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
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import java.util.List;

public class LimeLightTurretSystem {
    public DcMotor encoder;
    sparkFunMethodsClass sparkfun;
    public double targetangle = 165; //chnsge
    public Limelight3A limelight;
    public Pose3D botpose;
    public Position positionrelativetoapriltag;
    public boolean yesorno = true;

    public double botposeangle;
    public double angleerror = targetangle - botposeangle;
    public double velocityheading;
    public double kineticerror;
    public double distance_from_apriltag = 0; //meters
    public CRServo turretSpin;
    public List<LLResultTypes.FiducialResult> fiducialResults;

    double encoderDegreesPerTick = 0.008772; //THIS IS THE CORRECT COEFFICIANT!!!!!!!!!

    public void init(HardwareMap hardwareMap, Telemetry telemetry) {
        turretSpin = hardwareMap.get(CRServo.class, "turretSpin");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        encoder = hardwareMap.get(DcMotor.class, "encoder");
        encoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telemetry.setMsTransmissionInterval(11);
        limelight.pipelineSwitch(0);
        limelight.setPollRateHz(50);
        sparkfun = new sparkFunMethodsClass();
        sparkfun.init(hardwareMap,telemetry);
        limelight.start();
        // so botpose is never null
        botpose = new Pose3D(new Position(), new YawPitchRollAngles(AngleUnit.DEGREES,0,0,0,0));


    }
    public void turnWithKinetic() { //this doesn't work we need to ask doc sbout this
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



        yesorno = true;
        if (id == fiducialResults.get(0).getFiducialId()) {
            botposeangle = botpose.getOrientation().getYaw(AngleUnit.DEGREES);
            ///  fix this
//            angleerror = result.getTx(); //targetangle - botposeangle;
            if (encoder.getCurrentPosition() < 90/encoderDegreesPerTick && encoder.getCurrentPosition() > -90/encoderDegreesPerTick) { //change the big number
                if (angleerror < -1) {
                    turretSpin.setPower(0.2);
                } else if (angleerror > 1) {
                    turretSpin.setPower(-0.2);
                } else if (angleerror < 1 && angleerror > -1) {
                    turretSpin.setPower(0);
                } //TODO: Dylan Dorman wants a PID here but this is fine for now I GUESS
            } else if (encoder.getCurrentPosition() < -90/encoderDegreesPerTick && angleerror > 0) { //so if the encoder is past a certain point either way turn back the other way
                turretSpin.setPower(-0.2); //idk which direction is right
                // noih
            } else if (encoder.getCurrentPosition() > 90/encoderDegreesPerTick && angleerror < 0) {
                turretSpin.setPower(0.2);
            } else {
                turretSpin.setPower(0);
            }

        }
    }

    public void turnToNotAT(double power){
        turretSpin.setPower(power);
    }

    public boolean update(){ /// RUN THIS AT THE START OF EVERY LOOP!!!!!!!!
        LLResult result = limelight.getLatestResult();
        boolean seen =  (result.getBotpose().getPosition().x != 0);
        if (seen){
            botpose = result.getBotpose();
        }
        botposeangle = botpose.getOrientation().getYaw();

        return seen;
    }

    public double getDistance_from_apriltag(boolean isBlue) {
        //BLUEEEEEEEEE
        positionrelativetoapriltag = new Position(DistanceUnit.METER, botpose.getPosition().x + 1.482,botpose.getPosition().y + (isBlue? 1.413: -1.413), 0, 0);
        distance_from_apriltag = Math.sqrt(Math.pow(positionrelativetoapriltag.x, 2)+Math.pow(positionrelativetoapriltag.y, 2));


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
        double robox = botpose.getPosition().x + 1.482 - 0.18*Math.cos(Math.toRadians(botposeangle));
        double roboy = botpose.getPosition().y + (isBlue?1.413:-1.413) - 0.18*Math.sin(Math.toRadians(botposeangle));
        return new Pose2D(DistanceUnit.METER, robox, roboy, AngleUnit.DEGREES, botposeangle);
    }
}


