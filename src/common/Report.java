package common;

import db.Database;
import model.CheckIn;
import model.Hotel;
import model.Model;
import model.Staff;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by Kunmiao Yang on 2/13/2018.
 */
public class Report {
    private static Database database;

    public static Map<String, Occupancy> getOccupancyByHotel() {
        // TODO: query
        return null;
    }
    public static Map<String, Occupancy> getOccupancyByRoomType() {
        // TODO: query
        return null;
    }
    public static Map<String, Occupancy> getOccupancyByCity() {
        // TODO: query
        return null;
    }
    public static Map<String, Occupancy> getOccupancyByDateRange() {
        // TODO: query
        return null;
    }
    public static Map<String, List<Staff>> getStaffsByRole(){
        // TODO: query
        return null;
    }
    public static List<Staff> getServingStaffs(CheckIn checkIn) {
        // TODO: query
        return null;
    }

    /**
     * Calculate the revenue between given start and end date for a
     * given hotel. Only the check-ins whose start date is no earlier
     * than the given start date and end date is no later than the
     * given end date are considered.
     * @param hotel the given hotel
     * @param start the given start date
     * @param end the given end date
     * @return the revenue result
     */
    public static float getRevenue(Hotel hotel, LocalDate start, LocalDate end) {
        // TODO: query
        return 0;
    }

    public static void setDatabase(Database database) {
        Report.database = database;
    }

    public static class Occupancy {
        public int occupancy;
        public float percentage;
    }
}
