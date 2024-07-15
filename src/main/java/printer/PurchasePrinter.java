package printer;

import bean.Purchase;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PurchasePrinter extends Printer {

    private List<Purchase> purchases;

    public PurchasePrinter(Connection dbConnection, PrintStream printStream) {
        super(dbConnection, printStream);
    }

    @Override
    protected void init() {
        purchases = new ArrayList<>();
        collectData();
    }

    @Override
    protected void collectData() {
        String sqlQuery = "SELECT * FROM purchaseList";

        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Purchase nextPurchase;
            while (resultSet.next()) {
                nextPurchase = Purchase.builder()
                        .studentName(resultSet.getString("student_name"))
                        .courseName(resultSet.getString("course_name"))
                        .price(resultSet.getInt("price"))
                        .subscriptionDate(resultSet.getObject("subscription_date", LocalDate.class))
                        .build();
                purchases.add(nextPurchase);
            }
            printStream.println("Данные по продажам собраны!");
        } catch (SQLException e) {
            printStream.println("Ошибка при работе с базой данных!");
            throw new RuntimeException(e);
        }

    }

    @Override
    public void printData() {
        printStream.println("Вывожу данные по продажам:");
        purchases.sort(Comparator.comparing(Purchase::getSubscriptionDate));
        String formattedPurchaseInfo;
        for (Purchase p : purchases) {
            formattedPurchaseInfo = String.format("%s: %s приобрел курс «%s» за ₽%d",
                    p.getSubscriptionDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    p.getStudentName(),
                    p.getCourseName(),
                    p.getPrice());
            printStream.println(formattedPurchaseInfo);
        }
        printStream.println();
    }

}
