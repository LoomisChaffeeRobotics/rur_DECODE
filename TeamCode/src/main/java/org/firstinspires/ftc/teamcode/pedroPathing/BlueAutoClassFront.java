package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Indexer;
import org.firstinspires.ftc.teamcode.Launcher;
import org.firstinspires.ftc.teamcode.LimeLightTurretSystem;

import java.util.Arrays;
import java.util.List;


//only auto class that should theoretically work


@Autonomous
public class BlueAutoClassFront extends OpMode {
    public List<LLResultTypes.FiducialResult> results;
    DcMotor intake;
    Servo flipper;
    LimeLightTurretSystem limelightclass;
    Indexer turningthing;
    Launcher launcher;
    Follower follower;
    Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;
    private int shootingState = 0;
    private boolean isShooting = false;
    private double shootingDistance;
    public Indexer.SensedColor[] patternArray = {
            Indexer.SensedColor.PURPLE, Indexer.SensedColor.PURPLE, Indexer.SensedColor.PURPLE
    };
    Pose startPose = new Pose(21.36630602782071,123.52395672333849, Math.toRadians(143)); //heading in radians
    Pose detectPose = new Pose(34.68712123,107.7528485, Math.toRadians(45)); //to detect apriltag, same as launchposeMain but different heading
    Pose launchPoseMain = new Pose(45.40340030911901,97.706336939721788, Math.toRadians(137));
    Pose launchPose1 = new Pose(45.40340030911901,97.706336939721788, Math.toRadians(84));
    Pose controlPoint1 = new Pose(66.7958,83.5325,Math.PI);
    Pose pickupPose1 = new Pose (43.5,86.6, Math.PI); //this is the one that changes
    Pose intakePose1 = new Pose(18, 86.6, Math.PI);//this too
    Pose leavePose = new Pose(32, 75, Math.toRadians(137));
    Pose controlPoint2 = new Pose(68.80518,58.5278,Math.PI);
    Pose pickupPose2 = new Pose (42.5,61.09273570324575, Math.PI);
    Pose intake2 = new Pose(9.137, 61.092735, Math.PI);
    Pose controlPoint3 = new Pose(64.5429, 54.37311, Math.PI);
    private Path detectAT, scorePreload, pickup1, launch1, leave1, pickup2, launch2;
    private PathChain intake1chain, launch1chain, pickup2chain, launch2chain, intake2chain;
    public void buildPaths() {

        detectAT = new Path(new BezierCurve(startPose, detectPose));
        detectAT.setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(45));

        scorePreload = new Path(new BezierCurve(detectPose, launchPoseMain));
        scorePreload.setLinearHeadingInterpolation(Math.toRadians(65), Math.toRadians(137), 0.8);
        pickup1 =  new Path(new BezierCurve(launchPoseMain, controlPoint1, pickupPose1));
        pickup1.setLinearHeadingInterpolation(Math.toRadians(137), Math.toRadians(178));

