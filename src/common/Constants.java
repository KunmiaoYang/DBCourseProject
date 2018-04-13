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
    public static final String ATTR_HOTEL_NAME;
    public static final String ATTR_HOTEL_CITY;
    public static final String ATTR_HOTEL_STREET;
    public static final String ATTR_HOTEL_PHONE;
    public static final String ERROR_ACCOUNT_INVALID_CUSTOMER;
    public static final String ERROR_CHECK_IN_ROOM_UNAVAILABLE;
    public static final String ERROR_CHECK_IN_EXCEED_OCCUPANCY;
    public static final String ERROR_SERVICE_INVALID_CHECK_IN;
    public static final String ERROR_CONSOLE_INVALID_PARAMETER;
    public static final float PARAMETER_DISCOUNT;
    public static final String CONSOLE_MARKER_COMMAND;
    public static final String CONSOLE_MARKER_PARAMETER;
    public static final String PROMPT_MENU;
    public static final String PROMPT_CREATE;
    public static final String PROMPT_READ;
    public static final String PROMPT_UPDATE;
    public static final String PROMPT_DELETE;
    public static final String PROMPT_ASSIGN;
    public static final String PROMPT_RELEASE;
    public static final String PROMPT_PARAMETER_HOTEL;
    public static final String CMD_MENU = "menu";
    public static final String CMD_EXIT = "exit";
    public static final String CMD_CREATE = "enter";
    public static final String CMD_READ = "check";
    public static final String CMD_UPDATE = "update";
    public static final String CMD_DELETE = "delete";
    public static final String CMD_ASSIGN = "assign";
    public static final String CMD_RELEASE = "release";
    public static final String CMD_OBJECT_HOTEL = "hotel";
    public static final String CMD_OBJECT_ROOM = "room";
    public static final String CMD_OBJECT_STAFF = "staff";
    public static final String CMD_OBJECT_CUSTOMER = "customer";
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
        ATTR_HOTEL_NAME = config.getString("db.table.hotel.name");
        ATTR_HOTEL_CITY = config.getString("db.table.hotel.city");
        ATTR_HOTEL_STREET = config.getString("db.table.hotel.street");
        ATTR_HOTEL_PHONE = config.getString("db.table.hotel.phone");
        ERROR_ACCOUNT_INVALID_CUSTOMER = config.getString("error.account.invalidCustomer");
        ERROR_CHECK_IN_ROOM_UNAVAILABLE = config.getString("error.checkIn.roomUnavailable");
        ERROR_CHECK_IN_EXCEED_OCCUPANCY = config.getString("error.checkIn.exceedOccupancy");
        ERROR_SERVICE_INVALID_CHECK_IN = config.getString("error.service.invalidCheckIn");
        ERROR_CONSOLE_INVALID_PARAMETER = config.getString("error.console.invalidParameter");
        PARAMETER_DISCOUNT = Float.parseFloat(config.getString("parameter.discount"));
        CONSOLE_MARKER_COMMAND = config.getString("console.marker.command");
        CONSOLE_MARKER_PARAMETER = config.getString("console.marker.parameter");
        PROMPT_MENU = config.getString("prompt.menu");
        PROMPT_CREATE = config.getString("prompt.create");
        PROMPT_READ = config.getString("prompt.read");
        PROMPT_UPDATE = config.getString("prompt.update");
        PROMPT_DELETE = config.getString("prompt.delete");
        PROMPT_ASSIGN = config.getString("prompt.assign");
        PROMPT_RELEASE = config.getString("prompt.release");
        PROMPT_PARAMETER_HOTEL = config.getString("prompt.parameter.hotel");
    }
}
