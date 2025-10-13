package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.List;

@Autonomous
public class AutoClass extends LinearOpMode {
    public Limelight3A limelight;

    @Override
    public void runOpMode() throws InterruptedException {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(0);

        /*
         * Starts polling for data.  If you neglect to call start(), getLatestResult() will return null.
         */
        limelight.start();
        waitForStart();

        while (opModeIsActive()) {
            LLResult result = limelight.getLatestResult();
            List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
            for (LLResultTypes.FiducialResult fr : fiducialResults) {
                telemetry.addData("Fiducial", "ID: %d, Family: %s, X: %.2f, Y: %.2f", fr.getFiducialId(), fr.getFamily(), fr.getTargetXDegrees(), fr.getTargetYDegrees());
                if (fr.getFiducialId() == 23) {
                    telemetry.addLine("PPG");
                } else if (fr.getFiducialId() == 22) {
                    telemetry.addLine("PGP");
                } else if (fr.getFiducialId() == 21) {
                    telemetry.addLine("GPP");
                } else {
                    telemetry.addLine("nothing");
                }
            }
            telemetry.update();

        }
    }
}
