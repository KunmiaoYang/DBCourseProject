package model;

import java.util.Date;

/**
 *
 * Created by Kunmiao Yang on 2/12/2018.
 */
public class CheckIn extends Model {
    int id;
    Date checkInTime, checkOutTime;
    Customer customer;
    Room room;
    Bill bill;

    public CheckIn(Date checkInTime, Date checkOutTime, Customer customer, Room room) {
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.customer = customer;
        this.room = room;
        // TODO: create tuple in database
    }

    public static CheckIn getById(int id) {
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

    public Bill checkOut() {
        // TODO: check out and create bill
        return null;
    }

    public Service[] getAllServices() {
        // TODO: get all services created for this check in
        return null;
    }

    public int getId() {
        return id;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Date getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Date checkOutTime) {
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

    public Bill getBill() {
        return bill;
    }
}
