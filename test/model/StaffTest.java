package model;

import common.Constants;
import db.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 *
 * Created by Kunmiao Yang on 4/6/2018.
 */
public class StaffTest {
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
            Model.database.getStatement().executeUpdate("DELETE FROM staff WHERE staff_id = 123;");
            Model.database.getStatement().executeUpdate("INSERT INTO " +
                    "staff(staff_id, name, age, job_title, department, hotel_id, phone, address)" +
                    "VALUES (123, 'Java', 30, 'Front desk', 'management', 3, '919000111', 'Gorman St.');");
        } catch (SQLException e) {
//            e.printStackTrace();
        }
    }

    @Test
    public void testConstructor() throws Exception {
        Staff s = new Staff(123, 30, "Java", "Front dest", "management", "919000111", "GormanStreet", Hotel.getById(3));
        assertNotNull(s);
        ResultSet resultSet = Model.database.getStatement().executeQuery("SELECT * FROM staff WHERE staff_id = 123;");
        assertTrue(resultSet.next());
        resultSet.close();
    }

    @Test
    public void testGetById() throws Exception {
        initObject();
        Staff s = Staff.getById(123);
        assertNotNull(s);
        assertEquals(s.getName(), "Java");
    }

    @Test
    public void testRemove() throws Exception {
        initObject();
        Staff s = Staff.getById(123);
        assertNotNull(s);
        assertTrue(s.remove());
        s = Staff.getById(123);
        assertNull(s);
    }

    @Test
    public void testUpdate() throws Exception {
        initObject();
        Staff s = Staff.getById(123);
        assertNotNull(s);
        s.setTitle("Assistant Manager");
        s.setAddress("Avent Very");
        s.setPhoneNum("919111000");
        try {
            s.update();
        } catch (SQLException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }
}