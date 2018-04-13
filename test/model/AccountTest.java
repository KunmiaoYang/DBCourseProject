package model;

import db.Database;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static common.Constants.*;
import static org.junit.Assert.*;

/**
 *
 * Created by Kunmiao Yang on 4/6/2018.
 */
public class AccountTest {
    @Before
    public void setUp() throws Exception {
        Model.setDatabase(new Database(DB_DRIVER, DB_URL, DB_USER, DB_PASSWORD));
        Model.database.getStatement().executeUpdate("DELETE FROM account WHERE account_id = 123;");
    }

    @After
    public void tearDown() throws Exception {
        Model.database.getStatement().executeUpdate("DELETE FROM account WHERE account_id = 123;");
        Model.database.getStatement().executeUpdate("DELETE FROM account WHERE card_num = 1357924680;");
        Model.database.close();
    }

    public void initObject() {
        try {
            Model.database.getStatement().executeUpdate("INSERT INTO" +
                    " account(account_id, billing_address, payment_method, card_num, customer_id, payer_ssn)" +
                    " VALUES (123, 'Raleigh NC EBII', 'credit card', 1234567890, 1002, '777-8352');");
        } catch (SQLException e) {
//            e.printStackTrace();
        }
    }

    @Test
    public void testConstructor() throws Exception {
        // Test normal account creation
        Account a = new Account(Customer.getById(1002), "Raleigh NC EBII", "debit card", 1357924680, "777-8352");
        assertNotNull(a);
        assertNotNull(a.getCustomer());
        assertEquals(a.getCustomer().getName(), "Sarah");
        ResultSet resultSet = Model.database.getStatement().executeQuery("SELECT * FROM account WHERE card_num = 1357924680;");
        assertTrue(resultSet.next());
        assertEquals("Raleigh NC EBII", resultSet.getString("billing_address"));
        assertEquals("debit card", resultSet.getString("payment_method"));
        resultSet.close();
        Model.database.getStatement().executeUpdate("DELETE FROM account WHERE card_num = 1357924680;");

        // Test cash account creation
        a = new Account(Customer.getById(1002), "Raleigh NC EBII", "cash", null, "777-8352");
        assertNotNull(a);
        assertNotNull(a.getCustomer());
        assertEquals(a.getCustomer().getName(), "Sarah");
        resultSet = Model.database.getStatement().executeQuery("SELECT * FROM account WHERE ISNULL(card_num) AND payment_method = 'cash';");
        assertTrue(resultSet.next());
        assertEquals("cash", resultSet.getString("payment_method"));
        assertNull(resultSet.getObject("card_num"));
        resultSet.close();
        Model.database.getStatement().executeUpdate("DELETE FROM account WHERE ISNULL(card_num) AND payment_method = 'cash' AND customer_id = 1002;");

        // Test incorrect account creation
        try {
            new Account(null, "Raleigh NC EBII", "cash", null, "777-8352");
            assertTrue(false);
        } catch (SQLException e) {
            assertEquals(e.getMessage(), ERROR_ACCOUNT_INVALID_CUSTOMER);
        }
    }

    @Test
    public void testGetById() throws Exception {
        initObject();
        Account a = Account.getById(123);
        assertNotNull(a);
        assertNotNull(a.getCustomer());
        assertEquals(a.getCustomer().getName(), "Sarah");
        ResultSet resultSet = Model.database.getStatement().executeQuery("SELECT * FROM account WHERE account_id = 123;");
        assertTrue(resultSet.next());
        assertEquals("Raleigh NC EBII", resultSet.getString("billing_address"));
        assertEquals(1234567890, resultSet.getInt("card_num"));
        resultSet.close();
    }

    @Test
    public void testRemove() throws Exception {
        initObject();
        Account a = Account.getById(123);
        assertNotNull(a);
        assertTrue(a.remove());
        a = Account.getById(123);
        assertNull(a);
    }

    @Test
    public void testUpdate() throws Exception {
        initObject();
        Account a = Account.getById(123);
        assertNotNull(a);
        a.setAddress("Avent Very");
        a.setCardNumber(123456789);
        try {
            a.update();
        } catch (SQLException e) {
            e.printStackTrace();
            assertTrue(false);
        }
        ResultSet resultSet = Model.database.getStatement().executeQuery("SELECT * FROM account WHERE account_id = 123;");
        assertTrue(resultSet.next());
        assertEquals("Avent Very", resultSet.getString("billing_address"));
        assertEquals(123456789, resultSet.getInt("card_num"));
        resultSet.close();
    }
}