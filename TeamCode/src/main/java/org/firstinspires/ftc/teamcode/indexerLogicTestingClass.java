package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.Arrays;

@TeleOp
public class indexerLogicTestingClass extends OpMode {
    Indexer indexer;

    boolean adepressed;
    boolean bdepressed;
    boolean xdepressed;
    boolean ydepressed;

    @Override
    public void init() {
        indexer = new Indexer();
//        indexer.init(hardwareMap,telemetry);
    }

    @Override
    public void loop() {
//        indexer.SensedColorAll = (Arrays.asList(indexer.CurrentColor, indexer.CurrentColor2, indexer.CurrentColor3));
        if (gamepad1.a && !adepressed){
            indexer.shift_list(indexer.SensedColorAll,true);
            adepressed = true;
        } else if (!gamepad1.a) { adepressed = false; }

        if (gamepad1.b && !bdepressed){
            indexer.shift_list(indexer.SensedColorAll,false);
            bdepressed = true;
        } else if (!gamepad1.b) { bdepressed = false; }

        if (gamepad1.x && !xdepressed){
            indexer.turnBasedOfColor(Indexer.SensedColor.GREEN);
            xdepressed = true;
        } else if (!gamepad1.x) { xdepressed = false; }

        if (gamepad1.y && !ydepressed){
            indexer.turnBasedOfColor(Indexer.SensedColor.PURPLE);
            ydepressed = true;
        } else if (!gamepad1.y) { ydepressed = false; }

        if (gamepad1.dpad_down){
            indexer.removefirst(indexer.SensedColorAll);
        }
        if (gamepad1.dpad_left){
            indexer.SensedColorAll.set(0,Indexer.SensedColor.GREEN);
        }
        if (gamepad1.dpad_right){
            indexer.SensedColorAll.set(0,Indexer.SensedColor.PURPLE);
        }

        telemetry.addData("0", indexer.SensedColorAll.get(0));
        telemetry.addData("1", indexer.SensedColorAll.get(1));
        telemetry.addData("2", indexer.SensedColorAll.get(2));
        telemetry.update();

    }
}
