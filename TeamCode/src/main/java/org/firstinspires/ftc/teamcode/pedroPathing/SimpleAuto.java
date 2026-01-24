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

import org.firstinspires.ftc.teamcode.Indexer;
import org.firstinspires.ftc.teamcode.Launcher;
import org.firstinspires.ftc.teamcode.LimeLightTurretSystem;

import java.util.List;

@Autonomous
public class SimpleAuto extends OpMode {
    Limelight3A limelight;
    LimeLightTurretSystem limelightclass;
    Indexer turningthing;

    Launcher launcher;
    Follower follower;
    Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;
    Pose startPose = new Pose(56,9 , Math.PI/2); //heading in radians
    Pose endPose = new Pose(50, 24,Math.toRadians(78));
    public LLResultTypes.FiducialResult fr;
    private Path move;
    public void buildPaths() {
        move = new Path(new BezierCurve(startPose, endPose));
        move.setTangentHeadingInterpolation();
    }
    public Indexer.SensedColor[] patternArray = {
            Indexer.SensedColor.PURPLE, Indexer.SensedColor.PURPLE, Indexer.SensedColor.PURPLE
    };
    public void setPathState(int state) { //allows it to know where in tihe code it is
        pathState = state;
        pathTimer.resetTimer();
    }
    public void autoUpdate() {
        switch (pathState) {
            //these cases can also be used to check for time (if(pathTimer.getElapsedTimeSeconds() >1) {}
            //it can also be used to get the X value of the robot's position
            //IE: if(follower.getPose().getX() > 36) {}
            case 0:
                turningthing.turnBasedOffColor(patternArray[0]);
                actionTimer.resetTimer();
                while (actionTimer.getElapsedTime() < 1676.7) {
                    launcher.shoot(limelightclass.getDistance_from_apriltag(0));
                }
                turningthing.turnBasedOffColor(patternArray[1]);
                actionTimer.resetTimer();
                while (actionTimer.getElapsedTime() < 1676.7) {
                    launcher.shoot(limelightclass.getDistance_from_apriltag(0));
                }
                turningthing.turnBasedOffColor(patternArray[2]);
                actionTimer.resetTimer();
                while (actionTimer.getElapsedTime() < 1676.7) {
                    launcher.shoot(limelightclass.getDistance_from_apriltag(0));
                }
                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()) {
                    follower.followPath(move);
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
        turningthing.SensedColorAll.set(0, Indexer.SensedColor.PURPLE);
        turningthing.SensedColorAll.set(1, Indexer.SensedColor.PURPLE);
        turningthing.SensedColorAll.set(2, Indexer.SensedColor.GREEN);
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
        turningthing.indexerUpdate();
        follower.update();
        autoUpdate();
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }
}
