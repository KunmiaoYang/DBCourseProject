package common;

import db.Database;
import model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static common.Constants.*;
import static common.Report.*;
import static org.junit.Assert.*;

/**
 *
 * Created by Kunmiao Yang on 4/7/2018.
 */
public class ReportTest {
    Database database;

    @Before
    public void setUp() throws Exception {
        database = new Database(DB_DRIVER, DB_URL, DB_USER, DB_PASSWORD);
        Model.setDatabase(database);
        Report.setDatabase(database);
    }

    @After
    public void tearDown() throws Exception {
        database.close();
    }

    @Test
    public void testGetOccupancyByHotel() throws Exception {
        Map<Integer, Occupancy> report = getOccupancyByHotel();
        assertNotNull(report);
        assertTrue(report.containsKey(1));
        assertTrue(report.containsKey(2));
        assertTrue(report.containsKey(3));
        assertTrue(report.containsKey(4));
        assertEquals(0, report.get(1).percentage, 0.0001);
        assertEquals(0, report.get(2).percentage, 0.0001);
        assertEquals(1, report.get(3).percentage, 0.0001);
        assertEquals(0, report.get(4).percentage, 0.0001);        
        assertEquals(0, report.get(1).occupancy);
        assertEquals(0, report.get(2).occupancy);
        assertEquals(1, report.get(3).occupancy);
        assertEquals(0, report.get(4).occupancy);
    }

    @Test
    public void testGetOccupancyByRoomType() throws Exception {
        Map<String, Occupancy> report = getOccupancyByRoomType();
        assertNotNull(report);
        assertTrue(report.containsKey("Economy"));
        assertTrue(report.containsKey("Deluxe"));
        assertTrue(report.containsKey("Executive"));
        assertTrue(report.containsKey("Presidential"));
        assertEquals(0, report.get("Economy").percentage, 0.0001);
        assertEquals(0, report.get("Deluxe").percentage, 0.0001);
        assertEquals(1, report.get("Executive").percentage, 0.0001);
        assertEquals(0, report.get("Presidential").percentage, 0.0001);
        assertEquals(0, report.get("Economy").occupancy);
        assertEquals(0, report.get("Deluxe").occupancy);
        assertEquals(1, report.get("Executive").occupancy);
        assertEquals(0, report.get("Presidential").occupancy);
    }

    @Test
    public void testGetOccupancyByCity() throws Exception {
        Map<String, Occupancy> report = getOccupancyByCity();
        assertNotNull(report);
        assertTrue(report.containsKey("Raleigh"));
        assertTrue(report.containsKey("Greensboro"));
        assertTrue(report.containsKey("Rochester"));
        assertEquals(0, report.get("Raleigh").percentage, 0.0001);
        assertEquals(1, report.get("Greensboro").percentage, 0.0001);
        assertEquals(0, report.get("Rochester").percentage, 0.0001);
        assertEquals(0, report.get("Raleigh").occupancy);
        assertEquals(1, report.get("Greensboro").occupancy);
        assertEquals(0, report.get("Rochester").occupancy);
    }

    @Test
    public void testGetOccupancyByDateRange() throws Exception {
        Map<String, Occupancy> report = getOccupancyByDateRange();
        assertNotNull(report);
        assertTrue(report.containsKey("2016-05-10"));
        assertTrue(report.containsKey("2017-05-10"));
        assertTrue(report.containsKey("2018-05-10"));
        assertEquals(1.0/6, report.get("2016-05-10").percentage, 0.0001);
        assertEquals(1.0/3, report.get("2017-05-10").percentage, 0.0001);
        assertEquals(1.0/6, report.get("2018-05-10").percentage, 0.0001);
        assertEquals(1, report.get("2016-05-10").occupancy);
        assertEquals(2, report.get("2017-05-10").occupancy);
        assertEquals(1, report.get("2018-05-10").occupancy);
    }

    @Test
    public void testGetStaffsByRole() throws Exception {
        Map<String, List<Staff>> report = getStaffsByRole();
        assertNotNull(report);
        assertTrue(report.containsKey("Manager"));
        assertTrue(report.containsKey("Front Desk Staff"));
        assertTrue(report.containsKey("Catering Staff"));
        assertEquals(4, report.get("Manager").size());
        assertEquals(2, report.get("Front Desk Staff").size());
        assertEquals(1, report.get("Catering Staff").size());
        assertEquals(104, report.get("Catering Staff").get(0).getStaffId());
    }

    @Test
    public void testGetServingStaffs() throws Exception {
        try {
            database.getStatement().executeUpdate("INSERT INTO" +
                    " checkin(checkin_id, checkin_time, hotel_id, room_number, guest_num, customer_id, account_id)" +
                    " VALUES (123, '2018-04-05 13:20:36', 1, 2, 2, 1003, 3);");
            CheckIn checkIn = CheckIn.getById(123);
            assertNotNull(checkIn);

            List<Staff> staffs = getServingStaffs(checkIn);
            assertNotNull(staffs);
            assertEquals(0, staffs.size());

            new Service("gyms", checkIn, null);
            staffs = getServingStaffs(checkIn);
            assertNotNull(staffs);
            assertEquals(0, staffs.size());

            new Service("dry cleaning", checkIn, Staff.getById(104));
            new Service("room service", checkIn, Staff.getById(104));
            staffs = getServingStaffs(checkIn);
            assertNotNull(staffs);
            assertEquals(1, staffs.size());
            assertEquals(104, staffs.get(0).getStaffId());

            new Service("room service", checkIn, Staff.getById(103));
            staffs = getServingStaffs(checkIn);
            assertNotNull(staffs);
            assertEquals(2, staffs.size());
        } finally {
            Model.remove(TABLE_SERVICE, "checkin_id = 123");
            Model.remove(TABLE_CHECK_IN, "checkin_id = 123");
        }
    }

    @Test
    public void testGetRevenue() throws Exception {
        Hotel hotel1 = Hotel.getById(1), hotel4 = Hotel.getById(4);
        assertNotNull(hotel1);
        assertNotNull(hotel4);

        float revenue = getRevenue(hotel1, LocalDate.of(2017, 5, 10), LocalDate.of(2017, 5, 13));
        assertEquals(946, revenue, 0.0001);

        revenue = getRevenue(hotel1, LocalDate.of(2017, 5, 11), LocalDate.of(2017, 5, 12));
        assertEquals(0, revenue, 0.0001);

        revenue = getRevenue(hotel1, LocalDate.of(2016, 1, 1), LocalDate.of(2016, 12, 31));
        assertEquals(0, revenue, 0.0001);

        revenue = getRevenue(hotel4, LocalDate.of(2016, 1, 1), LocalDate.of(2018, 12, 31));
        assertEquals(0, revenue, 0.0001);
    }
}