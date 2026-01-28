package org.firstinspires.ftc.teamcode;

import android.text.ParcelableSpan;

import com.acmerobotics.dashboard.FtcDashboard;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp
public class EmptyTeleOp extends OpMode {
    Indexer indexClass;
    TelemetryManager t;
    ElapsedTime timer;
    FtcDashboard dash = FtcDashboard.getInstance();
    Telemetry t2 = dash.getTelemetry();
    @Override
    public void init() {
        indexClass = new Indexer();
        indexClass.init(hardwareMap, telemetry);
        indexClass.SensedColorAll.set(0, Indexer.SensedColor.PURPLE);
        indexClass.SensedColorAll.set(1, Indexer.SensedColor.GREEN);
        indexClass.SensedColorAll.set(2, Indexer.SensedColor.NEITHER);
        t = PanelsTelemetry.INSTANCE.getTelemetry();
        timer = new ElapsedTime();
        timer.reset();
    }

    @Override
    public void loop() {
//        if (gamepad1.aWasPressed()) {
//            indexClass.turnBasedOffColor(Indexer.SensedColor.GREEN);
//        } else if  (gamepad1.yWasPressed()) {
//            indexClass.turnBasedOffColor(Indexer.SensedColor.PURPLE);
//        } else if (gamepad1.bWasPressed()) {
//            indexClass.removefirst(indexClass.SensedColorAll);
//        }
//        if (indexClass.error < 100) {
//            indexClass.sensecolor();
//        }
        if (gamepad1.aWasPressed()) {
            indexClass.turn(false);
        } else if (gamepad1.yWasPressed()) {
            indexClass.turn(true);
        }
        indexClass.indexerUpdate();
        telemetry.addData("SensedColorAll", indexClass.SensedColorAll);
        telemetry.update();
        t2.addData("curPose", indexClass.curPose);
        t2.addData("Targ", indexClass.targetPosition);
        t2.addData("power", indexClass.power);
        t2.update();
    }
}

//code code code code

//@Colin guess what this does.
//you will never guess
//its kinda hard to tell imo