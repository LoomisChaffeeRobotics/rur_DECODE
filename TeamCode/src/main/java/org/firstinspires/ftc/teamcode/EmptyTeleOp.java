package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class EmptyTeleOp extends OpMode {
    Indexer indexClass;
    @Override
    public void init() {
        indexClass = new Indexer();
        indexClass.SensedColorAll.set(0, Indexer.SensedColor.PURPLE);
        indexClass.SensedColorAll.set(1, Indexer.SensedColor.GREEN);
        indexClass.SensedColorAll.set(2, Indexer.SensedColor.NEITHER);
    }

    @Override
    public void loop() {
        if (gamepad1.aWasPressed()) {
            indexClass.shift_list(indexClass.SensedColorAll, true);
        } else if  (gamepad1.yWasPressed()) {
            indexClass.shift_list(indexClass.SensedColorAll, false);
        }
        telemetry.addData("SensedColorAll", indexClass.SensedColorAll);
        telemetry.update();



    }
}

//@Colin guess what this does.
//you will never guess
//its kinda hard to tell imo