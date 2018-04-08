package common;

import db.Database;
import model.CheckIn;
import model.Hotel;
import model.Model;
import model.Staff;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by Kunmiao Yang on 2/13/2018.
 */
public class Report {
    private static Database database;

    public static Map<Integer, Occupancy> getOccupancyByHotel() {
        Map<Integer, Occupancy> report = new HashMap<>();
        try {
            ResultSet resultSet = database.getStatement().executeQuery(
                    "SELECT hotel_id," +
                    " count(*) - SUM(availability) AS occupancy," +
                    " 1 - SUM(availability)/COUNT(*) AS percentage" +
                    " FROM room GROUP BY hotel_id;");
            while (resultSet.next()) {
                report.put(resultSet.getInt("hotel_id"),
                        new Occupancy(resultSet.getInt("occupancy"), resultSet.getFloat("percentage")));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }
    public static Map<String, Occupancy> getOccupancyByRoomType() {
        Map<String, Occupancy> report = new HashMap<>();
        try {
            ResultSet resultSet = database.getStatement().executeQuery(
                    "SELECT room_type," +
                    " count(*) - SUM(availability) AS occupancy," +
                    " 1 - SUM(availability)/COUNT(*) AS percentage" +
                    " FROM room GROUP BY room_type;");
            while (resultSet.next()) {
                report.put(resultSet.getString("room_type"),
                        new Occupancy(resultSet.getInt("occupancy"), resultSet.getFloat("percentage")));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }
    public static Map<String, Occupancy> getOccupancyByCity() {
        Map<String, Occupancy> report = new HashMap<>();
        try {
            ResultSet resultSet = database.getStatement().executeQuery(
                    "SELECT city," +
                    " count(*) - SUM(availability) AS occupancy," +
                    " 1 - SUM(availability)/COUNT(*) AS percentage" +
                    " FROM room NATURAL JOIN hotel GROUP BY city;");
            while (resultSet.next()) {
                report.put(resultSet.getString("city"),
                        new Occupancy(resultSet.getInt("occupancy"), resultSet.getFloat("percentage")));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }
    public static Map<String, Occupancy> getOccupancyByDateRange() {
        // TODO: query
        Map<String, Occupancy> report = new HashMap<>();
        try {
            ResultSet resultSet = database.getStatement().executeQuery(
                    "SELECT DATE(checkin_time) AS date," +
                            " COUNT(*) AS occupancy," +
                            " COUNT(*)/total AS percentage" +
                            " FROM checkin," +
                            " (SELECT COUNT(*) AS total FROM room) AS roomCount" +
                            " GROUP BY date;");
            while (resultSet.next()) {
                report.put(resultSet.getString("date"),
                        new Occupancy(resultSet.getInt("occupancy"), resultSet.getFloat("percentage")));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
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

        public Occupancy(int occupancy, float percentage) {
            this.occupancy = occupancy;
            this.percentage = percentage;
        }
    }
}
