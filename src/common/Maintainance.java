package common;

import model.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

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
    public static Service createService(String serviceType, CheckIn checkIn, Staff staff) throws SQLException {
        return new Service(serviceType, checkIn, staff);
    }
    public static void updateService(Service service) throws SQLException {
        service.update();
    }
    public static CheckIn checkIn(LocalDateTime checkInTime, Customer customer, Account account, Room room, int numGuest) throws SQLException {
        Connection connection = Model.getDatabase().getConnection();
        try {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            InfoProcess.assignRoom(room, numGuest);
            return new CheckIn(checkInTime, customer, account, room, numGuest);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return null;
    }
    public static void checkOut(CheckIn checkIn, Account account) throws SQLException {
        Connection connection = Model.getDatabase().getConnection();
        try {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            InfoProcess.releaseRoom(checkIn.getRoom());
            if(null != account) checkIn.setAccount(account);
            checkIn.update();
            checkIn.checkOut(LocalDateTime.now());
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }
    public static void removeService(Service service) {
        try {
            service.remove();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    public static void removeCheckIn(CheckIn checkIn) {
        try {
            checkIn.remove();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
