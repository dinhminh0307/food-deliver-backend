package food.delivery.minh.common.models.products;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name= "product_cart",
        schema = "food-product",
        joinColumns = @JoinColumn(name= "product_id", referencedColumnName = "productId"),
        inverseJoinColumns = @JoinColumn(name="cart_id", referencedColumnName="cartId")

    )
    private List<Cart> productCart = new ArrayList<>();

    

}
