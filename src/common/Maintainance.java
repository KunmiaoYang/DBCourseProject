package common;

import model.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * 
 * Created by Kunmiao Yang on 2/13/2018.
 */
public class Maintainance {
    public static Service getServiceById(int id) {
        return Service.getById(id);
    }
    public static CheckIn getCheckInById(int id) {
        return CheckIn.getById(id);
    }
    public static Service createService(String serviceType, CheckIn checkIn, Staff staff) {
        return new Service(serviceType, checkIn, staff);
    }
    public static boolean updateService(Service service, Map<String, String> data) {
        // TODO: update data
        return false;
    }
    public static CheckIn checkIn(LocalDateTime checkInTime, Customer customer, Account account, Room room, int numGuest) throws SQLException {
        return new CheckIn(checkInTime, customer, account, room, numGuest);
    }
    public static void checkOut(CheckIn checkIn, Account account) {
        InfoProcess.releaseRoom(checkIn.getRoom());
        if(null != account) checkIn.setAccount(account);
        checkIn.checkOut(LocalDateTime.now());
    }
    public static boolean removeService(Service service) {
        return service.remove();
    }
    public static boolean removeCheckIn(CheckIn checkIn) {
        return checkIn.remove();
    }
}
