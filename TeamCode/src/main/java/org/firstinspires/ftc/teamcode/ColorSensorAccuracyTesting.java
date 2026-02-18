package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class ColorSensorAccuracyTesting extends OpMode {

    Indexer indexClass;
    public double emptyAccuracy = 0;
    public double accCount = 1;
    public double wrongCount = 0;

    @Override
    public void init() {
        indexClass = new Indexer();
        indexClass.init(hardwareMap, telemetry);
    }

    @Override
    public void loop() {
        accCount++;
        indexClass.sensecolor();
        if (indexClass.sensecolor() == Indexer.SensedColor.NEITHER && indexClass.SensedColorAll.get(0) != Indexer.SensedColor.NEITHER) {
            wrongCount++;
        }

        emptyAccuracy = wrongCount/accCount;

        telemetry.addData("confidence % that front ball is empty", Math.round(emptyAccuracy * 10000)/100);
        telemetry.addData("empty count", wrongCount);
        telemetry.addData("is sense", indexClass.sensecolor() == Indexer.SensedColor.NEITHER);
        telemetry.addData("is list not equal", indexClass.SensedColorAll.get(0) != Indexer.SensedColor.NEITHER);
        telemetry.addData("sensed color list", indexClass.SensedColorAll);
        telemetry.addData("color that was sensed: ", indexClass.hsvValues1[0]);
        telemetry.update();

        if (gamepad1.aWasPressed()) {
            accCount = 0;
            wrongCount = 0;
            emptyAccuracy = 0;
        }

        if (emptyAccuracy > .8 && wrongCount > 60) {

            accCount = 0;
            wrongCount = 0;
            emptyAccuracy = 0;

            indexClass.SensedColorAll.set(0, Indexer.SensedColor.NEITHER);
        }

    }
}
