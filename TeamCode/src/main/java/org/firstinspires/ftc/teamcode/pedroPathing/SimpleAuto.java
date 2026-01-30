package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.paths.Path;
import com.pedropathing.util.Timer;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Indexer;
import org.firstinspires.ftc.teamcode.Launcher;
import org.firstinspires.ftc.teamcode.LimeLightTurretSystem;

import java.util.List;

@Autonomous
public class SimpleAuto extends OpMode {
    LimeLightTurretSystem limelightclass;
    Indexer turningthing;
    LLResultTypes.FiducialResult result;

    Launcher launcher;
    Servo flipper;
    Follower follower;
    Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;
    Pose startPose = new Pose(56,9 , Math.toRadians(78)); //heading in radians
    Pose endPose = new Pose(45, 18,Math.toRadians(78));
    public LLResultTypes.FiducialResult fr;
    private Path move;
    public void buildPaths() {
        move = new Path(new BezierCurve(startPose, endPose));
    }
    public Indexer.SensedColor[] patternArray = {
            Indexer.SensedColor.PURPLE, Indexer.SensedColor.PURPLE, Indexer.SensedColor.GREEN
    };
    public void setPathState(int state) { //allows it to know where in tihe code it is
        pathState = state;
        pathTimer.resetTimer();
    }
    public void shootingMacro(double shootingdistance) {
        actionTimer.resetTimer();
        while (actionTimer.getElapsedTime() < 2676.7) {
            launcher.shoot(shootingdistance);
        }
        flipper.setPosition(0.3189);
        actionTimer.resetTimer();
        while (actionTimer.getElapsedTime() < 476.7){
        }
        flipper.setPosition(0.6741);
        turningthing.turn(false);
        actionTimer.resetTimer();
        while (actionTimer.getElapsedTime() < 4676.7) {
            turningthing.indexerUpdate();
            launcher.shoot(shootingdistance);
        }

        flipper.setPosition(0.3189);
        actionTimer.resetTimer();
        while (actionTimer.getElapsedTime() < 676.7){
        }
        flipper.setPosition(0.6741);
        turningthing.turn(false);
        actionTimer.resetTimer();
        while (actionTimer.getElapsedTime() < 4676.7) {
            launcher.shoot(shootingdistance);
            turningthing.indexerUpdate();
        }
        flipper.setPosition(0.3189);
        actionTimer.resetTimer();
        while (actionTimer.getElapsedTime() < 676.7){
        }
        flipper.setPosition(0.6741);
    }
    public void autoUpdate() {
        switch (pathState) {
            //these cases can also be used to check for time (if(pathTimer.getElapsedTimeSeconds() >1) {}
            //it can also be used to get the X value of the robot's position
            //IE: if(follower.getPose().getX() > 36) {}
            case 0:
//                turningthing.turn(false);
                shootingMacro(5.3);
                setPathState(1);

                break;
            case 1:
                if (!follower.isBusy()) {
                    follower.followPath(move);
                    actionTimer.resetTimer();
                    while (actionTimer.getElapsedTime() < 500) {
//                        follower.drivetrain.setXVelocity(0.1);
                    }
                }
                setPathState(-1);
                break;
//            case 2:
//                if (!follower.isBusy()) {
//                    intake.setPower(0);
//                    follower.followPath(run2);
//                    setPathState(3);
//                }
//                break;
//            case 3:
//                if (!follower.isBusy()) {
//                    turningthing.turnBasedOffColor(patternArray[0]);
//                    launcher.shoot(limelightclass.getDistance_from_apriltag(0)); //actually shoots
//                    turningthing.turnBasedOffColor(patternArray[1]);
//                    launcher.shoot(limelightclass.getDistance_from_apriltag(0)); //actually shoots
//                    turningthing.turnBasedOffColor(patternArray[2]); //these make it turns
//                    launcher.shoot(limelightclass.getDistance_from_apriltag(0)); //actually shoots
//                    follower.followPath(pickup2);
//                    setPathState(4);
//                }
//                break;
//            case 4:
//                if (!follower.isBusy()) {
//                    //run intake
//                    intake.setPower(1);
//                    follower.updateCallbacks();
//                    follower.followPath(intake2chain, 0.7, true);
//                    setPathState(5);
//                }
//                break;
//            case 5:
//                if (!follower.isBusy()) {
//                    intake.setPower(0);
//                    follower.followPath(run3);
//                    setPathState(6);
//                }
//                break;
//            case 6:
//                if (!follower.isBusy()) {
//                    turningthing.turnBasedOffColor(patternArray[0]);
//                    launcher.shoot(limelightclass.getDistance_from_apriltag(0)); //actually shoots
//                    turningthing.turnBasedOffColor(patternArray[1]);
//                    launcher.shoot(limelightclass.getDistance_from_apriltag(0)); //actually shoots
//                    turningthing.turnBasedOffColor(patternArray[2]); //these make it turns
//                    launcher.shoot(limelightclass.getDistance_from_apriltag(0)); //actually shoots
//                    setPathState(-1);
//                }
//                break;
//            case 7:
//                if (!follower.isBusy()) {
//                    follower.followPath(score);
//                    setPathState(8);
//                }
//                break;
//            case 8:
//                if (!follower.isBusy()) {
//                    follower.followPath(pickupMain);
//                    setPathState(9);
//                }
//                break;
//            case 9:
//                if (!follower.isBusy()) {
//                    setPathState(-1);
//
//                }
        }
    }
    @Override
    public void init() {
        flipper = hardwareMap.get(Servo.class,"flipper");
        turningthing = new Indexer();
        turningthing.init(hardwareMap, telemetry);
        launcher = new Launcher();
        launcher.init(hardwareMap, telemetry);
        limelightclass = new LimeLightTurretSystem();
        limelightclass.init(hardwareMap, telemetry);
        pathTimer = new Timer();
        actionTimer = new Timer();
        actionTimer.resetTimer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();
        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);
        setPathState(0);
        turningthing.SensedColorAll.set(0, Indexer.SensedColor.PURPLE);
        turningthing.SensedColorAll.set(1, Indexer.SensedColor.PURPLE);
        turningthing.SensedColorAll.set(2, Indexer.SensedColor.GREEN);
    }
    @Override
    public void start() {
//        if (limelightclass.result != null) {
//            result = limelightclass.result.getFiducialResults().get(0);
//        } else {return;}
        ///  "result" is no longer a class variable
        if (result.getFiducialId() == 23) {
            telemetry.addLine("PPG");
            patternArray[0] = Indexer.SensedColor.PURPLE;
            patternArray[1] = Indexer.SensedColor.PURPLE;
            patternArray[2] = Indexer.SensedColor.GREEN;
        } else if (result.getFiducialId() == 22) {
            telemetry.addLine("PGP");
            patternArray[0] = Indexer.SensedColor.PURPLE;
            patternArray[1] = Indexer.SensedColor.GREEN;
            patternArray[2] = Indexer.SensedColor.PURPLE;
        } else if (result.getFiducialId() == 21) {
            telemetry.addLine("GPP");
            patternArray[0] = Indexer.SensedColor.GREEN;
            patternArray[1] = Indexer.SensedColor.PURPLE;
            patternArray[2] = Indexer.SensedColor.PURPLE;

        } else {
            telemetry.addLine("nothing");
        }
        telemetry.update();
    }
    @Override
    public void loop() {
        turningthing.indexerUpdate();
        follower.update();
        autoUpdate();
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.addData("patternArray", patternArray);
        telemetry.addData("timet", actionTimer);
        telemetry.update();
    }
}
