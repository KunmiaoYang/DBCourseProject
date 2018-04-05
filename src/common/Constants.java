package common;

import java.util.ResourceBundle;

/**
 *
 * Created by Kunmiao Yang on 2/12/2018.
 */
public class Constants {
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
    }
}
