package model;

import db.Database;

import java.sql.SQLException;

/**
 *
 * Created by Kunmiao Yang on 2/13/2018.
 */
abstract public class Model {
    protected static Database database;

    public static void setDatabase(Database database) {
        Model.database = database;
    }

    public abstract boolean remove();

    public static boolean remove(String table, String whereClause) {
        try {
            database.getStatement().executeUpdate("DELETE FROM " + table + " WHERE " + whereClause + ";");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public abstract void update() throws SQLException;

    public static Database getDatabase() {
        return database;
    }
}
