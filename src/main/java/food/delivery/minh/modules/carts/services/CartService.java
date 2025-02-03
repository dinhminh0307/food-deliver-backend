package food.delivery.minh.modules.carts.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import food.delivery.minh.common.auth.jwt.JwtRequestFilter;
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

    public Page<Cart> getAllCart(Pageable pageable) {
        return cartRepository.findAll(pageable);
    }

    public void updateProductInRestApi(Product product) {
        try {
            String jwtToken = authFilter.getBrowserToken();
            // Create headers and add the JWT token in the Cookie
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cookie", "Authorization=" + jwtToken);

            // Create HttpEntity with headers and the product as the body
            HttpEntity<Product> requestEntity = new HttpEntity<>(product, headers);

            // Send the PUT request to the Product API
            ProductDTO saved = restTemplate.exchange(PRODUCT_UPDATE_API, HttpMethod.PUT, requestEntity, ProductDTO.class).getBody();
            System.out.println("Saved product: " + saved.getName());

        } catch (Exception ex) {
            System.err.println("Error occurred while updating product via REST API: " + ex.getMessage());
            // Handle exception appropriately (e.g., log it, rethrow it, or handle fallback)
        }
    }

    public void updateUserInRestApi(User user) {
        try {
            String jwtToken = authFilter.getBrowserToken();
            // Create headers and add the JWT token in the Cookie
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cookie", "Authorization=" + jwtToken);

            // Create HttpEntity with headers and the product as the body
            HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);

            // Send the PUT request to the Product API
            restTemplate.exchange(USER_UPDATE_API, HttpMethod.PUT, requestEntity, Void.class);

        } catch (Exception ex) {
            System.err.println("Error occurred while updating product via REST API: " + ex.getMessage());
            // Handle exception appropriately (e.g., log it, rethrow it, or handle fallback)
        }
    }

    // public CartDTO addCart(Product product, User user) {
    //     System.out.println("Email: " + user.getEmail());
    //     Cart carts = cartRepository.findByUserEmail(user.getEmail());

    //     if (carts == null) {
    //         System.out.println("Null Cart");
    //         Cart newCart = new Cart();
    //         newCart.setPrice(product.getPrice());
    //         newCart.setProducts(Arrays.asList(product));
    //         newCart.setUser(user);

    //         // Maintain bidirectional relationship
    //         product.getProductCart().add(newCart); // Add the cart to the product's productCart list
    //         user.setCart(newCart); // Set the cart in the user object

    //         // Update product and user in REST API
    //         updateProductInRestApi(product);
    //         updateUserInRestApi(user);

    //         Cart savedCart = cartRepository.save(newCart);

    //         // Convert Product list to ProductDTO list
    //         List<ProductDTO> productDTOs = savedCart.getProducts().stream()
    //             .map(prod -> new ProductDTO(
    //                     prod.getProductId(),
    //                     prod.getName(),
    //                     prod.getPrice(),
    //                     prod.getDescription()
    //             ))
    //             .collect(Collectors.toList());

    //         return new CartDTO(
    //             savedCart.getCartId(),
    //             savedCart.getPrice(),
    //             productDTOs
    //         );
    //     }

    //     // if not empty, update the cart
    //     Cart foundCart = carts;
    //     List<Product> updatedProducts = new ArrayList<>(foundCart.getProducts());
    //     updatedProducts.add(product);
    //     foundCart.setProducts(updatedProducts);
    //     foundCart.setPrice(foundCart.getPrice() + product.getPrice());

        

    //     Cart savedCart = cartRepository.save(foundCart);
    //     // Maintain bidirectional relationship
    //     product.getProductCart().add(savedCart);

    //     // Update product in REST API
    //     updateProductInRestApi(product);

    //     // Convert Product list to ProductDTO list
    //     List<ProductDTO> productDTOs = savedCart.getProducts().stream()
    //             .map(prod -> new ProductDTO(
    //                     prod.getProductId(),
    //                     prod.getName(),
    //                     prod.getPrice(),
    //                     prod.getDescription()
    //             ))
    //             .collect(Collectors.toList());

    //     return new CartDTO(
    //         savedCart.getCartId(),
    //         savedCart.getPrice(),
    //         productDTOs
    //     );
    // }

}
