package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.Mecanum;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.ftc.localization.constants.DriveEncoderConstants;
import com.pedropathing.ftc.localization.constants.OTOSConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

//

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(10.5)
//            .forwardZeroPowerAcceleration(-34.34311294135725)
//            .lateralZeroPowerAcceleration(-92.70228024532133)
            ;

    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1, 1);


    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName("rightFront")
            .leftFrontMotorName("leftFront")
            .leftRearMotorName("leftBack")
            .rightRearMotorName("rightBack")
//            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
//            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
//            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
//            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
//            .xVelocity(55.69855798065763)
//            .yVelocity(49.04440430657071)
            ;

    public static OTOSConstants localizerConstants = new OTOSConstants()
            .hardwareMapName("sensor_otos")
            .linearUnit(DistanceUnit.INCH)
            .angleUnit(AngleUnit.RADIANS)
            .offset(new SparkFunOTOS.Pose2D(6.5,5.5,-Math.PI/2))
            .linearScalar(1.31)
//            .angularScalar(0.978101568)
            ;


    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .OTOSLocalizer(localizerConstants)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .OTOSLocalizer(localizerConstants)
                .build();
    }
}