package model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * Created by Kunmiao Yang on 2/12/2018.
 */
public class Hotel extends Model {
    int id;
    String name, city, address, phoneNumber;

    protected Hotel() {}

    public Hotel(String name, String city, String address, String phoneNumber) throws SQLException {
        this.name = name;
        this.city = city;
        this.address = address;
        this.phoneNumber = phoneNumber;
        // Create tuple in database
        database.getStatement().executeUpdate("INSERT INTO " +
                "hotel(hotel_name, city, street_address, hotel_phone_number)" +
                "VALUES (\"" + name + "\", \"" + city + "\", \"" + address + "\", \"" + phoneNumber + "\");");
    }

    public static Hotel getById(int id) {
        // Get instance from database
        Hotel hotel = new Hotel();
        hotel.id = id;
        try {
            ResultSet resultSet = database.getStatement().executeQuery(
                    "SELECT * FROM hotel WHERE hotel_id = " + id + ";");
            resultSet.next();
            hotel.name = resultSet.getString("hotel_name");
            hotel.city = resultSet.getString("city");
            hotel.address = resultSet.getString("street_address");
            hotel.phoneNumber = resultSet.getString("hotel_phone_number");
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return hotel;
    }

    /**
     * Remove hotel from DB
     * @return true if success
     */
    public boolean remove() {
        // Remove from DB
        try {
            database.getStatement().executeUpdate("DELETE FROM hotel WHERE hotel_id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean update() {
        // Update attributes to DB
        try {
            database.getStatement().executeUpdate("UPDATE hotel " +
                    "SET hotel_name = \"" + name + "\"" +
                    ", city = \"" + city + "\"" +
                    ", street_address = \"" + address + "\"" +
                    ", hotel_phone_number = \"" + phoneNumber + "\"" +
                    " WHERE hotel_id = " + id + ";");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
