package printer;

import bean.Teacher;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherPrinter extends Printer {

    private List<Teacher> teachers;

    public TeacherPrinter(Connection dbConnection, PrintStream printStream) {
        super(dbConnection, printStream);
    }

    @Override
    protected void init() {
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
            printStream.println("Данные по преподавателям собраны!");
        } catch (SQLException e) {
            printStream.println("Ошибка при работе с базой данных!");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void printData() {
        printStream.println("Вывожу данные по преподавателям:");
        String formattedTeacherInfo;
        for (Teacher t : teachers) {
            formattedTeacherInfo = String.format("%d. %s, %s, зарплата — ₽%d",
                    t.getId(),
                    t.getName(),
                    getFormattedAge(t.getAge()),
                    t.getSalary());
            printStream.println(formattedTeacherInfo);
        }
        printStream.println();
    }
}
