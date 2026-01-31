package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class ArtimisLauncherStratey extends OpMode {

    DcMotorEx launcher;
    DcMotorEx launcher2;
    double launcherspeed = 0.0;
    double launcher2speed = 0.0;
    LimeLightTurretSystem limelight;
    @Override
    public void init() {
         launcher = hardwareMap.get(DcMotorEx.class, "launcher");
         launcher2 = hardwareMap.get(DcMotorEx.class, "launcher2");
         limelight = new LimeLightTurretSystem();
         limelight.init(hardwareMap, telemetry);

    }

    @Override
    public void loop() {

        limelight.update();
        if (gamepad1.dpad_up){
            launcherspeed -= 0.001;
        }
        if (gamepad1.dpad_down){
            launcherspeed += 0.001;
        }
        if (gamepad1.y){
            launcher2speed -= 0.001;
        }
        if (gamepad1.a){
            launcherspeed += 0.001;
        }

        launcher.setVelocity(launcherspeed);
        launcher2.setVelocity(launcher2speed);
        telemetry.addData("launcher set speed: ", launcherspeed);
        telemetry.addData("launcher acutual speed: ", launcher.getVelocity());
        telemetry.addData("launcher2 set speed: ", launcher2speed);
        telemetry.addData("launcher2 acutual speed: ", launcher2.getVelocity());
        telemetry.addData("limelight distance ", limelight.getDistance_from_apriltag(true));


    }
}
