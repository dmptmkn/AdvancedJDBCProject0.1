package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DbConnectionManager {

    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";

    private DbConnectionManager() {
    }

    public static Connection getDbConnection() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.getPropertyByKey(URL_KEY),
                    PropertiesUtil.getPropertyByKey(USERNAME_KEY),
                    PropertiesUtil.getPropertyByKey(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
