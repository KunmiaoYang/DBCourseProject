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

    abstract boolean remove();

    protected boolean remove(String table, String whereClause) {
        try {
            database.getStatement().executeUpdate("DELETE FROM " + table + " WHERE " + whereClause);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    abstract boolean update();
}
