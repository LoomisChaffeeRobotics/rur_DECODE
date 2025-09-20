package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@TeleOp
public class FieldCentricDriving extends OpMode {

    DcMotor left_front;
    DcMotor right_front;
    DcMotor left_back;
    DcMotor right_back;

    IMU imu;
    double Yaw;
    double Pitch;
    double Roll;
    YawPitchRollAngles robotOrientation;

    double left_front_velocity = 0;
    double right_front_velocity = 0;
    double left_back_velocity = 0;
    double right_back_velocity = 0;

    @Override
    public void init() {

        left_front = hardwareMap.get(DcMotor.class, "leftFront");
        right_front = hardwareMap.get(DcMotor.class, "rightFront");
        left_back = hardwareMap.get(DcMotor.class, "leftBack");
        right_back = hardwareMap.get(DcMotor.class, "rightBack");

        left_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        right_front.setDirection(DcMotorSimple.Direction.REVERSE);
        right_back.setDirection(DcMotorSimple.Direction.REVERSE);

        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters myIMUparameter;

        myIMUparameter = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        );

        imu.initialize(myIMUparameter);

        robotOrientation = imu.getRobotYawPitchRollAngles();
        imu.resetYaw();
        Yaw = robotOrientation.getYaw();
        Pitch = robotOrientation.getPitch();
        Roll = robotOrientation.getRoll();

    }

    @Override
    public void loop() {

        robotOrientation = imu.getRobotYawPitchRollAngles();

        Yaw = robotOrientation.getYaw();
        Pitch = robotOrientation.getPitch();
        Roll = robotOrientation.getRoll();

        //movement

        right_front_velocity = (gamepad1.left_stick_y - Math.sin(Math.toRadians(Yaw)) + (gamepad1.left_stick_x - Math.sin(Math.toRadians(Yaw))));
        left_front_velocity = (gamepad1.left_stick_y + Math.sin(Math.toRadians(Yaw)) - (gamepad1.left_stick_x + Math.sin(Math.toRadians(Yaw))));

        left_back_velocity = (gamepad1.left_stick_y - Math.sin(Math.toRadians(Yaw)) + (gamepad1.left_stick_x - Math.sin(Math.toRadians(Yaw))));
        right_back_velocity = (gamepad1.left_stick_y + Math.sin(Math.toRadians(Yaw)) - (gamepad1.left_stick_x + Math.sin(Math.toRadians(Yaw))));

        //rotation

        right_front_velocity += gamepad1.right_stick_x;
        right_back_velocity += gamepad1.right_stick_x;

        left_front_velocity += -gamepad1.right_stick_x;
        left_back_velocity += -gamepad1.right_stick_x;


        right_front.setPower(right_front_velocity);
        right_back.setPower(right_back_velocity);
        left_front.setPower(left_front_velocity);
        left_back.setPower(left_back_velocity);

        telemetry.addData("Yaw", robotOrientation.getYaw());
        telemetry.update();

    }
}
