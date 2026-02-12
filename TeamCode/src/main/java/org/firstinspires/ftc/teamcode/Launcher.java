package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.sleep;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class Launcher {
    ElapsedTime elapsedTime;
    Indexer indexer;
    public double MotorVelocity;
    public DcMotorEx launcher2;
    public DcMotorEx launcher;
    public static double launcherF = 16;
    public static double launcherP = 50;
    public static double launcherI = 0.9;
    public static double launcherD = 0;
    public PIDFCoefficients launcherPIDF;
    public double launcherError;
    public double launcher2Error;

//    double velocity_towards_target = 0;
//    double time_in_air = 0;
//    double[] between_point_1 = {0, 0};
//    double[] between_point_2 = {0, 0};


//    double[] target_ranges = {0.5, 1.2, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0};
//    double[] lower_motor_speeds = {1017,1032,1159,1405,1615,1819,2029,2246,2454,2697};
//    double[] upper_motor_speeds = {2152,1714,1600,1628,1747,1870,2054,2230,2386,2549};
//    double[] time_in_flights = {1.05, 0.9, 0.86, 0.92, 1.01, 1.08, 1.19, 1.27, 1.32, 1.34};

    // ^^^ old ^^^


    double[] target_ranges = {1.086, 1.2512, 1.4069, 1.6116, 1.8114, 2.0298, 2.297, 2.5567, 3.2474};
    double[] lower_motor_speeds = {2107.4, 2229.5, 2360.8, 2529, 2706.5, 2889.6, 3100.4, 3316.8, 3914};
    double[] upper_motor_speeds = {797.66, 752.05, 713.45, 679.53, 654.97, 646.78, 636.26, 632.75, 636.26};

    public double lower_motor_interporation_result;
    public double upper_motor_interporation_result;
    public void init(HardwareMap hardwareMap, Telemetry telemetry) {
        elapsedTime = new ElapsedTime();
        indexer = new Indexer();
        indexer.init(hardwareMap, telemetry);
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        launcher2 = hardwareMap.get(DcMotorEx.class, "launcher2");
    }
    public boolean checkIfSpunUp() {
        if (Math.abs((launcher2.getVelocity()) - upper_motor_interporation_result * (7.0/15.0)) < 30 && Math.abs((launcher.getVelocity()) - lower_motor_interporation_result * (7.0/15.0)) < 30) {
            return true;
        } else {
            return false;
        }
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
        if (distance == 0) {
            launcher.setVelocity(0);
            launcher2.setVelocity(0);
            return true;
        }
        boolean interpolation_set = false;
        launcherPIDF = new PIDFCoefficients(launcherP, launcherI, launcherD, launcherF);
        launcher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, launcherPIDF);
        launcher2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, launcherPIDF);

        int result = find_index(distance); //finds idnex
            //value 1 and 0 are the upper and lower bounds respectively, the later code interpolates between them (i think)


        if (result == 0){
            lower_motor_interporation_result = interpolate_points(distance, result+1, lower_motor_speeds);
            upper_motor_interporation_result = interpolate_points(distance, result+1, upper_motor_speeds);
        } else if (result == target_ranges.length + 1){ // changed to target ranges + 1 bc it'll never get to -1
            lower_motor_interporation_result = interpolate_points(distance, result-1, lower_motor_speeds);
            upper_motor_interporation_result = interpolate_points(distance, result-1, upper_motor_speeds);
        } else{
            lower_motor_interporation_result = interpolate_points(distance, result, lower_motor_speeds);
            upper_motor_interporation_result = interpolate_points(distance, result, upper_motor_speeds);

        }
        launcherError = launcher.getVelocity() - lower_motor_interporation_result*(7.0/15.0);
        launcher2Error = launcher2.getVelocity() - upper_motor_interporation_result*(7.0/15.0);
        launcher.setVelocity(lower_motor_interporation_result*(7.0/15.0));
        launcher2.setVelocity(upper_motor_interporation_result*(7.0/15.0));
//        indexer.removefirst(indexer.SensedColorAll); //remove? color sensor stuff must be tested before messing with this
//        flap1.setPosition(0.2);
//        waitAuto(0.5);
//        flap1.setPosition(0);

        return true;
    }

}
