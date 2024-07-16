package printer;

import dao.SubscriptionDao;
import entity.Subscription;

import java.io.PrintStream;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class SubscriptionPrinter extends Printer {

    List<Subscription> subscriptions;

    public SubscriptionPrinter(PrintStream printStream) {
        super(printStream);
    }

    @Override
    protected void collectData() {
        subscriptions = SubscriptionDao.getInstance().findAll();
    }

    @Override
    public void printData() {
        printStream.println("Вывожу данные по подпискам:");
        subscriptions.sort(Comparator.comparing(Subscription::getSubscriptionDate));
        for (Subscription s : subscriptions) {
            String formattedSubscriptionInfo = String.format("%s: %s подписался на курс «%s»",
                    s.getSubscriptionDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    s.getStudentName(),
                    s.getCourseName());
            printStream.println(formattedSubscriptionInfo);
        }
        printStream.println();
    }
}
