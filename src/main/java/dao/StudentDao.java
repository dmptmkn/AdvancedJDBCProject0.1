package dao;

import entity.Student;
import util.DbConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDao implements Dao<Student> {

    private static StudentDao instance;

    private StudentDao() {
    }

    public static StudentDao getInstance() {
        if (instance == null) {
            instance = new StudentDao();
        }
        return instance;
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();

        String sqlQuery = "SELECT * FROM students";
        try (Connection connection = DbConnectionManager.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Student nextStudent = Student.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .age(resultSet.getInt("age"))
                        .registrationDate(resultSet.getObject("registration_date", LocalDate.class))
                        .build();
                students.add(nextStudent);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return students;
    }
}
