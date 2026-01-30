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



//don't use, haven't modified yet and will not in time for qual


@Autonomous
public class BlueAutoClassBack extends OpMode {
    LLResultTypes.FiducialResult result;
    DcMotor intake;
    Servo flipper;
    Limelight3A limelight;
    LimeLightTurretSystem limelightclass;
    Indexer turningthing;
    Launcher launcher;
    Follower follower;
    Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;
    Pose startPose = new Pose(56,9 , Math.PI); //heading in radians
    Pose pickupPose1 =  new Pose(39.5,35.6656, Math.PI);
    Pose controlPoint1 =  new Pose(65.7585,37.226, Math.PI);
    Pose intakePose1 = new Pose(9.0743, 35.6656, Math.PI);
    Pose launchPoint1 = new Pose (61.5232,9.808, Math.PI);
    Pose controlPoint2 = new Pose(79.23384,61.873,Math.PI);
    Pose pickupPose2 = new Pose (39.5,60.6315, Math.PI);
    Pose intakePose2 = new Pose(9.0743, 60.6315, Math.PI);
    Pose launchPoint2 = new Pose(64.4210,83.8142,Math.PI);
    public LLResultTypes.FiducialResult fr;
    private Path pickup1, run2, pickup2, run3;
    private PathChain intake1chain, intake2chain;
    public void buildPaths() {

        pickup1 =  new Path((new BezierCurve(startPose, controlPoint1, pickupPose1)));
        pickup1.setConstantHeadingInterpolation(Math.PI);

        intake1chain = follower.pathBuilder()
                .addPath(new BezierCurve(pickupPose1, intakePose1))
                .setConstantHeadingInterpolation(Math.PI)
                .addParametricCallback(0.3, () -> turningthing.turn(true))
                .addParametricCallback(0.6, () -> turningthing.turn(true))
                .build();

        run2 =  new Path(new BezierCurve(intakePose1, launchPoint1));
        run2.setConstantHeadingInterpolation(Math.PI);

        pickup2 = new Path(new BezierCurve(launchPoint1, controlPoint2, pickupPose2));
        pickup2.setConstantHeadingInterpolation(Math.PI);

        intake2chain = follower.pathBuilder()
                .addPath(new BezierCurve(pickupPose2, intakePose2))
                .setConstantHeadingInterpolation(Math.PI)
                .addParametricCallback(0.3, () -> turningthing.turn(true))
                .addParametricCallback(0.6, () -> turningthing.turn(true))
                .build();


        run3 = new Path(new BezierCurve(intakePose2,launchPoint2));
        run3.setConstantHeadingInterpolation(Math.PI);
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
                turningthing.turnBasedOffColor(patternArray[0]);
                launcher.shoot(limelightclass.getDistance_from_apriltag( false));
                turningthing.turnBasedOffColor(patternArray[1]);
                launcher.shoot(limelightclass.getDistance_from_apriltag( false));
                turningthing.turnBasedOffColor(patternArray[2]);
                launcher.shoot(limelightclass.getDistance_from_apriltag( false));
                follower.followPath(pickup1);
                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()) {
                    intake.setPower(1);
                    // run intake, do it here please :) 👍
                    follower.updateCallbacks();
                    follower.followPath(intake1chain, 0.7, true);
                    setPathState(2);
                }
                break;
            case 2:
                if (!follower.isBusy()) {
                    intake.setPower(0);
                    follower.followPath(run2);
                    setPathState(3);
                }
                break;
            case 3:
                if (!follower.isBusy()) {
                    turningthing.turnBasedOffColor(patternArray[0]);
                    launcher.shoot(limelightclass.getDistance_from_apriltag( false)); //actually shoots
                    turningthing.turnBasedOffColor(patternArray[1]);
                    launcher.shoot(limelightclass.getDistance_from_apriltag( false)); //actually shoots
                    turningthing.turnBasedOffColor(patternArray[2]); //these make it turns
                    launcher.shoot(limelightclass.getDistance_from_apriltag( false)); //actually shoots
                    follower.followPath(pickup2);
                    setPathState(4);
                }
                break;
            case 4:
                if (!follower.isBusy()) {
                    //run intake
                    intake.setPower(1);
                    follower.updateCallbacks();
                    follower.followPath(intake2chain, 0.7, true);
                    setPathState(5);
                }
                break;
            case 5:
                if (!follower.isBusy()) {
                    intake.setPower(0);
                    follower.followPath(run3);
                    setPathState(6);
                }
                break;
            case 6:
                if (!follower.isBusy()) {
                    turningthing.turnBasedOffColor(patternArray[0]);
                    launcher.shoot(limelightclass.getDistance_from_apriltag( false)); //actually shoots
                    turningthing.turnBasedOffColor(patternArray[1]);
                    launcher.shoot(limelightclass.getDistance_from_apriltag( false)); //actually shoots
                    turningthing.turnBasedOffColor(patternArray[2]); //these make it turns
                    launcher.shoot(limelightclass.getDistance_from_apriltag( false)); //actually shoots
                    setPathState(-1);
                }
                break;
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
        LLResult result = limelight.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
        fr = fiducialResults.get(0);
        telemetry.addData("Fiducial", "ID: %d, Family: %s, X: %.2f, Y: %.2f", fr.getFiducialId(), fr.getFamily(), fr.getTargetXDegrees(), fr.getTargetYDegrees());
        if (fr.getFiducialId() == 23) {
            telemetry.addLine("PPG");
            patternArray[0] = Indexer.SensedColor.PURPLE;
            patternArray[1] = Indexer.SensedColor.PURPLE;
            patternArray[2] = Indexer.SensedColor.GREEN;
        } else if (fr.getFiducialId() == 22) {
            telemetry.addLine("PGP");
            patternArray[0] = Indexer.SensedColor.PURPLE;
            patternArray[1] = Indexer.SensedColor.GREEN;
            patternArray[2] = Indexer.SensedColor.PURPLE;
        } else if (fr.getFiducialId() == 21) {
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
