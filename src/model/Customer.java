package model;

import common.Constants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * Created by Kunmiao Yang on 2/12/2018.
 */
public class Customer extends Model {
    int id;
    String name, phone, email;
    LocalDate birth;

    protected Customer(int id) {
        this.id = id;
    }

    public Customer(int id, String name, String phone, String email, LocalDate birth) throws SQLException {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.birth = birth;
        // Create tuple in database
        database.getStatement().executeUpdate("INSERT INTO " +
                "customer(customer_id, name, date_of_birth, phone, email)" +
                "VALUES (" + this.id + ", \"" + this.name + "\", \"" + this.birth.toString() + "\", \"" +
                this.phone + "\", \"" + this.email + "\");");
    }

    public static Customer getById(int id) {
        // Get instance from database
        Customer customer = new Customer(id);
        try {
            ResultSet resultSet = database.getStatement().executeQuery(
                    "SELECT * FROM customer WHERE customer_id = " + id + ";");
            if(!resultSet.next()) return null;
            customer.setName(resultSet.getString("name"));
            customer.setPhone(resultSet.getString("phone"));
            customer.setEmail(resultSet.getString("email"));
            customer.setBirth(LocalDate.parse(resultSet.getString("date_of_birth")));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return customer;
    }

    public boolean remove() {
        // Remove from DB
        return remove(Constants.TABLE_CUSTOMER, "customer_id = " + this.id);
    }

    public boolean update() {
        // Update attributes to DB
        try {
            database.getStatement().executeUpdate("UPDATE customer" +
                    " SET name = \"" + name + "\"" +
                    ", date_of_birth = \"" + birth.toString() + "\"" +
                    ", phone = \"" + phone + "\"" +
                    ", email = \"" + email + "\"" +
                    " WHERE customer_id = " + id + ";");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Account getDefaultAccount() {
        // Get default account
        try {
            ResultSet resultSet = database.getStatement().executeQuery("SELECT * FROM account WHERE customer_id = " + id + ";");
            if(resultSet.next()) {
                int accountId = resultSet.getInt("account_id");
                resultSet.close();
                return Account.getById(accountId);
            }
            resultSet.close();
        } catch (SQLException e) {
//            e.printStackTrace();
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }
}
