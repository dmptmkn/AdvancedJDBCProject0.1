package printer;

import bean.Student;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StudentPrinter extends Printer {

    private List<Student> students;

    public StudentPrinter(Connection dbConnection, PrintStream printStream) {
        super(dbConnection, printStream);
    }

    @Override
    protected void init() {
        students = new ArrayList<>();
        collectData();
    }

    @Override
    protected void collectData() {
        String sqlQuery = "SELECT * FROM students";

        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sqlQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student nextStudent = Student.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .age(resultSet.getInt("age"))
                        .registrationDate(resultSet.getObject("registration_date", LocalDate.class))
                        .build();
                students.add(nextStudent);
            }
            printStream.println("Данные по студентам собраны!");
        } catch (SQLException e) {
            printStream.println("Ошибка при работе с базой данных!");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void printData() {
        printStream.println("Вывожу данные по студентам:");
        String formattedStudentInfo;
        for (Student s : students) {
            formattedStudentInfo = String.format("%d. %s, %s, зарегистрирован %s",
                    s.getId(),
                    s.getName(),
                    getFormattedAge(s.getAge()),
                    s.getRegistrationDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            printStream.println(formattedStudentInfo);
        }
        printStream.println();
    }
}
