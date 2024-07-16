package dao;

import entity.Teacher;
import util.DbConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDao implements Dao<Teacher> {

    private static final TeacherDao INSTANCE = new TeacherDao();

    private TeacherDao() {
    }

    public static TeacherDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Teacher> findAll() {
        List<Teacher> teachers = new ArrayList<>();

        String sqlQuery = "SELECT * FROM teachers";
        try (Connection connection = DbConnectionManager.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                Teacher nextTeacher = Teacher.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .salary(resultSet.getInt("salary"))
                        .age(resultSet.getInt("age"))
                        .build();
                teachers.add(nextTeacher);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return teachers;
    }
}
