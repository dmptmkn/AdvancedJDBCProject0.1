package bean;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Purchase {

    private String studentName;
    private String courseName;
    private int price;
    private LocalDate subscriptionDate;

}
