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

//    double[] target_ranges = {1.04, 1.06, 1.08, 1.1, 1.12, 1.14, 1.16, 1.18, 1.2, 1.22, 1.24, 1.26, 1.28, 1.3, 1.32, 1.34, 1.36, 1.38, 1.4, 1.42, 1.44, 1.46, 1.48, 1.5, 1.52, 1.54, 1.56, 1.58, 1.6, 1.62, 1.64, 1.66, 1.68, 1.7, 1.72, 1.74, 1.76, 1.78, 1.8, 1.82, 1.84, 1.86, 1.88, 1.9, 1.92, 1.94, 1.96, 1.98, 2, 2.02, 2.04, 2.06, 2.08, 2.1, 2.12, 2.14, 2.16, 2.18, 2.2, 2.22, 2.24, 2.26, 2.28, 2.3, 2.32, 2.34, 2.36, 2.38, 2.4, 2.42, 2.44, 2.46, 2.48, 2.5, 2.52, 2.54, 2.56, 2.58, 2.6, 2.62, 2.64, 2.66, 2.68, 2.7, 2.72, 2.74, 2.76, 2.78, 2.8, 2.82, 2.84, 2.86, 2.88, 2.9, 2.92, 2.94, 2.96, 2.98, 3, 3.02, 3.04, 3.06, 3.08, 3.1, 3.12, 3.14, 3.16, 3.18, 3.2, 3.22, 3.24, 3.26, 3.28, 3.3, 3.32, 3.34, 3.36, 3.38, 3.4};
//    double[] lower_motor_speeds = {1220, 1242.6, 1311.9, 1337.5, 1363.1, 1395.5, 1438.2, 1477.5, 1515.2, 1542.2, 1569.3, 1596.1, 1621.3, 1646.6, 1672, 1698.1, 1724.2, 1750.4, 1773.5, 1795.7, 1818, 1837.6, 1850.8, 1864, 1877.2, 1890.4, 1903.7, 1919.1, 1939.6, 1960.1, 1980.6, 2000.4, 2019.8, 2039.2, 2058.6, 2074.8, 2089.5, 2104.2, 2118.9, 2133.6, 2148.8, 2164.9, 2181, 2197.1, 2213.2, 2229.3, 2244.9, 2259.3, 2273.8, 2288.3, 2302.8, 2317.2, 2331.7, 2345.9, 2360.2, 2374.5, 2388.7, 2403, 2417.3, 2431.4, 2445.4, 2459.5, 2473.6, 2487.6, 2501.7, 2515.7, 2528.1, 2540.5, 2552.9, 2565.4, 2577.8, 2590.2, 2601.9, 2612.8, 2623.7, 2634.7, 2645.6, 2656.5, 2667.8, 2679.6, 2691.4, 2703.2, 2715, 2726.8, 2738.7, 2749.8, 2759.8, 2769.7, 2779.7, 2789.7, 2799.7, 2809.7, 2819.6, 2829.6, 2840.7, 2852.6, 2864.5, 2876.4, 2888.3, 2899.8, 2909.8, 2919.7, 2929.7, 2939.7, 2949.6, 2959.6, 2969.4, 2978.5, 2987.6, 2996.8, 3005.9, 3015, 3024.1, 3033.1, 3042.1, 3051.1, 3060.1, 3069, 3078.1};
//    double[] upper_motor_speeds = {1615.1, 1601.4, 1541.1, 1526, 1510.8, 1488.7, 1456.1, 1431.8, 1409.4, 1393.3, 1377.1, 1361.4, 1348.1, 1334.8, 1321.7, 1309.6, 1297.5, 1285.5, 1277.3, 1270.5, 1263.7, 1258.6, 1257.4, 1256.3, 1255.2, 1254, 1252.9, 1250.4, 1244.9, 1239.3, 1233.8, 1230.2, 1227.7, 1225.1, 1222.6, 1221.8, 1221.8, 1221.8, 1221.8, 1221.8, 1221.8, 1221.8, 1221.8, 1221.8, 1221.8, 1221.8, 1222.4, 1224.1, 1225.7, 1227.4, 1229.1, 1230.7, 1232.7, 1235.3, 1237.9, 1240.6, 1243.2, 1245.8, 1248.4, 1251.8, 1255.2, 1258.7, 1262.1, 1265.6, 1269, 1272.5, 1276.6, 1280.7, 1284.9, 1289, 1293.1, 1297.2, 1301.2, 1305.1, 1309, 1312.9, 1316.8, 1320.7, 1325.4, 1331.1, 1336.9, 1342.6, 1348.3, 1354.1, 1359.8, 1365.8, 1372.4, 1378.9, 1385.4, 1391.9, 1398.5, 1405, 1411.5, 1418.1, 1425.2, 1432.7, 1440.2, 1447.7, 1455.2, 1462.7, 1470.3, 1478, 1485.6, 1493.2, 1500.8, 1508.5, 1516, 1523.2, 1530.4, 1537.6, 1544.8, 1552, 1559.1, 1567.4, 1576.7, 1585.9, 1595.1, 1604.3, 1615};
//    double[] time_in_flights = {0.5067, 0.5149, 0.5205, 0.528, 0.5355, 0.5426, 0.549, 0.5548, 0.5605, 0.5673, 0.5742, 0.581, 0.5876, 0.5941, 0.6006, 0.6066, 0.6126, 0.6185, 0.6244, 0.6301, 0.6359, 0.6419, 0.6486, 0.6553, 0.662, 0.6687, 0.6754, 0.6817, 0.6872, 0.6927, 0.6981, 0.7033, 0.7084, 0.7134, 0.7185, 0.7239, 0.7295, 0.7351, 0.7407, 0.7462, 0.7516, 0.7567, 0.7617, 0.7667, 0.7717, 0.7768, 0.7818, 0.7867, 0.7917, 0.7966, 0.8016, 0.8066, 0.8114, 0.8161, 0.8207, 0.8254, 0.8301, 0.8347, 0.8394, 0.8438, 0.8482, 0.8526, 0.857, 0.8614, 0.8657, 0.8701, 0.8747, 0.8793, 0.8839, 0.8885, 0.8931, 0.8977, 0.9024, 0.9074, 0.9123, 0.9173, 0.9222, 0.9272, 0.9318, 0.9361, 0.9403, 0.9446, 0.9488, 0.9531, 0.9573, 0.9617, 0.9662, 0.9708, 0.9754, 0.9799, 0.9845, 0.989, 0.9936, 0.9981, 1.0022, 1.0059, 1.0096, 1.0133, 1.017, 1.0208, 1.0251, 1.0293, 1.0336, 1.0378, 1.042, 1.0463, 1.0506, 1.0551, 1.0597, 1.0643, 1.0688, 1.0734, 1.078, 1.0823, 1.0865, 1.0907, 1.0949, 1.0991, 1.1031};
//    double spacing = 0.02;
    double[] target_ranges = {1, 1.02, 1.04, 1.06, 1.08, 1.1, 1.12, 1.14, 1.16, 1.18, 1.2, 1.22, 1.24, 1.26, 1.28, 1.3, 1.32, 1.34, 1.36, 1.38, 1.4, 1.42, 1.44, 1.46, 1.48, 1.5, 1.52, 1.54, 1.56, 1.58, 1.6, 1.62, 1.64, 1.66, 1.68, 1.7, 1.72, 1.74, 1.76, 1.78, 1.8, 1.82, 1.84, 1.86, 1.88, 1.9, 1.92, 1.94, 1.96, 1.98, 2, 2.02, 2.04, 2.06, 2.08, 2.1, 2.12, 2.14, 2.16, 2.18, 2.2, 2.22, 2.24, 2.26, 2.28, 2.3, 2.32, 2.34, 2.36, 2.38, 2.4, 2.42, 2.44, 2.46, 2.48, 2.5, 2.52, 2.54, 2.56, 2.58, 2.6, 2.62, 2.64, 2.66, 2.68, 2.7, 2.72, 2.74, 2.76, 2.78, 2.8, 2.82, 2.84, 2.86, 2.88, 2.9, 2.92, 2.94, 2.96, 2.98, 3, 3.02, 3.04, 3.06, 3.08, 3.1, 3.12, 3.14, 3.16, 3.18, 3.2, 3.22, 3.24, 3.26, 3.28, 3.3, 3.32, 3.34, 3.36, 3.38, 3.4, 3.42, 3.44, 3.46, 3.48, 3.5, 3.52, 3.54, 3.56, 3.58, 3.6, 3.62, 3.64, 3.66, 3.68, 3.7, 3.72, 3.74, 3.76, 3.78, 3.8, 3.82, 3.84, 3.86, 3.88, 3.9, 3.92, 3.94, 3.96, 3.98, 4};
    double[] lower_motor_speeds = {1345.2, 1399.2, 1451.3, 1502.1, 1553.2, 1606.1, 1661.1, 1761, 1798.3, 1835.5, 1872.7, 1917.3, 1958.6, 1984.2, 2009.8, 2035.3, 2058.8, 2082.3, 2105.9, 2129.4, 2154.6, 2183.7, 2212.9, 2242, 2269.5, 2294.1, 2318.7, 2343.3, 2367.9, 2392.5, 2417.4, 2442.4, 2467.4, 2492.4, 2517.4, 2542.4, 2566.3, 2584.9, 2603.4, 2622, 2640.5, 2659.1, 2677.6, 2696.2, 2715.3, 2737.2, 2759.1, 2780.9, 2802.8, 2824.7, 2846.5, 2869.3, 2894.6, 2919.9, 2945.2, 2970.5, 2995.8, 3019.1, 3036.9, 3054.7, 3072.6, 3090.4, 3108.3, 3126.1, 3143.9, 3161.8, 3179.6, 3197.3, 3214.9, 3232.4, 3249.9, 3267.5, 3285, 3302.5, 3320.1, 3337.6, 3355.1, 3372.7, 3390, 3407.2, 3424.4, 3441.6, 3458.8, 3476, 3493.2, 3510.4, 3527.6, 3542.9, 3557.9, 3572.9, 3588, 3603, 3618, 3633.1, 3648.1, 3662.2, 3676.2, 3690.2, 3704.2, 3718.2, 3732.2, 3746.2, 3760.2, 3774.2, 3785.9, 3797.5, 3809.2, 3820.8, 3832.5, 3844.1, 3855.8, 3867.4, 3879.1, 3890.7, 3902.4, 3914, 3925.6, 3937.2, 3948.8, 3960.4, 3972, 3983.6, 3995.2, 4006.7, 4017.9, 4029.2, 4040.4, 4051.6, 4062.9, 4074.1, 4085.4, 4096.6, 4107.9, 4119.9, 4132, 4144.1, 4156.2, 4168.3, 4180.4, 4192, 4202.9, 4213.7, 4224.5, 4235.3, 4246.1, 4256.9, 4265.9, 4274.4, 4282.8, 4291.2, 4299.7};
    double[] upper_motor_speeds = {1485.9, 1440.3, 1396.9, 1355.1, 1313.3, 1271.4, 1229.2, 1143.6, 1117, 1090.4, 1063.8, 1031.1, 1001.7, 988.1, 974.4, 960.7, 948.2, 935.6, 923.1, 910.6, 897.6, 883.7, 869.8, 855.9, 843, 832.1, 821.1, 810.2, 799.2, 788.2, 779.1, 770.4, 761.8, 753.2, 744.6, 735.9, 728, 723.8, 719.5, 715.2, 710.9, 706.7, 702.4, 698.1, 693.4, 686.8, 680.3, 673.7, 667.1, 660.5, 654, 647.2, 640, 632.8, 625.5, 618.3, 611.1, 605.3, 603.2, 601.1, 599, 596.9, 594.8, 592.7, 590.7, 588.6, 586.5, 584.9, 583.8, 582.7, 581.7, 580.6, 579.6, 578.5, 577.5, 576.4, 575.3, 574.3, 574, 574, 574, 574, 574, 574, 574, 574, 574, 574.8, 575.6, 576.4, 577.3, 578.1, 579, 579.8, 580.7, 583.9, 587.5, 591, 594.6, 598.2, 601.8, 605.4, 608.9, 612.5, 618.1, 623.7, 629.4, 635, 640.7, 646.4, 652, 657.7, 663.3, 669, 674.6, 680.1, 685.5, 690.9, 696.4, 701.8, 707.2, 712.6, 718, 723.9, 731.3, 738.7, 746, 753.4, 760.7, 768.1, 775.4, 782.8, 790.2, 797.3, 804.5, 811.6, 818.8, 825.9, 833.1, 840.7, 849.4, 858.1, 866.8, 875.5, 884.2, 893, 900.4, 907.6, 914.7, 921.9, 929};
