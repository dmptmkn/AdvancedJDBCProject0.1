package printer;

import dao.StudentDao;
import entity.Student;

import java.io.PrintStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StudentPrinter extends Printer {

    private List<Student> students;

    public StudentPrinter(PrintStream printStream) {
        super(printStream);
    }

    @Override
    protected void collectData() {
        students = StudentDao.getInstance().findAll();
    }

    @Override
    public void printData() {
        printStream.println("Вывожу данные по студентам:");
        for (Student s : students) {
            String formattedStudentInfo = String.format("%d. %s, %s, зарегистрирован %s",
                    s.getId(),
                    s.getName(),
                    getFormattedAge(s.getAge()),
                    s.getRegistrationDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            printStream.println(formattedStudentInfo);
        }
        printStream.println();
    }
}
