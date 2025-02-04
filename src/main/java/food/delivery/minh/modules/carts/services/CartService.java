package food.delivery.minh.modules.carts.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import food.delivery.minh.common.api.RestApiService;
import food.delivery.minh.common.auth.jwt.JwtRequestFilter;
import food.delivery.minh.common.dto.CartDTO;
import food.delivery.minh.common.dto.ProductDTO;
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

    private String GET_USER_URL = "http://localhost:8080/currentUser";

    private static final  String PRODUCT_FIND_ID_API = "http://localhost:8080/product/get/uuid?id=";

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
    
    public CartDTO getCartFromUser() throws NoResourceFoundException {
        // get the current user rest api
        User user = restApiService.getRequest(GET_USER_URL, User.class).getBody();
        
        // get the cart base on account id
         // Fetch the user's cart directly instead of looping
         Optional<Cart> optionalCart = cartRepository.findByAccountId(user.getAccount_id());
         if(optionalCart.isPresent()) {
            Cart foundCart = optionalCart.get();
            
            // get the product list rest api
            List<ProductDTO> products = new ArrayList<>();
            ResponseEntity<Product> response;
            for(UUID p : foundCart.getProducts()) {
                String newEndpoint = PRODUCT_FIND_ID_API + p.toString();
                response = restApiService.getRequest(newEndpoint, Product.class);
                if(response.getStatusCode().is2xxSuccessful()) {
                    products.add(new ProductDTO(response.getBody().getProductId(), response.getBody().getName(), response.getBody().getPrice(), response.getBody().getDescription()));
                } else {
                    throw new NoResourceFoundException(null, "ERror fetch cart");
                }
            }
            return new CartDTO(foundCart.getCartId(), 
                    foundCart.getPrice(), 
                    products);
         }
         throw new NoResourceFoundException(null, "User have no cart");
    }

    public Cart removeProductItem(UUID productId, int cartId) throws NoResourceFoundException {
        // fetch product and cart
        Cart cart = findCartById(cartId);
        Product product = restApiService.getRequest(PRODUCT_FIND_ID_API + productId, Product.class).getBody();

        List<UUID> products = cart.getProducts();
        List<Integer> carts = product.getProductCart();
        
        if(products.contains(productId)) {
            products.remove(productId);
        }
        cart.setProducts(products);
        cart.setPrice(cart.getPrice() - product.getPrice());
        Cart newCart = cartRepository.save(cart);

        // Maintain bidirectional relationship for product
        if(carts.contains(cartId)) {
            carts.remove(cartId);
        }
        product.setProductCart(carts);

        restApiService.putRequest(PRODUCT_UPDATE_API, product, Product.class);
        return newCart;
    }

    public Cart findCartById(int id) throws NoResourceFoundException {
        Optional<Cart> cart = cartRepository.findById(id);
        if(cart.isPresent()) {
            return cart.get();
        }
        throw new NoResourceFoundException(null, "Not matching Cart Id");
    }
}
