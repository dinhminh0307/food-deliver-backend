package food.delivery.minh.modules.carts.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import food.delivery.minh.common.models.products.Cart;

@Repository
public interface  CartRepository extends  JpaRepository<Cart, Integer>{
    
}
