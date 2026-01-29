package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class KineticLauncherThing {
    public SparkFunOTOS.Pose2D pos;
    public SparkFunOTOS.Pose2D velocity;
    double angleerror;
    double flight_time;
    double velocitymag;
    public double expecteddistance;
    LimeLightTurretSystem limelightsystem;
    Launcher launcher;
    sparkFunMethodsClass sparkfun;
    double velocity_towards_target = 0;
    double time_in_air = 0;
    double[] between_point_1 = {0, 0};
    double[] between_point_2 = {0, 0};

    double[] target_ranges = {0.5, 1.2, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0};
    public double[] lower_motor_speeds = {1017,1032,1159,1405,1615,1819,2029,2246,2454,2697};
    public double[] upper_motor_speeds = {2152,1714,1600,1628,1747,1870,2054,2230,2386,2549};
    public double[] time_in_flights = {1.05, 0.9, 0.86, 0.92, 1.01, 1.08, 1.19, 1.27, 1.32, 1.34};

//    public Servo flap1;


    public double[] result = {0.0, 0.0};

    public double time_in_flight_value_0 = 0.0;
    public double lower_motor_value_0 = 0.0;
    public double upper_motor_value_0 = 0.0;

    public double time_in_flight_value_1 = 0.0;
    public double lower_motor_value_1 = 0.0;
    public double upper_motor_value_1 = 0.0;
    public double flight_time_interporation_result = 0.0;

    public double lower_motor_interporation_result = 0.0;

    public double upper_motor_interporation_result = 0.0;

    public double distancefromat;



    public void init(HardwareMap hardwareMap, Telemetry telemetry) {
        launcher = new Launcher();
        launcher.init(hardwareMap, telemetry);
        sparkfun = new sparkFunMethodsClass();
        sparkfun.init(hardwareMap, telemetry);
    }
    public void runKineticStuff(Telemetry telemetry) { //ignore for now
        // Get the latest position, which includes the x and y coordinates, plus the
        // heading angle
        pos = sparkfun.myOtos.getPosition();  //in meters and radians
        velocity = sparkfun.myOtos.getVelocity(); // like meters per second  possibly
        velocitymag = Math.sqrt(Math.pow(velocity.x, 2) + Math.pow(velocity.y, 2));
        distancefromat = limelightsystem.getDistance_from_apriltag(0, false); //
        velocity_towards_target = velocitymag * Math.cos(limelightsystem.botposeangle); //i think works
        angleerror = pos.h - 2.87979; //i literaly dont know it might be 165 degrees
        result = launcher.find_closest_x(distancefromat); //finds distance from apriltag - two numbers: upper and lower bound

        time_in_flight_value_0 = result[0] >= 1.5 ? time_in_flights[(int) (result[0] * 2) - 1] : time_in_flights[(result[0] == 0.5 ? 0 : 1)]; // finds the time in flight for both upper and lower bound
        time_in_flight_value_1 = result[1] >= 2 ? time_in_flights[(int) (result[1] * 2)] : time_in_flights[(result[1] == 0.5 ? 1 : 2)]; // finds the time in flight for both upper and lower bound
        flight_time_interporation_result = launcher.interpolate_points(
                limelightsystem.getDistance_from_apriltag(0, false), // distance
                new double[]{result[0], time_in_flight_value_0}, //first (x,y) is (lower distance bound, flight time)
                new double[]{result[1], time_in_flight_value_1} // second (x,y) is (upper distance bound, flight time)
        );
        expecteddistance = distancefromat - (velocity_towards_target * flight_time_interporation_result);
        // Log the position to the telemetry
        telemetry.addData("X coordinate", pos.x);
        telemetry.addData("Y coordinate", pos.y);
        telemetry.addData("Heading angle", pos.h); //use to check radians vs degrees

        // Update the telemetry on the driver station
        telemetry.update();
    }
    public void calculateKINETICvalues(Telemetry telemetry) {
        result = launcher.find_closest_x(limelightsystem.getDistance_from_apriltag(0, false)); //finds distance from apriltag - two numbers: upper and lower bound

        time_in_flight_value_0 = result[0] >= 1.5 ? time_in_flights[(int) (result[0] * 2) - 1] : time_in_flights[(result[0] == 0.5 ? 0 : 1)]; // finds the time in flight for both upper and lower bound
        lower_motor_value_0 = result[0] >= 1.5 ? lower_motor_speeds[(int) (result[0] * 2) - 1] : lower_motor_speeds[(result[0] == 0.5 ? 0 : 1)]; // lower motor speed for upper and lower
        upper_motor_value_0 = result[0] >= 1.5 ? upper_motor_speeds[(int) (result[0] * 2) - 1] : upper_motor_speeds[(result[0] == 0.5 ? 0 : 1)]; // upper motor speed for upper and lower

        time_in_flight_value_1 = result[1] >= 2 ? time_in_flights[(int) (result[1] * 2)] : time_in_flights[(result[1] == 0.5 ? 1 : 2)]; // finds the time in flight for both upper and lower bound
        lower_motor_value_1 = result[1] >= 2 ? lower_motor_speeds[(int) (result[1] * 2)] : lower_motor_speeds[(result[1] == 0.5 ? 1 : 2)]; // lower motor speed for upper and lower
        upper_motor_value_1 = result[1] >= 2 ? upper_motor_speeds[(int) (result[1] * 2)] : upper_motor_speeds[(result[1] == 0.5 ? 1 : 2)]; // upper motor speed for upper and lower


        //legit no idea if thats going tow ork
        flight_time_interporation_result = launcher.interpolate_points(
                limelightsystem.getDistance_from_apriltag(0, false), // distance
                new double[]{result[0], time_in_flight_value_0}, //first (x,y) is (lower distance bound, flight time)
                new double[]{result[1], time_in_flight_value_1} // second (x,y) is (upper distance bound, flight time)
        );

        lower_motor_interporation_result = launcher.interpolate_points(
                limelightsystem.getDistance_from_apriltag(sparkfun.expecteddistance, false),
                new double[]{result[0], lower_motor_value_0},
                new double[]{result[1], lower_motor_value_1}
        ) * (7.0 / 15.0);

        upper_motor_interporation_result = launcher.interpolate_points(
                limelightsystem.getDistance_from_apriltag(sparkfun.expecteddistance, false),
                new double[]{result[0], upper_motor_value_0},
                new double[]{result[1], upper_motor_value_1}
        ) * (7.0 / 15.0);




        telemetry.addData("lower motor power: ", lower_motor_interporation_result);

        telemetry.addData("lower motor 0: ", lower_motor_value_0 * (7.0/15.0));
        telemetry.addData("lower motor 1: ", lower_motor_value_1 * (7.0/15.0));

        telemetry.addData("upper motor power: ", upper_motor_interporation_result);

        telemetry.addData("upper motor 0: ", upper_motor_value_0 * (7.0/15.0));
        telemetry.addData("upper motor 1: ", upper_motor_value_1 * (7.0/15.0));
    }

}
