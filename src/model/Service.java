package model;

/**
 *
 * Created by Kunmiao Yang on 2/12/2018.
 */
public class Service extends Model {
    int id;
    float price;
    String serviceType;
    CheckIn checkIn;
    Staff staff;

    public Service(String serviceType, CheckIn checkIn, Staff staff) {
        this.serviceType = serviceType;
        this.checkIn = checkIn;
        this.staff = staff;
        // TODO: Create tuple in database
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

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
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
