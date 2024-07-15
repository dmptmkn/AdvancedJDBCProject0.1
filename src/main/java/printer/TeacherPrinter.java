package printer;

import bean.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherPrinter extends Printer {

    private List<Teacher> teachers;

    public TeacherPrinter(Connection dbConnection) {
        super(dbConnection);
    }

    @Override
    protected void init() {
        printer = System.out;
        teachers = new ArrayList<>();
        collectData();
    }

    @Override
    protected void collectData() {
        String sqlQuery = "SELECT * FROM teachers";

        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery(sqlQuery);
            Teacher nextTeacher;
            while (resultSet.next()) {
                nextTeacher = Teacher.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .salary(resultSet.getInt("salary"))
                        .age(resultSet.getInt("age"))
                        .build();
                teachers.add(nextTeacher);
            }
        } catch (SQLException e) {
            printer.println("Ошибка при работе с базой данных!");
            throw new RuntimeException(e);
        }
        printer.println("Данные по преподавателям собраны!");

    }

    @Override
    public void printData() {
        printer.println("Вывожу данные:");
        String formattedTeacherInfo;
        for (Teacher t : teachers) {
            formattedTeacherInfo = String.format("%d. %s\nВозраст: %s\nЗарплата: ₽%d/мес.",
                    t.getId(),
                    t.getName(),
                    getFormattedAge(t.getAge()),
                    t.getSalary());
            printer.println(formattedTeacherInfo);
            printer.println("========================================================================================");
        }
    }
}
