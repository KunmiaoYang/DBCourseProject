package model;

import common.Constants;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * Created by Kunmiao Yang on 2/12/2018.
 */
public class Staff extends Model {
    int staffId, age;
    String name, title, department, phoneNum, address;
    Hotel hotel;

    private Staff(int staffId) {
        this.staffId = staffId;
    }

    public Staff(int staffId, int age, String name, String title, String department, String phoneNum, String address, Hotel hotel) throws SQLException {
        this.staffId = staffId;
        this.age = age;
        this.name = name;
        this.title = title;
        this.department = department;
        this.phoneNum = phoneNum;
        this.address = address;
        this.hotel = hotel;
        // Create tuple in database
        database.getStatement().executeUpdate("INSERT INTO " +
                "staff(staff_id, name, age, job_title, department, hotel_id, phone, address) " +
                "VALUES (" + staffId + ", '" + name + "', " + age + ", '" + title + "', '" + department + "', " +
                hotel.getId() + ", '" + phoneNum + "', '" + address + "');");
    }

    public static Staff getById(int id) {
        // Get instance from database
        Staff staff = new Staff(id);
        try {
            ResultSet resultSet = database.getStatement().executeQuery(
                    "SELECT * FROM staff WHERE staff_id = " + id + ";");
            if(!resultSet.next()) return null;
            staff.setName(resultSet.getString("name"));
            staff.setAge(resultSet.getInt("age"));
            staff.setTitle(resultSet.getString("job_title"));
            staff.setDepartment(resultSet.getString("department"));
            int hotelId = resultSet.getInt("hotel_id");
            staff.setPhoneNum(resultSet.getString("phone"));
            staff.setAddress(resultSet.getString("address"));
            resultSet.close();
            staff.setHotel(Hotel.getById(hotelId));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return staff;
    }

    @Override
    public boolean remove() {
        // Remove from DB
        return remove(Constants.TABLE_STAFF, "staff_id = " + staffId);
    }

    @Override
    public boolean update() {
        // Update attributes to DB
        try {
            database.getStatement().executeUpdate("UPDATE staff " +
                    "SET name = '" + name + "'" +
                    ", age = " + age +
                    ", job_title = '" + title + "'" +
                    ", department = '" + department + "'" +
                    ", hotel_id = " + hotel.getId() +
                    ", phone = '" + phoneNum + "'" +
                    ", address = '" + address + "'" +
                    " WHERE staff_id = " + staffId + ";");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
