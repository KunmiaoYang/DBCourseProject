package db;

import common.Constants;
import model.Hotel;
import model.Model;

import java.sql.*;

/**
 *
 * Created by Kunmiao Yang on 2/12/2018.
 */
public class Database {
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;
    private String driver;
    private String jdbcURL;
    private String user;
    private String password;

    public Database(String driver, String jdbcURL, String user, String password) throws SQLException, ClassNotFoundException {
        this.driver = driver;
        this.jdbcURL = jdbcURL;
        this.user = user;
        this.password = password;
        connectToDatabase();
    }

    private void connectToDatabase() throws ClassNotFoundException, SQLException {
        Class.forName(driver);

        connection = DriverManager.getConnection(jdbcURL, user, password);
        statement = connection.createStatement();
    }

    private void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (result != null) {
            try {
                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setJdbcURL(String jdbcURL) {
        this.jdbcURL = jdbcURL;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJdbcURL() {
        return jdbcURL;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Database db = new Database(Constants.DB_DRIVER, Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
        db.connectToDatabase();
        Model.setDatabase(db);
//        Hotel h = new Hotel("javaInn", "Raleigh", "Avent very", "919000111");
        Hotel h = Hotel.getById(5);
        if (h != null) {
            h.setAddress("Gorman Street");
            h.setName("RaleighInn");
            h.setPhoneNumber("919111000");
            h.update();
        }
//        if (h != null) {
//            h.remove();
//        }
//        db.statement.executeUpdate("CREATE TABLE Students (Name VARCHAR(20), "
//                + "School VARCHAR(10), Age INTEGER, FundingReceived INTEGER, Income INTEGER, Sex CHAR(1))");
//        ResultSet r = db.statement.executeQuery("SELECT * FROM Students;");
//        while(r.next()) {
//            System.out.print("Name: " + r.getString("Name"));
//            System.out.println(" Age: " + r.getInt("Age"));
//        }
        db.close();
    }
}
