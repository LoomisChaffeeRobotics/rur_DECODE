package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.concurrent.TimeUnit;

public class Launcher {
    ElapsedTime elapsedTime;
    unnecessaryLimeLightTurretSystem limelightsystem;
    public double MotorVelocity;
    public DcMotorEx launcher2;
    public DcMotorEx launcher;

    double velocity_towards_target = 0;
    double time_in_air = 0;
    double[] between_point_1 = {0, 0};
    double[] between_point_2 = {0, 0};

    double[] target_ranges = {0.5, 1.2, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0};
    double[] lower_motor_speeds = {1017,1032,1159,1405,1615,1819,2029,2246,2454,2697};
    double[] upper_motor_speeds = {2152,1714,1600,1628,1747,1870,2054,2230,2386,2549};
    double[] time_in_flights = {1.05, 0.9, 0.86, 0.92, 1.01, 1.08, 1.19, 1.27, 1.32, 1.34};

//    public Servo flap1;


    double[] result = {0.0, 0.0};

    double time_in_flight_index = 0.0;
    double lower_motor_index = 0.0;
    double upper_motor_index = 0.0;

    double flight_time_interporation_result = 0.0;

    double lower_motor_interporation_result = 0.0;

    double upper_motor_interporation_result = 0.0;


    public void init(HardwareMap hardwareMap, Telemetry telemetry) {
        elapsedTime = new ElapsedTime();
//        flap1 = hardwareMap.get(Servo.class, "flap1");
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        launcher2 = hardwareMap.get(DcMotorEx.class, "launcher2");
        limelightsystem = new unnecessaryLimeLightTurretSystem();
        limelightsystem.init(hardwareMap, telemetry);
    }
    boolean timerTestStart = false;
    public void waitAuto(double seconds){
        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        while (timer.seconds() < seconds) {

        }

    }

    public double[] find_closest_x(double target_x) {

        if (target_x >= 0.5 && target_x < 1.2) {
            return new double[]{0.5, 1.2};
        }
        else if (target_x >= 1.2 && target_x < 1.5) {
            return new double[]{1.2, 1.5};
        }
        else {
            return new double[]{(Math.ceil(target_x * 2) / 2) - 0.5, Math.ceil(target_x * 2) / 2};
        }
    }

    public double interpolate_points(double targetX, double[] point1, double[] point2) {
        double slope = (point1[0] - point2[0])/(point1[1] - point2[0]);
        double x_times_slope = slope * (targetX - point1[0]);
        double y_intercept = point1[1] - (slope * point1[0]);

        return x_times_slope + y_intercept;
    }
    public boolean shoot() {

        velocity_towards_target = FieldCentricDriving.velocity_vector * Math.cos(limelightsystem.botposeangle);

        result = find_closest_x(limelightsystem.distance_from_apriltag);

        time_in_flight_index = result[0] >= 1.5 ? time_in_flights[(int)(result[0] * 2) - 1] : (result[0] == 0.5 ? 0 : 1);
        lower_motor_index = result[0] >= 1.5 ? lower_motor_speeds[(int)(result[0] * 2) - 1] : (result[0] == 0.5 ? 0 : 1);
        upper_motor_index = result[0] >= 1.5 ? upper_motor_speeds[(int)(result[0] * 2) - 1] : (result[0] == 0.5 ? 0 : 1);

        flight_time_interporation_result = interpolate_points(
                limelightsystem.distance_from_apriltag,
                new double[] {result[0], time_in_flight_index},
                new double[] {result[1], time_in_flight_index + 1}
        );

        lower_motor_interporation_result = interpolate_points(
                limelightsystem.distance_from_apriltag,
                new double[] {result[0], lower_motor_index},
                new double[] {result[1], lower_motor_index + 1}
        ) * (7.0/15.0);

        upper_motor_interporation_result = interpolate_points(
                limelightsystem.distance_from_apriltag,
                new double[] {result[0], upper_motor_index},
                new double[] {result[1], upper_motor_index + 1}
        ) * (7.0/15.0);

        //insert motorvelocity shoot calculations here
        launcher.setVelocity(lower_motor_interporation_result);
        launcher2.setVelocity(upper_motor_interporation_result);
//        flap1.setPosition(0.2);
//        waitAuto(0.5);
//        flap1.setPosition(0);

        return true;
    }

}
