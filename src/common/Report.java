package common;

import model.CheckIn;
import model.Hotel;
import model.Model;
import model.Staff;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by Kunmiao Yang on 2/13/2018.
 */
public class Report {
    public static Map<String, String> getOccupancy(Map<String, String> condition, Date start, Date end) {
        // TODO: query
        return null;
    }
    public static List<Staff> getStaffsByRole(String role){
        // TODO: query
        return null;
    }
    public static List<Staff> getServingStaffs(CheckIn checkIn) {
        // TODO: query
        return null;
    }
    public static float getRevenue(Hotel hotel, Date start, Date end) {
        // TODO: query
        return 0;
    }
}
