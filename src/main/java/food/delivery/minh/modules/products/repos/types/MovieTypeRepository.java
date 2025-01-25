package food.delivery.minh.modules.products.repos.types;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import food.delivery.minh.common.models.types.MovieType;


@Repository
public interface  MovieTypeRepository extends JpaRepository<MovieType, Integer>{
    Optional<MovieType> findByMovieType(String movieType);
}
