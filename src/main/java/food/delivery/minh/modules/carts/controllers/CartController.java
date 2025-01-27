package food.delivery.minh.modules.carts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import food.delivery.minh.common.dto.CartDTO;
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
        return ResponseEntity.ok(cartService.addCart(product));
    }
}
