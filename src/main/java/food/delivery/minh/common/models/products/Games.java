package food.delivery.minh.common.models.products;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import food.delivery.minh.common.models.types.GameType;
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

@Table(name="games", schema = "food-product")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Games {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @MapsId // map this pk to product PK
    @JoinColumn(name = "id") 
    private Product product;

    @OneToMany
    @JoinColumn(name = "game_id")  // Creates foreign key in FoodType table
    private List<GameType> gameTypes = new ArrayList<>();
}
