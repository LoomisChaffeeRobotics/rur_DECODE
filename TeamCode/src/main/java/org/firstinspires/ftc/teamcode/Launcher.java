package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Launcher {
    ElapsedTime elapsedTime;
    Indexer indexer;
    public double MotorVelocity;
    public DcMotorEx launcher2;
    public DcMotorEx launcher;

//    double velocity_towards_target = 0;
//    double time_in_air = 0;
//    double[] between_point_1 = {0, 0};
//    double[] between_point_2 = {0, 0};


//    double[] target_ranges = {0.5, 1.2, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0};
//    double[] lower_motor_speeds = {1017,1032,1159,1405,1615,1819,2029,2246,2454,2697};
//    double[] upper_motor_speeds = {2152,1714,1600,1628,1747,1870,2054,2230,2386,2549};
//    double[] time_in_flights = {1.05, 0.9, 0.86, 0.92, 1.01, 1.08, 1.19, 1.27, 1.32, 1.34};

    // ^^^ old ^^^

    double[] target_ranges = {1.2359, 1.3256, 1.453, 1.6205, 1.8114, 2.0505, 2.2717, 2.5062, 2.785, 2.9954, 2.9956};
    double[] lower_motor_speeds = {1391.5, 1473.2, 1578.9,1699, 1846.3, 2004.8, 2139.3, 2265.8, 2389.1, 2494.8, 2496.4};
    double[] upper_motor_speeds = {1384.6, 1333.4, 1282.2, 1242.4,1208.3, 1193.1, 1208.3, 1248.1, 1316.4, 1369.5, 1367.6};
    double[] time_in_flights = {0.48, 0.51,0.55, 0.6, 0.65, 0.71, 0.76, 0.81, 0.87, 0.91, 0.91};




    public void init(HardwareMap hardwareMap, Telemetry telemetry) {
        elapsedTime = new ElapsedTime();//??
        indexer = new Indexer();//??
        indexer.init(hardwareMap, telemetry);//??
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        launcher2 = hardwareMap.get(DcMotorEx.class, "launcher2");
    }

    public int find_index(double target_x) {

        for (int i = 0; i < target_ranges.length; i++){
            if (target_ranges[i] > target_x){
                return i;
            }
        }
        return target_ranges.length + 1; //idk
    }

    public double interpolate_points(double targetX, int index, double[] data) {
        double slope = (data[index] - data[index-1])/(target_ranges[index] - target_ranges[index-1]);
        double x_times_slope = slope * (targetX);
        double y_intercept = data[index] - (slope * target_ranges[index]);

        return x_times_slope + y_intercept;
    }
    public boolean shoot(double distance) {

        boolean interpolation_set = false;

        int result = find_index(distance); //finds idnex
            //value 1 and 0 are the upper and lower bounds respectively, the later code interpolates between them (i think)

        double lower_motor_interporation_result;
        double upper_motor_interporation_result;

        if (result == 0){
            lower_motor_interporation_result = interpolate_points(distance, result+1, lower_motor_speeds);
            upper_motor_interporation_result = interpolate_points(distance, result+1, upper_motor_speeds);
        } else if (result == -1){
            lower_motor_interporation_result = interpolate_points(distance, result-1, lower_motor_speeds);
            upper_motor_interporation_result = interpolate_points(distance, result-1, upper_motor_speeds);
        } else{
            lower_motor_interporation_result = interpolate_points(distance, result, lower_motor_speeds);
            upper_motor_interporation_result = interpolate_points(distance, result, upper_motor_speeds);

        }

        launcher.setVelocity(lower_motor_interporation_result);
        launcher2.setVelocity(upper_motor_interporation_result);
        indexer.removefirst(indexer.SensedColorAll); //remove? color sensor stuff must be tested before messing with this
//        flap1.setPosition(0.2);
//        waitAuto(0.5);
//        flap1.setPosition(0);

        return true;
    }

}
