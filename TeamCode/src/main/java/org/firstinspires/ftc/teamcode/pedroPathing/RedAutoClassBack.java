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

import java.util.List;
//change paths to pathcnages sometimes

@Autonomous
public class RedAutoClassBack extends OpMode {
    Limelight3A limelight;
    public LLResultTypes.FiducialResult fr;
    Indexer turningthing;
    Launcher launcher;
    Follower follower;
    Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;
    private Pose startPose = new Pose(144-56,9 , Math.PI/2); //heading in radians
    private Pose pickupPose1 =  new Pose(144-24.0743,35.6656, Math.PI);
    private Pose controlPoint1 =  new Pose(144-65.7585,37.226, Math.PI);
    private Pose launchPoint1 = new Pose (144-61.5232,9.808, Math.PI);
    private Pose controlPoint2 = new Pose(144-79.23384,61.873,Math.PI);
    private Pose pickupPose2 = new Pose (144-23.8513,60.6315, Math.PI);
    private Pose launchPoint2 = new Pose(144-64.4210,83.8142,Math.PI);
    private Path scorePreload, pickup1, run2, pickup2, run3, pickupMain, score;
    public void buildPaths() {


        pickup1 =  new Path(new BezierCurve(startPose, controlPoint1, pickupPose1));
        pickup1.setConstantHeadingInterpolation(0);

        run2 =  new Path(new BezierCurve(pickupPose1, launchPoint1));
        run2.setConstantHeadingInterpolation(0);

        pickup2 = new Path(new BezierCurve(launchPoint1, controlPoint2, pickupPose2));
        pickup2.setConstantHeadingInterpolation(0);

        run3 = new Path(new BezierCurve(pickupPose2,launchPoint2));
        run3.setConstantHeadingInterpolation(0);
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
    public void autoUpdate() {
        switch (pathState) {
            //these cases can also be used to check for time (if(pathTimer.getElapsedTimeSeconds() >1) {}
            //it can also be used to get the X value of the robot's position
            //IE: if(follower.getPose().getX() > 36) {}
            case 0:
                follower.followPath(pickup1);
                //CANNOT do follower.followPath(scorePreload,true); because it's a path
                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()) {
                    follower.followPath(run2,true);
                    turningthing.turnBasedOfColor(patternArray[0]);
                    launcher.shoot(hardwareMap, telemetry); //actually shoots
                    turningthing.turnBasedOfColor(patternArray[1]);
                    launcher.shoot(hardwareMap, telemetry); //actually shoots
                    turningthing.turnBasedOfColor(patternArray[2]); //these make it turns
                    launcher.shoot(hardwareMap, telemetry); //actually shoots
                    setPathState(2);
                }
                break;
            case 2:
                if (!follower.isBusy()) {
//                    follower.followPath();
                    setPathState(3);
                }
                break;
            case 3:
                if (!follower.isBusy()) {
//                    follower.followPath(movespec3);
                    setPathState(4);
                }
                break;
            case 4:
                if (!follower.isBusy()) {
//                    follower.followPath(pickup1);
                    setPathState(5);
                }
                break;
            case 5:
                if (!follower.isBusy()) {
//                    follower.followPath(score);
                    setPathState(6);
                }
                break;
            case 6:
                if (!follower.isBusy()) {
//                    follower.followPath(pickupMain);
                    setPathState(7);
                }
                break;
            case 7:
                if (!follower.isBusy()) {
//                    follower.followPath(score);
                    setPathState(8);
                }
                break;
            case 8:
                if (!follower.isBusy()) {
//                    follower.followPath(pickupMain);
                    setPathState(9);
                }
                break;
            case 9:
                if (!follower.isBusy()) {
                    setPathState(-1);

                }
        }
    }
    @Override
    public void init() {
        turningthing = new Indexer();
        turningthing.init(hardwareMap, telemetry);
        launcher = new Launcher();
        launcher.init(hardwareMap, telemetry);
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();
        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        telemetry.setMsTransmissionInterval(11);

        limelight.pipelineSwitch(0);

        /*
         * Starts polling for data.  If you neglect to call start(), getLatestResult() will return null.
         */
        limelight.start();
    }
    @Override
    public void start() {
        opmodeTimer.resetTimer(); //not really used but would turn out useful
        setPathState(0);
    }
    @Override
    public void loop() {
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
        follower.update();
        autoUpdate();
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }
}
