package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.control.PIDFCoefficients;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp

@Config
public class SFA3BTClass extends OpMode {
    /** SPARK FUN AS AN APRILTAG BACKUP TESTING CLASS */
    public static double SFKf = 0.00;
    public static double SFKp = 0.02;
    public static double SFKi = 0;
    public static double SFKd = 0.0;
    public static double TXKf = 0;
    public static double TXKp = 0.04;
    public static double TXKi = 0;
    public static double TXKd = 0.001;
    FtcDashboard dash = FtcDashboard.getInstance();
    Telemetry t2 = dash.getTelemetry();

    LimeLightTurretSystem limelight;
    DcMotor left_front;
    DcMotor right_front;
    DcMotor left_back;
    DcMotor right_back;
    IMU imu;


    @Override
    public void init() {
        limelight = new LimeLightTurretSystem();
        limelight.init(hardwareMap,telemetry);
        left_front = hardwareMap.get(DcMotor.class, "leftFront");
        right_front = hardwareMap.get(DcMotor.class, "rightFront");
        left_back = hardwareMap.get(DcMotor.class, "leftBack");
        right_back = hardwareMap.get(DcMotor.class, "rightBack");
        left_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_front.setDirection(DcMotorSimple.Direction.FORWARD);
        right_front.setDirection(DcMotorSimple.Direction.FORWARD);
        left_back.setDirection(DcMotorSimple.Direction.FORWARD);
        right_back.setDirection(DcMotorSimple.Direction.FORWARD);
        imu = hardwareMap.get(IMU.class, "imu2");
        IMU.Parameters myIMUparameter;

        myIMUparameter = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
                        RevHubOrientationOnRobot.UsbFacingDirection.RIGHT
                )
        );

        imu.initialize(myIMUparameter);
        imu.resetYaw();
    }


    @Override
    public void loop() {

        limelight.update(true);
        limelight.turretControl.setCoefficients(new PIDFCoefficients(SFKp, SFKi, SFKd, SFKf));
        limelight.turretFineControl.setCoefficients(new PIDFCoefficients(TXKp, TXKi, TXKd, TXKf));
        double thing = limelight.turntoAT(true);

        t2.addData("encoder postiton", limelight.encoder.getCurrentPosition());
        t2.addData("turret power", limelight.turretSpin.getPower());
        t2.addData("target thihng", 0);
        t2.addData("error", limelight.turretFineControl.getError());
        t2.addData("current thing", limelight.limelight.getLatestResult().getTx());
        t2.addData("turret pos", limelight.turretPosition);
        t2.addData("heading", -limelight.roboPoseRelativeToAT.h - Math.toDegrees(Math.atan(limelight.roboPoseRelativeToAT.x/limelight.roboPoseRelativeToAT.y)));
        t2.addData("thing", thing);
        telemetry.addData("atseen", limelight.ATSeen);
        telemetry.addData("pos", limelight.turretControl.getTargetPosition());
        telemetry.addData("pos", limelight.turretPosition);
        t2.update();

        fieldCentricDriving();
    }

    public void fieldCentricDriving(){ // Done!

        //inputs
        double x = gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;
        double rx = gamepad1.right_stick_x;
        double Yaw = imu.getRobotYawPitchRollAngles().getYaw();

        //rotation
        double x_altered = (x * Math.cos(Math.toRadians(Yaw))) + (y * Math.sin(Math.toRadians(Yaw)));
        double y_altered = (y * Math.cos(Math.toRadians(Yaw))) - (x * Math.sin(Math.toRadians(Yaw)));

        //adding it all together
        double right_front_velocity = y_altered - x_altered - rx;
        double left_front_velocity = y_altered + x_altered + rx;
        double left_back_velocity = y_altered - x_altered + rx;
        double right_back_velocity = y_altered + x_altered - rx;

        //denominator (keep ratio of speeds)
        double denominator = 1d;
        double max = Math.max(Math.max(Math.abs(right_back_velocity),Math.abs(right_front_velocity)), Math.max(Math.abs(left_back_velocity),Math.abs(left_front_velocity)));

        if(max > 1){
            denominator = max;
        }
        if (gamepad1.a){
            denominator *= 3;
        }

        right_front.setPower(right_front_velocity/denominator);
        right_back.setPower(right_back_velocity/denominator);
        left_front.setPower(left_front_velocity/denominator);
        left_back.setPower(left_back_velocity/denominator);


    }
}
