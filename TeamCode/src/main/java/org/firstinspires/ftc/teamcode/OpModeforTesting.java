package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class OpModeforTesting extends OpMode {
    sparkFunMethodsClass sparkfun;

    public SparkFunOTOS.Pose2D pos;
    public SparkFunOTOS.Pose2D velocity;
    @Override
    public void init() {
        sparkfun = new sparkFunMethodsClass();
        sparkfun.init(hardwareMap, telemetry);
        sparkfun.configureOtos(telemetry);
    }

    @Override
    public void loop() {
        pos = sparkfun.myOtos.getPosition();  //in meters and radians
        velocity = sparkfun.myOtos.getVelocity(); // like meters per second  possibly
        telemetry.addData("pos", sparkfun.pos);
        telemetry.addData("velocity", sparkfun.velocity);
        telemetry.update();
    }
}