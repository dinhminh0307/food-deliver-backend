package food.delivery.minh.common.models.types;

import food.delivery.minh.common.models.products.Movies;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @jakarta.persistence.Column(name = "movie_type_id")
    private int movieTypeId;

    private String movieType;

    @ManyToOne
    @JoinColumn(name = "movie_id", referencedColumnName = "id")  // Correct foreign key reference
    private Movies movie;

}
