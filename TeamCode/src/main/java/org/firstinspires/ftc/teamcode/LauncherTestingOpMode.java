package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class LauncherTestingOpMode extends OpMode {
    //formerly blahblahblah2

    DcMotorEx encoder;
    LimeLightTurretSystem limelight;

    @Override
    public void init() {
        encoder = hardwareMap.get(DcMotorEx.class,"encoder"); // TODO: check name
        encoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        limelight = new LimeLightTurretSystem();
        limelight.init(hardwareMap, telemetry);
    }

    @Override
    public void loop() {
        telemetry.addData("encoder: ",encoder.getCurrentPosition());
        limelight.turntoAT();
        telemetry.addData("result",limelight.yesorno);
        telemetry.addData("tx",limelight.angleerror);
        telemetry.addData("powertotrutrretpsi", limelight.turretSpin.getPower());
        telemetry.update();
    }
}
