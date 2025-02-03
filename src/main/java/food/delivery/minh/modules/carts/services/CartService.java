package food.delivery.minh.modules.carts.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import food.delivery.minh.common.api.RestApiService;
import food.delivery.minh.common.auth.jwt.JwtRequestFilter;
import food.delivery.minh.common.models.accounts.User;
import food.delivery.minh.common.models.products.Cart;
import food.delivery.minh.common.models.products.Product;
import food.delivery.minh.modules.carts.repos.CartRepository;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private JwtRequestFilter authFilter;

    private static final String PRODUCT_UPDATE_API = "http://localhost:8080/product/update";

    private static final String USER_UPDATE_API = "http://localhost:8080/currentUser/update";

    @Autowired
    RestApiService restApiService;

    public Page<Cart> getAllCart(Pageable pageable) {
        return cartRepository.findAll(pageable);
    }


    public Cart addCart(Product product, User user) {
        System.out.println("Email: " + user.getEmail());
    
        // Fetch the user's cart directly instead of looping
        Optional<Cart> optionalCart = cartRepository.findByAccountId(user.getAccount_id());
    
        Cart cart;
        boolean isNewCart = false;
    
        if (optionalCart.isPresent()) {
            // If cart exists, update it
            cart = optionalCart.get();
            cart.getProducts().add(product.getProductId());
            cart.setPrice(cart.getPrice() + product.getPrice());
        } else {
            // If no cart exists, create a new one
            cart = new Cart();
            
            cart.setPrice(product.getPrice());
            cart.setProducts(new ArrayList<>(Arrays.asList(product.getProductId())));
            cart.setAccountId(user.getAccount_id()); // Ensure accountId is set
            isNewCart = true;
        }
    
        // Save the cart before updating relationships
        Cart savedCart = cartRepository.save(cart);
        System.out.println("Cart account: " + savedCart.getCartId());
        if (isNewCart) {
            // ✅ Ensure user.cartId is updated only after cart is saved
            user.setCartId(savedCart.getCartId());
            restApiService.putRequest(USER_UPDATE_API, user, User.class);
        }
    
        // Maintain bidirectional relationship for product
        product.getProductCart().add(savedCart.getCartId());
        restApiService.putRequest(PRODUCT_UPDATE_API, product, Product.class);
    
        return savedCart;
    }
    

}
