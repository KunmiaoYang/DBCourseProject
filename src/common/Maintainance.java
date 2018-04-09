package common;

import model.*;

import java.sql.Connection;
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
        try {
            return new Service(serviceType, checkIn, staff);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static boolean updateService(Service service) {
        return service.update();
    }
    public static CheckIn checkIn(LocalDateTime checkInTime, Customer customer, Account account, Room room, int numGuest) throws SQLException {
        Connection connection = Model.getDatabase().getConnection();
        try {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            if(InfoProcess.assignRoom(room, numGuest))          // If successfully assign a room, check in the customer
                return new CheckIn(checkInTime, customer, account, room, numGuest);
            else connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return null;
    }
    public static void checkOut(CheckIn checkIn, Account account) {
        InfoProcess.releaseRoom(checkIn.getRoom());
        if(null != account) checkIn.setAccount(account);
        checkIn.update();
        checkIn.checkOut(LocalDateTime.now());
    }
    public static boolean removeService(Service service) {
        return service.remove();
    }
    public static boolean removeCheckIn(CheckIn checkIn) {
        return checkIn.remove();
    }
}
