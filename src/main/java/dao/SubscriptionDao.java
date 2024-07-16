package dao;

import entity.Subscription;
import util.DbConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDao implements Dao<Subscription> {

    private static final SubscriptionDao INSTANCE = new SubscriptionDao();

    private SubscriptionDao() {
    }

    public static SubscriptionDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Subscription> findAll() {
        List<Subscription> subscriptions = new ArrayList<>();

        String sqlQuery = """
                SELECT students.name AS student_name, courses.name AS course_name, subscriptions.subscription_date AS subscription_date
                FROM subscriptions
                        JOIN students ON subscriptions.student_id = students.id
                        JOIN courses ON subscriptions.course_id = courses.id
                """;
        try (Connection connection = DbConnectionManager.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Subscription nextSubscription = Subscription.builder()
                        .studentName(resultSet.getString("student_name"))
                        .courseName(resultSet.getString("course_name"))
                        .subscriptionDate(resultSet.getObject("subscription_date", LocalDate.class))
                        .build();
                subscriptions.add(nextSubscription);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return subscriptions;
    }
}
