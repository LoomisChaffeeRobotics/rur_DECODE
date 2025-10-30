package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;

import java.util.concurrent.TimeUnit;

public class Launcher {
    ElapsedTime elapsedTime;
    public double Motor1Velocity;
    public double Motor2Velocity;
    public DcMotorEx launcher2;
    public DcMotorEx launcher;
    public Servo flap1;
    public void init(HardwareMap hardwareMap) {
        elapsedTime = new ElapsedTime();
        flap1 = hardwareMap.get(Servo.class, "flap1");
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        launcher2 = hardwareMap.get(DcMotorEx.class, "launcher2");
    }
    boolean timerTestStart = false;
    public void waitAuto(double seconds){
        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        while (timer.seconds() < seconds) {

        }

    }
    public boolean shoot() {

        Position pos = unnecessaryLimeLightTurretSystem.botpose.getPosition();

        double distance = Math.sqrt(Math.pow(pos.x, 2) + Math.pow(pos.y, 2) + Math.pow(pos.z, 2));

        //insert motorvelocity shoot calculations here

        //velocities of each launcher motor CAN BE DIFFERENT!!

        launcher.setVelocity(Motor1Velocity);
        launcher2.setVelocity(Motor2Velocity);
        flap1.setPosition(0.2);
        waitAuto(0.5);
        flap1.setPosition(0);

        return true;
    }

}
