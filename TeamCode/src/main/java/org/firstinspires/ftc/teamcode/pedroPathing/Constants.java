package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.Mecanum;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.ftc.localization.constants.DriveEncoderConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(13.15)
            .forwardZeroPowerAcceleration(-34.34311294135725)
            .lateralZeroPowerAcceleration(-92.70228024532133);

    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1, 1);


    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName("rightFront")
            .leftFrontMotorName("leftFront")
            .leftRearMotorName("leftBack")
            .rightRearMotorName("rightBack")
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .xVelocity(55.69855798065763)
            .yVelocity(49.04440430657071);

    public static DriveEncoderConstants localizerConstants = new DriveEncoderConstants()
            .rightFrontMotorName("rightFront")
            .leftFrontMotorName("leftFront")
            .leftRearMotorName("leftBack")
            .rightRearMotorName("rightBack")
            .leftFrontEncoderDirection(Encoder.REVERSE)
            .rightFrontEncoderDirection(Encoder.FORWARD)
            .leftRearEncoderDirection(Encoder.REVERSE)
            .rightRearEncoderDirection(Encoder.FORWARD)
            .robotWidth(15.5)
            .robotLength(8.5)
            .forwardTicksToInches(0.00550543269)
            .strafeTicksToInches(0.00573934426 )
            .turnTicksToInches(0.0114);

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .driveEncoderLocalizer(localizerConstants)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .build();
    }


}