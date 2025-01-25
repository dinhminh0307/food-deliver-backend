package food.delivery.minh.common.models.products;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import food.delivery.minh.common.models.types.FoodType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "foods", schema = "food-product")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Foods {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;

    private String ingredients;

    @OneToOne
    @MapsId // map this pk to product PK
    @JoinColumn(name = "id") 
    private Product product;

    @OneToMany
    @JoinColumn(name = "food_id")  // Creates foreign key in FoodType table
    private List<FoodType> foodTypes = new ArrayList<>();
}
