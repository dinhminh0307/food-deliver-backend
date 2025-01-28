package food.delivery.minh.modules.carts.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import food.delivery.minh.common.models.products.Cart;

@Repository
public interface  CartRepository extends  JpaRepository<Cart, Integer>{
    @Query("SELECT c FROM Cart c WHERE c.user.account_id = (SELECT u.account_id FROM User u WHERE u.email = :email)")
    Cart findByUserEmail(@Param("email") String email);

}
