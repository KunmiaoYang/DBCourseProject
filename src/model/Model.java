package model;

import db.Database;

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

    abstract boolean update();

    protected String getFromDB(String ...name) {
        // TODO: get value from DB by name
        return null;
    }

    protected String setToDB(String ...nameValue) {
        // TODO: set value in DB by name
        return null;
    }
}
