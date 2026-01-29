package org.firstinspires.ftc.teamcode;

import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class verysimpleautored extends OpMode {
    DcMotor leftFront;
    DcMotor rightFront;
    Servo flipper;
    DcMotor leftBack;
    DcMotor rightBack;
    Launcher launchClass;
    LimeLightTurretSystem limelight;
    Indexer indexClass;
    Timer actionTimer;
    int state;
    public void init() {
        flipper = hardwareMap.get(Servo.class,"flipper");
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        actionTimer = new Timer();
        actionTimer.resetTimer();
        limelight = new LimeLightTurretSystem();
        launchClass = new Launcher();
        indexClass = new Indexer();
        indexClass.init(hardwareMap,telemetry);
    }
    public void loop(){
        indexClass.indexerUpdate();
        switch(state){
            case 0:
                if(limelight.limelight.getLatestResult() == null) {
                    leftFront.setPower(-0.2);
                    rightFront.setPower(-0.2);
                    leftBack.setPower(-0.2);
                    rightBack.setPower(-0.2);
                } else {
                    state = 1;
                }
            case 1:
                actionTimer.resetTimer();
                while (actionTimer.getElapsedTime() < 2676.7) {
//                    launchClass.shoot(5.3);
                }
                flipper.setPosition(0.3189);
                actionTimer.resetTimer();
                while (actionTimer.getElapsedTime() < 476.7){
                }
                flipper.setPosition(0.6741);
                indexClass.turn(false);
                actionTimer.resetTimer();
                while (actionTimer.getElapsedTime() < 4676.7) {
                    indexClass.indexerUpdate();
                    launchClass.shoot(limelight.getDistance_from_apriltag(0, true));
                }

                flipper.setPosition(0.3189);
                actionTimer.resetTimer();
                while (actionTimer.getElapsedTime() < 676.7){
                }
                flipper.setPosition(0.6741);
                indexClass.turn(false);
                actionTimer.resetTimer();
                while (actionTimer.getElapsedTime() < 4676.7) {
                    launchClass.shoot(limelight.getDistance_from_apriltag(0, true));
                    indexClass.indexerUpdate();
                }
                flipper.setPosition(0.3189);
                actionTimer.resetTimer();
                while (actionTimer.getElapsedTime() < 676.7){
                }
                flipper.setPosition(0.6741);

                state = 2;
            case 2:
                actionTimer.resetTimer();
                while (actionTimer.getElapsedTime() < 350) {
                    rightBack.setPower(-0.2);
                    rightFront.setPower(0.2);
                    leftBack.setPower(0.2);
                    leftFront.setPower(-0.2);
                }
                state = -1;


        }
    }
}