//    double[] target_ranges = {1, 1.02, 1.04, 1.06, 1.08, 1.1, 1.12, 1.14, 1.16, 1.18, 1.2, 1.22, 1.24, 1.26, 1.28, 1.3, 1.32, 1.34, 1.36, 1.38, 1.4, 1.42, 1.44, 1.46, 1.48, 1.5, 1.52, 1.54, 1.56, 1.58, 1.6, 1.62, 1.64, 1.66, 1.68, 1.7, 1.72, 1.74, 1.76, 1.78, 1.8, 1.82, 1.84, 1.86, 1.88, 1.9, 1.92, 1.94, 1.96, 1.98, 2, 2.02, 2.04, 2.06, 2.08, 2.1, 2.12, 2.14, 2.16, 2.18, 2.2, 2.22, 2.24, 2.26, 2.28, 2.3, 2.32, 2.34, 2.36, 2.38, 2.4, 2.42, 2.44, 2.46, 2.48, 2.5, 2.52, 2.54, 2.56, 2.58, 2.6, 2.62, 2.64, 2.66, 2.68, 2.7, 2.72, 2.74, 2.76, 2.78, 2.8, 2.82, 2.84, 2.86, 2.88, 2.9, 2.92, 2.94, 2.96, 2.98, 3, 3.02, 3.04, 3.06, 3.08, 3.1, 3.12, 3.14, 3.16, 3.18, 3.2, 3.22, 3.24, 3.26, 3.28, 3.3, 3.32, 3.34, 3.36, 3.38, 3.4, 3.42, 3.44, 3.46, 3.48, 3.5, 3.52, 3.54, 3.56, 3.58, 3.6, 3.62, 3.64, 3.66, 3.68, 3.7, 3.72, 3.74, 3.76, 3.78, 3.8, 3.82, 3.84, 3.86, 3.88, 3.9, 3.92, 3.94, 3.96, 3.98, 4, 4.02, 4.04, 4.06, 4.08, 4.1, 4.12, 4.14, 4.16, 4.18, 4.2, 4.22, 4.24, 4.26, 4.28, 4.3, 4.32, 4.34, 4.36, 4.38, 4.4, 4.42, 4.44, 4.46, 4.48, 4.5, 4.52, 4.54, 4.56, 4.58, 4.6, 4.62, 4.64, 4.66, 4.68, 4.7, 4.72, 4.74, 4.76, 4.78, 4.8};
//    double[] lower_motor_speeds = {1027.9, 1028.8, 1030, 1032.4, 1035.2, 1038, 1040.8, 1043.6, 1046.4, 1049.2, 1052, 1054.8, 1057.6, 1060.4, 1063.2, 1066, 1068.9, 1071.7, 1074.5, 1077.3, 1080.1, 1083.4, 1088.1, 1092.8, 1097.6, 1102.3, 1107, 1111.8, 1116.5, 1121.2, 1126, 1130.7, 1135.4, 1140.2, 1144.9, 1151.3, 1157.8, 1164.2, 1170.6, 1177, 1183.4, 1189.8, 1196.2, 1202.6, 1209, 1215.5, 1221.9, 1228.3, 1234.7, 1242.5, 1251.3, 1260.2, 1269, 1277.8, 1286.7, 1295.5, 1304.4, 1313.2, 1322, 1330.9, 1340.8, 1352.3, 1363.8, 1375.3, 1386.8, 1398.3, 1409.8, 1421.3, 1432.8, 1444.3, 1455.8, 1467.3, 1479.4, 1493.8, 1508.2, 1522.7, 1537.1, 1551.5, 1565.9, 1580.3, 1595.3, 1614.3, 1633.2, 1652.2, 1671.1, 1690.1, 1709, 1728.3, 1757.1, 1785.9, 1814.6, 1843.4, 1873.2, 1919.6, 1966, 2012, 2057.6, 2103.2, 2170.3, 2251.7, 2307.4, 2353.4, 2399.4, 2441.9, 2484.4, 2526.9, 2569.4, 2614, 2660.2, 2706.4, 2751.9, 2794.5, 2837.1, 2879.7, 2922.3, 2960.7, 2999.2, 3037.7, 3076.1, 3107.8, 3138.6, 3169.4, 3200.3, 3231.1, 3261.7, 3290, 3318.2, 3346.5, 3374.8, 3403, 3431.3, 3459.6, 3487.9, 3516.2, 3544.6, 3572.9, 3601.3, 3629.6, 3657.8, 3682.3, 3706.9, 3731.4, 3756, 3780.5, 3805, 3829.5, 3853.6, 3877.8, 3901.9, 3926, 3950.2, 3974.3, 3998.4, 4022.6, 4046.7, 4077.4, 4109.6, 4141.7, 4173.9, 4206, 4238.2, 4267.2, 4293.1, 4319, 4344.9, 4370.8, 4396.7, 4422.6, 4448.5, 4474.4, 4500.5, 4526.5, 4552.5, 4578.5, 4604.5, 4630.5, 4656.9, 4681.2, 4705.5, 4729.8, 4754.1, 4778.4, 4802.8, 4827.1, 4851.4, 4874.4, 4897.1, 4919.9, 4942.6, 4965.4, 4988.2};
//    double[] upper_motor_speeds = {1785, 1790.6, 1797, 1806.1, 1815.9, 1825.7, 1835.5, 1845.3, 1855.1, 1864.9, 1874.7, 1884.5, 1894.3, 1904.1, 1914, 1923.8, 1933.6, 1943.4, 1953.2, 1963, 1972.8, 1983, 1994.4, 2005.8, 2017.2, 2028.6, 2040.1, 2051.5, 2062.9, 2074.3, 2085.7, 2097.1, 2108.5, 2119.9, 2131.3, 2142.2, 2153, 2163.9, 2174.7, 2185.5, 2196.4, 2207.2, 2218.1, 2228.9, 2239.7, 2250.6, 2261.4, 2272.3, 2283.1, 2293, 2302.2, 2311.4, 2320.6, 2329.8, 2339, 2348.2, 2357.4, 2366.6, 2375.8, 2385, 2393, 2399.2, 2405.4, 2411.6, 2417.7, 2423.9, 2430.1, 2436.3, 2442.5, 2448.7, 2454.9, 2461.1, 2466.8, 2470.8, 2474.8, 2478.8, 2482.9, 2486.9, 2490.9, 2494.9, 2498.1, 2495.8, 2493.5, 2491.2, 2489, 2486.7, 2484.4, 2481.8, 2472.1, 2462.3, 2452.6, 2442.8, 2431.7, 2396, 2360.3, 2325.6, 2291.9, 2258.2, 2201.5, 2129.3, 2085.1, 2051.5, 2017.8, 1991, 1964.1, 1937.3, 1910.5, 1879.7, 1846, 1812.2, 1779.9, 1752.9, 1726, 1699, 1672.1, 1652.4, 1632.8, 1613.1, 1593.4, 1577.2, 1561.4, 1545.6, 1529.8, 1514, 1498.6, 1488.1, 1477.6, 1467, 1456.5, 1445.9, 1435.4, 1425, 1414.7, 1404.5, 1394.2, 1384, 1373.8, 1363.5, 1353.5, 1348.7, 1344, 1339.2, 1334.5, 1329.7, 1325, 1319.6, 1312.1, 1304.7, 1297.2, 1289.7, 1282.3, 1274.8, 1267.4, 1259.9, 1252.4, 1242.7, 1232.4, 1222.2, 1211.9, 1201.6, 1191.4, 1183.1, 1176.8, 1170.5, 1164.3, 1158, 1151.7, 1145.4, 1139.1, 1132.4, 1125.6, 1118.8, 1111.9, 1105.1, 1098.3, 1091.4, 1086.9, 1083.9, 1080.8, 1077.8, 1074.7, 1071.6, 1068.6, 1065.5, 1062.5, 1059.6, 1056.8, 1053.9, 1051.1, 1048.3, 1045.4};
    double[] time_in_flights = {0.4805, 0.4871, 0.4937, 0.5003, 0.5066, 0.5125, 0.518, 0.5209, 0.5273, 0.5336, 0.5399, 0.5454, 0.5511, 0.5575, 0.5638, 0.5701, 0.5765, 0.5828, 0.5892, 0.5955, 0.6015, 0.6066, 0.6117, 0.6168, 0.622, 0.6273, 0.6327, 0.638, 0.6434, 0.6487, 0.6535, 0.658, 0.6626, 0.6671, 0.6717, 0.6762, 0.6808, 0.6858, 0.6909, 0.6959, 0.7009, 0.7059, 0.7109, 0.7159, 0.7208, 0.7251, 0.7295, 0.7338, 0.7381, 0.7425, 0.7468, 0.7509, 0.7542, 0.7575, 0.7609, 0.7642, 0.7676, 0.7711, 0.7752, 0.7793, 0.7833, 0.7874, 0.7915, 0.7956, 0.7997, 0.8037, 0.8078, 0.8117, 0.8154, 0.8192, 0.8229, 0.8266, 0.8303, 0.8341, 0.8378, 0.8415, 0.8452, 0.8489, 0.8524, 0.8559, 0.8593, 0.8627, 0.8661, 0.8695, 0.8729, 0.8763, 0.8797, 0.8834, 0.8871, 0.8909, 0.8946, 0.8983, 0.902, 0.9057, 0.9095, 0.9129, 0.9163, 0.9196, 0.923, 0.9264, 0.9297, 0.9331, 0.9365, 0.9399, 0.9434, 0.9469, 0.9505, 0.954, 0.9576, 0.9611, 0.9647, 0.9682, 0.9718, 0.9753, 0.9789, 0.9825, 0.986, 0.9896, 0.9931, 0.9967, 1.0003, 1.0038, 1.0074, 1.0109, 1.0141, 1.0173, 1.0206, 1.0238, 1.027, 1.0303, 1.0335, 1.0367, 1.04, 1.043, 1.046, 1.049, 1.0519, 1.0549, 1.0579, 1.0609, 1.064, 1.0671, 1.0701, 1.0732, 1.0763, 1.0793, 1.0832, 1.0874, 1.0916, 1.0958, 1.1};
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
    boolean isSpunUp = false;


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
    public boolean checkIfSpunUp() {
        if (Math.abs((launcher2.getVelocity()) - upper_motor_interporation_result) < 30 && Math.abs((launcher.getVelocity()) - lower_motor_interporation_result) < 30) {
            return true;
        } else {
            return false;
        }
    }
    public boolean shoot(double distance) {

        boolean interpolation_set = false;

//        if (distance == 1.3) {
//            upper_motor_interporation_result = upper_motor_speeds[0] * (0.4666666667);
//            lower_motor_interporation_result = lower_motor_speeds[0] * (0.466667);
//        } else if (distance == 3) {
//            upper_motor_interporation_result = upper_motor_speeds[1] * (0.46666667);
//            lower_motor_interporation_result = lower_motor_speeds[1] * (0.466666667);
//        } else if (distance == 0) {
//            upper_motor_interporation_result = 0;
//            lower_motor_interporation_result = 0;
//        }

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
