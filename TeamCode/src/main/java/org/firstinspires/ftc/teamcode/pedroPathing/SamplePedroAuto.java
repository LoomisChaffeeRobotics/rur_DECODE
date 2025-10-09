package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine; //this is a straight line
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Autonomous
public class SamplePedroAuto extends OpMode {
    Follower follower;
    Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;
    private Pose startPose = new Pose(12,48 , 0); //heading in radians

    private Pose scorePose = new Pose(37, 72, 0);
    private Path scorePreload, pickup1, pickupMain, score;
    private PathChain movespec1, movespec2, movespec3;
    public void buildPaths() {
        scorePreload = new Path(new BezierCurve(startPose, scorePose));
        scorePreload.setConstantHeadingInterpolation(0);
        //Path chains are chains of paths - so you can add multiple as shown below
//        movespec1 = follower.pathBuilder()
//                .addPath(new Path(new BezierCurve(put points here)))
        //i didn't import stuff because pepa 1.0.8 which we used to build this sample code last year uses different formatting and stuff so i had to change it

    }
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
                    follower.followPath(movespec1);
                    //CAN do follower.followPath(scorePreload,true); because it's a pathChain - this makes it "hold the endpoint" idk what that means
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
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();
        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);
    }
    @Override
    public void start() {
        opmodeTimer.resetTimer(); //not really used but would turn out useful
        setPathState(0);
    }
    @Override
    public void loop() {
        follower.update();
        autoUpdate();
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }
}
