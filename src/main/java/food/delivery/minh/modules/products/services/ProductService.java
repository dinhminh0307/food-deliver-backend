package food.delivery.minh.modules.products.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import food.delivery.minh.common.dto.response.ProductDTO;
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

    public Page<ProductDTO> getAllPRoduct(Pageable pageable) {
        Page<Product> listProducts = productRepository.findAll(pageable);
        return listProducts.map(prod -> new ProductDTO(
                prod.getProductId(),
                prod.getName(),
                prod.getPrice(),
                prod.getDescription(),
                prod.getImageUrl()
        ));
    }

    public Page<ProductDTO> getAllFood(Pageable pageable) {
        Page<Foods> listFood = foodRepository.findAll(pageable);
        return listFood.map(food -> new ProductDTO(
                food.getProduct().getProductId(),
                food.getProduct().getName(),
                food.getProduct().getPrice(),
                food.getProduct().getDescription(),
                food.getProduct().getImageUrl()
        ));
    }

    public Page<ProductDTO> getAllGame(Pageable pageable) {
        Page<Games> listGame = gameRepository.findAll(pageable);
        return listGame.map(game -> new ProductDTO(
                game.getProduct().getProductId(),
                game.getProduct().getName(),
                game.getProduct().getPrice(),
                game.getProduct().getDescription(),
                game.getProduct().getImageUrl()
        ));
    }

    public Page<ProductDTO> getAllMovie(Pageable pageable) {
        Page<Movies> listMovies = movieRepository.findAll(pageable);
        return listMovies.map(movie -> new ProductDTO(
                movie.getProduct().getProductId(),
                movie.getProduct().getName(),
                movie.getProduct().getPrice(),
                movie.getProduct().getDescription(),
                movie.getProduct().getImageUrl()
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
                                    createdProduct.getPrice(), createdProduct.getDescription(), createdProduct.getImageUrl());
        } else if(ProductType.GAMES.name().toLowerCase().equals(type)) {
            Games savedGame = new Games();
            List<GameType> productTypeList = new ArrayList<>();
            savedGame.setProduct(createdProduct);
            GameType savedGameType = productTypeService.addGameType(productType);
            productTypeList.add(savedGameType);
            savedGame.setGameTypes(productTypeList);

            gameRepository.save(savedGame);
            return new ProductDTO(createdProduct.getProductId(), createdProduct.getName(),
                                    createdProduct.getPrice(), createdProduct.getDescription(), createdProduct.getImageUrl());
        } else if(ProductType.MOVIES.name().toLowerCase().equals(type)) {
            Movies savedMovies = new Movies();
            List<MovieType> productTypeList = new ArrayList<>();
            savedMovies.setProduct(createdProduct);
            MovieType savedMovieType = productTypeService.addMovieType(productType);
            productTypeList.add(savedMovieType);
            savedMovies.setMovieTypes(productTypeList);


            movieRepository.save(savedMovies);
            return new ProductDTO(createdProduct.getProductId(), createdProduct.getName(),
                                    createdProduct.getPrice(), createdProduct.getDescription(), createdProduct.getImageUrl());
        }
        return null;
    }
    
    public ProductDTO updateProduct(Product product) {
        // Fetch the product from the main repository
        Product existingProduct = productRepository.findById(product.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    
        // Update basic product details
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        if(product.getImageUrl() != null) {
            existingProduct.setImageUrl(product.getImageUrl());
        }
    
        // Save updated product to the repository
        Product updatedProduct = productRepository.save(existingProduct);
    
        // Check which specific type table the product belongs to and update accordingly
        Foods food = foodRepository.findById(product.getProductId()).orElse(null);
        if (food != null) {
            // Update food-specific details if found
            food.setProduct(updatedProduct); // Update reference to updated product
            foodRepository.save(food);
        }
    
        Games game = gameRepository.findById(product.getProductId()).orElse(null);
        if (game != null) {
            // Update game-specific details if found
            game.setProduct(updatedProduct); // Update reference to updated product
            gameRepository.save(game);
        }
    
        Movies movie = movieRepository.findById(product.getProductId()).orElse(null);
        if (movie != null) {
            // Update movie-specific details if found
            movie.setProduct(updatedProduct); // Update reference to updated product
            movieRepository.save(movie);
        }
    
        // Return updated product as DTO
        return new ProductDTO(
                updatedProduct.getProductId(),
                updatedProduct.getName(),
                updatedProduct.getPrice(),
                updatedProduct.getDescription(),
                updatedProduct.getImageUrl()
        );
    }
    
    public Product getProductById(UUID id) throws NoResourceFoundException {
       Optional<Product> product = productRepository.findById(id);
       if(!product.isPresent()) {
            throw new NoResourceFoundException(null, "Cant not find product by Id");
        }
        return product.get();
    }
}
         