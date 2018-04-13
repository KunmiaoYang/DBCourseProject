package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import static common.Constants.*;

/**
 *
 * Created by Kunmiao Yang on 2/12/2018.
 */
public class Account extends Model {
    int id;
    Integer cardNumber;
    Customer customer;
    String address, payMethod, ssn;

    private Account(int id) {
        this.id = id;
    }

    public Account(Customer customer, String address, String payMethod, Integer cardNumber, String ssn) throws SQLException {
        if(null == customer) throw new SQLException(ERROR_ACCOUNT_INVALID_CUSTOMER);
        this.customer = customer;
        this.address = address;
        this.payMethod = payMethod;
        this.cardNumber = cardNumber;
        this.ssn = ssn;
        // Create tuple in database
        database.getStatement().executeUpdate("INSERT INTO " +
                "account(billing_address, payment_method, card_num, customer_id, payer_ssn) " +
                "VALUES ('" + address + "', '" + payMethod + "', " + cardNumber + ", " + customer.getId() +
                ", '" + ssn + "');");
    }

    public static Account getById(int id) {
        // Get instance from database
        Account account = new Account(id);
        try {
            ResultSet resultSet = database.getStatement().executeQuery("SELECT * FROM account WHERE account_id = " + id);
            if(!resultSet.next()) return null;
            int customerId = resultSet.getInt("customer_id");
            account.setAddress(resultSet.getString("billing_address"));
            account.setPayMethod(resultSet.getString("payment_method"));
            account.setCardNumber(resultSet.getInt("card_num"));
            account.setSsn(resultSet.getString("payer_ssn"));
            resultSet.close();
            account.setCustomer(Customer.getById(customerId));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return account;
    }

    public void remove() throws SQLException {
        // Remove from DB
        remove(TABLE_ACCOUNT, "account_id = " + id);
    }

    public void update() throws SQLException {
        // Update attributes to DB
        database.getStatement().executeUpdate("UPDATE account" +
                " SET billing_address = '" + address + "'" +
                ", payment_method = '" + payMethod + "'" +
                ", card_num = " + cardNumber +
                ", customer_id = " + customer.getId() +
                ", payer_ssn = '" + ssn + "'" +
                " WHERE account_id = " + id + ";");
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
}
