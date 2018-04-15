package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static common.Constants.*;

/**
 *
 * Created by Kunmiao Yang on 2/12/2018.
 */
public class CheckIn extends Model {
    int id;
    LocalDateTime checkInTime, checkOutTime;
    Customer customer;
    Account account;
    Room room;
    int numGuest;
    Float amount;

    private CheckIn(int id) {
        this.id = id;
    }

    public CheckIn(LocalDateTime checkInTime, Customer customer, Account account, Room room, int numGuest) throws SQLException {
        this.checkInTime = checkInTime;
        this.customer = customer;
        this.account = account;
        this.room = room;
        this.numGuest = numGuest;
        // Create tuple in database
        if(!room.availability) throw new SQLException(ERROR_CHECK_IN_ROOM_UNAVAILABLE);
        if(room.getMaxOccupy() < numGuest) throw new SQLException(ERROR_CHECK_IN_EXCEED_OCCUPANCY);
//        room.setAvailability(false);
//        room.update();
        Connection connection = database.getConnection();
        try {
            database.getStatement().executeUpdate("INSERT INTO " +
                    "checkin(checkin_time, hotel_id, room_number, guest_num, customer_id, account_id) " +
                    "VALUES ('" + checkInTime + "', " + room.getHotel().getId() + ", " + room.getNumber() + ", " +
                    numGuest + ", " + customer.getId() + ", " + account.getId() + ");");
            ResultSet resultSet = database.getStatement().executeQuery("SELECT * FROM checkin" +
                    " WHERE hotel_id = " + room.getHotel().getId() +
                    " AND room_number = " + room.getNumber() +
                    " AND customer_id = " + customer.getId() +
                    " ORDER BY checkin_time DESC;");
            resultSet.next();
            this.id = resultSet.getInt("checkin_id");
            resultSet.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    public static CheckIn getByRoom(Room room) {
        try {
            ResultSet resultSet = database.getStatement().executeQuery(
                    "SELECT checkin_id FROM checkin NATURAL JOIN room" +
                    " WHERE hotel_id = " + room.getHotel().getId() +
                    " AND room_number = " + room.getNumber() +
                    " ORDER BY checkin_time DESC LIMIT 1;");
            if(resultSet.next()) return getById(resultSet.getInt("checkin_id"));
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static CheckIn getById(int id) {
        // Get instance from database
        CheckIn checkIn = new CheckIn(id);
        try {
            ResultSet resultSet = database.getStatement().executeQuery("SELECT * FROM checkin WHERE checkin_id = " + id);
            if(!resultSet.next()) return null;
            int hotelId = resultSet.getInt("hotel_id");
            int roomNumber = resultSet.getInt("room_number");
            int customerId = resultSet.getInt("customer_id");
            int accountId = resultSet.getInt("account_id");
            checkIn.setCheckInTime(LocalDateTime.parse(resultSet.getString("checkin_time").split("\\.")[0], DATE_TIME_FORMATTER));
            String checkOutTime = resultSet.getString("checkout_time");
            if(null != checkOutTime) checkIn.setCheckOutTime(LocalDateTime.parse(checkOutTime.split("\\.")[0], DATE_TIME_FORMATTER));
            checkIn.setNumGuest(resultSet.getInt("guest_num"));
            if(null != resultSet.getObject("amount")) checkIn.setAmount(resultSet.getFloat("amount"));
            else checkIn.setAmount(null);
            resultSet.close();
            checkIn.setRoom(Room.getById(hotelId, roomNumber));
            checkIn.setCustomer(Customer.getById(customerId));
            checkIn.setAccount(Account.getById(accountId));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return checkIn;
    }

    public void remove() throws SQLException {
        // Remove from DB
        remove(TABLE_CHECK_IN, "checkin_id = " + id);
    }

    public void update() throws SQLException {
        // update attributes to DB
        database.getStatement().executeUpdate("UPDATE checkin " +
                "SET checkin_time = '" + checkInTime.toString() + "'" +
                ", hotel_id = " + room.getHotel().getId() +
                ", room_number = " + room.getNumber() +
                ", guest_num = " + numGuest +
                ", customer_id = " + customer.getId() +
                ", account_id = " + account.getId() +
                ", checkout_time = "+ (null == checkOutTime ? "NULL" : ("'" + checkOutTime.toString() + "'")) +
                ", amount = " + (null == amount ? "NULL" : amount) +
                " WHERE checkin_id = " + id + ";");
    }

    /**
     * Calculate bill amount
     */
    public void calculateBill() throws SQLException {
        // Calculate and set the amount
        try {
            ResultSet resultSet = database.getStatement().executeQuery(
                    "SELECT R.price + S.price AS price" +
                    " FROM (SELECT checkin_id, nightly_rate * DATEDIFF(checkout_time, checkin_time) AS price" +
                    " FROM checkin NATURAL JOIN room NATURAL JOIN room_type" +
                    " WHERE checkin_id = " + id + ") AS R, " +
                    " (SELECT checkin_id, sum(fee) AS price" +
                    " FROM service_record NATURAL JOIN service_type" +
                    " WHERE checkin_id = " + id + ") AS S;");
            resultSet.next();
            this.amount = (float) resultSet.getInt("price");
            resultSet.close();
            // Calculate discount
            if(account.getPayMethod().equals("hotel credit")) this.amount = this.amount * (1 - PARAMETER_DISCOUNT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        update();
    }

    public List<Service> getAllServices() {
        // Get all services created for this check in
        List<Integer> serviceIds = new LinkedList<>();
        try {
            ResultSet resultSet = database.getStatement().executeQuery("SELECT * FROM service_record" +
                    " WHERE checkin_id = " + id + ";");
            while (resultSet.next()) serviceIds.add(resultSet.getInt("service_id"));
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        List<Service> services = new ArrayList<>(serviceIds.size());
        for(Integer serviceId : serviceIds) services.add(Service.getById(serviceId));
        return services;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public int getNumGuest() {
        return numGuest;
    }

    public void setNumGuest(int numGuest) {
        this.numGuest = numGuest;
    }
}
