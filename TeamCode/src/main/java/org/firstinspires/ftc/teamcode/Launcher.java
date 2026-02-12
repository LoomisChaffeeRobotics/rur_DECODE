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


//    double[] target_ranges = {1.086, 1.2512, 1.4069, 1.6116, 1.8114, 2.0298, 2.297, 2.5567, 3.2474};
//    double[] lower_motor_speeds = {2107.4, 2229.5, 2360.8, 2529, 2706.5, 2889.6, 3100.4, 3316.8, 3914};
//    double[] upper_motor_speeds = {797.66, 752.05, 713.45, 679.53, 654.97, 646.78, 636.26, 632.75, 636.26};

    double[] target_ranges = {1.14,1.16,1.18,1.2,1.22,1.24,1.26,1.28,1.3,1.32,1.34,1.36,1.38,1.4,1.42,1.44,1.46,1.48,1.5,1.52,1.54,1.56,1.58,1.6,1.62,1.64,1.66,1.68,1.7,1.72,1.74,1.76,1.78,1.8,1.82,1.84,1.86,1.88,1.9,1.92,1.94,1.96,1.98,2,2.02,2.04,2.06,2.08,2.1,2.12,2.14,2.16,2.18,2.2,2.22,2.24,2.26,2.28,2.3,2.32,2.34,2.36,2.38,2.4,2.42,2.44,2.46,2.48,2.5,2.52,2.54,2.56,2.58,2.6,2.62,2.64,2.66,2.68,2.7,2.72,2.74,2.76,2.78,2.8,2.82,2.84,2.86,2.88,2.9,2.92,2.94,2.96,2.98,3,3.02,3.04,3.06,3.08,3.1,3.12,3.14,3.16,3.18,3.2,3.22,3.24,3.26,3.28,3.3,3.32,3.34,3.36,3.38,3.4,3.42,3.44,3.46,3.48,3.5,3.52,3.54,3.56,3.58,3.6,3.62,3.64,3.66,3.68,3.7,3.72,3.74,3.76,3.78,3.8,3.82,3.84,3.86,3.88,3.9,3.92,3.94,3.96,3.98,4,4.02,4.04,4.06,4.08,4.1,4.12,4.14,4.16,4.18,4.2,4.22,4.24,4.26,4.28,4.3,4.32,4.34,4.36,4.38,4.4,4.42,4.44,4.46,4.48,4.5};
    double[] lower_motor_speeds = {1115.1,1154,1184,1213.9,1244.6,1277.5,1310.5,1343.3,1372.5,1401.7,1431,1455.6,1477.2,1498.8,1520.4,1542,1563.6,1585.7,1608,1630.4,1652.8,1675.1,1697.4,1719.5,1741.6,1763.7,1785.8,1807.8,1828.9,1849.7,1870.4,1891.2,1912,1932.7,1953.9,1975.1,1996.3,2017.5,2038.7,2059.9,2080.7,2098.2,2115.8,2133.4,2151,2168.6,2186.2,2203.8,2221.4,2238.6,2255.4,2272.1,2288.9,2305.7,2322.5,2339.3,2356.1,2372.8,2388.8,2404.4,2420,2435.6,2451.2,2466.8,2482.4,2498,2513.6,2529.2,2544.8,2561.3,2578.8,2596.3,2613.7,2631.2,2648.7,2666.1,2683.6,2701.1,2716.8,2732,2747.2,2762.4,2777.7,2792.9,2808.1,2823.3,2838.6,2853.8,2868.9,2883.3,2897.7,2912.1,2926.5,2940.8,2955.2,2969.6,2984,2998.4,3012.8,3026.9,3040.9,3054.8,3068.7,3082.7,3096.6,3110.5,3124.5,3138.4,3152.4,3166.3,3180,3193.1,3206.2,3219.4,3232.5,3245.6,3258.7,3271.9,3285,3298.1,3311.3,3324.2,3336.9,3349.6,3362.2,3374.9,3387.5,3400.2,3412.9,3425.5,3438.2,3450.8,3463.5,3476.1,3488.7,3501.3,3513.8,3526.4,3539,3551.5,3564.1,3576.7,3589.2,3601.8,3614.4,3627,3639.8,3652.6,3665.4};
    double[] upper_motor_speeds = {1436.1,1409.7,1393.2,1376.7,1359.2,1338.3,1317.5,1297,1282.5,1268,1253.5,1241.8,1232.1,1222.3,1212.6,1202.9,1193.1,1184.2,1176,1167.8,1159.5,1151.3,1143.1,1134.8,1126.5,1118.2,1109.9,1101.6,1095.3,1089.6,1084,1078.3,1072.6,1066.9,1061.4,1055.9,1050.4,1045,1039.5,1034,1028.9,1026.6,1024.2,1021.8,1019.4,1017,1014.7,1012.3,1009.9,1007.7,1005.6,1003.5,1001.5,999.4,997.4,995.3,993.2,991.2,989.7,988.5,987.2,986,984.8,983.6,982.4,981.2,979.9,978.7,977.5,976.5,975.7,974.9,974.1,973.3,972.6,971.8,971,970.2,970.4,970.8,971.2,971.7,972.1,972.5,972.9,973.4,973.8,974.2,974.7,975.6,976.5,977.3,978.2,979.1,979.9,980.8,981.7,982.5,983.4,985.2,987.4,989.6,991.8,994,996.2,998.4,1000.6,1002.8,1005,1007.2,1009.6,1012.3,1015,1017.6,1020.3,1023,1025.7,1028.4,1031.1,1033.8,1036.4,1039.6,1044,1048.4,1052.8,1057.1,1061.5,1065.9,1070.3,1074.6,1079,1083.4,1087.8,1092.2,1096.7,1101.3,1105.8,1110.3,1114.9,1119.4,1123.9,1128.5,1133,1137.5,1142.1,1146.9,1152.3,1157.8,1163.3};
    double[] time_in_flights = {0.5953,0.6017,0.6083,0.6149,0.6214,0.6277,0.634,0.6403,0.6461,0.6519,0.6577,0.664,0.6706,0.6772,0.6837,0.6903,0.6969,0.703,0.7087,0.7144,0.7201,0.7258,0.7314,0.7369,0.7424,0.7479,0.7533,0.7588,0.7639,0.7689,0.7739,0.7789,0.7839,0.7889,0.7935,0.798,0.8025,0.807,0.8115,0.816,0.8205,0.8252,0.8298,0.8345,0.8391,0.8438,0.8484,0.8531,0.8577,0.8623,0.8668,0.8714,0.8759,0.8804,0.885,0.8895,0.894,0.8986,0.9031,0.9075,0.912,0.9164,0.9209,0.9253,0.9298,0.9343,0.9387,0.9432,0.9476,0.9516,0.9551,0.9585,0.962,0.9655,0.9689,0.9724,0.9758,0.9793,0.983,0.9868,0.9906,0.9943,0.9981,1.0019,1.0056,1.0094,1.0132,1.017,1.0207,1.0245,1.0283,1.0321,1.0359,1.0397,1.0435,1.0473,1.0511,1.055,1.0588,1.0624,1.0659,1.0694,1.0729,1.0765,1.08,1.0835,1.087,1.0906,1.0941,1.0976,1.1012,1.1048,1.1084,1.1121,1.1157,1.1193,1.1229,1.1266,1.1302,1.1338,1.1375,1.141,1.1444,1.1477,1.151,1.1544,1.1577,1.1611,1.1644,1.1678,1.1711,1.1744,1.1778,1.1811,1.1844,1.1878,1.1911,1.1944,1.1977,1.201,1.2044,1.2077,1.211,1.2143,1.2176,1.2209,1.2239,1.2269,1.2299,1.2329,1.2359,1.2389,1.2419,1.2449,1.2479,1.2509,1.2539,1.2569,1.2599,1.2629,1.2659,1.2689,1.2718,1.2748,1.2778,1.281,1.2844,1.2879,1.2913};


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
        return target_ranges.length; //idk
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
        } else if (result == target_ranges.length){ // changed to target ranges + 1 bc it'll never get to -1
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
