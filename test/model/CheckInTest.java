package model;

import common.Constants;
import db.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 *
 * Created by Kunmiao Yang on 4/6/2018.
 */
public class CheckInTest {
    @Before
    public void setUp() throws Exception {
        Model.setDatabase(new Database(Constants.DB_DRIVER, Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD));
        Model.database.getStatement().executeUpdate("DELETE FROM staff WHERE staff_id = 123;");
        new Staff(123, 30, "testStaff", "Waiter", "Catering", "919919919", "Raleigh NC 27", Hotel.getById(1));
    }

    @After
    public void tearDown() throws Exception {
        Model.remove(Constants.TABLE_CHECK_IN, "checkin_id = 123");
        Room room = Room.getById(1, 5);
        assert room != null;
        room.setAvailability(true);
        room.update();
        Model.database.close();
    }

    public void initObject() {
        try {
            Model.database.getStatement().executeUpdate("INSERT INTO" +
                    " checkin(checkin_id, checkin_time, hotel_id, room_number, guest_num, customer_id, account_id)" +
                    " VALUES (123, '2018-04-05 13:20:36', 1, 2, 2, 1003, 3);");
        } catch (SQLException e) {
//            e.printStackTrace();
        }
    }

    @Test
    public void testConstructor() throws Exception {
        Room room = Room.getById(1, 5);
        assertNotNull(room);
        assertTrue(room.availability);

        // Test normal check in
        CheckIn c = new CheckIn(LocalDateTime.of(2018, 4, 5, 13, 20, 36), Customer.getById(1002), Account.getById(2), room, 2);
        assertNotNull(c);
        ResultSet resultSet = Model.database.getStatement().executeQuery("SELECT * FROM checkin NATURAL JOIN room" +
                " WHERE customer_id = 1002 AND account_id = 2 AND hotel_id = 1 AND room_number = 5;");
        assertTrue(resultSet.next());
        assertEquals(0, resultSet.getInt("availability"));
        resultSet.close();
        Model.remove(Constants.TABLE_CHECK_IN, "customer_id = 1002");
        room.setAvailability(true);
        room.update();

        // Test when guest number exceeds the maximum allowed guest number of the room
        c = new CheckIn(LocalDateTime.of(2018, 4, 5, 13, 20, 36), Customer.getById(1002), Account.getById(2), room, 3);
        assertNotNull(c);
        resultSet = Model.database.getStatement().executeQuery("SELECT * FROM checkin NATURAL JOIN room" +
                " WHERE customer_id = 1002 AND account_id = 2 AND hotel_id = 1 AND room_number = 5;");
        assertFalse(resultSet.next());
        resultSet.close();
        room = Room.getById(1, 5);
        assertNotNull(room);
        assertTrue(room.availability);
        Model.remove(Constants.TABLE_CHECK_IN, "customer_id = 1002");
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
        c.remove();
        c = CheckIn.getById(123);
        assertNull(c);
    }

    @Test
    public void testUpdate() throws Exception {
        initObject();
        CheckIn c = CheckIn.getById(123);
        assertNotNull(c);
        assertNotEquals((int)c.getAmount(), 12345);
        assertNotEquals(c.getNumGuest(), 1);
        c.setAmount(12345);
        c.setNumGuest(1);
        assertTrue(c.update());
        assertEquals((int)c.getAmount(), 12345);
        assertEquals(c.getNumGuest(), 1);
    }

    @Test
    public void testCheckOut() throws Exception {
        initObject();
        CheckIn c = CheckIn.getById(123);
        assertNotNull(c);
        Room r = c.getRoom();
        assertNotNull(r);
        assertFalse(r.availability);
        assertNotEquals((int) c.getAmount(), 54321);
        c.setAmount(54321);
        c.checkOut();
        assertTrue(r.availability);
        assertEquals((int) c.getAmount(), 54321);
    }

    @Test
    public void testGetAllServices() throws Exception {
        initObject();
        CheckIn c = CheckIn.getById(123);
        assertNotNull(c);
        Staff staff = Staff.getById(104);
        assertNotNull(staff);
        new Service("gyms", c, null);
        new Service("dry cleaning", c, staff);
        new Service("special requests", c, staff);
        Service[] services = c.getAllServices();
        assertEquals(3, services.length);
    }
}