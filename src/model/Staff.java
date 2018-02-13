package model;

import java.util.Date;

/**
 *
 * Created by Kunmiao Yang on 2/12/2018.
 */
public class Staff extends Person {
    String title, department, address;
    Hotel hotel;

    public Staff(int ssn, String name, String phoneNum, Date birth, String title, String department, String address, Hotel hotel) {
        super(ssn, name, phoneNum, birth);
        this.title = title;
        this.department = department;
        this.address = address;
        this.hotel = hotel;
        // TODO: create tuple in database
    }

    public static Staff getBySSN(int ssn) {
        // TODO: get instance from database
        return null;
    }

    public Service serve(Room room) {
        // TODO: create service record
        return null;
    }

    @Override
    public boolean remove() {
        if(!super.remove()) return false;
        // TODO: remove from DB
        return false;
    }

    @Override
    public boolean update() {
        if(!super.update()) return false;
        // TODO: update attributes to DB
        return false;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
