package model;

/**
 *
 * Created by Kunmiao Yang on 2/12/2018.
 */
public class Staff extends Model {
    int staffId, age;
    String name, title, department, phoneNum, address;
    Hotel hotel;

    public Staff(int staffId, int age, String name, String title, String department, String phoneNum, String address, Hotel hotel) {
        this.staffId = staffId;
        this.age = age;
        this.name = name;
        this.title = title;
        this.department = department;
        this.phoneNum = phoneNum;
        this.address = address;
        this.hotel = hotel;
        // TODO: create tuple in database
    }

    public static Staff getById(int id) {
        // TODO: get instance from database
        return null;
    }

    public Service serve(Room room) {
        // TODO: create service record
        return null;
    }

    @Override
    public boolean remove() {
        // TODO: remove from DB
        return false;
    }

    @Override
    public boolean update() {
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

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
