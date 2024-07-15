package printer;

import bean.Subscription;

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

public class SubscriptionPrinter extends Printer {

    List<Subscription> subscriptions;

    public SubscriptionPrinter(Connection dbConnection, PrintStream printStream) {
        super(dbConnection, printStream);
    }

    @Override
    protected void init() {
        printStream = System.out;
        subscriptions = new ArrayList<>();
        collectData();
    }

    @Override
    protected void collectData() {
        String sqlQuery = """
                SELECT students.name AS student_name, courses.name AS course_name, subscriptions.subscription_date AS subscription_date
                FROM subscriptions
                        JOIN students ON subscriptions.student_id = students.id
                        JOIN courses ON subscriptions.course_id = courses.id
                """;

        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Subscription nextSubscription;
            while (resultSet.next()) {
                nextSubscription = Subscription.builder()
                        .studentName(resultSet.getString("student_name"))
                        .courseName(resultSet.getString("course_name"))
                        .subscriptionDate(resultSet.getObject("subscription_date", LocalDate.class))
                        .build();
                subscriptions.add(nextSubscription);
            }
            printStream.println("Данные по подпискам собраны!");
        } catch (SQLException e) {
            printStream.println("Ошибка при работе с базой данных!");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void printData() {
        printStream.println("Вывожу данные по подпискам:");
        String formattedSubscriptionInfo;
        subscriptions.sort(Comparator.comparing(Subscription::getSubscriptionDate));
        for (Subscription s : subscriptions) {
            formattedSubscriptionInfo = String.format("%s: %s подписался на курс «%s»",
                    s.getSubscriptionDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    s.getStudentName(),
                    s.getCourseName());
            printStream.println(formattedSubscriptionInfo);
        }
        printStream.println();
    }
}
