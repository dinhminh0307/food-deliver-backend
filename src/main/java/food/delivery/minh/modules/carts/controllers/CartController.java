package food.delivery.minh.modules.carts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import food.delivery.minh.common.api.RestApiService;
import food.delivery.minh.common.auth.jwt.JwtRequestFilter;
import food.delivery.minh.common.dto.CartDTO;
import food.delivery.minh.common.models.accounts.User;
import food.delivery.minh.common.models.products.Product;
import food.delivery.minh.modules.carts.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/")
public class CartController {
    @Autowired
    CartService cartService;

    @Autowired
    RestTemplate restTemplate;

     @Autowired
    private JwtRequestFilter authFilter;

    @Autowired
    RestApiService restApiService;

    private String GET_USER_URL = "http://localhost:8080/currentUser";
    
    @PostMapping("/cart/add")
    @Operation(
        summary = "Add a product to the cart",
        description = "This API allows adding a product to the shopping cart",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully added",
                content = @Content(schema = @Schema(implementation = CartDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
        }
    )

    public ResponseEntity<?> addToCart(@RequestBody Product product) {
        // Extract token using JwtRequestFilter
        ResponseEntity<User> response = restApiService.getRequest(GET_USER_URL, User.class);
        System.out.println("Response Body: " + response.getBody());
        // Get the user object from the response
        User user = response.getBody();
        System.out.println("Response User: " + user.getEmail());
        // Process and return the response
        return ResponseEntity.ok(cartService.addCart(product, user));
    }

    @GetMapping("cart/all")
    public ResponseEntity<?> getAllCart() {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(cartService.getAllCart(pageable));
    }
}
