package common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 *
 * Created by Kunmiao Yang on 2/12/2018.
 */
public class Constants {
    public static final String DB_DRIVER;
    public static final String DB_URL;
    public static final String DB_USER;
    public static final String DB_PASSWORD;
    static{
        ResourceBundle config = ResourceBundle.getBundle("config");
        ResourceBundle account = ResourceBundle.getBundle("account");
        DB_USER = account.getString("db.user");
        DB_PASSWORD = account.getString("db.password");
        DB_DRIVER = config.getString("db.driver");
        DB_URL = config.getString("db.url") + DB_USER;
    }
}
