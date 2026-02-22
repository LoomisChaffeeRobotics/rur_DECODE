package org.firstinspires.ftc.teamcode.pedroPathing;

import com.acmerobotics.dashboard.FtcDashboard;
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
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Indexer;
import org.firstinspires.ftc.teamcode.Launcher;
import org.firstinspires.ftc.teamcode.LimeLightTurretSystem;

import java.util.Arrays;
import java.util.List;


//only auto class that should theoretically work


@Autonomous
public class RedAutoClassBack extends OpMode {
    public List<LLResultTypes.FiducialResult> results;
    DcMotorEx launcherMotor;
    DcMotorEx launcher2Motor;
    FtcDashboard dash = FtcDashboard.getInstance();
    Telemetry t2 = dash.getTelemetry();
    DcMotor intake;
    Servo flipper;
    LimeLightTurretSystem limelightclass;
    Indexer turningthing;
    Launcher launcher;
    Follower follower;
    Timer pathTimer, actionTimer, launcherTimer;
    private int pathState;
    private int shootingState = 0;
    private boolean isShooting = false;
    private double shootingDistance;
    public Indexer.SensedColor[] patternArray = {
            Indexer.SensedColor.PURPLE, Indexer.SensedColor.PURPLE, Indexer.SensedColor.GREEN
    };
    Pose startPose = new Pose(144-56,11, Math.toRadians(90)); //heading in radians
    Pose launchPoseMain = new Pose(144-54.22,17.72, Math.toRadians(73.5));
    Pose controlPoint1 = new Pose(144-56.46,37.98,0);
    Pose pickupPose1 = new Pose (144-42.20,37.77, 0); //this is the one that changes
    Pose intakePose1 = new Pose(144-8.44, 37.77, 0);//this too


    Pose controlPoint2 = new Pose(144-58.80,58.5278,0);
    Pose pickupPose2 = new Pose (144-45.55,63.37, 0);
    Pose intake2Pose = new Pose(144-11.44, 63.37, 0);
    Pose controlPoint3 = new Pose(144-64.5429, 54.37311, 0);

