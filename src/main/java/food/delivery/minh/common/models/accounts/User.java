package food.delivery.minh.common.models.accounts;

import java.util.ArrayList;
import java.util.List;

import food.delivery.minh.common.models.reviews.Review;
import food.delivery.minh.common.models.schedules.Schedule;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "user_schedule",
        schema = "food-product",
        joinColumns =  @JoinColumn(name = "account_id"),
        inverseJoinColumns = @JoinColumn(name = "schedule_id")
    )
    private List<Schedule> userSchedule = new ArrayList<>();

    @OneToMany(mappedBy = "owner") // refer to the owner field in the review entity
    private List<Review> userReviews = new ArrayList<>();
}
