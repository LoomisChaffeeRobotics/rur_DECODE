package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ColorSensorAccuracyClass {


    Indexer indexClass;
    public double emptyAccuracy = 0;
    public double accCount = 1;
    public double wrongCount = 0;


    public void init(HardwareMap hardwareMap, Telemetry telemetry) {
        indexClass = new Indexer();
        indexClass.init(hardwareMap, telemetry);
    }


    public void update() {
        if (!indexClass.indexer_is_moving) {
            accCount++;
            //        indexClass.sensecolor();
            if (indexClass.sensecolor() == 0 && indexClass.SensedColorAll.get(0) != Indexer.SensedColor.NEITHER) {
                wrongCount++;
            }

            emptyAccuracy = wrongCount / accCount;
        }

//        telemetry.addData("confidence % that front ball is empty", Math.round(emptyAccuracy * 10000)/100);
//        telemetry.update();

//        if (gamepad1.aWasPressed()) {
//            accCount = 0;
//            wrongCount = 0;
//            emptyAccuracy = 0;
//        }

    }

    public void reset() {

        accCount = 1;
        wrongCount = 0;
        emptyAccuracy = 0;

    }

}
