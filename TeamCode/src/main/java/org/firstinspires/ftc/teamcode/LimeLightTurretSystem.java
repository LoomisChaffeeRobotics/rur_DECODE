package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import java.util.List;
import java.util.Objects;

@Config
public class LimeLightTurretSystem {
    public static double SFKf = 0;
    public static double SFKp = 0.1;
    public static double SFKi = 0;
    public static double SFKd = 0.01;
    public static double TXKf = 0;
    public static double TXKp = 0.1;
    public static double TXKi = 0;
    public static double TXKd = 0.01;

    public LLResult result;
    public DcMotorEx encoder;
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
    PIDFController turretControl; //when sfa3b
    PIDFController turretFineControl; //when ATseen

    double encoderDegreesPerTick = 0.008772; //THIS IS THE CORRECT COEFFICIANT!!!!!!!!!

    public void init(HardwareMap hardwareMap, Telemetry telemetry) {
        turretSpin = hardwareMap.get(CRServo.class, "turretSpin");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        encoder = hardwareMap.get(DcMotorEx.class, "encoder");
        encoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telemetry.setMsTransmissionInterval(11);
        limelight.pipelineSwitch(0);
        limelight.setPollRateHz(50);
        sparkfun = new sparkFunMethodsClass();
        sparkfun.init(hardwareMap,telemetry);
        limelight.start();
        // so botpose is never null
        botpose = new Pose3D(new Position(), new YawPitchRollAngles(AngleUnit.DEGREES,0,0,0,0));
        turretControl = new PIDFController(new PIDFCoefficients(0.1,0, 0.01, 0));
        turretFineControl = new PIDFController(new PIDFCoefficients(0.02,0, 0.00, 0));
        turretControl.setTargetPosition(0);
        turretFineControl.setTargetPosition(0);

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
    public void turntoAT(boolean isBlue) { //COMPLETE RE-DO USING SFA3B!!!!!
        boolean ATSeen = false;
        double turretPosition = encoder.getCurrentPosition() * -0.00877192982456;
        ATSeen = update();


        if(ATSeen){
            Pose2D ATSeenRoboPose = getPositionCenterRelative(isBlue);
            SparkFunOTOS.Pose2D roboPoseRelativeToAT = new SparkFunOTOS.Pose2D(ATSeenRoboPose.getX(DistanceUnit.INCH), ATSeenRoboPose.getY(DistanceUnit.INCH), ATSeenRoboPose.getHeading(AngleUnit.DEGREES) - turretPosition);
            sparkfun.myOtos.setPosition(roboPoseRelativeToAT); // the problem could be due to the turning

            turretFineControl.updatePosition(limelight.getLatestResult().getTx());
            turretFineControl.setTargetPosition(0);
            if (Math.abs(roboPoseRelativeToAT.h) < 20 ) {turretSpin.setPower(turretFineControl.run());}
            else {
                turretControl.updatePosition(turretPosition);
                if (Math.abs(roboPoseRelativeToAT.h) < 30) {
                    turretControl.setTargetPosition(-roboPoseRelativeToAT.h);
                }
                turretSpin.setPower(turretControl.run());
            }
        }
        else {
            SparkFunOTOS.Pose2D roboPoseRelativeToAT = sparkfun.myOtos.getPosition();

            turretControl.updatePosition(turretPosition);
            if (Math.abs(roboPoseRelativeToAT.h) < 30 ) {turretControl.setTargetPosition(-roboPoseRelativeToAT.h);}
            turretSpin.setPower(turretControl.run());
        }

    }

    public void turnToNotAT(double power){
        turretSpin.setPower(power);
    }

    public boolean update(){ /// RUN THIS AT THE START OF EVERY LOOP!!!!!!!!
        result = limelight.getLatestResult();
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
        double shiftedHeading = botposeangle + 135;
        double robox = botpose.getPosition().x + 1.482;// + 0.18 //*Math.cos(Math.toRadians(botposeangle));
        double roboy = botpose.getPosition().y + (isBlue?1.413:-1.413);// + 0.18 //*Math.sin(Math.toRadians(botposeangle));
        double roboxRotated = (robox-roboy) * 0.707106781187;
        double roboyRotated = (robox+roboy) * 0.707106781187;
        roboyRotated += 0.25;
        return new Pose2D(DistanceUnit.METER, roboxRotated, roboyRotated, AngleUnit.DEGREES, shiftedHeading);
    }
}


