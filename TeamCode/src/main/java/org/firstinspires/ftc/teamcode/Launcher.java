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

    double[] target_ranges = {0.96, 0.98, 1, 1.02, 1.04, 1.06, 1.08, 1.1, 1.12, 1.14, 1.16, 1.18, 1.2, 1.22, 1.24, 1.26, 1.28, 1.3, 1.32, 1.34, 1.36, 1.38, 1.4, 1.42, 1.44, 1.46, 1.48, 1.5, 1.52, 1.54, 1.56, 1.58, 1.6, 1.62, 1.64, 1.66, 1.68, 1.7, 1.72, 1.74, 1.76, 1.78, 1.8, 1.82, 1.84, 1.86, 1.88, 1.9, 1.92, 1.94, 1.96, 1.98, 2, 2.02, 2.04, 2.06, 2.08, 2.1, 2.12, 2.14, 2.16, 2.18, 2.2, 2.22, 2.24, 2.26, 2.28, 2.3, 2.32, 2.34, 2.36, 2.38, 2.4, 2.42, 2.44, 2.46, 2.48, 2.5, 2.52, 2.54, 2.56, 2.58, 2.6, 2.62, 2.64, 2.66, 2.68, 2.7, 2.72, 2.74, 2.76, 2.78, 2.8, 2.82, 2.84, 2.86, 2.88, 2.9, 2.92, 2.94, 2.96, 2.98, 3, 3.02, 3.04, 3.06, 3.08, 3.1, 3.12, 3.14, 3.16, 3.18, 3.2, 3.22, 3.24, 3.26, 3.28, 3.3, 3.32, 3.34, 3.36, 3.38, 3.4, 3.42, 3.44, 3.46, 3.48, 3.5, 3.52, 3.54, 3.56, 3.58, 3.6, 3.62, 3.64, 3.66, 3.68, 3.7, 3.72, 3.74, 3.76, 3.78, 3.8, 3.82, 3.84, 3.86, 3.88, 3.9, 3.92, 3.94, 3.96, 3.98, 4, 4.02, 4.04, 4.06, 4.08, 4.1, 4.12, 4.14, 4.16, 4.18, 4.2, 4.22, 4.24, 4.26, 4.28, 4.3, 4.32, 4.34, 4.36, 4.38, 4.4, 4.42, 4.44, 4.46, 4.48, 4.5, 4.52, 4.54, 4.56, 4.58, 4.6, 4.62, 4.64, 4.66, 4.68, 4.7, 4.72, 4.74, 4.76, 4.78, 4.8, 4.82, 4.84, 4.86, 4.88, 4.9, 4.92, 4.94, 4.96, 4.98, 5, 5.02, 5.04, 5.06, 5.08, 5.1, 5.12, 5.14, 5.16, 5.18, 5.2, 5.22, 5.24, 5.26, 5.28, 5.3, 5.32, 5.34, 5.36, 5.38, 5.4, 5.42, 5.44, 5.46, 5.48};
    double[] lower_motor_speeds = {1010, 1010.5, 1011.5, 1013.3, 1014.6, 1016, 1017.4, 1018.8, 1020.2, 1021.6, 1023, 1024.4, 1025.8, 1027.2, 1028.8, 1031.9, 1035.1, 1038.3, 1041.4, 1044.6, 1047.8, 1050.9, 1054.1, 1057.2, 1060.2, 1063.2, 1066.2, 1069.2, 1072.3, 1075.3, 1078.3, 1081.3, 1085.5, 1090, 1094.6, 1099.2, 1103.8, 1108.3, 1112.9, 1117.5, 1122.1, 1126.6, 1130.6, 1134.6, 1138.5, 1142.5, 1146.4, 1150.4, 1154.3, 1158.3, 1162.2, 1166.2, 1169.9, 1173.6, 1177.2, 1180.9, 1184.6, 1188.3, 1192, 1195.7, 1199.4, 1203.5, 1208.2, 1212.9, 1217.6, 1222.2, 1226.9, 1231.6, 1236.3, 1241, 1245.9, 1251.1, 1256.3, 1261.5, 1266.7, 1271.9, 1277.1, 1282.3, 1287.8, 1293.4, 1298.9, 1304.4, 1310, 1315.5, 1321, 1327.2, 1334.2, 1341.1, 1348.1, 1355, 1362, 1368.9, 1375.9, 1384.9, 1394.6, 1404.4, 1414.2, 1423.9, 1433.7, 1443.5, 1453.2, 1463.1, 1473.2, 1483.3, 1493.4, 1503.5, 1513.6, 1523.8, 1533.9, 1544, 1554.3, 1564.6, 1575, 1585.4, 1595.7, 1606.1, 1616.5, 1627, 1637.4, 1647.8, 1658.2, 1668.7, 1681.3, 1695.8, 1710.4, 1724.9, 1739.6, 1754.9, 1770.2, 1785.5, 1800.8, 1815.3, 1828.9, 1842.5, 1856.2, 1869.8, 1883.4, 1899.5, 1919.1, 1938.7, 1958.4, 1973.6, 1988.4, 2003.2, 2018, 2035.7, 2056.9, 2078.1, 2099.3, 2120.7, 2142.5, 2164.3, 2186.2, 2208, 2236.3, 2265.7, 2295.2, 2344.4, 2390.9, 2421.1, 2451.3, 2491.6, 2540.4, 2596.1, 2645.7, 2676.9, 2708.2, 2748.4, 2809.2, 2842.3, 2875.4, 2917.2, 2971.6, 3026.9, 3083, 3164.2, 3239.7, 3304.6, 3367.4, 3437.7, 3476.5, 3514.8, 3589.8, 3669.5, 3737, 3800.4, 3864.3, 3928.4, 4000.9, 4182.2, 4306.7, 4337.1, 4484.4, 4575.1, 4654.2, 4704.7, 4742.6, 4780.6, 4827.8, 4889.6, 4944, 4977.8, 5011.5, 5045.3, 5079, 5113.6, 5148.5, 5183.4, 5218.1, 5249.9, 5281.7, 5313.6, 5345.6, 5379, 5412.4, 5445.8, 5479.2, 5511.9, 5542.7, 5573.5, 5604.3, 5635, 5660, 5681.7, 5703.3, 5725, 5746.7};
    double[] upper_motor_speeds = {1790.1, 1792.5, 1796.9, 1806.7, 1815.2, 1823.7, 1832.2, 1840.7, 1849.1, 1857.6, 1866.1, 1874.6, 1883.1, 1891.6, 1900.5, 1911.9, 1923.4, 1934.8, 1946.2, 1957.7, 1969.1, 1980.5, 1992, 2004.2, 2017, 2029.7, 2042.4, 2055.2, 2067.9, 2080.7, 2093.4, 2106.2, 2119.7, 2133.4, 2147.2, 2161, 2174.8, 2188.5, 2202.3, 2216.1, 2229.9, 2243.6, 2257.9, 2272.2, 2286.5, 2300.8, 2315.1, 2329.3, 2343.6, 2357.9, 2372.2, 2386.5, 2400.4, 2414.3, 2428.2, 2442.1, 2456, 2470, 2483.9, 2497.8, 2511.7, 2526.4, 2542.1, 2557.8, 2573.5, 2589.2, 2604.9, 2620.6, 2636.4, 2652.1, 2667.5, 2682.7, 2697.9, 2713.1, 2728.3, 2743.5, 2758.7, 2773.8, 2789.6, 2805.3, 2821, 2836.8, 2852.5, 2868.2, 2883.9, 2898.9, 2912.8, 2926.8, 2940.8, 2954.7, 2968.7, 2982.6, 2996.6, 3009.1, 3021.1, 3033.1, 3045.1, 3057.1, 3069.1, 3081.1, 3093, 3104.8, 3115.5, 3126.3, 3137, 3147.7, 3158.4, 3169.1, 3179.9, 3190.6, 3201.1, 3211.5, 3221.9, 3232.4, 3242.8, 3252.8, 3262.2, 3271.6, 3281, 3290.5, 3299.9, 3309.3, 3317, 3323.3, 3329.6, 3335.8, 3342.5, 3350.6, 3358.6, 3366.7, 3374.8, 3380.9, 3385.3, 3389.7, 3394.1, 3398.5, 3402.9, 3407.4, 3412, 3416.6, 3421.3, 3422.8, 3424, 3425.2, 3426.4, 3426.5, 3425.2, 3424, 3422.7, 3420, 3412.6, 3405.2, 3397.8, 3390.4, 3375.7, 3359.7, 3343.8, 3299.9, 3259, 3239.5, 3220, 3191.9, 3156.7, 3107.5, 3063.7, 3042.5, 3021.3, 2990.7, 2938.3, 2912.4, 2886.4, 2851.6, 2803.8, 2756.8, 2710.7, 2633.6, 2558.9, 2505.8, 2459, 2402.1, 2370.9, 2340.1, 2279.9, 2213, 2157.3, 2105.4, 2055.6, 2006.4, 1950.2, 1794.5, 1684.5, 1664.6, 1540.9, 1471.5, 1414.3, 1383.2, 1363.6, 1344, 1318.2, 1282.7, 1253.4, 1241.4, 1229.4, 1217.5, 1205.5, 1192.6, 1179.4, 1166.2, 1153.3, 1145.1, 1136.9, 1128.7, 1120.2, 1110.2, 1100.1, 1090, 1080, 1071.2, 1065.4, 1059.6, 1053.8, 1048, 1045.9, 1045.9, 1045.9, 1045.9, 1045.9};
    double[] time_in_flights = {0.4791, 0.4896, 0.4997, 0.5087, 0.518, 0.5274, 0.5367, 0.546, 0.5553, 0.5646, 0.5739, 0.5832, 0.5926, 0.6019, 0.611, 0.6193, 0.6275, 0.6358, 0.644, 0.6522, 0.6605, 0.6687, 0.6769, 0.6849, 0.6928, 0.7007, 0.7085, 0.7164, 0.7243, 0.7321, 0.74, 0.7479, 0.7552, 0.7624, 0.7695, 0.7766, 0.7838, 0.7909, 0.7981, 0.8052, 0.8124, 0.8195, 0.8266, 0.8338, 0.8409, 0.848, 0.8551, 0.8623, 0.8694, 0.8765, 0.8836, 0.8908, 0.898, 0.9052, 0.9124, 0.9196, 0.9268, 0.9341, 0.9413, 0.9485, 0.9557, 0.9627, 0.9694, 0.9762, 0.9829, 0.9896, 0.9963, 1.003, 1.0097, 1.0165, 1.0232, 1.0299, 1.0366, 1.0432, 1.0499, 1.0566, 1.0633, 1.07, 1.0767, 1.0833, 1.0899, 1.0965, 1.1031, 1.1098, 1.1164, 1.1229, 1.1294, 1.1359, 1.1423, 1.1488, 1.1552, 1.1617, 1.1682, 1.1742, 1.18, 1.1859, 1.1917, 1.1976, 1.2034, 1.2092, 1.2151, 1.2209, 1.2268, 1.2326, 1.2384, 1.2443, 1.2501, 1.256, 1.2618, 1.2676, 1.2734, 1.2792, 1.285, 1.2908, 1.2966, 1.3024, 1.3082, 1.314, 1.3198, 1.3256, 1.3315, 1.3373, 1.3425, 1.3471, 1.3517, 1.3564, 1.3609, 1.3652, 1.3695, 1.3738, 1.378, 1.3826, 1.3875, 1.3924, 1.3973, 1.4022, 1.4071, 1.4112, 1.414, 1.4169, 1.4197, 1.4241, 1.4286, 1.4331, 1.4376, 1.4411, 1.4434, 1.4458, 1.4482, 1.4505, 1.4528, 1.4551, 1.4574, 1.4597, 1.46, 1.46, 1.46, 1.4546, 1.45, 1.45, 1.45, 1.447, 1.4416, 1.4349, 1.43, 1.43, 1.43, 1.4277, 1.42, 1.42, 1.42, 1.4179, 1.4127, 1.4072, 1.4015, 1.3908, 1.3825, 1.3753, 1.3682, 1.3601, 1.36, 1.36, 1.3513, 1.3423, 1.3362, 1.3312, 1.3259, 1.3205, 1.3137, 1.2868, 1.2728, 1.2755, 1.2571, 1.2486, 1.242, 1.24, 1.24, 1.24, 1.2382, 1.2335, 1.23, 1.23, 1.23, 1.23, 1.23, 1.23, 1.23, 1.23, 1.23, 1.23, 1.23, 1.23, 1.23, 1.23, 1.23, 1.23, 1.23, 1.23, 1.23, 1.23, 1.23, 1.23, 1.2312, 1.2331, 1.2349, 1.2368, 1.2386};

    double spacing = 0.02;
