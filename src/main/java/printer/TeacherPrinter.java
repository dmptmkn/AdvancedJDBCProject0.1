package printer;

import dao.TeacherDao;
import entity.Teacher;

import java.io.PrintStream;
import java.util.List;

public class TeacherPrinter extends Printer {

    private List<Teacher> teachers;

    public TeacherPrinter(PrintStream printStream) {
        super(printStream);
    }

    @Override
    protected void collectData() {
        teachers = TeacherDao.getInstance().findAll();
    }

    @Override
    public void printData() {
        printStream.println("Вывожу данные по преподавателям:");
        for (Teacher t : teachers) {
            String formattedTeacherInfo = String.format("%d. %s, %s, зарплата — ₽%d",
                    t.getId(),
                    t.getName(),
                    getFormattedAge(t.getAge()),
                    t.getSalary());
            printStream.println(formattedTeacherInfo);
        }
        printStream.println();
    }
}
