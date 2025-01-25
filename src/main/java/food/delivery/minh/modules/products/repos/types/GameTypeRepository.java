package food.delivery.minh.modules.products.repos.types;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import food.delivery.minh.common.models.types.GameType;


@Repository
public interface  GameTypeRepository extends JpaRepository<GameType, Integer>{
    Optional<GameType> findByGameType(String gameType);
}
