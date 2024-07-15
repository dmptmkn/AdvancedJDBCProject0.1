package printer;

import bean.Course;
import bean.CourseType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CoursePrinter extends Printer {

    private List<Course> courses;

    public CoursePrinter(Connection dbConnection) {
        super(dbConnection);
    }

    @Override
    protected void init() {
        printer = System.out;
        courses = new ArrayList<>();
        collectData();
    }

    @Override
    protected void collectData() {
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

        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Course nextCourse;
            while (resultSet.next()) {
                nextCourse = Course.builder()
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
            printer.println("Ошибка при работе с базой данных!");
            throw new RuntimeException(e);
        }
        printer.println("Данные по курсам собраны!");
    }

    @Override
    public void printData() {
        printer.println("Вывожу данные:");
        String formattedCourseInfo;
        for (Course c : courses) {
             formattedCourseInfo = String.format(Locale.US, "Курс №%d «%s»\nCпециальность: %s\nОписание курса: %s\nПреподаватель: %s\nДлительность курса: %d ч.\nКоличество студентов на курсе: %d\nСтоимость курса: ₽%d (или ₽%.0f за час)",
                    c.getId(),
                    c.getName(),
                    c.getType().getDescription(),
                    c.getDescription(),
                    c.getTeacherName(),
                    c.getDuration(),
                    c.getStudentsCount(),
                    c.getPrice(),
                    c.getPricePerHour());
            printer.println(formattedCourseInfo);
            printer.println("========================================================================================");
        }
        printer.println();
    }
}
