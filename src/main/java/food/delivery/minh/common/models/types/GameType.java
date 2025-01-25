package food.delivery.minh.common.models.types;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Table(name="game_types", schema = "food-product")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @jakarta.persistence.Column(name = "game_type_id")
    private int gameTypeId;

    private String gameType;

}
