package food.delivery.minh.common.models.types;

import food.delivery.minh.common.models.products.Foods;
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

@Table(name="food_types", schema = "food-product")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @jakarta.persistence.Column(name = "food_type_id")
    private int foodTypeId;
    private String foodType; // then convert to enum

    @ManyToOne
    @JoinColumn(name = "food_id", referencedColumnName = "id")  // Correct foreign key reference
    private Foods foods;
}
