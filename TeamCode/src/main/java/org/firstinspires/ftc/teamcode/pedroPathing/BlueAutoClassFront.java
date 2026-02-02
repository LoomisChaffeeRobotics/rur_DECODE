package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
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



//only auto class that should theoretically work


@Autonomous
public class BlueAutoClassFront extends OpMode {
    public LLResultTypes.FiducialResult result;
    DcMotor intake;
    Servo flipper;
    LimeLightTurretSystem limelightclass;
    Indexer turningthing;
    Launcher launcher;
    Follower follower;
    Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;
    Pose startPose = new Pose(21.36630602782071,123.52395672333849, Math.toRadians(143)); //heading in radians
    Pose detectPose = new Pose(34.68712123,107.7528485, Math.toRadians(45)); //to detect apriltag, same as launchposeMain but different heading
    Pose launchPoseMain = new Pose(45.40340030911901,97.706336939721788, 2.3911);
    Pose controlPoint1 = new Pose(66.7958,83.5325,Math.PI);
    Pose pickupPose1 = new Pose (43.5,84.6, Math.PI);
    Pose intake1 = new Pose(18, 84.6, Math.PI);
    Pose leavePose = new Pose(32, 75, Math.toRadians(137));
    Pose controlPoint2 = new Pose(68.80518,58.5278,Math.PI);
    Pose pickupPose2 = new Pose (39.5,60.09273570324575, Math.PI);
    Pose intake2 = new Pose(9.137, 60.092735, Math.PI);
    private Path detectAT, scorePreload, pickup1, launch1, leave1, pickup2, launch2;
    private PathChain intake1chain, intake2chain;
    public void buildPaths() {

        detectAT = new Path(new BezierCurve(startPose, detectPose));
        detectAT.setLinearHeadingInterpolation(Math.toRadians(143), Math.toRadians(65));

        scorePreload = new Path(new BezierCurve(detectPose, launchPoseMain));
        scorePreload.setLinearHeadingInterpolation(Math.toRadians(65), Math.toRadians(137), 0.8);
        pickup1 =  new Path(new BezierCurve(launchPoseMain, controlPoint1, pickupPose1));
        pickup1.setLinearHeadingInterpolation(Math.toRadians(137), Math.PI);

        intake1chain = follower.pathBuilder()
                .addPath(new BezierCurve(pickupPose1, intake1))
                .setConstantHeadingInterpolation(Math.PI)
                .addParametricCallback(0.2, () -> turningthing.turn(true)) //adjust these t values when needed
                .addParametricCallback(0.6, () -> turningthing.turn(true))
                .build();
        launch1 =  new Path(new BezierCurve(intake1, launchPoseMain));
        launch1.setLinearHeadingInterpolation(Math.PI, Math.toRadians(137));
        leave1 = new Path(new BezierCurve(launchPoseMain, leavePose));
        leave1.setConstantHeadingInterpolation(Math.toRadians(137));

        pickup2 = new Path(new BezierCurve(launchPoseMain, controlPoint2, pickupPose2));

        intake2chain = follower.pathBuilder()
                .addPath(new BezierCurve(pickupPose2, intake2))
                .setConstantHeadingInterpolation(Math.PI)
                .addParametricCallback(0.3, () -> turningthing.turn(true))
                .addParametricCallback(0.6, () -> turningthing.turn(true))
                .build();
        launch2 = new Path(new BezierCurve(intake2,launchPoseMain));
        //Path chains are chains of paths - so you can add multiple as shown below

    }
    public Indexer.SensedColor[] patternArray = {
            Indexer.SensedColor.PURPLE, Indexer.SensedColor.PURPLE, Indexer.SensedColor.GREEN
    };
    public void setPathState(int state) { //state machine :)
        pathState = state;
        pathTimer.resetTimer();
    }
    public void shootingMacro(double shootingdistance) { //can replace turnbasedoffcolor to just turn() if needed

//        turningthing.turnBasedOffColor(patternArray[0]);
        actionTimer.resetTimer();
        while (actionTimer.getElapsedTime() < 2776.7) { //fix times
            launcher.shoot(shootingdistance);
        }
        flipper.setPosition(0);
        actionTimer.resetTimer();
        while (actionTimer.getElapsedTime() < 676.7){ //this is probably okay
        }
        flipper.setPosition(0.3778);
        actionTimer.resetTimer();
        while (actionTimer.getElapsedTime() < 400) {
        }
//        turningthing.turnBasedOffColor(patternArray[1]);
        turningthing.turn(false);
        actionTimer.resetTimer();
        while (actionTimer.getElapsedTime() < 1676.7) { //fix timings
            turningthing.indexerUpdate();
            launcher.shoot(shootingdistance);
        }

        flipper.setPosition(0);
        actionTimer.resetTimer();
        while (actionTimer.getElapsedTime() < 676.7){ //should probably be same as 2nd
        }
        flipper.setPosition(0.3778);
        actionTimer.resetTimer();
        while (actionTimer.getElapsedTime() < 400) {
        }
//        turningthing.turnBasedOffColor(patternArray[2]);
        turningthing.turn(false);
        actionTimer.resetTimer();
        while (actionTimer.getElapsedTime() < 1676.7) { //fix
            launcher.shoot(shootingdistance);
            turningthing.indexerUpdate();
        }
        flipper.setPosition(0);
        actionTimer.resetTimer();
        while (actionTimer.getElapsedTime() < 876.7){ //same as 2nd
        }
        flipper.setPosition(0.3778);
    }
    public void autoUpdate() {
        switch (pathState) {
            //these cases can also be used to check for time (if(pathTimer.getElapsedTimeSeconds() >1) {}
            //it can also be used to get the X value of the robot's position
            //IE: if(follower.getPose().getX() > 36) {}
            case 0:
                intake.setPower(-0.2);
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
                    follower.followPath(scorePreload);
                    setPathState(2);
                }
                    break;
            case 2:
                    if (!follower.isBusy()){
//                    shootingMacro(limelightclass.getDistance_from_apriltag( true));
                    shootingMacro(1.3); //uhhhhh this should probably work lowkey

                    setPathState(3);
                }
                break;
            case 3:
                if (!follower.isBusy()) {
//                    follower.followPath(leave1);
                    follower.followPath(pickup1);
                    setPathState(4);
                }
                break;
            case 4:
                if (!follower.isBusy()) {
                    intake.setPower(-1);
                    follower.followPath(intake1chain, 0.3, true); //maxPower should go down probably
                    setPathState(5);
                }
                break;
            case 5:
                if (!follower.isBusy()) {
                    intake.setPower(0);
                    follower.followPath(launch1);
                    follower.turnToDegrees(137);
                    setPathState(6);
                }
                break;
//
            case 6:
                if (!follower.isBusy()) {
//                    shootingMacro(limelightclass.getDistance_from_apriltag(true));
                    shootingMacro(1.3);

                    //STOP HERE FOR QUAL UNLESS EXTRA TIME
                    //GO FIX STUFF THAT ARE BROKEN
//                    follower.followPath(leave1);
                    follower.followPath(pickup2);
                    setPathState(-1);
                }
                break;
//            case 6:
//                if (!follower.isBusy()) {
//                    //run intake
//                    intake.setPower(1);
//                    follower.updateCallbacks();
//                    follower.followPath(intake2chain, 0.5, true);
//                    setPathState(7);
//                }
//                break;
//            case 7:
//                if (!follower.isBusy()) {
//                    follower.followPath(launch2);
//                    setPathState(8);
//                }
//                break;
//            case 8:
//                if (!follower.isBusy()) {
//                    shootingMacro(limelightclass.getDistance_from_apriltag(0, true));
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
        follower.setStartingPose(startPose);
        setPathState(0);
        turningthing.SensedColorAll.set(0, Indexer.SensedColor.PURPLE); //preload
        turningthing.SensedColorAll.set(1, Indexer.SensedColor.PURPLE);
        turningthing.SensedColorAll.set(2, Indexer.SensedColor.GREEN);

    }
    @Override
    public void loop() {
////        turningthing.sensecolor();
        turningthing.indexerUpdate();
        limelightclass.update();
        follower.update();
//        limelightclass.turntoAT(20);
        autoUpdate();
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.addData("patternArray", patternArray);
        telemetry.update();
    }
}
