package food.delivery.minh.modules.products.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import food.delivery.minh.common.dto.ProductDTO;
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
}
