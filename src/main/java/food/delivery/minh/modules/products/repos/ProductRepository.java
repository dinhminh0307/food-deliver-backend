package food.delivery.minh.modules.products.repos;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import food.delivery.minh.common.models.products.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>{
    
}
