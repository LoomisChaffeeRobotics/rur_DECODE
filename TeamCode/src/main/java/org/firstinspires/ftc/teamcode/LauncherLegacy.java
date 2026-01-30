package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class LauncherLegacy {
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

    double[] target_ranges = {1.04, 1.06, 1.08, 1.1, 1.12, 1.14, 1.16, 1.18, 1.2, 1.22, 1.24, 1.26, 1.28, 1.3, 1.32, 1.34, 1.36, 1.38, 1.4, 1.42, 1.44, 1.46, 1.48, 1.5, 1.52, 1.54, 1.56, 1.58, 1.6, 1.62, 1.64, 1.66, 1.68, 1.7, 1.72, 1.74, 1.76, 1.78, 1.8, 1.82, 1.84, 1.86, 1.88, 1.9, 1.92, 1.94, 1.96, 1.98, 2, 2.02, 2.04, 2.06, 2.08, 2.1, 2.12, 2.14, 2.16, 2.18, 2.2, 2.22, 2.24, 2.26, 2.28, 2.3, 2.32, 2.34, 2.36, 2.38, 2.4, 2.42, 2.44, 2.46, 2.48, 2.5, 2.52, 2.54, 2.56, 2.58, 2.6, 2.62, 2.64, 2.66, 2.68, 2.7, 2.72, 2.74, 2.76, 2.78, 2.8, 2.82, 2.84, 2.86, 2.88, 2.9, 2.92, 2.94, 2.96, 2.98, 3, 3.02, 3.04, 3.06, 3.08, 3.1, 3.12, 3.14, 3.16, 3.18, 3.2, 3.22, 3.24, 3.26, 3.28, 3.3, 3.32, 3.34, 3.36, 3.38, 3.4};
    double[] lower_motor_speeds = {1220, 1242.6, 1311.9, 1337.5, 1363.1, 1395.5, 1438.2, 1477.5, 1515.2, 1542.2, 1569.3, 1596.1, 1621.3, 1646.6, 1672, 1698.1, 1724.2, 1750.4, 1773.5, 1795.7, 1818, 1837.6, 1850.8, 1864, 1877.2, 1890.4, 1903.7, 1919.1, 1939.6, 1960.1, 1980.6, 2000.4, 2019.8, 2039.2, 2058.6, 2074.8, 2089.5, 2104.2, 2118.9, 2133.6, 2148.8, 2164.9, 2181, 2197.1, 2213.2, 2229.3, 2244.9, 2259.3, 2273.8, 2288.3, 2302.8, 2317.2, 2331.7, 2345.9, 2360.2, 2374.5, 2388.7, 2403, 2417.3, 2431.4, 2445.4, 2459.5, 2473.6, 2487.6, 2501.7, 2515.7, 2528.1, 2540.5, 2552.9, 2565.4, 2577.8, 2590.2, 2601.9, 2612.8, 2623.7, 2634.7, 2645.6, 2656.5, 2667.8, 2679.6, 2691.4, 2703.2, 2715, 2726.8, 2738.7, 2749.8, 2759.8, 2769.7, 2779.7, 2789.7, 2799.7, 2809.7, 2819.6, 2829.6, 2840.7, 2852.6, 2864.5, 2876.4, 2888.3, 2899.8, 2909.8, 2919.7, 2929.7, 2939.7, 2949.6, 2959.6, 2969.4, 2978.5, 2987.6, 2996.8, 3005.9, 3015, 3024.1, 3033.1, 3042.1, 3051.1, 3060.1, 3069, 3078.1};
    double[] upper_motor_speeds = {1615.1, 1601.4, 1541.1, 1526, 1510.8, 1488.7, 1456.1, 1431.8, 1409.4, 1393.3, 1377.1, 1361.4, 1348.1, 1334.8, 1321.7, 1309.6, 1297.5, 1285.5, 1277.3, 1270.5, 1263.7, 1258.6, 1257.4, 1256.3, 1255.2, 1254, 1252.9, 1250.4, 1244.9, 1239.3, 1233.8, 1230.2, 1227.7, 1225.1, 1222.6, 1221.8, 1221.8, 1221.8, 1221.8, 1221.8, 1221.8, 1221.8, 1221.8, 1221.8, 1221.8, 1221.8, 1222.4, 1224.1, 1225.7, 1227.4, 1229.1, 1230.7, 1232.7, 1235.3, 1237.9, 1240.6, 1243.2, 1245.8, 1248.4, 1251.8, 1255.2, 1258.7, 1262.1, 1265.6, 1269, 1272.5, 1276.6, 1280.7, 1284.9, 1289, 1293.1, 1297.2, 1301.2, 1305.1, 1309, 1312.9, 1316.8, 1320.7, 1325.4, 1331.1, 1336.9, 1342.6, 1348.3, 1354.1, 1359.8, 1365.8, 1372.4, 1378.9, 1385.4, 1391.9, 1398.5, 1405, 1411.5, 1418.1, 1425.2, 1432.7, 1440.2, 1447.7, 1455.2, 1462.7, 1470.3, 1478, 1485.6, 1493.2, 1500.8, 1508.5, 1516, 1523.2, 1530.4, 1537.6, 1544.8, 1552, 1559.1, 1567.4, 1576.7, 1585.9, 1595.1, 1604.3, 1615};
    double[] time_in_flights = {0.5067, 0.5149, 0.5205, 0.528, 0.5355, 0.5426, 0.549, 0.5548, 0.5605, 0.5673, 0.5742, 0.581, 0.5876, 0.5941, 0.6006, 0.6066, 0.6126, 0.6185, 0.6244, 0.6301, 0.6359, 0.6419, 0.6486, 0.6553, 0.662, 0.6687, 0.6754, 0.6817, 0.6872, 0.6927, 0.6981, 0.7033, 0.7084, 0.7134, 0.7185, 0.7239, 0.7295, 0.7351, 0.7407, 0.7462, 0.7516, 0.7567, 0.7617, 0.7667, 0.7717, 0.7768, 0.7818, 0.7867, 0.7917, 0.7966, 0.8016, 0.8066, 0.8114, 0.8161, 0.8207, 0.8254, 0.8301, 0.8347, 0.8394, 0.8438, 0.8482, 0.8526, 0.857, 0.8614, 0.8657, 0.8701, 0.8747, 0.8793, 0.8839, 0.8885, 0.8931, 0.8977, 0.9024, 0.9074, 0.9123, 0.9173, 0.9222, 0.9272, 0.9318, 0.9361, 0.9403, 0.9446, 0.9488, 0.9531, 0.9573, 0.9617, 0.9662, 0.9708, 0.9754, 0.9799, 0.9845, 0.989, 0.9936, 0.9981, 1.0022, 1.0059, 1.0096, 1.0133, 1.017, 1.0208, 1.0251, 1.0293, 1.0336, 1.0378, 1.042, 1.0463, 1.0506, 1.0551, 1.0597, 1.0643, 1.0688, 1.0734, 1.078, 1.0823, 1.0865, 1.0907, 1.0949, 1.0991, 1.1031};
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

        if (target_x >= 0 && target_x < target_ranges[0]) {
            return new double[]{0, target_ranges[0]};
        }

        if (target_x >= target_ranges[target_ranges.length - 1]) {
            return new double[]{target_ranges[target_ranges.length - 1], 15}; //??? why
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

        boolean interpolation_set = false;

        result = find_closest_x(distance); //finds distance from apriltag - two numbers: upper and lower bound
        // result is the upper and lower bounds on the x
        if (result[1] != target_ranges[0] && result[0] != target_ranges[target_ranges.length - 1]) {

            time_in_flight_value_0 = time_in_flights[(int) ((result[0] / spacing) - (target_ranges[0] / spacing))];
            lower_motor_value_0 = lower_motor_speeds[(int) ((result[0] / spacing) - (target_ranges[0] / spacing))];
            upper_motor_value_0 = upper_motor_speeds[(int) ((result[0] / spacing) - (target_ranges[0] / spacing))];
            time_in_flight_value_1 = time_in_flights[(int) ((result[0] / spacing) - (target_ranges[0] / spacing) + 1)];
            lower_motor_value_1 = lower_motor_speeds[(int) ((result[0] / spacing) - (target_ranges[0] / spacing) + 1)];
            upper_motor_value_1 = upper_motor_speeds[(int) ((result[0] / spacing) - (target_ranges[0] / spacing) + 1)];
            //value 1 and 0 are the upper and lower bounds respectively, the later code interpolates between them (i think)

        } else if (result[1] == target_ranges[0]) {

            interpolation_set = true;

//            time_in_flight_value_0 = 0;
//            lower_motor_value_0 = 0;
//            upper_motor_value_0 = 0;
//            time_in_flight_value_1 = time_in_flights[0];
//            lower_motor_value_1 = lower_motor_speeds[0];
//            upper_motor_value_1 = upper_motor_speeds[0];

            flight_time_interporation_result = time_in_flights[0]
                    + (time_in_flights[1]
                    - time_in_flights[0])
                    * (distance - target_ranges[0]);


            lower_motor_interporation_result = lower_motor_speeds[0]
                    + (lower_motor_speeds[1]
                    - lower_motor_speeds[0])
                    * (distance - target_ranges[0]);


            upper_motor_interporation_result = upper_motor_speeds[0]
                    + (upper_motor_speeds[1]
                    - upper_motor_speeds[0])
                    * (distance - target_ranges[0]);


        } else {


            interpolation_set = true;
//            time_in_flight_value_0 = time_in_flights[time_in_flights.length - 1];
//            lower_motor_value_0 = lower_motor_speeds[lower_motor_speeds.length - 1];
//            upper_motor_value_0 = upper_motor_speeds[upper_motor_speeds.length - 1];

//            the statements below create line approximations; slope is based off of the "distance" between the last item
//            and the second last item.

        // will NOT be as accurate as regular look-up table--if needs to be more accurate,
//             will need to find an approximate function for the look-up table

//            ^^^^^^

            flight_time_interporation_result = time_in_flights[time_in_flights.length - 1]
                    + (time_in_flights[time_in_flights.length - 1]
                        - time_in_flights[time_in_flights.length - 2])
                    * (distance - target_ranges[target_ranges.length - 1]);


            lower_motor_interporation_result = lower_motor_speeds[lower_motor_speeds.length - 1]
                    + (lower_motor_speeds[lower_motor_speeds.length - 1]
                        - lower_motor_speeds[lower_motor_speeds.length - 2])
                    * (distance - target_ranges[target_ranges.length - 1]);


            upper_motor_interporation_result = upper_motor_speeds[upper_motor_speeds.length - 1]
                    + (upper_motor_speeds[upper_motor_speeds.length - 1]
                        - upper_motor_speeds[upper_motor_speeds.length - 2])
                    * (distance - target_ranges[target_ranges.length - 1]);
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

        //legit no idea if thats going tow ork //it didnt :)
        if (!interpolation_set) {
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
