package model;

import java.time.LocalDateTime;

/**
 *
 * Created by Kunmiao Yang on 2/12/2018.
 */
public class CheckIn extends Model {
    int id;
    LocalDateTime checkInTime, checkOutTime;
    Customer customer;
    Account account;
    Room room;
    int numGuest;
    float amount;

    public CheckIn(LocalDateTime checkInTime, Customer customer, Account account, Room room, int numGuest) {
        this.checkInTime = checkInTime;
        this.customer = customer;
        this.account = account;
        this.room = room;
        this.numGuest = numGuest;
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

    public void checkOut(Account account) {
        // TODO: check out and create bill
    }

    public Service[] getAllServices() {
        // TODO: get all services created for this check in
        return null;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalDateTime checkOutTime) {
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getNumGuest() {
        return numGuest;
    }

    public void setNumGuest(int numGuest) {
        this.numGuest = numGuest;
    }
}
