package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

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
        room.setAvailability(false);
        room.update();
        database.getStatement().executeUpdate("INSERT INTO " +
                "checkin(checkin_time, hotel_id, room_number, guest_num, customer_id, account_id) " +
                "VALUES ('" + checkInTime + "', " + room.getHotel().getId() + ", " + room.getNumber() + ", " +
                numGuest + ", " + customer.getId() + ", " + account.getId() + ");");
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
            if(null != resultSet.getObject("amount")) checkIn.setAmount((float) resultSet.getInt("amount"));
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

    public boolean remove() {
        // Remove from DB
        return remove(TABLE_CHECK_IN, "checkin_id = " + id);
    }

    public boolean update() {
        // update attributes to DB
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void checkOut(LocalDateTime time) {
        // TODO: check out and create bill amount
        room.setAvailability(true);
        room.update();

    }

    public void serve(Service service) throws SQLException {
        // TODO: create service record
        if(room.availability) return;     // Only the rooms that are checked in could be served.

        // Find the current check-in id of the room
        int checkInId;
        try {
            ResultSet resultSet = database.getStatement().executeQuery("SELECT * FROM checkin" +
                    " WHERE hotel_id = " + room.hotel.getId() +
                    " AND room_number = " + room.number +
                    " ORDER BY checkin_time DESC");
            resultSet.next();
            checkInId = resultSet.getInt("checkin_id");
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Corresponding check in record NOT found!");
        }

        // TODO: Create service and add it to current check-in
//        Service service = new Service();
    }

    public Service[] getAllServices() {
        // TODO: get all services created for this check in
        return null;
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
