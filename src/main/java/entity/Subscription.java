package entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Subscription {

    private String studentName;
    private String courseName;
    private LocalDate subscriptionDate;

}
