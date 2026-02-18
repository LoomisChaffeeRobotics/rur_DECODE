package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ColorSensorAccuracyClass {


    Indexer indexClass;
    public double emptyAccuracy = 0;
    public double accCount = 1;
    public double wrongCount = 0;

    public double new_wrong = 0;
    boolean can_reset = false;

    int test_wrong = 0;

    public boolean[] lastWrongArray = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};


    public void init(HardwareMap hardwareMap, Telemetry telemetry) {
        indexClass = new Indexer();
        indexClass.init(hardwareMap, telemetry);
    }

    void update_wrong_array() {

        test_wrong = 0;

        for (int i = 0; i < 124; ++i) {

            lastWrongArray[i] = lastWrongArray[i + 1];

            if (lastWrongArray[i]) {
                test_wrong++;
            }

        }

        lastWrongArray[124] = false;

        wrongCount = test_wrong;

    }


    public void update() {

        if (can_reset) {
            can_reset = false;

            accCount = 1;
            wrongCount = 0;
            emptyAccuracy = 0;
        }

        if (!indexClass.indexer_is_moving) {
//            if (accCount >= 125) {update_wrong_array();}
//            else {accCount++;}
            accCount++;
            //        indexClass.sensecolor();
            if (indexClass.sensecolor() == Indexer.SensedColor.NEITHER && indexClass.SensedColorAll.get(0) != Indexer.SensedColor.NEITHER) {
                wrongCount++;

//                lastWrongArray[(int)accCount-1] = true;

            }
//            else {lastWrongArray[(int)accCount-1] = false;}

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

        can_reset = true;

    }

}
