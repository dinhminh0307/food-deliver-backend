package food.delivery.minh.modules.products.repos.types;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import food.delivery.minh.common.models.types.FoodType;


@Repository
public interface FoodTypeRepository extends JpaRepository<FoodType, Integer> {
    Optional<FoodType> findByFoodType(String foodType);
}
