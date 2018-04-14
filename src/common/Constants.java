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
    public static final String ERROR_CHECK_OUT_AVAILABLE_ROOM;
    public static final String ERROR_RELEASE_AVAILABLE_ROOM;
    public static final String ERROR_ROOM_INVALID_ROOM_TYPE;
    public static final String ERROR_SERVICE_INVALID_SERVICE_TYPE;
    public static final String ERROR_SERVICE_INVALID_CHECK_IN;
    public static final String ERROR_CONSOLE_INVALID_PARAMETER;
    public static final String ERROR_CONSOLE_INVALID_KEY;

    public static final float PARAMETER_DISCOUNT;

    public static final String CONSOLE_MARKER_COMMAND;
    public static final String CONSOLE_MARKER_PARAMETER;

    public static final String PROMPT_STATUS_SUCCESS;
    public static final String PROMPT_STATUS_FAIL;
    public static final String PROMPT_MENU;
    public static final String PROMPT_CREATE;
    public static final String PROMPT_READ;
    public static final String PROMPT_UPDATE;
    public static final String PROMPT_DELETE;
    public static final String PROMPT_KEY;

    public static final String PROMPT_PARAMETER_KEY_HOTEL;
    public static final String PROMPT_PARAMETER_KEY_ROOM;
    public static final String PROMPT_PARAMETER_KEY_STAFF;
    public static final String PROMPT_PARAMETER_KEY_CUSTOMER;
    public static final String PROMPT_PARAMETER_KEY_ACCOUNT;
    public static final String PROMPT_PARAMETER_KEY_SERVICE;
    public static final String PROMPT_PARAMETER_KEY_CHECK_IN;
    public static final String PROMPT_PARAMETER_HOTEL;
    public static final String PROMPT_PARAMETER_ROOM;
    public static final String PROMPT_PARAMETER_STAFF;
    public static final String PROMPT_PARAMETER_CUSTOMER;
    public static final String PROMPT_PARAMETER_ACCOUNT;
    public static final String PROMPT_PARAMETER_SERVICE;
    public static final String PROMPT_PARAMETER_ASSIGN;
    public static final String PROMPT_PARAMETER_BILL;
    public static final String PROMPT_CONDITION_ROOM;
    public static final String PROMPT_TABLE_HEADER_ROOM;
    public static final String PROMPT_TABLE_HEADER_SERVICE;
    public static final String PROMPT_TABLE_HEADER_HOTEL_OCCUPY;
    public static final String PROMPT_TABLE_HEADER_ROOM_TYPE_OCCUPY;
    public static final String PROMPT_TABLE_HEADER_DATE_RANGE_OCCUPY;
    public static final String PROMPT_TABLE_HEADER_CITY_OCCUPY;
    public static final String PROMPT_TABLE_HEADER_STAFF_BY_ROLE;
    public static final String PROMPT_TABLE_HEADER_STAFF_BY_STAY;

    public static final String FORMAT_PROMPT_TABLE_ROOM;
    public static final String FORMAT_PROMPT_TABLE_HOTEL_OCCUPY;
    public static final String FORMAT_PROMPT_TABLE_STRING_OCCUPY;
    public static final String FORMAT_PROMPT_TABLE_STAFF_BY_ROLE;
    public static final String FORMAT_PROMPT_TABLE_STAFF_BY_STAY;
    public static final String FORMAT_BILL_CHECK_IN;
    public static final String FORMAT_BILL_SERVICE;
    public static final String FORMAT_BILL_TOTAL;

    public static final String CMD_MENU = "menu";
    public static final String CMD_EXIT = "exit";
    public static final String CMD_CREATE = "enter";
    public static final String CMD_CHECK = "check";
    public static final String CMD_REPORT = "report";
    public static final String CMD_UPDATE = "update";
    public static final String CMD_DELETE = "delete";
    public static final String CMD_ASSIGN = "assign";
    public static final String CMD_RELEASE = "release";

    public static final String CMD_OBJECT_HOTEL = "hotel";
    public static final String CMD_OBJECT_ROOM = "room";
    public static final String CMD_OBJECT_STAFF = "staff";
    public static final String CMD_OBJECT_CUSTOMER = "customer";
    public static final String CMD_OBJECT_ACCOUNT = "account";
    public static final String CMD_OBJECT_SERVICE = "service";
    public static final String CMD_OBJECT_BILL = "bill";
    public static final String CMD_OBJECT_OCCUPY = "occupy";

    public static final String CMD_ATTR_HOTEL = "hotel";
    public static final String CMD_ATTR_ROOM_TYPE = "roomtype";
    public static final String CMD_ATTR_DATE_RANGE = "daterange";
    public static final String CMD_ATTR_CITY = "city";
    public static final String CMD_ATTR_ROLE = "role";
    public static final String CMD_ATTR_STAY = "stay";
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
        ERROR_CHECK_OUT_AVAILABLE_ROOM = config.getString("error.checkOut.availableRoom");
        ERROR_RELEASE_AVAILABLE_ROOM = config.getString("error.release.availableRoom");
        ERROR_ROOM_INVALID_ROOM_TYPE = config.getString("error.room.invalidRoomType");
        ERROR_SERVICE_INVALID_SERVICE_TYPE = config.getString("error.service.invalidServiceType");
        ERROR_SERVICE_INVALID_CHECK_IN = config.getString("error.service.invalidCheckIn");
        ERROR_CONSOLE_INVALID_PARAMETER = config.getString("error.console.invalidParameter");
        ERROR_CONSOLE_INVALID_KEY = config.getString("error.console.invalidObjectKey");

        PARAMETER_DISCOUNT = Float.parseFloat(config.getString("parameter.discount"));

        CONSOLE_MARKER_COMMAND = config.getString("console.marker.command");
        CONSOLE_MARKER_PARAMETER = config.getString("console.marker.parameter");

        PROMPT_STATUS_SUCCESS = config.getString("prompt.status.success");
        PROMPT_STATUS_FAIL = config.getString("prompt.status.fail");
        PROMPT_MENU = config.getString("prompt.menu");
        PROMPT_CREATE = config.getString("prompt.create");
        PROMPT_READ = config.getString("prompt.read");
        PROMPT_UPDATE = config.getString("prompt.update");
        PROMPT_DELETE = config.getString("prompt.delete");
        PROMPT_KEY = config.getString("prompt.key");

        PROMPT_PARAMETER_KEY_HOTEL = config.getString("prompt.parameter.key.hotel");
        PROMPT_PARAMETER_KEY_ROOM = config.getString("prompt.parameter.key.room");
        PROMPT_PARAMETER_KEY_STAFF = config.getString("prompt.parameter.key.staff");
        PROMPT_PARAMETER_KEY_CUSTOMER = config.getString("prompt.parameter.key.customer");
        PROMPT_PARAMETER_KEY_ACCOUNT = config.getString("prompt.parameter.key.account");
        PROMPT_PARAMETER_KEY_SERVICE = config.getString("prompt.parameter.key.service");
        PROMPT_PARAMETER_KEY_CHECK_IN = config.getString("prompt.parameter.key.checkIn");
        PROMPT_PARAMETER_HOTEL = config.getString("prompt.parameter.hotel");
        PROMPT_PARAMETER_ROOM = config.getString("prompt.parameter.room");
        PROMPT_PARAMETER_STAFF = config.getString("prompt.parameter.staff");
        PROMPT_PARAMETER_CUSTOMER = config.getString("prompt.parameter.customer");
        PROMPT_PARAMETER_ACCOUNT = config.getString("prompt.parameter.account");
        PROMPT_PARAMETER_SERVICE = config.getString("prompt.parameter.service");
        PROMPT_PARAMETER_ASSIGN = config.getString("prompt.parameter.assign");
        PROMPT_PARAMETER_BILL = config.getString("prompt.parameter.bill");
        PROMPT_CONDITION_ROOM = config.getString("prompt.condition.room");

        PROMPT_TABLE_HEADER_ROOM = config.getString("prompt.table.header.room");
        PROMPT_TABLE_HEADER_SERVICE = config.getString("prompt.table.header.service");
        PROMPT_TABLE_HEADER_HOTEL_OCCUPY = config.getString("prompt.table.header.hotelOccupy");
        PROMPT_TABLE_HEADER_ROOM_TYPE_OCCUPY = config.getString("prompt.table.header.roomTypeOccupy");
        PROMPT_TABLE_HEADER_DATE_RANGE_OCCUPY = config.getString("prompt.table.header.dateRangeOccupy");
        PROMPT_TABLE_HEADER_CITY_OCCUPY = config.getString("prompt.table.header.cityOccupy");
        PROMPT_TABLE_HEADER_STAFF_BY_ROLE = config.getString("prompt.table.header.staff.role");
        PROMPT_TABLE_HEADER_STAFF_BY_STAY = config.getString("prompt.table.header.staff.stay");

        FORMAT_PROMPT_TABLE_ROOM = config.getString("format.prompt.table.room");
        FORMAT_PROMPT_TABLE_HOTEL_OCCUPY = config.getString("format.prompt.table.hotelOccupy");
        FORMAT_PROMPT_TABLE_STRING_OCCUPY = config.getString("format.prompt.table.stringOccupy");
        FORMAT_PROMPT_TABLE_STAFF_BY_ROLE = config.getString("format.prompt.table.staff.role");
        FORMAT_PROMPT_TABLE_STAFF_BY_STAY = config.getString("format.prompt.table.staff.stay");
        FORMAT_BILL_CHECK_IN = config.getString("format.bill.checkin");
        FORMAT_BILL_SERVICE = config.getString("format.bill.service");
        FORMAT_BILL_TOTAL = config.getString("format.bill.total");
    }
}
