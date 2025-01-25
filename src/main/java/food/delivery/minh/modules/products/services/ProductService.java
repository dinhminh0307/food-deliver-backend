package food.delivery.minh.modules.products.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import food.delivery.minh.common.dto.ProductDTO;
import food.delivery.minh.common.enums.TypeEnum.ProductType;
import food.delivery.minh.common.models.products.Foods;
import food.delivery.minh.common.models.products.Games;
import food.delivery.minh.common.models.products.Movies;
import food.delivery.minh.common.models.products.Product;
import food.delivery.minh.common.models.types.FoodType;
import food.delivery.minh.common.models.types.GameType;
import food.delivery.minh.common.models.types.MovieType;
import food.delivery.minh.modules.products.repos.ProductRepository;
import food.delivery.minh.modules.products.repos.product.FoodRepository;
import food.delivery.minh.modules.products.repos.product.GameRepository;
import food.delivery.minh.modules.products.repos.product.MovieRepository;

@Service
public class ProductService {
    @Autowired
    FoodRepository foodRepository;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductTypeService productTypeService;

    public Page<ProductDTO> getAllFood(Pageable pageable) {
        Page<Foods> listFood = foodRepository.findAll(pageable);
        return listFood.map(food -> new ProductDTO(
                food.getProduct().getProductId(),
                food.getProduct().getName(),
                food.getProduct().getPrice(),
                food.getProduct().getDescription()
        ));
    }

    public Page<ProductDTO> getAllGame(Pageable pageable) {
        Page<Games> listGame = gameRepository.findAll(pageable);
        return listGame.map(game -> new ProductDTO(
                game.getProduct().getProductId(),
                game.getProduct().getName(),
                game.getProduct().getPrice(),
                game.getProduct().getDescription()
        ));
    }

    public Page<ProductDTO> getAllMovie(Pageable pageable) {
        Page<Movies> listMovies = movieRepository.findAll(pageable);
        return listMovies.map(movie -> new ProductDTO(
                movie.getProduct().getProductId(),
                movie.getProduct().getName(),
                movie.getProduct().getPrice(),
                movie.getProduct().getDescription()
        ));
    }

    public ProductDTO addProduct(Product product, String type, String productType) {
        Product createdProduct = productRepository.save(product);
        
        if(ProductType.FOODS.name().toLowerCase().equals(type)) {
            Foods savedFood = new Foods();
            List<FoodType> productTypeList = new ArrayList<>();
            savedFood.setProduct(createdProduct);

            // add the product type id
            FoodType savedFoodType = productTypeService.addFoodType(productType);
            // add the product type list to the food
            productTypeList.add(savedFoodType);
            savedFood.setFoodTypes(productTypeList);

            foodRepository.save(savedFood);
            return new ProductDTO(createdProduct.getProductId(), createdProduct.getName(),
                                    createdProduct.getPrice(), createdProduct.getDescription());
        } else if(ProductType.GAMES.name().toLowerCase().equals(type)) {
            Games savedGame = new Games();
            List<GameType> productTypeList = new ArrayList<>();
            savedGame.setProduct(createdProduct);
            GameType savedGameType = productTypeService.addGameType(productType);
            productTypeList.add(savedGameType);
            savedGame.setGameTypes(productTypeList);

            gameRepository.save(savedGame);
            return new ProductDTO(createdProduct.getProductId(), createdProduct.getName(),
                                    createdProduct.getPrice(), createdProduct.getDescription());
        } else if(ProductType.MOVIES.name().toLowerCase().equals(type)) {
            Movies savedMovies = new Movies();
            List<MovieType> productTypeList = new ArrayList<>();
            savedMovies.setProduct(createdProduct);
            MovieType savedMovieType = productTypeService.addMovieType(productType);
            productTypeList.add(savedMovieType);
            savedMovies.setMovieTypes(productTypeList);


            movieRepository.save(savedMovies);
            return new ProductDTO(createdProduct.getProductId(), createdProduct.getName(),
                                    createdProduct.getPrice(), createdProduct.getDescription());
        }
        return null;
    }                                        
}
         