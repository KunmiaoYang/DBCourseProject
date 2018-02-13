package db;

import common.Constants;

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
}
