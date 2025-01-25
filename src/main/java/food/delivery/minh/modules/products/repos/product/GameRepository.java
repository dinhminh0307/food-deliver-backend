package food.delivery.minh.modules.products.repos.product;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import food.delivery.minh.common.models.products.Games;

@Repository
public interface GameRepository extends JpaRepository<Games, UUID>{
    @Override
    Page<Games> findAll(Pageable pageable);
}
