package common;

import model.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static common.Constants.FORMAT_BILL_CHECK_IN;
import static common.Constants.FORMAT_BILL_SERVICE;
import static common.Constants.FORMAT_BILL_TOTAL;

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
    public static void update(Model model) throws SQLException {
        model.update();
    }
    public static Bill generateBill(CheckIn checkIn) throws SQLException {
        checkIn.calculateBill(LocalDateTime.now());
        return new Bill(checkIn, checkIn.getAmount(), checkIn.getAllServices());
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
    public static Bill checkOut(CheckIn checkIn, Account account) throws SQLException {
        Connection connection = Model.getDatabase().getConnection();
        Bill bill = null;
        try {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            InfoProcess.releaseRoom(checkIn.getRoom());
            if(null != account) checkIn.setAccount(account);
            checkIn.update();
            checkIn.calculateBill(LocalDateTime.now());
            bill = generateBill(checkIn);
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return bill;
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

    public static class Bill {
        public CheckIn checkIn;
        public float amount;
        public List<Service> services;

        public Bill(CheckIn checkIn, float amount, List<Service> services) {
            this.checkIn = checkIn;
            this.amount = amount;
            this.services = services;
        }

        @Override
        public String toString() {
            Room room = checkIn.getRoom();
            Hotel hotel = room.getHotel();
            StringBuilder sb = new StringBuilder(String.format(FORMAT_BILL_CHECK_IN,
                    hotel.getName(), room.getNumber(), room.getType(), room.getNightlyRate(),
                    checkIn.getCheckInTime(), checkIn.getCheckOutTime()));
            for(Service service: services)
                sb.append(String.format(FORMAT_BILL_SERVICE,
                        service.getServiceType(), service.getPrice()));
            sb.append(String.format(FORMAT_BILL_TOTAL, amount));
            return sb.toString();
        }
    }
}
