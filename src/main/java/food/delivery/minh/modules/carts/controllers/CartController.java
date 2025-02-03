package food.delivery.minh.modules.carts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import food.delivery.minh.common.auth.jwt.JwtRequestFilter;
import food.delivery.minh.common.dto.CartDTO;
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
    // public ResponseEntity<?> addToCart(@RequestBody Product product) {
    //     // Extract token using JwtRequestFilter
    //     String token = authFilter.getBrowserToken();
    //     System.out.println("Token: " + token);

    //     // Set the token in the Cookie header
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.add("Cookie", "Authorization=" + token);

    //     // Create HttpEntity with headers
    //     HttpEntity<String> entity = new HttpEntity<>(headers);

    //     // Make the API call to fetch the current user
    //     ResponseEntity<User> response = restTemplate.exchange(
    //         "http://localhost:8080/currentUser",
    //         HttpMethod.GET,
    //         entity,
    //         User.class
    //     );
    //     System.out.println("Response Body: " + response.getBody());
    //     // Get the user object from the response
    //     User user = response.getBody();
    //     System.out.println("Response User: " + user.getEmail());
    //     // Process and return the response
    //     return ResponseEntity.ok(cartService.addCart(product, user));
    // }

    @GetMapping("cart/all")
    public ResponseEntity<?> getAllCart() {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(cartService.getAllCart(pageable));
    }
}
