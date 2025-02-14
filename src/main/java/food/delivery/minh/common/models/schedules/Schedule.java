package food.delivery.minh.common.models.schedules;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.SequenceGenerator;
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
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "schedule_seq_gen") // the id of the next records will depend from previous record id
    @SequenceGenerator(
        name = "schedule_seq_gen", 
        sequenceName = "food-product.schedule_seq", 
        allocationSize = 1
    )
    private Integer schedule_id;

    private DayOfWeek dayOfWeek; // Enum type (Monday, Tuesday, etc.)

    private LocalTime scheduleTime; // Rename to avoid conflicts
    
    private String name;

    private String category;

    private boolean isPassed = false;

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
        name = "product_schedule", 
        schema = "food-product",
        joinColumns = @JoinColumn(name = "schedule_id") // Foreign key reference
    )
    @Column(name = "cart_id")
    private List<UUID> productId = new ArrayList<>(); // ✅ Store only User IDs
}
