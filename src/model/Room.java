package model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * Created by Kunmiao Yang on 2/12/2018.
 */
public class Room extends Model {
    int id, number, maxOccupy;
    Hotel hotel;
    String type;
    float nightlyRate;
    boolean availability;

    public Room(Hotel hotel, int number, String type, boolean availability) throws SQLException {
        this.hotel = hotel;
        this.number = number;
        this.type = type;
        this.availability = availability;
        // TODO: query maxOccupy and nightly rate
        ResultSet resultSet = database.getStatement().executeQuery(
                "SELECT * FROM room_type WHERE type = " + this.type + ";");
        if(!resultSet.next()) throw new SQLException("Invalid room type!");
        this.maxOccupy = resultSet.getInt("max_occupancy");
        this.nightlyRate = resultSet.getInt("nightly_rate");

        // TODO: create tuple in database
        database.getStatement().executeUpdate("INSERT INTO " +
                "room(hotel_id, room_number, type_id, availability)" +
                "VALUES (" + this.hotel.getId() + ", " + this.number + ", " + this.type + ", " + (availability?1:0) + ");");
    }

    public static Room getById(int id) {
        // TODO: get instance from database
        return null;
    }

    public boolean remove() {
        // TODO: remove from DB
        return false;
    }

    public boolean update() {
        // TODO: update attributes to DB
        return false;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
