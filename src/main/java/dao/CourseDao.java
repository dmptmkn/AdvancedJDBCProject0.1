package dao;

import entity.Course;
import entity.CourseType;
import util.DbConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao implements Dao<Course> {

    private static CourseDao instance;

    private CourseDao() {
    }

    public static CourseDao getInstance() {
        if (instance == null) {
            instance = new CourseDao();
        }
        return instance;
    }

    @Override
    public List<Course> findAll() {
        List<Course> courses = new ArrayList<>();

        String sqlQuery = """
                SELECT c.id,
                       c.name,
                       c.duration,
                       c.type,
                       c.description,
                       t.name AS teacher_name,
                       c.students_count,
                       c.price,
                       c.price_per_hour
                FROM courses AS c
                        JOIN teachers AS t ON t.id = c.teacher_id
                """;
        try (Connection connection = DbConnectionManager.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Course nextCourse = Course.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .duration(resultSet.getInt("duration"))
                        .type(CourseType.valueOf(resultSet.getString("type")))
                        .description(resultSet.getString("description"))
                        .teacherName(resultSet.getString("teacher_name"))
                        .studentsCount(resultSet.getInt("students_count"))
                        .price(resultSet.getInt("price"))
                        .pricePerHour(resultSet.getDouble("price_per_hour"))
                        .build();
                courses.add(nextCourse);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return courses;
    }
}
