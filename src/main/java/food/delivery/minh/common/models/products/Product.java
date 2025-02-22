package food.delivery.minh.common.models.products;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "products", schema = "food-product")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID productId;

    private String name;
    private double price;

    private String description;

    private String imageUrl;

    @ElementCollection
    @CollectionTable(
        name = "product_cart",
        schema = "food-product",
        joinColumns = @JoinColumn(name = "product_id") // FK reference
    )
    @Column(name = "cart_id")
    private List<Integer> productCart = new ArrayList<>();

    

}
