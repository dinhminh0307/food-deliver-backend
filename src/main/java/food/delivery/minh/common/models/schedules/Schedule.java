package food.delivery.minh.common.models.schedules;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    @ElementCollection
    @CollectionTable(
        name = "user_schedule", 
        schema = "food-product",
        joinColumns = @JoinColumn(name = "schedule_id") // Foreign key reference
    )
    @Column(name = "account_id")
    private List<Integer> accountIds = new ArrayList<>(); // ✅ Store only User IDs

    @ElementCollection
    @CollectionTable(
        name = "cart_schedule", 
        schema = "food-product",
        joinColumns = @JoinColumn(name = "schedule_id") // Foreign key reference
    )
    @Column(name = "cart_id")
    private List<Integer> cartId = new ArrayList<>(); // ✅ Store only User IDs
}
