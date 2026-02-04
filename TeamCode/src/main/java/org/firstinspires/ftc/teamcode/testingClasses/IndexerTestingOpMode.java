package org.firstinspires.ftc.teamcode.testingClasses;

import com.acmerobotics.dashboard.FtcDashboard;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Indexer;

@TeleOp
public class IndexerTestingOpMode extends OpMode {
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

    long counter = 0;

    @Override
    public void loop() {
        if (!indexClass.indexer_is_moving) {
            indexClass.sensecolor();
            counter++;
        }

        if (gamepad1.dpadLeftWasPressed()) {
            indexClass.gain++;
        } else if (gamepad1.dpadRightWasPressed()) {
            indexClass.gain--;
        }
        indexClass.colorSensor1.setGain(indexClass.gain);
        telemetry.addData("gain", indexClass.gain);
        telemetry.addData("ran color sensor x", counter);

        if (gamepad1.bWasPressed()) {
            indexClass.turnBasedOffColor(Indexer.SensedColor.GREEN);
        } else if  (gamepad1.xWasPressed()) {
            indexClass.turnBasedOffColor(Indexer.SensedColor.PURPLE);
        }
        if (gamepad1.aWasPressed()) {
            indexClass.turn(false);
        } else if (gamepad1.yWasPressed()) {
            indexClass.turn(true);
        }
        if (gamepad1.dpadUpWasPressed()) {
            indexClass.removefirst(indexClass.SensedColorAll);
        }
        indexClass.indexerUpdate();
        telemetry.addData("SensedColorAll", indexClass.SensedColorAll);
        telemetry.addData("error", indexClass.error);
        telemetry.addData("current color hsv", indexClass.hsvValues1[0]);
        telemetry.addData("current color but color", indexClass.SensedColorAll.get(0));
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