    Pose leavePose = new Pose(144-56.34, 33.55, 0);
    PathChain scorePreload, pickup1,intake1, launch1, pickup2, intake2, launch2, leave;
    public void buildPaths() {

        scorePreload = follower.pathBuilder()
                .addPath(new BezierCurve(startPose, launchPoseMain))
                .setLinearHeadingInterpolation(startPose.getHeading(), launchPoseMain.getHeading(), 0.8)
                .build();
        pickup1 = follower.pathBuilder()
                .addPath(new BezierCurve(launchPoseMain, controlPoint1, pickupPose1))
                .setLinearHeadingInterpolation(launchPoseMain.getHeading(), pickupPose1.getHeading())
                .build();

        intake1 = follower.pathBuilder()
                .addPath(new BezierLine(pickupPose1, intakePose1))
                .setConstantHeadingInterpolation(pickupPose1.getHeading())
                .addParametricCallback(0.25, () -> turningthing.turn(true)) //adjust these t values when needed
                .addParametricCallback(0.6, () -> turningthing.turn(true))
                .build();
        launch1 = follower.pathBuilder()
                .addPath(new BezierCurve(intakePose1, launchPoseMain))
                .setLinearHeadingInterpolation(intakePose1.getHeading(), launchPoseMain.getHeading())
                .build();
        pickup2 = follower.pathBuilder()
                .addPath(new BezierCurve(launchPoseMain, controlPoint2, pickupPose2))
                .setLinearHeadingInterpolation(launchPoseMain.getHeading(), pickupPose2.getHeading())
                .build();
        leave = follower.pathBuilder()
                .addPath(new Path(new BezierCurve(launchPoseMain, leavePose)))
                .setLinearHeadingInterpolation(launchPoseMain.getHeading(), leavePose.getHeading())
                .build();
        intake2 = follower.pathBuilder()
                .addPath(new BezierCurve(pickupPose2, intake2Pose))
                .setConstantHeadingInterpolation(pickupPose2.getHeading())
                .addParametricCallback(0.3, () -> turningthing.turn(true))
                .addParametricCallback(0.6, () -> turningthing.turn(true))

                .build();

        launch2 = follower.pathBuilder()
                .addPath(new BezierCurve(intake2Pose, controlPoint3, launchPoseMain))
                .setLinearHeadingInterpolation(intake2Pose.getHeading(), launchPoseMain.getHeading())
                .build();

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
                launcher.shoot(3.5);
                actionTimer.resetTimer();
                shootingState = 1;
                break;

            case 1:
                if (launcher.checkIfSpunUp()) {

                    flipper.setPosition(0.42);
                    actionTimer.resetTimer();
                    shootingState = 2;
                }
                break;

            case 2:
                if (actionTimer.getElapsedTime() > 876.7) {//time for flipper to finish going up
                    flipper.setPosition(0.8117);
                    turningthing.removefirst(turningthing.SensedColorAll);
                    actionTimer.resetTimer();
                    shootingState = 3;
                }
                break;

            case 3:
                if (actionTimer.getElapsedTime() > 500) { //time for flipper to move down
                    turningthing.turnBasedOffColor(patternArray[1]);
                    launcher.shoot(shootingDistance);
                    actionTimer.resetTimer();
                    shootingState = 4;
                }
                break;

            case 4:
                if (actionTimer.getElapsedTime() > 576.7 && launcher.checkIfSpunUp()) { // time to let indexer turn
                    flipper.setPosition(0.42);
                    actionTimer.resetTimer();
                    shootingState = 5;
                }
                break;

            case 5:
                if (actionTimer.getElapsedTime() > 876.7) { //time to flipper go up
                    flipper.setPosition(0.8117);
                    turningthing.removefirst(turningthing.SensedColorAll);
                    actionTimer.resetTimer();
                    shootingState = 6;
                }
                break;

            case 6:
                if (actionTimer.getElapsedTime() > 500) { //time for flipper to go down
                    turningthing.turnBasedOffColor(patternArray[2]);
                    actionTimer.resetTimer();
                    shootingState = 7;
                }
                break;

            case 7:
                if (actionTimer.getElapsedTime() > 576.7 && launcher.checkIfSpunUp()) { //indexer turn itme
                    flipper.setPosition(0.42);
                    turningthing.removefirst(turningthing.SensedColorAll);
                    actionTimer.resetTimer();
                    shootingState = 8;
                }
                break;
            case 8:
                if (actionTimer.getElapsedTime() > 706.7) { //flipper down time
                    flipper.setPosition(0.8117);
                    isShooting = false;
                    shootingState = 9;
                }
                break;
        }
    }
    public void autoUpdate() {
        switch (pathState) {
            //these cases can also be used to check for time (if(pathTimer.getElapsedTimeSeconds() >1) {}
            //it can also be used to get the X value of the robot's position
            //IE: if(follower.getPose().getX() > 36) {}
            case 0:
                pathTimer.resetTimer();
                setPathState(1);
                break;
            case 1:
                if (pathTimer.getElapsedTime() > 100) {
                    intake.setPower(-0.5);
                    follower.followPath(scorePreload, true);
                    startShooting(3.5);
                    setPathState(2);
                }
                break;
            case 2:
                if (!follower.isBusy()) {
                    limelightclass.limelight.close();
                    setPathState(3);
                }
                break;
            case 3:
                if (shootingState == 9) {
                    intake.setPower(0);
                    follower.followPath(pickup1, true);
                    setPathState(4);
                }
                break;
            case 4:
                if (!follower.isBusy()){
                    intake.setPower(-1);
                    follower.followPath(intake1, 0.5, true);
                    setPathState(5);
                }
                break;
            case 5:
                if (!follower.isBusy()) {
                    turningthing.SensedColorAll.set(0, Indexer.SensedColor.PURPLE);
                    turningthing.SensedColorAll.set(1, Indexer.SensedColor.GREEN);
                    turningthing.SensedColorAll.set(2, Indexer.SensedColor.PURPLE);
                    intake.setPower(0.2);
                    follower.followPath(launch1, true);
                    launcherTimer.resetTimer();
                    setPathState(6);
                }
                break;
            case 6:
                if (launcherTimer.getElapsedTime() > 1300) {
                    intake.setPower(-0.5);
                    startShooting(3.5);
                    setPathState(7);
                }
                break;
//
            case 7:
                if (shootingState == 9) {
                    intake.setPower(0);
                    follower.followPath(pickup2, true);
                    setPathState(8);
                }
                break;
            case 8:
                if (!follower.isBusy()) {
                    intake.setPower(-1);
                    follower.followPath(intake2, 0.7, true);
                    setPathState(9);
                }
                break;
            case 9:
                if (!follower.isBusy()) {
                    follower.followPath(launch2, true);
                    intake.setPower(0.2);
                    turningthing.SensedColorAll.set(0, Indexer.SensedColor.PURPLE);
                    turningthing.SensedColorAll.set(1, Indexer.SensedColor.PURPLE);
                    turningthing.SensedColorAll.set(2, Indexer.SensedColor.GREEN);
                    launcherTimer.resetTimer();
                    setPathState(10);
                }
                break;
            case 10:
                if (launcherTimer.getElapsedTime() > 1700) {
                    intake.setPower(-0.5);
                    startShooting(3.5);
                    setPathState(11);
                }
                break;
            case 11:
                if (shootingState == 9) {
                    follower.followPath(leave, true);
                    setPathState(-1);
                }
                break;
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
        launcherMotor = hardwareMap.get(DcMotorEx.class, "launcher");
        launcher2Motor = hardwareMap.get(DcMotorEx.class, "launcher2");
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
        launcherTimer = new Timer();
        launcherTimer.resetTimer();
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
        if (pathState == 0 || pathState == 1) {
            limelightclass.updateAuto(false);
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
        turningthing.errorCalc();
        turningthing.indexerUpdate();
        follower.update();
        shootingUpdate();
//        limelightclass.turntoAT(20);
        autoUpdate();
        t2.addData("path state", pathState);
        t2.addData("shooting state", shootingState);
        t2.addData("roboX", follower.getPose().getX());
        t2.addData("roboY", follower.getPose().getY());
        t2.addData("robot heading", follower.getPose().getHeading());
        t2.addData("SensedCOlorAll", turningthing.SensedColorAll);
        t2.update();
        telemetry.addData("path state", pathState);
        telemetry.addData("Hue value", turningthing.hsvValues1[0]);
        telemetry.addData("patterna rray", Arrays.toString(patternArray));
        telemetry.addData("SensedCOlorAll", turningthing.SensedColorAll);
        telemetry.update();
    }
}