        launch1 =  new Path(new BezierCurve(intakePose1, launchPoseMain));
        launch1.setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(137));

        intake1chain = follower.pathBuilder()
                .addPath(new BezierLine(pickupPose1, intakePose1))
                .setLinearHeadingInterpolation(pickupPose1.getHeading(), intakePose1.getHeading())
                .addParametricCallback(0.3, () -> turningthing.turn(true)) //adjust these t values when needed
                .addParametricCallback(0.6, () -> turningthing.turn(true))
                .build();
        launch1chain = follower.pathBuilder()
                .addPath(launch1)
                .setLinearHeadingInterpolation(intakePose1.getHeading(), launchPoseMain.getHeading())
                .build();

        pickup2 = new Path(new BezierCurve(launchPoseMain, controlPoint2, pickupPose2));
        pickup2.setLinearHeadingInterpolation(Math.toRadians(137), Math.PI);

        pickup2chain = follower.pathBuilder()
                .addPath(pickup2)
                .setLinearHeadingInterpolation(launchPoseMain.getHeading(), pickupPose2.getHeading())
                .build();

        intake2chain = follower.pathBuilder()
                .addPath(new BezierCurve(pickupPose2, intake2))
                .setConstantHeadingInterpolation(Math.PI)
                .addParametricCallback(0.3, () -> turningthing.turn(true))
                .addParametricCallback(0.6, () -> turningthing.turn(true))
                
                .build();
        launch2 = new Path(new BezierCurve(intake2, controlPoint3, launchPoseMain));
        launch2.setLinearHeadingInterpolation(intake2.getHeading(), launchPoseMain.getHeading());
        launch2chain = follower.pathBuilder()
                .addPath(launch2)
                .setLinearHeadingInterpolation(intake2.getHeading(), launchPoseMain.getHeading())
                .build();
        //Path chains are chains of paths - so you can add multiple as shown below

    }
    public void setPathState(int state) { //state machine :)
        pathState = state;
        pathTimer.resetTimer();
    }
    public void startShooting(double distance) {
        shootingDistance = distance;
        isShooting = true;
        shootingState = 0;
        actionTimer.resetTimer();
    }
    public void shootingUpdate() {

        if (!isShooting) return;

        switch (shootingState) {

            case 0:
                turningthing.turnBasedOffColor(patternArray[0]);
                launcher.shoot(shootingDistance);
                actionTimer.resetTimer();
                shootingState = 1;
                break;

            case 1:
                if (actionTimer.getElapsedTime() > 1576.7) {//time for turret to spin up - change

                    flipper.setPosition(0);
                    actionTimer.resetTimer();
                    shootingState = 2;
                }
                break;

            case 2:
                if (actionTimer.getElapsedTime() > 676.7) {//time for flipper to finish going up
                    flipper.setPosition(0.3778);
                    turningthing.removefirst(turningthing.SensedColorAll);
                    actionTimer.resetTimer();
                    shootingState = 3;
                }
                break;

            case 3:
                if (actionTimer.getElapsedTime() > 700) { //time for flipper to move down
                    turningthing.turnBasedOffColor(patternArray[1]);
                    launcher.shoot(shootingDistance);
                    actionTimer.resetTimer();
                    shootingState = 4;
                }
                break;

            case 4:
                if (actionTimer.getElapsedTime() > 1076.7) { // time to let indexer turn
                    flipper.setPosition(0);
                    actionTimer.resetTimer();
                    shootingState = 5;
                }
                break;

            case 5:
                if (actionTimer.getElapsedTime() > 676.7) { //time to flipper go up
                    flipper.setPosition(0.3778);
                    turningthing.removefirst(turningthing.SensedColorAll);
                    actionTimer.resetTimer();
                    shootingState = 6;
                }
                break;

            case 6:
                if (actionTimer.getElapsedTime() > 800) { //time for flipper to go down
                    turningthing.turnBasedOffColor(patternArray[2]);
                    actionTimer.resetTimer();
                    shootingState = 7;
                }
                break;

            case 7:
                if (actionTimer.getElapsedTime() > 1076.7) { //indexer turn itme
                    flipper.setPosition(0);
                    turningthing.removefirst(turningthing.SensedColorAll);
                    actionTimer.resetTimer();
                    shootingState = 8;
                }
                break;

            case 8:
                if (actionTimer.getElapsedTime() > 676.7) { //flipper down time
                    flipper.setPosition(0.3778);
                    isShooting = false;
                    shootingState = 9;
                }
                break;
        }
    }
    public void shootingMacro(double shootingdistance) { //can replace turnbasedoffcolor to just turn() if needed

        turningthing.turnBasedOffColor(patternArray[0]);
        actionTimer.resetTimer();
        while (actionTimer.getElapsedTime() < 776.7) { //fix times
            launcher.shoot(shootingdistance);
            turningthing.removefirst(turningthing.SensedColorAll);
        }
        flipper.setPosition(0);
        actionTimer.resetTimer();
        while (actionTimer.getElapsedTime() < 676.7){ //this is probably okay
        }
        flipper.setPosition(0.3778);
        actionTimer.resetTimer();
        while (actionTimer.getElapsedTime() < 400) {
        }
        turningthing.turnBasedOffColor(patternArray[1]);
        actionTimer.resetTimer();
        while (actionTimer.getElapsedTime() < 676.7) { //fix timings
            turningthing.indexerUpdate();
            launcher.shoot(shootingdistance);
            turningthing.removefirst(turningthing.SensedColorAll);
        }

        flipper.setPosition(0);
        actionTimer.resetTimer();
        while (actionTimer.getElapsedTime() < 676.7){ //should probably be same as 2nd
        }
        flipper.setPosition(0.3778);
        actionTimer.resetTimer();
        while (actionTimer.getElapsedTime() < 400) {
        }
        turningthing.turnBasedOffColor(patternArray[2]);
        actionTimer.resetTimer();
        while (actionTimer.getElapsedTime() < 676.7) { //fix
            launcher.shoot(shootingdistance);
            turningthing.indexerUpdate();

            turningthing.removefirst(turningthing.SensedColorAll);
        }
        flipper.setPosition(0);
        actionTimer.resetTimer();
        while (actionTimer.getElapsedTime() < 676.7){ //same as 2nd
        }
        flipper.setPosition(0.3778);
    }
    public void autoUpdate() {
        switch (pathState) {
            //these cases can also be used to check for time (if(pathTimer.getElapsedTimeSeconds() >1) {}
            //it can also be used to get the X value of the robot's position
            //IE: if(follower.getPose().getX() > 36) {}
            case 0:
                intake.setPower(-1);
                follower.followPath(detectAT);
//                if (limelightclass.result != null) {
//                    result = limelightclass.result.getFiducialResults().get(0); //might break
//                    if (result.getFiducialId() == 23) {
//                        patternArray[0] = Indexer.SensedColor.PURPLE;
//                        patternArray[1] = Indexer.SensedColor.PURPLE;
//                        patternArray[2] = Indexer.SensedColor.GREEN;
//                    } else if (result.getFiducialId() == 22) {
//                        patternArray[0] = Indexer.SensedColor.PURPLE;
//                        patternArray[1] = Indexer.SensedColor.GREEN;
//                        patternArray[2] = Indexer.SensedColor.PURPLE;
//                    } else if (result.getFiducialId() == 21) {
//                        patternArray[0] = Indexer.SensedColor.GREEN;
//                        patternArray[1] = Indexer.SensedColor.PURPLE;
//                        patternArray[2] = Indexer.SensedColor.PURPLE;
//
//                    } else {
//                        telemetry.addLine("nothing");
//                    }
//                }

                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()) {
                    pathTimer.resetTimer();
                    setPathState(2);
                }
                    break;
            case 2:
                if (pathTimer.getElapsedTime() > 200) {
                    intake.setPower(-1);
                    launcher.shoot(1.3);
                    follower.followPath(scorePreload);
                    setPathState(3);
                }
                    break;
            case 3:
                    if (!follower.isBusy()){
                    intake.setPower(-0.5);
                    limelightclass.limelight.close();
//                    shootingMacro(limelightclass.getDistance_from_apriltag( true));
                    startShooting(1.3); //uhhhhh this should probably work lowkey

                    setPathState(4);
                }
                break;
            case 4:
                if (shootingState == 9) {
//                    follower.followPath(leave1);
                    launcher.shoot(0);
                    follower.followPath(pickup1);
                    setPathState(5);
                }
                break;
            case 5:
                if (!follower.isBusy()) {
                    intake.setPower(-1);
                    follower.followPath(intake1chain, 0.3, true); //maxPower should go down probably
                    setPathState(6);
                }
                break;
            case 6:
                if (!follower.isBusy()) {
                    intake.setPower(0);
                    launcher.shoot(1.3);
                    follower.followPath(launch1chain, true);
                    setPathState(7);
                }
                break;
//
            case 7:
                if (!follower.isBusy()) {

                    intake.setPower(-0.5);
//                    shootingMacro(limelightclass.getDistance_from_apriltag(true));
                    startShooting(1.3);

//                    follower.followPath(leave1);
                    setPathState(8);
                }
                break;
            case 8:
                if (shootingState == 9) {
                    launcher.shoot(0);
                    follower.followPath(pickup2chain, true);
                    setPathState(-1);
                }
                break;
//            case 9:
//                if (!follower.isBusy()) {
//                    //run intake
//                    intake.setPower(-1);
//                    follower.followPath(intake2chain, 0.7, true);
//                    setPathState(10);
//                }
//                break;
//            case 10:
//                if (!follower.isBusy()) {
//                    follower.followPath(launch2chain, true);
//                    setPathState(11);
//                }
//                break;
//            case 11:
//                if (!follower.isBusy()) {
//                    startShooting(1.3);
//                    setPathState(-1);
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
        flipper = hardwareMap.get(Servo.class, "flipper");
        intake = hardwareMap.get(DcMotor.class, "intake");
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
//        follower.setStartingPose(startPose);
        follower.setStartingPose(startPose);
        setPathState(0);
        turningthing.SensedColorAll.set(0, Indexer.SensedColor.PURPLE); //preload
        turningthing.SensedColorAll.set(1, Indexer.SensedColor.PURPLE);
        turningthing.SensedColorAll.set(2, Indexer.SensedColor.GREEN);
    }
    @Override
    public void start() {
        limelightclass.limelight.start();
        limelightclass.limelight.setPollRateHz(100);
    }
    @Override
    public void loop() {
        if (pathState == 1 || pathState == 2) {
            limelightclass.update(true);
            results = limelightclass.result.getFiducialResults(); //might break
            if (results != null) {
                for (LLResultTypes.FiducialResult result : results) {
                    if (result.getFiducialId() == 23) {
                        patternArray = new Indexer.SensedColor[] {Indexer.SensedColor.PURPLE, Indexer.SensedColor.PURPLE, Indexer.SensedColor.GREEN};
                    } else if (result.getFiducialId() == 22) {
                        patternArray = new Indexer.SensedColor[] {Indexer.SensedColor.PURPLE, Indexer.SensedColor.GREEN, Indexer.SensedColor.PURPLE};
                    } else if (result.getFiducialId() == 21) {
                        patternArray = new Indexer.SensedColor[] {Indexer.SensedColor.GREEN, Indexer.SensedColor.PURPLE, Indexer.SensedColor.PURPLE};
                    }
                }
            }
        }
        if (!turningthing.indexer_is_moving) {
            turningthing.sensecolor();
        }
        turningthing.indexerUpdate();
        follower.update();
        shootingUpdate();
//        limelightclass.turntoAT(20);
        autoUpdate();
        telemetry.addData("path state", pathState);
        telemetry.addData("Hue value", turningthing.hsvValues1[0]);
        telemetry.addData("patterna rray", Arrays.toString(patternArray));
        telemetry.addData("SensedCOlorAll", turningthing.SensedColorAll);
        telemetry.update();
    }
}
