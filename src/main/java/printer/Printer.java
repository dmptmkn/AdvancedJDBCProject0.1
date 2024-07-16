package printer;

import java.io.PrintStream;
import java.text.ChoiceFormat;

public abstract class Printer {

    protected PrintStream printStream;

    public Printer(PrintStream printStream) {
        this.printStream = printStream;
        collectData();
    }

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
