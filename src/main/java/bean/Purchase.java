package bean;

import java.util.Date;
import java.util.Objects;

public class Purchase {

    private String studentName;
    private String courseName;
    private int price;
    private Date subscriptionDate;

    public Purchase(String studentName, String courseName, int price, Date subscriptionDate) {
        this.studentName = studentName;
        this.courseName = courseName;
        this.price = price;
        this.subscriptionDate = subscriptionDate;
    }

    public Purchase() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Purchase purchase)) return false;
        return price == purchase.price && Objects.equals(studentName, purchase.studentName) && Objects.equals(courseName, purchase.courseName) && Objects.equals(subscriptionDate, purchase.subscriptionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentName, courseName, price, subscriptionDate);
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }
}
