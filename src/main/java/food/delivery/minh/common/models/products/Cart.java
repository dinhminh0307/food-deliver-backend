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
import jakarta.persistence.SequenceGenerator;
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
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "cart_seq_gen")
    @SequenceGenerator(
        name = "cart_seq_gen", 
        sequenceName = "food-product.cart_seq", 
        allocationSize = 1
    )
    private Integer cartId;

    private double price;
    
    @ElementCollection
    @CollectionTable(
        name = "product_cart",
        schema = "food-product",
        joinColumns = @JoinColumn(name = "cart_id") // FK reference
    )
    @Column(name = "product_id")
    private List<UUID> products = new ArrayList<>();

    @Column(name = "account_id")
    private Integer accountId; // Just store the foreign key reference
}
