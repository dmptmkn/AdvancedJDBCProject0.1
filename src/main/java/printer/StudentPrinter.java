package printer;

import bean.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ChoiceFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StudentPrinter extends Printer {

    private List<Student> students;

    public StudentPrinter(Connection dbConnection) {
        super(dbConnection);
    }

    @Override
    protected void init() {
        printer = System.out;
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
        } catch (SQLException e) {
            printer.println("Ошибка при работе с базой данных!");
            throw new RuntimeException(e);
        }
        printer.println("Данные по студентам собраны!");
    }

    @Override
    public void printData() {
        printer.println("Вывожу данные:");
        String formattedStudentInfo;
        for (Student s : students) {
            formattedStudentInfo = String.format("%d. %s, %s, зарегистрирован %s",
                    s.getId(),
                    s.getName(),
                    getFormattedAge(s.getAge()),
                    s.getRegistrationDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            printer.println(formattedStudentInfo);
        }
        printer.println();
    }

    private String getFormattedAge(int age) {
        double[] limits = {0, 1, 2, 5};
        String[] ageForms = {"лет", "год", "года", "лет"};

        ChoiceFormat format = new ChoiceFormat(limits, ageForms);
        int rule = 11 <= (age % 100) && (age % 100) <= 14 ? age : age % 10;
        return String.valueOf(age) + ' ' + format.format(rule);
    }
}
