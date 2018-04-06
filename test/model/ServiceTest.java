package model;

import db.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static common.Constants.*;
import static org.junit.Assert.*;

/**
 * Created by Kunmiao Yang on 4/6/2018.
 */
public class ServiceTest {
    @Before
    public void setUp() throws Exception {
        Model.setDatabase(new Database(DB_DRIVER, DB_URL, DB_USER, DB_PASSWORD));
        Model.database.getStatement().executeUpdate("DELETE FROM staff WHERE staff_id = 123;");
        new Staff(123, 30, "testStaff", "Waiter", "Catering", "919919919", "Raleigh NC 27", Hotel.getById(1));
        Model.database.getStatement().executeUpdate("DELETE FROM checkin WHERE checkin_id = 123;");
        new CheckIn(LocalDateTime.of(2018, 4, 5, 13, 20, 36), Customer.getById(1002), Account.getById(2), Room.getById(1, 5), 1);
    }

    @After
    public void tearDown() throws Exception {
        Model.remove(TABLE_SERVICE, "checkin_id = 123");
        Model.remove(TABLE_CHECK_IN, "checkin_id = 123");
        Model.remove(TABLE_STAFF, "staff_id = 123");
        Room room = Room.getById(1, 5);
        assert room != null;
        room.setAvailability(true);
        room.update();
        Model.database.close();
    }

    public void initObject() {
        try {
            Model.database.getStatement().executeUpdate("INSERT INTO" +
                    " service_record(service_id, service_type, staff_id, checkin_id)" +
                    " VALUES (123, 'room service', 123, 123)" +
                    ", (124, 'phone bills', NULL, 123);");
        } catch (SQLException e) {
//            e.printStackTrace();
        }
    }

    @Test
    public void testConstructor() throws Exception {
        Staff staff = Staff.getById(123);
        assertNotNull(staff);
        CheckIn checkIn = CheckIn.getById(123);
        assertNotNull(123);

        Service s = new Service("dry cleaning", checkIn, staff);
        assertNotNull(s);
        ResultSet resultSet = Model.database.getStatement().executeQuery("SELECT * FROM service_record WHERE checkin_id = 123");
        assertTrue(resultSet.next());
        assertEquals("dry cleaning", resultSet.getString("service_type"));
        assertEquals(123, resultSet.getInt("staff_id"));

        s = new Service("gyms", checkIn, null);
        assertNotNull(s);
        resultSet = Model.database.getStatement().executeQuery("SELECT * FROM service_record WHERE checkin_id = 123 AND ISNULL(staff_id)");
        assertTrue(resultSet.next());
        assertEquals("dry cleaning", resultSet.getString("service_type"));
        assertNull(resultSet.getInt("staff_id"));
    }

    @Test
    public void testGetById() throws Exception {
        initObject();

        Service s = Service.getById(123);
        assertNotNull(s);
        assertEquals("room service", s.getServiceType());
        assertEquals(123, s.getStaff().getStaffId());

        s = Service.getById(124);
        assertNotNull(s);
        assertEquals("phone bills", s.getServiceType());
        assertEquals(123, s.getCheckIn().getId());
        assertNull(s.getStaff());
    }

    @Test
    public void testRemove() throws Exception {
        initObject();
        Service s = Service.getById(123);
        assertNotNull(s);
        assertTrue(s.remove());
        s = Service.getById(123);
        assertNull(s);
    }

    @Test
    public void testUpdate() throws Exception {
        initObject();
        Service s = Service.getById(123);
        assertNotNull(s);
        s.setServiceType("gyms");
        s.setStaff(null);
        assertTrue(s.update());
        ResultSet resultSet = Model.database.getStatement().executeQuery("SELECT * FROM service_record WHERE checkin_id = 123;");
        assertTrue(resultSet.next());
        assertEquals("gyms", resultSet.getString("service_type"));
        assertNull(resultSet.getInt("staff_id"));
        resultSet.close();
    }
}