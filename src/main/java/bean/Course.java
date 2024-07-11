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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public CourseType getType() {
        return type;
    }

    public void setType(CourseType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getStudentsCount() {
        return studentsCount;
    }

    public void setStudentsCount(int studentsCount) {
        this.studentsCount = studentsCount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
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
