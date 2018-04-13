package common;

import db.Database;
import model.CheckIn;
import model.Hotel;
import model.Model;
import model.Staff;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

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
        Map<String, List<Integer>> table = new HashMap<>();
        try {
            ResultSet resultSet = database.getStatement().executeQuery("SELECT job_title, staff_id FROM staff ORDER BY job_title;");
            while (resultSet.next())
                table.computeIfAbsent(resultSet.getString("job_title"), k -> new LinkedList<>()).add(resultSet.getInt("staff_id"));
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        Map<String, List<Staff>> report = new HashMap<>();
        for(String role: table.keySet()) {
            List<Integer> ids = table.get(role);
            List<Staff> staffs = new ArrayList<>(ids.size());
            for(Integer id: ids) staffs.add(Staff.getById(id));
            report.put(role, staffs);
        }
        return report;
    }
    public static List<Staff> getServingStaffs(CheckIn checkIn) {
        List<Integer> ids = new LinkedList<>();
        try {
            ResultSet resultSet = database.getStatement().executeQuery("SELECT DISTINCT staff_id" +
                    " FROM staff NATURAL JOIN service_record WHERE checkin_id = " + checkIn.getId() + ";");
            while (resultSet.next()) ids.add(resultSet.getInt("staff_id"));
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        List<Staff> staffs = new ArrayList<>(ids.size());
        for(Integer id: ids) staffs.add(Staff.getById(id));
        return staffs;
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
        try {
            ResultSet resultSet = database.getStatement().executeQuery("SELECT SUM(amount) AS revenue" +
                    " FROM checkin WHERE NOT ISNULL(amount)" +
                    " AND DATE(checkin_time) >= '" + start.toString() +"'" +
                    " AND DATE (checkout_time) <= '" + end.toString() + "'" +
                    " AND hotel_id = " + hotel.getId() + ";");
            if(resultSet.next()) return resultSet.getInt("revenue");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
