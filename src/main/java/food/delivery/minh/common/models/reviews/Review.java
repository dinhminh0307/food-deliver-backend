package food.delivery.minh.common.models.reviews;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "reviews", schema = "food-product")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer reviewId;

    private String comment;

    private double rating;

    @Column(name = "account_id")
    private Integer ownerId; // Store only the FK instead of the User entity

    @Column(name = "movie_id")
    private UUID movieId; // Store only the FK instead of the User entity
}
