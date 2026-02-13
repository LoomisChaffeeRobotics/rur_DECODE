package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class ColorSensorAccuracy extends OpMode {

    Indexer indexClass;
    double emptyAccuracy = 0;
    double accCount = 0;
    double wrongCount = 0;

    @Override
    public void init() {
        indexClass = new Indexer();
        indexClass.init(hardwareMap, telemetry);
    }

    @Override
    public void loop() {
        accCount++;
        if (indexClass.sensecolor() == 0 && indexClass.SensedColorAll.get(0) != Indexer.SensedColor.NEITHER) {
            wrongCount++;
        }

        emptyAccuracy = wrongCount/accCount;

        telemetry.addData("confidence % that front ball is empty", Math.round(emptyAccuracy * 10000)/100);
        telemetry.update();

        if (gamepad1.aWasPressed()) {
            accCount = 0;
            wrongCount = 0;
            emptyAccuracy = 0;
        }

    }
}
