package food.delivery.minh.common.models.schedules;
import java.util.ArrayList;
import java.util.List;

import food.delivery.minh.common.models.accounts.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Table(name = "schedules", schema = "food-product")
@Entity
@Data
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor // Generates a constructor with all arguments
public class Schedule {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int schedule_id;

    private String time;

    private String name;

    private String category;

    @ManyToMany(mappedBy = "userSchedule") // refer to the userSchedule field in the User entity
    private List<User> accounts = new ArrayList<>();
}
