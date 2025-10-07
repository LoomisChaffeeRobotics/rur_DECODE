package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

@TeleOp
public class unnecessaryLimeLightTurretSystem extends OpMode {
    public double targetangle = 165;
    public Limelight3A limelight;
    public Pose3D botpose;
    public double botposeangle;
    public double angleerror = targetangle - botposeangle;
    CRServo sv;
    @Override
    public void init() {
        sv = hardwareMap.get(CRServo.class, "sv");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(0);

        /*
         * Starts polling for data.  If you neglect to call start(), getLatestResult() will return null.
         */
        limelight.start();


    }

    @Override
    public void loop() {
        angleerror = targetangle - botposeangle;
        if (angleerror < -1 || angleerror > 180) {
            sv.setPower(-0.1);
        } else if (angleerror > 1 && angleerror < 165) {
            sv.setPower(0.1);
        } else if (angleerror < 1 && angleerror > -1){
            sv.setPower(0);
        }
        if (gamepad1.right_bumper) {
            sv.setPower(0);
        } // thtey wouldh ave to hold down the button - might not be necessary if a better solution is found or something
        telemetry.addData("angle error", angleerror);
        telemetry.addData("power",sv.getPower());
        telemetry.addData("angle", botposeangle);
        LLStatus status = limelight.getStatus();
//        telemetry.addData("Name", "%s",
//                status.getName());
//        telemetry.addData("LL", "Temp: %.1fC, CPU: %.1f%%, FPS: %d",
//                status.getTemp(), status.getCpu(),(int)status.getFps());
//        telemetry.addData("Pipeline", "Index: %d, Type: %s",
//                status.getPipelineIndex(), status.getPipelineType());
        LLResult result = limelight.getLatestResult();
            // Access general information
            botpose = result.getBotpose();
            botposeangle = botpose.getOrientation().getYaw(AngleUnit.DEGREES);
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

            telemetry.addData("Botpose", botpose.toString());
            telemetry.addData("botposeangle", botposeangle);
            telemetry.update();
//             Access barcode results
            List<LLResultTypes.BarcodeResult> barcodeResults = result.getBarcodeResults();
            for (LLResultTypes.BarcodeResult br : barcodeResults) {
                telemetry.addData("Barcode", "Data: %s", br.getData());
            }
//
//            // Access classifier results
//            List<LLResultTypes.ClassifierResult> classifierResults = result.getClassifierResults();
//            for (LLResultTypes.ClassifierResult cr : classifierResults) {
//                telemetry.addData("Classifier", "Class: %s, Confidence: %.2f", cr.getClassName(), cr.getConfidence());
//            }
//
//            // Access detector results
            List<LLResultTypes.DetectorResult> detectorResults = result.getDetectorResults();
            for (LLResultTypes.DetectorResult dr : detectorResults) {
                telemetry.addData("Detector", "Class: %s, Area: %.2f", dr.getClassName(), dr.getTargetArea());
            }
//
//            // Access fiducial results
//            List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
//            for (LLResultTypes.FiducialResult fr : fiducialResults) {
//                telemetry.addData("Fiducial", "ID: %d, Family: %s, X: %.2f, Y: %.2f", fr.getFiducialId(), fr.getFamily(), fr.getTargetXDegrees(), fr.getTargetYDegrees());
//            }
//
//            // Access color results
//            List<LLResultTypes.ColorResult> colorResults = result.getColorResults();
//            for (LLResultTypes.ColorResult cr : colorResults) {
//                telemetry.addData("Color", "X: %.2f, Y: %.2f", cr.getTargetXDegrees(), cr.getTargetYDegrees());
//            }


    }
}

