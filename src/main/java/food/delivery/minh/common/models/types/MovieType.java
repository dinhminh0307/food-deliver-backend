package food.delivery.minh.common.models.types;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Table(name="movie_types", schema = "food-product")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class MovieType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_type_seq_gen")
    @SequenceGenerator(
        name = "movie_type_seq_gen",
        sequenceName = "food-product.movie_type_seq",
        allocationSize = 1
    )
    @jakarta.persistence.Column(name = "movie_type_id")
    private int movieTypeId;

    private String movieType;

}
