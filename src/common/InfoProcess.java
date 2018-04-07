package common;

import db.Database;
import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 
 * Created by Kunmiao Yang on 2/13/2018.
 */
public class InfoProcess {
    private static Database database;

    public static Hotel getHotelById(int id) {
        return Hotel.getById(id);
    }
    public static Room getRoomById(int hotelId, int roomNumber) {
        return Room.getById(hotelId, roomNumber);
    }
    public static Staff getStaffById(int id) {
        return Staff.getById(id);
    }
    public static Customer getCustomerById(int id) {
        return Customer.getById(id);
    }
    public static Account getAccountById(int id) {
        return Account.getById(id);
    }

    public static Hotel createHotel(String name, String city, String address, String phoneNumber) {
        try {
            return new Hotel(name, city, address, phoneNumber);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Room createRoom(Hotel hotel, int number, String type, boolean availability) {
        try {
            return new Room(hotel, number, type, availability);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Staff createStaff(int staffId, int age, String name, String title, String department, String phoneNum, String address, Hotel hotel) {
        try {
            return new Staff(staffId, age, name, title, department, phoneNum, address, hotel);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Customer createCustomer(int id, String name, String phoneNum, String email, LocalDate birth) {
        try {
            return new Customer(id, name, phoneNum, email, birth);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Account createAccount(Customer customer, String address, String payMethod, Integer cardNumber, String ssn) {
        try {
            return new Account(customer, address, payMethod, cardNumber, ssn);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean updateHotel(Hotel hotel) {
        return hotel.update();
    }
    public static boolean updateRoom(Room room) {
        return room.update();
    }
    public static boolean updateStaff(Staff staff) {
        return staff.update();
    }
    public static boolean updateCustomer(Customer customer) {
        return customer.update();
    }
    public static boolean updateAccount(Account account) {
        return account.update();
    }

    public static boolean removeHotel(Hotel hotel) {
        return hotel.remove();
    }
    public static boolean removeRoom(Room room) {
        return room.remove();
    }
    public static boolean removeStaff(Staff staff) {
        return staff.remove();
    }
    public static boolean removeCustomer(Customer customer) {
        return customer.remove();
    }
    public static boolean removeAccount(Account account) {
        return account.remove();
    }

    public static Room[] getAvailableRooms(Hotel hotel, String room_type) {
        List<Integer> hotelIds = new LinkedList<>(), roomNums = new LinkedList<>();
        try {
            ResultSet resultSet = database.getStatement().executeQuery("SELECT * FROM room" +
                    " WHERE availability = 1" +
                    (null == hotel ? "" : (" AND hotel_id = " + hotel.getId())) +
                    (null == room_type ? "" : (" AND room_type = '" + room_type + "'")) +
                    ";");
            while (resultSet.next()) {
                hotelIds.add(resultSet.getInt("hotel_id"));
                roomNums.add(resultSet.getInt("room_number"));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return new Room[0];
        }
        Room[] rooms = new Room[roomNums.size()];
        int i = 0;
        for(Iterator<Integer> itHotel = hotelIds.iterator(), itRoom = roomNums.iterator(); itHotel.hasNext() && itRoom.hasNext(); i++)
            rooms[i] = Room.getById(itHotel.next(), itRoom.next());
        return rooms;
    }

    public static boolean assignRoom(CheckIn checkIn, Room room, int numGuest) {
        if(!room.isAvailability() || numGuest > room.getMaxOccupy()) return false;
        room.setAvailability(false);
        room.update();

        Room oldRoom = checkIn.getRoom();
        releaseRoom(oldRoom);
        checkIn.setRoom(room);
        checkIn.update();
        return true;
    }

    public static boolean releaseRoom(Room room) {
        // Release room
        if(room.isAvailability()) return false;
        room.setAvailability(true);
        room.update();
        return true;
    }

    public static void setDatabase(Database database) {
        InfoProcess.database = database;
    }
}
