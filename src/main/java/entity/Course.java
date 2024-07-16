package entity;

import lombok.*;

@Data
@Builder
public class Course {

    private int id;
    private String name;
    private int duration;
    private CourseType type;
    private String description;
    private String teacherName;
    private int studentsCount;
    private int price;
    private double pricePerHour;

}
