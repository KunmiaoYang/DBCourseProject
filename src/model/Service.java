package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import static common.Constants.*;

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

    public Service(int id) {
        this.id = id;
    }

    public Service(String serviceType, CheckIn checkIn, Staff staff) throws SQLException {
        this.serviceType = serviceType;
        this.checkIn = checkIn;
        this.staff = staff;
        // Create tuple in database
        if(null == checkIn) throw new SQLException(ERROR_SERVICE_INVALID_CHECK_IN);
        database.getStatement().executeUpdate("INSERT INTO" +
                " service_record(service_type, staff_id, checkin_id)" +
                " VALUES ('" + serviceType + "'" +
                ", " + (null == staff ? "NULL" : staff.getStaffId()) +
                ", " + checkIn.getId() +
                ")");
    }

    public static Service getById(int id) {
        // Get instance from database
        Service service = new Service(id);
        try {
            ResultSet resultSet = database.getStatement().executeQuery("SELECT * FROM service_record WHERE service_id = " +
                    id + ";");
            if(!resultSet.next()) return null;
            Integer staffId = (Integer) resultSet.getObject("staff_id");
            int checkin_id = resultSet.getInt("checkin_id");
            service.setServiceType(resultSet.getString("service_type"));
            resultSet.close();
            service.setCheckIn(CheckIn.getById(checkin_id));
            if(null != staffId) service.setStaff(Staff.getById(staffId));
            else service.setStaff(null);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return service;
    }

    public boolean remove() {
        // Remove from DB
        return remove(TABLE_SERVICE, "service_id = " + id);
    }

    public void update() throws SQLException {
        // Update attributes to DB
        database.getStatement().executeUpdate("UPDATE service_record" +
                " SET service_type = '" + serviceType + "'" +
                ", checkin_id = " + checkIn.getId() +
                ", staff_id = " + (null == staff ? "NULL" : staff.getStaffId()) +
                " WHERE service_id = " + id + ";");
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
