package food.delivery.minh.modules.products.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import food.delivery.minh.common.models.types.FoodType;
import food.delivery.minh.common.models.types.GameType;
import food.delivery.minh.common.models.types.MovieType;
import food.delivery.minh.modules.products.repos.types.FoodTypeRepository;
import food.delivery.minh.modules.products.repos.types.GameTypeRepository;
import food.delivery.minh.modules.products.repos.types.MovieTypeRepository;

@Service
public class ProductTypeService {
    @Autowired
    FoodTypeRepository foodTypeRepository;

    @Autowired 
    GameTypeRepository gameTypeRepository;

    @Autowired
    MovieTypeRepository movieTypeRepository;
    
    public FoodType addFoodType(String foodType) {
        FoodType savedFoodType = foodTypeRepository.findByFoodType(foodType)
                        .orElse(null);
        if(savedFoodType == null) {
            FoodType newFoodType = new FoodType();
            newFoodType.setFoodType(foodType);
            return foodTypeRepository.save(newFoodType);
        }
        return savedFoodType;      
    }

    public GameType addGameType(String gameType) {
        GameType savedGameType = gameTypeRepository.findByGameType(gameType)
                        .orElse(null);
        if(savedGameType == null) {
            GameType newGameType = new GameType();
            newGameType.setGameType(gameType);
            return gameTypeRepository.save(newGameType);
        }
        return savedGameType;   
    }

    public MovieType addMovieType(String movieType) {
        MovieType savedMovieType = movieTypeRepository.findByMovieType(movieType)
                        .orElse(null);
        if(savedMovieType == null) {
            MovieType newMovieType = new MovieType();
            newMovieType.setMovieType(movieType);
            return movieTypeRepository.save(newMovieType);
        }   
        return savedMovieType;   
    }
}
