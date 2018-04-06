package common;

import db.Database;
import model.Hotel;
import model.Model;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import static common.Constants.*;
import static common.InfoProcess.*;
import static org.junit.Assert.*;

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
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }

    @Test
    public void testGetAvailableRooms() throws Exception {

    }

    @Test
    public void testAssignRoom() throws Exception {

    }

    @Test
    public void testReleaseRoom() throws Exception {

    }
}