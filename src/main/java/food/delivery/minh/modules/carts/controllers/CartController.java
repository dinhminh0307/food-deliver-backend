package food.delivery.minh.modules.carts.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import food.delivery.minh.common.api.RestApiService;
import food.delivery.minh.common.auth.jwt.JwtRequestFilter;
import food.delivery.minh.common.dto.request.DeleteCartRequest;
import food.delivery.minh.common.dto.response.CartDTO;
import food.delivery.minh.common.dto.response.ProductDTO;
import food.delivery.minh.common.models.accounts.User;
import food.delivery.minh.common.models.products.Cart;
import food.delivery.minh.common.models.products.Product;
import food.delivery.minh.exceptions.DuplicateResourceException;
import food.delivery.minh.exceptions.PassedException;
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

    private static final  String PRODUCT_FIND_ID_API = "http://localhost:8080/product/get/uuid?id=";
    
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

    @GetMapping("cart/currentUser")
    public ResponseEntity<?> getCartFromUser() {
        try {
            return ResponseEntity.ok(cartService.getCartFromUser());
        } catch (NoResourceFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); 
        }
    }

    @DeleteMapping("/cart/delete/item")
    public ResponseEntity<?> removeCartItem(@RequestParam UUID productId, @RequestParam int cartId) {
        try {
            Cart cart = cartService.removeProductItem(productId, cartId);
            List<ProductDTO> products = new ArrayList<>();
            ResponseEntity<Product> response;

            for(UUID p : cart.getProducts()) {
                String newEndpoint = PRODUCT_FIND_ID_API + p.toString();
                response = restApiService.getRequest(newEndpoint, Product.class);
                if(response.getStatusCode().is2xxSuccessful()) {
                    products.add(new ProductDTO(response.getBody().getProductId(), response.getBody().getName(), response.getBody().getPrice(), response.getBody().getDescription()));
                } else {
                    throw new NoResourceFoundException(null, "ERror fetch cart");
                }
            }

            return ResponseEntity.ok(new CartDTO(
                cart.getCartId(),
                cart.getPrice(),
                products
            ));
        } catch (NoResourceFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); 
        }
    }

    @GetMapping("cart/item/check")
    public ResponseEntity<?> validateItemCart(@RequestParam UUID productId) {
        try {
            cartService.checkDuplicateItem(productId);
            return ResponseEntity.ok().build();
        } catch (NoResourceFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()); 
        } catch (DuplicateResourceException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage()); 
        } catch (PassedException e) {
            return ResponseEntity.ok().build(); 
        }
    }

    @PutMapping("cart/remove")
    public ResponseEntity<?> deleteCart(@RequestBody DeleteCartRequest request) {
        try {
            cartService.deleteCart(request.getCart(), request.getUser());
            return ResponseEntity.ok().build();
        } catch (NoResourceFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
