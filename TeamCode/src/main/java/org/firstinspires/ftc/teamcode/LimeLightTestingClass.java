package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;

import java.util.List;

@TeleOp
public class LimeLightTestingClass extends OpMode {

    LimeLightTurretSystem limelight;
    @Override
    public void init() {
        limelight = new LimeLightTurretSystem();
        limelight.init(hardwareMap,telemetry);
    }

    @Override
    public void loop() {
        limelight.update();
        double distance = limelight.getDistance_from_apriltag( true);
        Pose2D position = limelight.getPositionCenterRelative(true);
        telemetry.addData("Distance: ", distance);
        telemetry.addData("heading ", position.getHeading(AngleUnit.DEGREES));
        telemetry.addData("position x ", position.getX(DistanceUnit.METER));
        telemetry.addData("position y ", position.getY(DistanceUnit.METER));

        telemetry.addData("x ", limelight.botpose.getPosition().x);
        telemetry.update();
    }
}
