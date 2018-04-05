package common;

import model.Customer;
import model.Hotel;
import model.Room;
import model.Staff;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Created by Kunmiao Yang on 2/13/2018.
 */
public class InfoProcess {
    public static Hotel getHotelById(int id) {
        // TODO: query
        return null;
    }
    public static Room getRoomById(int id) {
        // TODO: query
        return null;
    }
    public static Staff getStaffBySSN(int ssn) {
        // TODO: query
        return null;
    }
    public static Customer getCustomerBySSN(int ssn) {
        // TODO: query
        return null;
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
        return new Room(hotel, number, type, availability);
    }
    public static Staff createStaff(int ssn, String name, String phoneNum, Date birth, String title, String department, String address, Hotel hotel) {
        return new Staff(ssn, name, phoneNum, birth, title, department, address, hotel);
    }
    public static Customer createCustomer(int ssn, String name, String phoneNum, Date birth, String email) {
        return new Customer(ssn, name, phoneNum, birth, email);
    }

    public static boolean updateHotel(Hotel hotel, Map<String, String> data) {
        // TODO: update data
        return false;
    }
    public static boolean updateRoom(Room room, Map<String, String> data) {
        // TODO: update data
        return false;
    }
    public static boolean updateStaff(Staff staff, Map<String, String> data) {
        // TODO: update data
        return false;
    }
    public static boolean updateCustomer(Customer customer, Map<String, String> data) {
        // TODO: update data
        return false;
    }

    public static boolean removeHotel(Hotel hotel) {
        // TODO: remove data
        return false;
    }
    public static boolean removeRoom(Room room) {
        // TODO: remove data
        return false;
    }
    public static boolean removeStaff(Staff staff) {
        // TODO: remove data
        return false;
    }
    public static boolean removeCustomer(Customer customer) {
        // TODO: remove data
        return false;
    }

    public static Room[] getAvailableRooms(HashMap<String, String> roomInfo) {
        // TODO: query
        return null;
    }
    public static Room assignRoom(Room room, Customer customer, LocalDateTime dateTime) {
        // TODO: assign room
        return null;
    }
    public static boolean releaseRoom(Room room) {
        // TODO: release room
        return false;
    }
}
