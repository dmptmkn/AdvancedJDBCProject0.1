package printer;

import dao.PurchaseDao;
import entity.Purchase;

import java.io.PrintStream;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class PurchasePrinter extends Printer {

    private List<Purchase> purchases;

    public PurchasePrinter(PrintStream printStream) {
        super(printStream);
    }

    @Override
    protected void collectData() {
        purchases = PurchaseDao.getInstance().findAll();
    }

    @Override
    public void printData() {
        printStream.println("Вывожу данные по продажам:");
        purchases.sort(Comparator.comparing(Purchase::getSubscriptionDate));
        for (Purchase p : purchases) {
            String formattedPurchaseInfo = String.format("%s: %s приобрел курс «%s» за ₽%d",
                    p.getSubscriptionDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    p.getStudentName(),
                    p.getCourseName(),
                    p.getPrice());
            printStream.println(formattedPurchaseInfo);
        }
        printStream.println();
    }
}