//    public Servo flap1;


    double[] result = {0.0, 0.0};

    double time_in_flight_value_0 = 0.0;
    double lower_motor_value_0 = 0.0;
    double upper_motor_value_0 = 0.0;

    double time_in_flight_value_1 = 0.0;
    double lower_motor_value_1 = 0.0;
    double upper_motor_value_1 = 0.0;
    double flight_time_interporation_result = 0.0;

    double lower_motor_interporation_result = 0.0;

    double upper_motor_interporation_result = 0.0;


    public void init(HardwareMap hardwareMap, Telemetry telemetry) {
        elapsedTime = new ElapsedTime();
        indexer = new Indexer();
        indexer.init(hardwareMap, telemetry);
//        flap1 = hardwareMap.get(Servo.class, "flap1");
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        launcher2 = hardwareMap.get(DcMotorEx.class, "launcher2");
    }
    boolean timerTestStart = false;
    public void waitAuto(double seconds){
        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        while (timer.seconds() < seconds) {

        }

    }

    public double[] find_closest_x(double target_x) {

        if (target_x >= 0 && target_x < 0.96) {
            return new double[]{0, 0.96};
        }

        if (target_x >= 5.48) {
            return new double[]{5.48, 15}; //??? why
        }
//        else if (target_x >= 1.2 && target_x < 1.5) {
//            return new double[]{1.2, 1.5};
//        }
        else {
            return new double[]{(Math.ceil(target_x/spacing) * spacing) - spacing, Math.ceil(target_x/spacing) * spacing};
        }
    }

    public double interpolate_points(double targetX, double[] point1, double[] point2) {
        double slope = (point2[1] - point1[1])/(point2[0] - point1[0]);
        double x_times_slope = slope * (targetX);
        double y_intercept = point1[1] - (slope * point1[0]);

        return x_times_slope + y_intercept;
    }
    public boolean shoot(double distance) {
       
        result = find_closest_x(distance); //finds distance from apriltag - two numbers: upper and lower bound

         if (result[1] != target_ranges[0] && result[0] != target_ranges[target_ranges.length - 1]) {

            time_in_flight_value_0 = time_in_flights[(int) ((result[0]/spacing) - (target_ranges[0]/spacing))];
            lower_motor_value_0 = lower_motor_speeds[(int) ((result[0]/spacing) - (target_ranges[0]/spacing))];
            upper_motor_value_0 = upper_motor_speeds[(int) ((result[0]/spacing) - (target_ranges[0]/spacing))];
            time_in_flight_value_1 = time_in_flights[(int) ((result[0]/spacing) - (target_ranges[0]/spacing) - 1)];
            lower_motor_value_1 = lower_motor_speeds[(int) ((result[0]/spacing) - (target_ranges[0]/spacing) - 1)];
            upper_motor_value_1 = upper_motor_speeds[(int) ((result[0]/spacing) - (target_ranges[0]/spacing) - 1)];

      } else if (result[1] == target_ranges[0]) {
            time_in_flight_value_0 = 0;
            lower_motor_value_0 = 0;
            upper_motor_value_0 = 0;
            time_in_flight_value_1 = time_in_flights[0];
            lower_motor_value_1 = lower_motor_speeds[0];
            upper_motor_value_1 = upper_motor_speeds[0];
      }
       else {

           time_in_flight_value_0 = time_in_flights[time_in_flights.length - 1];
            lower_motor_value_0 = lower_motor_speeds[lower_motor_speeds.length - 1];
            upper_motor_value_0 = upper_motor_speeds[upper_motor_speeds.length - 1];


//            the statements below create line approximations; slope is based off of the "distance" between the last item
//            and the second last item.

             // will NOT be as accurate as regular look-up table--if needs to be more accurate,
//             will need to find an approximate function for the look-up table

//            ^^^^^^

//            time_in_flight_value_1 = time_in_flights[time_in_flights.length - 1]
//                    + (time_in_flights[time_in_flights.length - 1]
//                        - time_in_flights[time_in_flights.length - 2])
//                    * (distance - 5.48);
//
//
//            lower_motor_value_1 = lower_motor_speeds[lower_motor_speeds.length - 1]
//                    + (lower_motor_speeds[lower_motor_speeds.length - 1]
//                        - lower_motor_speeds[lower_motor_speeds.length - 2])
//                    * (distance - 5.48);
//
//
//            upper_motor_value_1 = upper_motor_speeds[upper_motor_speeds.length - 1]
//                    + (upper_motor_speeds[upper_motor_speeds.length - 1]
//                        - upper_motor_speeds[upper_motor_speeds.length - 2])
//                    * (distance - 5.48);

        }
//        else {
//
//            time_in_flight_value_0 = time_in_flights[0];
//
//        }

//        time_in_flight_value_0 = result[0] >= 1.5 ? time_in_flights[(int) (result[0] * 2) - 1] : time_in_flights[(result[0] == 0.5 ? 0 : 1)]; // finds the time in flight for both upper and lower bound
//        lower_motor_value_0 = result[0] >= 1.5 ? lower_motor_speeds[(int) (result[0] * 2) - 1] : lower_motor_speeds[(result[0] == 0.5 ? 0 : 1)]; // lower motor speed for upper and lower
//        upper_motor_value_0 = result[0] >= 1.5 ? upper_motor_speeds[(int) (result[0] * 2) - 1] : upper_motor_speeds[(result[0] == 0.5 ? 0 : 1)]; // upper motor speed for upper and lower
//
//        time_in_flight_value_1 = result[1] >= 2 ? time_in_flights[(int) (result[1] * 2)] : time_in_flights[(result[1] == 0.5 ? 1 : 2)]; // finds the time in flight for both upper and lower bound
//        lower_motor_value_1 = result[1] >= 2 ? lower_motor_speeds[(int) (result[1] * 2)] : lower_motor_speeds[(result[1] == 0.5 ? 1 : 2)]; // lower motor speed for upper and lower
//        upper_motor_value_1 = result[1] >= 2 ? upper_motor_speeds[(int) (result[1] * 2)] : upper_motor_speeds[(result[1] == 0.5 ? 1 : 2)]; // upper motor speed for upper and lower

        //legit no idea if thats going tow ork
        flight_time_interporation_result = interpolate_points(
                distance, // distance
                new double[]{result[0], time_in_flight_value_0}, //first (x,y) is (lower distance bound, flight time)
                new double[]{result[1], time_in_flight_value_1} // second (x,y) is (upper distance bound, flight time)
        );

        lower_motor_interporation_result = interpolate_points(
                distance,
                new double[]{result[0], lower_motor_value_0},
                new double[]{result[1], lower_motor_value_1}
        ) * (7.0 / 15.0);

        upper_motor_interporation_result = interpolate_points(
                distance,
                new double[]{result[0], upper_motor_value_0},
                new double[]{result[1], upper_motor_value_1}
        ) * (7.0 / 15.0);
        launcher.setVelocity(lower_motor_interporation_result);
        launcher2.setVelocity(upper_motor_interporation_result);
        indexer.removefirst(indexer.SensedColorAll);
//        flap1.setPosition(0.2);
//        waitAuto(0.5);
//        flap1.setPosition(0);

        return true;
    }

}
