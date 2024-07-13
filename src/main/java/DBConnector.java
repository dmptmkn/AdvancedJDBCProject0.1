import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private static final String URL = "jdbc:mysql://localhost:3306/top";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root";

    private Connection dbConnection;

    public DBConnector() {
        establishConnection();
    }

    private void establishConnection() {
        try {
            dbConnection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            System.out.println("Соединение с базой данных установлено!");
        } catch (SQLException e) {
            System.out.println("Соединение с базой данных не установлено!");
        }
    }

    public Connection getDbConnection() {
        return dbConnection;
    }
}
