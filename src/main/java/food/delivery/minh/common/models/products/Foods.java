package food.delivery.minh.common.models.products;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
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


}
