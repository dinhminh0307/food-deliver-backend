package food.delivery.minh.modules.carts.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import food.delivery.minh.common.auth.jwt.JwtRequestFilter;
import food.delivery.minh.common.dto.CartDTO;
import food.delivery.minh.common.dto.ProductDTO;
import food.delivery.minh.common.models.products.Cart;
import food.delivery.minh.common.models.products.Product;
import food.delivery.minh.modules.carts.repos.CartRepository;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    private JwtRequestFilter authFilter;

    public CartDTO addCart(Product product) {
        
        Cart carts = cartRepository.findByUserEmail(authFilter.getUserEmail());
        if(carts == null) {
            System.out.println("Null Cart");
            Cart newCart = new Cart();
            newCart.setPrice(product.getPrice());
            newCart.setProducts(Arrays.asList(product));
            Cart savedCart = cartRepository.save(newCart);

            // Convert Product list to ProductDTO list
            List<ProductDTO> productDTOs = savedCart.getProducts().stream()
                .map(prod -> new ProductDTO(
                        prod.getProductId(),
                        prod.getName(),
                        prod.getPrice(),
                        prod.getDescription()
                ))
                .collect(Collectors.toList());

            return new CartDTO(
                savedCart.getCartId(),
                savedCart.getPrice(),
                productDTOs
            );
        }
        
        // if not empty, update the cart
        Cart foundCart = carts;
        List<Product> updatedProducts = new ArrayList<>(foundCart.getProducts());
        updatedProducts.add(product);
        foundCart.setProducts(updatedProducts);
        foundCart.setPrice(foundCart.getPrice() + product.getPrice());

        Cart savedCart = cartRepository.save(foundCart);
        List<ProductDTO> productDTOs = savedCart.getProducts().stream()
                                        .map(prod -> new ProductDTO(
                                            prod.getProductId(),
                                            prod.getName(),
                                            prod.getPrice(),
                                            prod.getDescription()
                                        ))
                                        .collect(Collectors.toList());
        return new CartDTO(
            savedCart.getCartId(),
            savedCart.getPrice(),
            productDTOs
        );
    }

    
}
