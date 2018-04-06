package common;

import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 *
 * Created by Kunmiao Yang on 2/12/2018.
 */
public class Constants {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final String DB_DRIVER;
    public static final String DB_URL;
    public static final String DB_USER;
    public static final String DB_PASSWORD;
    public static final String TABLE_ACCOUNT;
    public static final String TABLE_BILL;
    public static final String TABLE_CHECK_IN;
    public static final String TABLE_CUSTOMER;
    public static final String TABLE_HOTEL;
    public static final String TABLE_ROOM;
    public static final String TABLE_SERVICE;
    public static final String TABLE_STAFF;
    public static final String ERROR_ACCOUNT_INVALID_CUSTOMER;
    public static final String ERROR_CHECK_IN_ROOM_UNAVAILABLE;
    public static final String ERROR_CHECK_IN_EXCEED_OCCUPANCY;
    public static final String ERROR_SERVICE_INVALID_CHECK_IN;
    static{
        ResourceBundle config = ResourceBundle.getBundle("config");
        ResourceBundle account = ResourceBundle.getBundle("account");
        DB_USER = account.getString("db.user");
        DB_PASSWORD = account.getString("db.password");
        DB_DRIVER = config.getString("db.driver");
        DB_URL = config.getString("db.url") + DB_USER;
        TABLE_ACCOUNT = config.getString("db.table.account");
        TABLE_BILL = config.getString("db.table.bill");
        TABLE_CHECK_IN = config.getString("db.table.checkIn");
        TABLE_CUSTOMER = config.getString("db.table.customer");
        TABLE_HOTEL = config.getString("db.table.hotel");
        TABLE_ROOM = config.getString("db.table.room");
        TABLE_SERVICE = config.getString("db.table.service");
        TABLE_STAFF = config.getString("db.table.staff");
        ERROR_ACCOUNT_INVALID_CUSTOMER = config.getString("error.account.invalidCustomer");
        ERROR_CHECK_IN_ROOM_UNAVAILABLE = config.getString("error.checkIn.roomUnavailable");
        ERROR_CHECK_IN_EXCEED_OCCUPANCY = config.getString("error.checkIn.exceedOccupancy");
        ERROR_SERVICE_INVALID_CHECK_IN = config.getString("error.service.invalidCheckIn");
    }
}
