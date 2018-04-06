package model;

import common.Constants;
import db.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 *
 * Created by Kunmiao Yang on 4/5/2018.
 */
public class CustomerTest {
    @Before
    public void setUp() throws Exception {
        Model.setDatabase(new Database(Constants.DB_DRIVER, Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD));
        try {
            Customer customer = new Customer(123, "Yang", "404372", "yang@ncsu.edu", LocalDate.parse("1997-06-02"));
        } catch (SQLException e) {
//            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        Customer customer = Customer.getById(123);
        if (customer != null) {
            customer.remove();
        }
        Model.database.close();
    }

    @Test
    public void testConstructor() throws Exception {
        Customer customer = Customer.getById(123);
        if(null != customer) customer.remove();
        customer = new Customer(123, "Yang", "404372", "yang@ncsu.edu", LocalDate.parse("1997-06-02"));
        assertNotNull(customer);
    }

    @Test
    public void testGetById() throws Exception {
        Customer customer = Customer.getById(123);
        assertNotNull(customer);
    }

    @Test
    public void testUpdate() throws Exception {
        Customer customer = Customer.getById(123);
        assertNotNull(customer);
        customer.setBirth(LocalDate.now());
        customer.setName("Kunmiao");
        assertTrue(customer.update());
    }

    @Test
    public void testRemove() throws Exception {
        Customer customer = Customer.getById(123);
        assertNotNull(customer);
        assertTrue(customer.remove());
        customer = Customer.getById(123);
        assertNull(customer);
    }
}