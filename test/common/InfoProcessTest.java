package common;

import db.Database;
import model.Hotel;
import model.Model;
import model.Room;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static common.Constants.*;
import static common.InfoProcess.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 *
 * Created by Kunmiao Yang on 4/6/2018.
 */
public class InfoProcessTest {
    Database database;

    @Before
    public void setUp() throws Exception {
        database = new Database(DB_DRIVER, DB_URL, DB_USER, DB_PASSWORD);
        Model.setDatabase(database);
        InfoProcess.setDatabase(database);
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }

    @Test
    public void testGetAvailableRooms() throws Exception {
        Hotel hotel = Hotel.getById(1);
        assertNotNull(hotel);

        Room[] rooms = getAvailableRooms(2, hotel, "Deluxe");
        assertNotNull(rooms);
        assertTrue(rooms.length > 0);
        for(Room room: rooms) assertTrue(room.isAvailability() && room.getHotel().getId() == 1 && Objects.equals(room.getType(), "Deluxe"));

        rooms = getAvailableRooms(2, hotel, null);
        assertNotNull(rooms);
        assertTrue(rooms.length > 0);
        for(Room room: rooms) assertTrue(room.isAvailability() && room.getHotel().getId() == 1);

        rooms = getAvailableRooms(1, null, "Economy");
        assertNotNull(rooms);
        assertTrue(rooms.length > 0);
        for(Room room: rooms) assertTrue(room.isAvailability() && Objects.equals(room.getType(), "Economy"));

        rooms = getAvailableRooms(1, null, null);
        assertNotNull(rooms);
        assertTrue(rooms.length > 0);
        for(Room room: rooms) assertTrue(room.isAvailability());

        rooms = getAvailableRooms(1, hotel, "Executive");
        assertNotNull(rooms);
        assertTrue(rooms.length == 0);
    }
}