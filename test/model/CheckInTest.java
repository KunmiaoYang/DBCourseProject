package model;

import common.Constants;
import db.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 *
 * Created by Kunmiao Yang on 4/6/2018.
 */
public class CheckInTest {
    @Before
    public void setUp() throws Exception {
        Model.setDatabase(new Database(Constants.DB_DRIVER, Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD));
    }

    @After
    public void tearDown() throws Exception {
        Model.remove(Constants.TABLE_STAFF, "staff_id = 123");
        Model.database.close();
    }

    public void initObject() {
        try {
            Model.database.getStatement().executeUpdate("INSERT INTO" +
                    " checkin(checkin_id, checkin_time, hotel_id, room_number, guest_num, customer_id, account_id)" +
                    " VALUES (123, '2018-4-5', 1, 2, 2, 1003, 3);");
        } catch (SQLException e) {
//            e.printStackTrace();
        }
    }

    @Test
    public void testGetById() throws Exception {
        initObject();
        CheckIn c = CheckIn.getById(123);
        assertNotNull(c);
        assertEquals(c.getCheckInTime().toString(), "2018-4-5");
        assertEquals(c.getCustomer().getName(), "Joseph");
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