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

import org.firstinspires.ftc.teamcode.ColorTurningMechanismThing;
import org.firstinspires.ftc.teamcode.Launcher;

import java.util.List;
//change paths to pathcnages sometimes

@Autonomous
public class BlueAutoClassFront extends OpMode {
    Limelight3A limelight;
    ColorTurningMechanismThing turningthing;
    Launcher launcher;
    Follower follower;
    Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;
    private Pose startPose = new Pose(21.36630602782071,123.52395672333849, Math.toRadians(323)); //heading in radians
    private Pose launchPose1 =  new Pose(37.39103554868624,108.16692426584235, Math.toRadians(323));
    private Pose launchPoseMain = new Pose (45.40340030911901,97.706336939721788, Math.PI);
    private Pose controlPoint1 = new Pose(60.53786707882535,82.34930448222565,Math.PI);
    private Pose pickupPose1 = new Pose (16.024729520865534,83.68469860896445, Math.PI);
    private Pose controlPoint2 = new Pose(50.07727975270479,54.30602782071097,Math.PI);
    private Pose pickupPose2 = new Pose (17.13755795981453,60.09273570324575, Math.PI);
    private Pose launchPoint2 = new Pose(64.4210,83.8142,Math.PI);
    public LLResultTypes.FiducialResult fr;
    private Pose scorePose = new Pose(37, 72, 0);
    private Path scorePreload, pickup1, launch1, pickup2, launch2, pickupMain, score;
    private PathChain runAuto, movespec2, movespec3;
    public void buildPaths() {

        scorePreload = new Path(new BezierCurve(startPose, launchPose1));
        scorePreload.setConstantHeadingInterpolation(Math.toRadians(323));
        pickup1 =  new Path(new BezierCurve(launchPose1, controlPoint1, pickupPose1));
        pickup1.setTangentHeadingInterpolation();

        launch1 =  new Path(new BezierCurve(pickupPose1, launchPoseMain));
        launch1.setConstantHeadingInterpolation(180);

        pickup2 = new Path(new BezierCurve(launchPoseMain, controlPoint2, pickupPose2));
        pickup2.setConstantHeadingInterpolation(180);

        launch2 = new Path(new BezierCurve(pickupPose2,launchPoseMain));
        launch2.setConstantHeadingInterpolation(180);
        //Path chains are chains of paths - so you can add multiple as shown below
        //i didn't import stuff because pepa 1.0.8 which we used to build this sample code last year uses different formatting and stuff so i had to change it

    }
    public ColorTurningMechanismThing.SensedColor[] patternArray = {
            ColorTurningMechanismThing.SensedColor.PURPLE,ColorTurningMechanismThing.SensedColor.PURPLE,ColorTurningMechanismThing.SensedColor.PURPLE
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
                follower.followPath(scorePreload);
                //CANNOT do follower.followPath(scorePreload,true); because it's a path
                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()) {
                    follower.followPath(runAuto,true); //bad runatuo doesnt do anything
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
                    follower.followPath(movespec2); //all broken
                    setPathState(3);
                }
                break;
            case 3:
                if (!follower.isBusy()) {
                    follower.followPath(movespec3); //no
                    setPathState(4);
                }
                break;
            case 4:
                if (!follower.isBusy()) {
                    follower.followPath(pickup1); //change ltierally all of these
                    setPathState(5);
                }
                break;
            case 5:
                if (!follower.isBusy()) {
                    follower.followPath(score);
                    setPathState(6);
                }
                break;
            case 6:
                if (!follower.isBusy()) {
                    follower.followPath(pickupMain);
                    setPathState(7);
                }
                break;
            case 7:
                if (!follower.isBusy()) {
                    follower.followPath(score);
                    setPathState(8);
                }
                break;
            case 8:
                if (!follower.isBusy()) {
                    follower.followPath(pickupMain);
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
        turningthing = new ColorTurningMechanismThing();
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
            patternArray[0] = ColorTurningMechanismThing.SensedColor.PURPLE;
            patternArray[1] = ColorTurningMechanismThing.SensedColor.PURPLE;
            patternArray[2] = ColorTurningMechanismThing.SensedColor.GREEN;
        } else if (fr.getFiducialId() == 22) {
            telemetry.addLine("PGP");
            patternArray[0] = ColorTurningMechanismThing.SensedColor.PURPLE;
            patternArray[1] = ColorTurningMechanismThing.SensedColor.GREEN;
            patternArray[2] = ColorTurningMechanismThing.SensedColor.PURPLE;
        } else if (fr.getFiducialId() == 21) {
            telemetry.addLine("GPP");
            patternArray[0] = ColorTurningMechanismThing.SensedColor.GREEN;
            patternArray[1] = ColorTurningMechanismThing.SensedColor.PURPLE;
            patternArray[2] = ColorTurningMechanismThing.SensedColor.PURPLE;

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
