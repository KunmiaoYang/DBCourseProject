package common;

import db.Database;
import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

/**
 * 
 * Created by Kunmiao Yang on 2/13/2018.
 */
public class InfoProcess {
    private static Database database;

    public static Hotel createHotel(String name, String city, String address, String phoneNumber) throws SQLException {
        return new Hotel(name, city, address, phoneNumber);
    }
    public static Room createRoom(Hotel hotel, int number, String type, boolean availability) throws SQLException {
        return new Room(hotel, number, type, availability);
    }
    public static Staff createStaff(int staffId, int age, String name, String title, String department, String phoneNum, String address, Hotel hotel) throws SQLException {
        return new Staff(staffId, age, name, title, department, phoneNum, address, hotel);
    }
    public static Customer createCustomer(int id, String name, String phoneNum, String email, LocalDate birth) throws SQLException {
        return new Customer(id, name, phoneNum, email, birth);
    }
    public static Account createAccount(Customer customer, String address, String payMethod, Integer cardNumber, String ssn) throws SQLException {
        return new Account(customer, address, payMethod, cardNumber, ssn);
    }

    public static void update(Model model) throws SQLException {
        model.update();
    }

    public static void remove(Model model) throws SQLException {
        model.remove();
    }

    public static List<Room> getAvailableRooms(int numGuest, Hotel hotel, String room_type) {
        List<Integer> hotelIds = new LinkedList<>(), roomNums = new LinkedList<>();
        try {
            ResultSet resultSet = database.getStatement().executeQuery("SELECT * FROM room NATURAL JOIN room_type" +
                    " WHERE availability = 1 AND max_occupancy >= " + numGuest +
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
            return new ArrayList<>();
        }
        List<Room> rooms = new ArrayList<>(roomNums.size());
        for(Iterator<Integer> itHotel = hotelIds.iterator(), itRoom = roomNums.iterator(); itHotel.hasNext() && itRoom.hasNext(); )
            rooms.add(Room.getById(itHotel.next(), itRoom.next()));
        return rooms;
    }

    public static boolean assignRoom(Room room, int numGuest) {
        if(!room.isAvailability() || numGuest > room.getMaxOccupy()) return false;
        room.setAvailability(false);
        try {
            room.update();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }

//        Room oldRoom = checkIn.getRoom();
//        releaseRoom(oldRoom);
//        checkIn.setRoom(room);
//        checkIn.update();
        return true;
    }

    public static boolean releaseRoom(Room room) {
        // Release room
        if(room.isAvailability()) return false;
        room.setAvailability(true);
        try {
            room.update();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static void setDatabase(Database database) {
        InfoProcess.database = database;
    }

//    public static Hotel getHotelById(int id) {
//        return Hotel.getById(id);
//    }
//    public static Room getRoomById(int hotelId, int roomNumber) {
//        return Room.getById(hotelId, roomNumber);
//    }
//    public static Staff getStaffById(int id) {
//        return Staff.getById(id);
//    }
//    public static Customer getCustomerById(int id) {
//        return Customer.getById(id);
//    }
//    public static Account getAccountById(int id) {
//        return Account.getById(id);
//    }
}
