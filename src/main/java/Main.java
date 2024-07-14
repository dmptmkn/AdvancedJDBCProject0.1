import printer.CoursePrinter;
import printer.PurchasePrinter;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {
        Connection connection = new DBConnector().getDbConnection();

        new CoursePrinter(connection).printData();
        new PurchasePrinter(connection).printData();
    }
}
