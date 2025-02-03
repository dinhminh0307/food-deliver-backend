package food.delivery.minh.common.models.accounts;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true) // Include fields from Account class
@Table(name = "users", schema = "food-product")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends Account{
    @ElementCollection // Enables a collection of primitive types
    @CollectionTable(
        name = "user_schedule", 
        schema = "food-product",
        joinColumns = @JoinColumn(name = "account_id")
    )
    @Column(name = "schedule_id")
    private List<Integer> scheduleIds = new ArrayList<>();


    @ElementCollection
    @CollectionTable(
        name = "user_reviews",
        schema = "food-product",
        joinColumns = @JoinColumn(name = "account_id") // FK reference
    )
    @Column(name = "review_id")
    private List<Integer> reviewIds = new ArrayList<>(); // Store only Review IDs


    @Column(name = "cart_id")
    private Integer cartId; // ✅ Integer allows null values

}
