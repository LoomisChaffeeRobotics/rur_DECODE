package org.firstinspires.ftc.teamcode;




//I've already moved all of the code into DriveClass - any changes that need to be made should be made there
//10-7-25




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
    CRServo sv;

    public void init(HardwareMap hardwareMap, Telemetry telemetry) {
//        sv = hardwareMap.get(CRServo.class, "sv");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(0);

        /*
         * Starts polling for data.  If you neglect to call start(), getLatestResult() will return null.
         */
        limelight.start();


    }


    public double getDistance_from_apriltag() {
        //BLUEEEEEEEEE
        result = limelight.getLatestResult();
        botpose = result.getBotpose();
        botposeangle = botpose.getOrientation().getYaw(AngleUnit.RADIANS);
        positionrelativetoapriltag = new Position(DistanceUnit.INCH, botpose.getPosition().x + 58.346457,botpose.getPosition().y + 55.629921, 0, 0);
        distance_from_apriltag = Math.sqrt(Math.pow(positionrelativetoapriltag.x, 2)+Math.pow(positionrelativetoapriltag.y, 2)) * 0.00254;

        angleerror = targetangle - botposeangle;
//        if (angleerror < -1 || angleerror > 180) {
//            sv.setPower(-0.1);
//        } else if (angleerror > 1 && angleerror < 165) {
//            sv.setPower(0.1);
//        } else if (angleerror < 1 && angleerror > -1){
//            sv.setPower(0);
//        }

        // thtey wouldh ave to hold down the button - might not be necessary if a better solution is found or something

        status = limelight.getStatus();
//        telemetry.addData("Name", "%s",
//                status.getName());
//        telemetry.addData("LL", "Temp: %.1fC, CPU: %.1f%%, FPS: %d",
//                status.getTemp(), status.getCpu(),(int)status.getFps());
//        telemetry.addData("Pipeline", "Index: %d, Type: %s",
//                status.getPipelineIndex(), status.getPipelineType());
//            double captureLatency = result.getCaptureLatency();
//            double targetingLatency = result.getTargetingLatency();
//            double parseLatency = result.getParseLatency();
//            telemetry.addData("LL Latency", captureLatency + targetingLatency);
//            telemetry.addData("Parse Latency", parseLatency);
//            telemetry.addData("PythonOutput", java.util.Arrays.toString(result.getPythonOutput()));
//
//            telemetry.addData("tx", result.getTx());
//            telemetry.addData("txnc", result.getTxNC());
//            telemetry.addData("ty", result.getTy());
//            telemetry.addData("tync", result.getTyNC());


            // Access barcode results
//            List<LLResultTypes.BarcodeResult> barcodeResults = result.getBarcodeResults();
//            for (LLResultTypes.BarcodeResult br : barcodeResults) {
//                telemetry.addData("Barcode", "Data: %s", br.getData());
//            }
//
//            // Access classifier results
//            List<LLResultTypes.ClassifierResult> classifierResults = result.getClassifierResults();
//            for (LLResultTypes.ClassifierResult cr : classifierResults) {
//                telemetry.addData("Classifier", "Class: %s, Confidence: %.2f", cr.getClassName(), cr.getConfidence());
//            }
//
//            // Access detector results
//            List<LLResultTypes.DetectorResult> detectorResults = result.getDetectorResults();
//            for (LLResultTypes.DetectorResult dr : detectorResults) {
//                telemetry.addData("Detector", "Class: %s, Area: %.2f", dr.getClassName(), dr.getTargetArea());
//            }

            // Access fiducial results

//            telemetry.update();
//

//            // Access color results
//            List<LLResultTypes.ColorResult> colorResults = result.getColorResults();
//            for (LLResultTypes.ColorResult cr : colorResults) {
//                telemetry.addData("Color", "X: %.2f, Y: %.2f", cr.getTargetXDegrees(), cr.getTargetYDegrees());
//            }

            return distance_from_apriltag;
    }
}

