package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Indexer;
import org.firstinspires.ftc.teamcode.Launcher;
import org.firstinspires.ftc.teamcode.LimeLightTurretSystem;

import java.util.List;
@Autonomous
public class BlueAutoClassFront extends OpMode {
    LLResultTypes.FiducialResult result;
    DcMotor intake;
    Servo flipper;
    LimeLightTurretSystem limelightclass;
    Limelight3A limelight;
    Indexer turningthing;
    Launcher launcher;
    Follower follower;
    Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;
    Pose startPose = new Pose(21.36630602782071,123.52395672333849, Math.toRadians(143)); //heading in radians
    Pose launchPose1 =  new Pose(37.39103554868624,108.16692426584235, Math.toRadians(143));
    Pose launchPoseMain = new Pose (45.40340030911901,97.706336939721788, Math.PI);
    Pose controlPoint1 = new Pose(60.53786707882535,82.34930448222565,Math.PI);
    Pose pickupPose1 = new Pose (39.5,83.68469860896445, Math.PI);
    Pose intake1 = new Pose(16, 83.68469860896445, Math.PI);
    Pose controlPoint2 = new Pose(50.07727975270479,54.30602782071097,Math.PI);
    Pose pickupPose2 = new Pose (39.5,60.09273570324575, Math.PI);
    Pose intake2 = new Pose(9.137, 60.092735, Math.PI);
    public LLResultTypes.FiducialResult fr;
    private Path scorePreload, pickup1, launch1, pickup2, launch2;
    private PathChain intake1chain, intake2chain;
    public void buildPaths() {

        scorePreload = new Path(new BezierCurve(startPose, launchPose1));
        scorePreload.setConstantHeadingInterpolation(Math.toRadians(143));

        pickup1 =  new Path(new BezierCurve(launchPose1, controlPoint1, pickupPose1));
        pickup1.setTangentHeadingInterpolation();

        intake1chain = follower.pathBuilder()
                .addPath(new BezierCurve(pickupPose1, intake1))
                .setConstantHeadingInterpolation(Math.PI)
                .addParametricCallback(0.3, () -> turningthing.turn(true))
                .addParametricCallback(0.6, () -> turningthing.turn(true))
                .build();
        launch1 =  new Path(new BezierCurve(intake1, launchPoseMain));
        launch1.setConstantHeadingInterpolation(Math.PI);

        pickup2 = new Path(new BezierCurve(launchPoseMain, controlPoint2, pickupPose2));
        pickup2.setConstantHeadingInterpolation(Math.PI);

        intake2chain = follower.pathBuilder()
                .addPath(new BezierCurve(pickupPose2, intake2))
                .setConstantHeadingInterpolation(Math.PI)
                .addParametricCallback(0.3, () -> turningthing.turn(true))
                .addParametricCallback(0.6, () -> turningthing.turn(true))
                .build();
        launch2 = new Path(new BezierCurve(intake2,launchPoseMain));
        launch2.setConstantHeadingInterpolation(Math.PI);
        //Path chains are chains of paths - so you can add multiple as shown below
        //i didn't import stuff because pepa 1.0.8 which we used to build this sample code last year uses different formatting and stuff so i had to change it

    }
    public Indexer.SensedColor[] patternArray = {
            Indexer.SensedColor.PURPLE, Indexer.SensedColor.PURPLE, Indexer.SensedColor.PURPLE
    };
    public void setPathState(int state) { //allows it to know where in tihe code it is
        pathState = state;
        pathTimer.resetTimer();
    }
    public void shootingMacro(double shootingdistance) { //can replace turnbasedoffcolor to just turn() if needed

        turningthing.turnBasedOffColor(patternArray[1]);
        actionTimer.resetTimer();
        while (actionTimer.getElapsedTime() < 2676.7) {
            launcher.shoot(shootingdistance);
        }
        flipper.setPosition(0.3189);
        actionTimer.resetTimer();
        while (actionTimer.getElapsedTime() < 476.7){
        }
        flipper.setPosition(0.6741);
        turningthing.turnBasedOffColor(patternArray[1]);
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
        turningthing.turnBasedOffColor(patternArray[2]);
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
                follower.followPath(scorePreload);
                shootingMacro(limelightclass.getDistance_from_apriltag(0));
                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()) {
                    follower.followPath(pickup1);
                    setPathState(2);
                }
                break;
            case 2:
                if (!follower.isBusy()) {
                    intake.setPower(1);
                    follower.updateCallbacks();
                    follower.followPath(intake1chain, 0.7, true); //all broken
                    setPathState(3);
                }
                break;
            case 3:
                if (!follower.isBusy()) {
                    follower.followPath(launch1);
                    setPathState(4);
                }
                break;
            case 4:
                if (!follower.isBusy()) {
                    shootingMacro(limelightclass.getDistance_from_apriltag(0));
                    follower.followPath(pickup2);
                    setPathState(5);
                }
                break;
            case 5:
                if (!follower.isBusy()) {
                    //run intake
                    intake.setPower(1);
                    follower.updateCallbacks();
                    follower.followPath(intake2chain, 0.7, true);
                    setPathState(6);
                }
                break;
            case 6:
                if (!follower.isBusy()) {
                    follower.followPath(launch2);
                    setPathState(7);
                }
                break;
            case 7:
                if (!follower.isBusy()) {
                    shootingMacro(limelightclass.getDistance_from_apriltag(0));
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
        intake = hardwareMap.get(DcMotor.class, "intake");
        turningthing = new Indexer();
        turningthing.init(hardwareMap, telemetry);
        launcher = new Launcher();
        launcher.init(hardwareMap, telemetry);
        limelightclass = new LimeLightTurretSystem();
        limelightclass.init(hardwareMap, telemetry);
        pathTimer = new Timer();
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
    public void init_loop() {
        if (limelightclass.result != null) {
            result = limelightclass.result.getFiducialResults().get(0);
        } else {return;}
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
        turningthing.sensecolor();
        turningthing.indexerUpdate();
        follower.update();
        limelightclass.turntoAT(20);
        autoUpdate();
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }
}
