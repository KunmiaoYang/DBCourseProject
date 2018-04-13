package model;

import db.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import static common.Constants.*;

/**
 *
 * Created by Kunmiao Yang on 4/6/2018.
 */
public class CheckInTest {
    @Before
    public void setUp() throws Exception {
        Model.setDatabase(new Database(DB_DRIVER, DB_URL, DB_USER, DB_PASSWORD));
        Model.database.getStatement().executeUpdate("DELETE FROM staff WHERE staff_id = 123;");
        new Staff(123, 30, "testStaff", "Waiter", "Catering", "919919919", "Raleigh NC 27", Hotel.getById(1));
    }

    @After
    public void tearDown() throws Exception {
        Model.remove(TABLE_SERVICE, "checkin_id = 123");
        Model.remove(TABLE_CHECK_IN, "checkin_id = 123");
        Model.database.getStatement().executeUpdate("UPDATE room" +
                " SET availability = 1 WHERE hotel_id = 1;");
        Model.database.close();
    }

    public void initObject() {
        try {
            Model.database.getStatement().executeUpdate("INSERT INTO" +
                    " checkin(checkin_id, checkin_time, hotel_id, room_number, guest_num, customer_id, account_id)" +
                    " VALUES (123, '2018-04-05 13:20:36', 1, 2, 2, 1003, 3);");
            Model.database.getStatement().executeUpdate("UPDATE room" +
                    " SET availability = 0 WHERE hotel_id = 1 AND room_number = 2;");
        } catch (SQLException e) {
//            e.printStackTrace();
        }
    }

    @Test
    public void testConstructor() throws Exception {

        // Test normal check in
        Room room = Room.getById(1, 5);
        assertNotNull(room);
//        assertTrue(room.availability);
        CheckIn c = new CheckIn(LocalDateTime.of(2018, 4, 5, 13, 20, 36), Customer.getById(1002), Account.getById(2), room, 2);
        assertNotNull(c);
        ResultSet resultSet = Model.database.getStatement().executeQuery("SELECT * FROM checkin NATURAL JOIN room" +
                " WHERE customer_id = 1002 AND account_id = 2 AND hotel_id = 1 AND room_number = 5;");
        assertTrue(resultSet.next());
//        assertEquals(0, resultSet.getInt("availability"));
        assertEquals(c.getId(), resultSet.getInt("checkin_id"));
        resultSet.close();
        Model.remove(TABLE_CHECK_IN, "customer_id = 1002 AND account_id = 2 AND hotel_id = 1 AND room_number = 5;");
        room.setAvailability(true);
        room.update();

        // Test when the room is not available
        room = Room.getById(3, 2);
        assertNotNull(room);
//        assertFalse(room.availability);
        try {
            c = new CheckIn(LocalDateTime.of(2018, 4, 5, 13, 20, 36), Customer.getById(1002), Account.getById(2), room, 1);
            assertTrue(false);
        } catch (SQLException e) {
            assertEquals(e.getMessage(), ERROR_CHECK_IN_ROOM_UNAVAILABLE);
        }
        resultSet = Model.database.getStatement().executeQuery("SELECT * FROM checkin NATURAL JOIN room" +
                " WHERE customer_id = 1002 AND account_id = 2 AND hotel_id = 3 AND room_number = 2;");
        assertFalse(resultSet.next());
        resultSet.close();
        Model.remove(TABLE_CHECK_IN, "customer_id = 1002 AND account_id = 2 AND hotel_id = 3 AND room_number = 2;");

        // Test when guest number exceeds the maximum allowed guest number of the room
        room = Room.getById(1, 5);
        assertNotNull(room);
//        assertTrue(room.availability);
        try {
            c = new CheckIn(LocalDateTime.of(2018, 4, 5, 13, 20, 36), Customer.getById(1002), Account.getById(2), room, 3);
            assertTrue(false);
        } catch (SQLException e) {
            assertEquals(e.getMessage(), ERROR_CHECK_IN_EXCEED_OCCUPANCY);
        }
        resultSet = Model.database.getStatement().executeQuery("SELECT * FROM checkin NATURAL JOIN room" +
                " WHERE customer_id = 1002 AND account_id = 2 AND hotel_id = 1 AND room_number = 5;");
        assertFalse(resultSet.next());
        resultSet.close();
        room = Room.getById(1, 5);
        assertNotNull(room);
//        assertTrue(room.availability);
        Model.remove(TABLE_CHECK_IN, "customer_id = 1002 AND account_id = 2 AND hotel_id = 1 AND room_number = 5;");
    }

