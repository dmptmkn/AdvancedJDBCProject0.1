package printer;

import java.io.PrintStream;
import java.sql.Connection;
import java.text.ChoiceFormat;

public abstract class Printer {

    protected final Connection dbConnection;
    protected PrintStream printStream;

    public Printer(Connection dbConnection, PrintStream printStream) {
        if (dbConnection == null) throw new IllegalArgumentException("База данных не может быть null");
        this.dbConnection = dbConnection;
        this.printStream = printStream;
        init();
    }

    protected abstract void init();
    protected abstract void collectData();
    public abstract void printData();

    protected String getFormattedAge(int age) {
        double[] limits = {0, 1, 2, 5};
        String[] ageForms = {"лет", "год", "года", "лет"};

        ChoiceFormat format = new ChoiceFormat(limits, ageForms);
        int rule = 11 <= (age % 100) && (age % 100) <= 14 ? age : age % 10;
        return String.valueOf(age) + ' ' + format.format(rule);
    }
}
