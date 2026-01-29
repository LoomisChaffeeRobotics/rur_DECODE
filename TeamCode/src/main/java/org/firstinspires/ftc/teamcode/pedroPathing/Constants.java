package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
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
            .mass(10.9769354)
            .forwardZeroPowerAcceleration(-43.81054521998005)
            .lateralZeroPowerAcceleration(-68.40326896087805)
            .translationalPIDFCoefficients(new PIDFCoefficients(0.15,0,0.02,0.07))
            .headingPIDFCoefficients(new PIDFCoefficients(0.85,0,0.005,0.04))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.003,0,0.00001,0,0))
            .centripetalScaling(0.00005);
            ;

    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 0.9, 1.2);


    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName("rightFront")
            .leftFrontMotorName("leftFront")
            .leftRearMotorName("leftBack")
            .rightRearMotorName("rightBack")
            .leftFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .leftRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .xVelocity(56.35359155850148)
            .yVelocity(43.18537674550935)
            ;

    public static OTOSConstants localizerConstants = new OTOSConstants()
            .hardwareMapName("sensor_otos")
            .linearUnit(DistanceUnit.INCH)
            .angleUnit(AngleUnit.RADIANS)
            .offset(new SparkFunOTOS.Pose2D(7.5,6.75,-Math.PI/2))
            .linearScalar(1.31)
            .angularScalar(0.96)
            ;


    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .OTOSLocalizer(localizerConstants)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .build();
    }
}