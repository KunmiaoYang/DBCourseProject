package model;

import java.util.Date;

/**
 *
 * Created by Kunmiao Yang on 2/12/2018.
 */
public class Hotel {
    int id;
    String name, address, phoneNumber, managerID;

    public Hotel(String name, String address, String phoneNumber, String managerID) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.managerID = managerID;
        // TODO: create tuple in database
    }

    public static Hotel getById(int id) {
        // TODO: get instance from database
        return null;
    }

    public static float getOccupancy(Hotel hotel, String type, Date start, Date end) {
        // TODO: query
        return 0;
    }

    public static float getOccupancy(String city, String type, Date start, Date end) {
        // TODO: query
        return 0;
    }

    public boolean remove() {
        // TODO: remove from DB
        return false;
    }

    public boolean update() {
        // TODO: update attributes to DB
        return false;
    }

    public Room[] getAvailableRooms(String roomType){
        // TODO: query
        return null;
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

    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }
}
