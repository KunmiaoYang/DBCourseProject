package model;

/**
 *
 * Created by Kunmiao Yang on 2/12/2018.
 */
public class Room {
    int id, number;
    Hotel hotel;
    String type;
    boolean availability;

    public Room(Hotel hotel, int number, String type, boolean availability) {
        this.hotel = hotel;
        this.number = number;
        this.type = type;
        this.availability = availability;
        // TODO: create tuple in database
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