    @Test
    public void testGetById() throws Exception {
        initObject();
        CheckIn c = CheckIn.getById(123);
        assertNotNull(c);
        assertEquals(c.getCustomer().getName(), "Joseph");
        LocalDateTime dateTime = c.getCheckInTime();
        assertNotNull(dateTime);
        assertEquals(dateTime.getYear(), 2018);
        assertEquals(dateTime.getMonthValue(), 4);
        assertEquals(dateTime.getDayOfMonth(), 5);
        assertEquals(dateTime.getHour(), 13);
        assertEquals(dateTime.getSecond(), 36);
    }

    @Test
    public void testRemove() throws Exception {
        initObject();
        CheckIn c = CheckIn.getById(123);
        assertNotNull(c);
        assertTrue(c.remove());
        c = CheckIn.getById(123);
        assertNull(c);
    }

    @Test
    public void testUpdate() throws Exception {
        initObject();
        CheckIn c = CheckIn.getById(123);
        assertNotNull(c);
        assertNull(c.getAmount());
        assertNotEquals(c.getNumGuest(), 1);
        c.setAmount((float) 12345);
        c.setNumGuest(1);
        try {
            c.update();
        } catch (SQLException e) {
            e.printStackTrace();
            assertTrue(false);
        }
        assertEquals(c.getAmount().intValue(), 12345);
        assertEquals(c.getNumGuest(), 1);
    }

    @Test
    public void testCheckOut() throws Exception {
        initObject();
        CheckIn c = CheckIn.getById(123);
        assertNotNull(c);
        Staff staff = Staff.getById(123);
        assertNotNull(staff);
        Room r = c.getRoom();
        assertNotNull(r);
        assertFalse(r.availability);
        assertNull(c.getAmount());
        new Service("gyms", c, null);
        new Service("dry cleaning", c, staff);
        new Service("special requests", c, staff);
        c.checkOut(c.checkInTime.plusDays(2));
        Float amount = c.getAmount();
        assertTrue(r.availability);
        assertNotNull(amount);
        assertEquals(451, amount.intValue());
    }

    @Test
    public void testDiscountCheckOut() throws Exception {
        initObject();
        CheckIn c = CheckIn.getById(123);
        assertNotNull(c);
        Staff staff = Staff.getById(123);
        assertNotNull(staff);
        Room r = c.getRoom();
        assertNotNull(r);
        assertFalse(r.availability);
        c.setAccount(Account.getById(2));
        c.update();
        assertNull(c.getAmount());
        new Service("special requests", c, staff);
        c.checkOut(c.checkInTime.plusDays(2));
        Float amount = c.getAmount();
        assertTrue(r.availability);
        assertNotNull(amount);
        assertEquals(399, amount.intValue());
    }

    @Test
    public void testGetAllServices() throws Exception {
        initObject();
        CheckIn c = CheckIn.getById(123);
        assertNotNull(c);
        Staff staff = Staff.getById(123);
        assertNotNull(staff);
        List<Service> services = c.getAllServices();
        assertEquals(0, services.size());

        new Service("gyms", c, null);
        new Service("dry cleaning", c, staff);
        new Service("special requests", c, staff);
        services = c.getAllServices();
        assertNotNull(services);
        assertEquals(3, services.size());
        Model.database.getStatement().executeUpdate("DELETE FROM service_record WHERE checkin_id = 123;");
    }
}