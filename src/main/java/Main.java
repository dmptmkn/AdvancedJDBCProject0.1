import printer.*;
import util.DbConnectionManager;

import java.io.PrintStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final List<Printer> printers = new ArrayList<>();

    static {
        PrintStream printStream = System.out;

        printers.add(new CoursePrinter(printStream));
        printers.add(new TeacherPrinter(printStream));
        printers.add(new StudentPrinter(printStream));
        printers.add(new SubscriptionPrinter(printStream));
        printers.add(new PurchasePrinter(printStream));
    }

    public static void main(String[] args) {
        printers.forEach(Printer::printData);
    }
}

