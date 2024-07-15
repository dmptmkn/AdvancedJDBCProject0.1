import printer.*;

import java.io.PrintStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final List<Printer> printers = new ArrayList<>();

    static {
        Connection connection = new DBConnector().getDbConnection();
        PrintStream printStream = System.out;

        printers.add(new CoursePrinter(connection, printStream));
        printers.add(new TeacherPrinter(connection, printStream));
        printers.add(new StudentPrinter(connection, printStream));
        printers.add(new SubscriptionPrinter(connection, printStream));
        printers.add(new PurchasePrinter(connection, printStream));
    }

    public static void main(String[] args) {
        printers.forEach(Printer::printData);
    }
}

