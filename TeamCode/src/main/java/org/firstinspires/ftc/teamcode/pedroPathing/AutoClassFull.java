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

import org.firstinspires.ftc.teamcode.ColorTurningMechanism;
import org.firstinspires.ftc.teamcode.Launcher;

import java.util.List;

@Autonomous
public class AutoClassFull extends OpMode {
    Limelight3A limelight;
    ColorTurningMechanism turningthing;
    Launcher launcher;
    Follower follower;
    Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;
    private Pose startPose = new Pose(12,48 , 0); //heading in radians
    public LLResultTypes.FiducialResult fr;
    private Pose scorePose = new Pose(37, 72, 0);
    private Path scorePreload, pickup1, pickupMain, score;
    private PathChain movespec1, movespec2, movespec3;
    public void buildPaths() {
        scorePreload = new Path(new BezierCurve(startPose, scorePose));
        scorePreload.setConstantHeadingInterpolation(0);
        //Path chains are chains of paths - so you can add multiple as shown below
        movespec1 = follower.pathBuilder()
                .addPath(new Path(new BezierCurve(startPose, scorePose)))
                .build();
        //i didn't import stuff because pepa 1.0.8 which we used to build this sample code last year uses different formatting and stuff so i had to change it

    }
    public ColorTurningMechanism.SensedColor[] patternArray = {
            ColorTurningMechanism.SensedColor.PURPLE,ColorTurningMechanism.SensedColor.PURPLE,ColorTurningMechanism.SensedColor.PURPLE
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
                    follower.followPath(movespec1,true);
                    turningthing.turnBasedOffColor(patternArray[0]);
                    turningthing.turnBasedOffColor(patternArray[1]);
                    turningthing.turnBasedOffColor(patternArray[2]); //these make it turns
                    launcher.shoot(); //actually shoots
                    setPathState(2);
                }
                break;
            case 2:
                if (!follower.isBusy()) {
                    follower.followPath(movespec2);
                    setPathState(3);
                }
                break;
            case 3:
                if (!follower.isBusy()) {
                    follower.followPath(movespec3);
                    setPathState(4);
                }
                break;
            case 4:
                if (!follower.isBusy()) {
                    follower.followPath(pickup1);
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
        turningthing = new ColorTurningMechanism();
        turningthing.init(telemetry, hardwareMap);
        launcher = new Launcher();
        launcher.init(hardwareMap);
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
                patternArray[0] = ColorTurningMechanism.SensedColor.PURPLE;
                patternArray[1] = ColorTurningMechanism.SensedColor.PURPLE;
                patternArray[2] = ColorTurningMechanism.SensedColor.GREEN;
            } else if (fr.getFiducialId() == 22) {
                telemetry.addLine("PGP");
                patternArray[0] = ColorTurningMechanism.SensedColor.PURPLE;
                patternArray[1] = ColorTurningMechanism.SensedColor.GREEN;
                patternArray[2] = ColorTurningMechanism.SensedColor.PURPLE;
            } else if (fr.getFiducialId() == 21) {
                telemetry.addLine("GPP");
                patternArray[0] = ColorTurningMechanism.SensedColor.GREEN;
                patternArray[1] = ColorTurningMechanism.SensedColor.PURPLE;
                patternArray[2] = ColorTurningMechanism.SensedColor.PURPLE;
                
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
