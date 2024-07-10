package bean;

import java.util.Objects;

public class Course {

    private int id;
    private String name;
    private int duration;
    private CourseType type;
    private String description;
    private int teacherId;
    private int studentsCount;
    private int price;
    private double pricePerHour;

    public Course(int id, String name,
                  int duration,
                  CourseType type,
                  String description,
                  int teacherId,
                  int studentsCount,
                  int price,
                  double pricePerHour) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.type = type;
        this.description = description;
        this.teacherId = teacherId;
        this.studentsCount = studentsCount;
        this.price = price;
        this.pricePerHour = pricePerHour;
    }

    public Course() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)) return false;
        return id == course.id && duration == course.duration && teacherId == course.teacherId && studentsCount == course.studentsCount && price == course.price && Double.compare(course.pricePerHour, pricePerHour) == 0 && Objects.equals(name, course.name) && type == course.type && Objects.equals(description, course.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, duration, type, description, teacherId, studentsCount, price, pricePerHour);
    }
}
