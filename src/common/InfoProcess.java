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

    public static Room[] getAvailableRooms(Hotel hotel, String room_type) {
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
