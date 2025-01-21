package food.delivery.minh.common.models.products;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * In spring, should create table with JPA
 * 
 * When Should You Create the Table Manually in PostgreSQL?
You might prefer manual table creation in PostgreSQL if:

Your organization has strict database management policies.
The database is shared across multiple applications.
You need fine-grained control over the schema, indexes, and constraints.
You are migrating from an existing database with pre-existing data.
 */

@Table(name="carts", schema = "food-product")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private int cartId;

    private double price;
    
    @ManyToMany(mappedBy = "productCart")
    private List<Product> products = new ArrayList<>();
}
