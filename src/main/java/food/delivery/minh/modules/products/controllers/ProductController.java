package food.delivery.minh.modules.products.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import food.delivery.minh.common.dto.response.ProductDTO;
import food.delivery.minh.common.enums.TypeEnum;
import food.delivery.minh.common.models.products.Product;
import food.delivery.minh.modules.products.services.ProductService;

@RestController
@RequestMapping("/")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("product/add")
    public ResponseEntity<?> createProduct(@RequestBody Product product, @RequestParam String type, @RequestParam String productType) {
        ProductDTO returnProduct = productService.addProduct(product, type, productType);
        if(returnProduct != null) {
            return ResponseEntity.ok(returnProduct);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fail to create product");
    }
    
    @GetMapping("product/get")
    public ResponseEntity<?> getProductsByType(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam String type) {
        Pageable pageable = PageRequest.of(page, size);
        if(TypeEnum.ProductType.FOODS.name().toLowerCase().equals(type)) {
            return ResponseEntity.ok().body(productService.getAllFood(pageable));
        } else if(TypeEnum.ProductType.GAMES.name().toLowerCase().equals(type)) {
            return ResponseEntity.ok().body(productService.getAllGame(pageable));
        } else if(TypeEnum.ProductType.MOVIES.name().toLowerCase().equals(type)) {
            return ResponseEntity.ok().body(productService.getAllMovie(pageable));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No resource found");
    }
    
    @GetMapping("product/all")
    public ResponseEntity<?> getAllProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(productService.getAllPRoduct(pageable));
    }

    @PutMapping("product/update")
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(product));
    }

    @GetMapping("product/get/uuid")
    public ResponseEntity<?> getProductById(@RequestParam UUID id) {
        try {
            return ResponseEntity.ok(productService.getProductById(id));
        } catch (NoResourceFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
