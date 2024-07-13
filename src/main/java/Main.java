import printer.CoursePrinter;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {
        DBConnector dbConnector = new DBConnector();
        Connection connection = dbConnector.getDbConnection();

        CoursePrinter coursePrinter = new CoursePrinter(connection);
        coursePrinter.printData();
    }
}
