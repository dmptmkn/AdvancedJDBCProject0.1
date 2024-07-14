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
import java.util.List;

public class PurchasePrinter {

    private final Connection dbConnection;
    private PrintStream printer;
    private List<Purchase> purchases;

    public PurchasePrinter(Connection dbConnection) {
        if (dbConnection == null) throw new IllegalArgumentException("Database Connection cannot be Null");
        this.dbConnection = dbConnection;
        init();
    }

    private void init() {
        this.purchases = new ArrayList<>();
        this.printer = System.out;
        collectData();
    }

    private void collectData() {
        String sqlQuery = "SELECT * FROM purchaseList";

        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Purchase nextPurchase = Purchase.builder()
                        .studentName(resultSet.getString("student_name"))
                        .courseName(resultSet.getString("course_name"))
                        .price(resultSet.getInt("price"))
                        .subscriptionDate(resultSet.getObject("subscription_date", LocalDate.class))
                        .build();
                purchases.add(nextPurchase);
            }
        } catch (SQLException e) {
            printer.println("Ошибка при работе с базой данных!");
        }
        printer.println("Данные по продажам собраны!");
    }

    public void printData() {
        printer.println("Вывожу данные в консоль:");
        for (Purchase p : purchases) {
            String formattedPurchaseInfo = String.format("%s года %s приобрел курс «%s» за ₽%d",
                    p.getSubscriptionDate().format(DateTimeFormatter.ofPattern("d MMMM yyyy")),
                    p.getStudentName(),
                    p.getCourseName(),
                    p.getPrice());
            printer.println(formattedPurchaseInfo);
        }
    }

}
