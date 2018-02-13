package model;

/**
 *
 * Created by Kunmiao Yang on 2/12/2018.
 */
public class Service extends Model {
    int id;
    float price;
    String name;
    CheckIn checkIn;
    Staff staff;

    public Service(float price, String name, CheckIn checkIn, Staff staff) {
        this.price = price;
        this.name = name;
        this.checkIn = checkIn;
        this.staff = staff;
    }

    public static Service getById(int id) {
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

    public int getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CheckIn getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(CheckIn checkIn) {
        this.checkIn = checkIn;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
