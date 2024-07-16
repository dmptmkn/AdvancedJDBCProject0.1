package dao;

import entity.Purchase;
import util.DbConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDao implements Dao<Purchase> {

    private static final PurchaseDao INSTANCE = new PurchaseDao();

    private PurchaseDao() {
    }

    public static PurchaseDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Purchase> findAll() {
        List<Purchase> purchases = new ArrayList<>();

        String sqlQuery = "SELECT * FROM purchaseList";
        try (Connection connection = DbConnectionManager.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
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
            throw new RuntimeException(e);
        }

        return purchases;
    }
}
