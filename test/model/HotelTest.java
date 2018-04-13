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
 * Created by Kunmiao Yang on 4/5/2018.
 */
public class HotelTest {
    @Before
    public void setUp() throws Exception {
        Model.setDatabase(new Database(Constants.DB_DRIVER, Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD));
    }

    @After
    public void tearDown() throws Exception {
        Model.remove(Constants.TABLE_HOTEL, "hotel_name = \"javaInn\"");
//        Model.remove(Constants.TABLE_HOTEL, "hotel_id = 123");
        Model.database.close();
    }

    public void initObject() {
        try {
            Model.database.getStatement().executeUpdate("INSERT INTO " +
                    "hotel(hotel_id, hotel_name, city, street_address, hotel_phone_number)" +
                    "VALUES (123, 'javaInn', 'Raleigh', 'Avent very', '919000111');");
        } catch (SQLException e) {
//            e.printStackTrace();
        }
    }

    @Test
    public void testConstructor() throws Exception {
        Hotel h = new Hotel("javaInn", "Raleigh", "Avent very", "919000111");
        assertNotNull(h);
    }

    @Test
    public void testGetById() throws Exception {
        initObject();
        Hotel h = Hotel.getById(123);
        assertNotNull(h);
    }

    @Test
    public void testRemove() throws Exception {
        initObject();
        Hotel h = Hotel.getById(123);
        assertNotNull(h);
        assertTrue(h.remove());
        h = Hotel.getById(123);
        assertNull(h);
    }

    @Test
    public void testUpdate() throws Exception {
        initObject();
        Hotel h = Hotel.getById(123);
        assertNotNull(h);
        h.setAddress("Gorman Street");
        h.setPhoneNumber("919111000");
        try {
            h.update();
        } catch (SQLException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }
}