package com.sampleurban.demo.controller;

import com.sampleurban.demo.DTO.ProductRequest;
import com.sampleurban.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*") // Enable this if you're calling from React or other frontend
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public String addProduct(@RequestBody ProductRequest product) {
        productService.addProduct(
                product.getProductId(),
                product.getProductName(),
                product.getCategory(),
                product.getType(),
                product.getDescription(),
                product.getImageUrl()
        );
        return "Product added successfully";
    }

    @PutMapping("/update")
    public String updateProduct(@RequestBody ProductRequest product) {
        productService.updateProduct(
                product.getProductId(),
                product.getProductName(),
                product.getCategory(),
                product.getType(),
                product.getDescription(),
                product.getImageUrl()
        );
        return "Product updated successfully";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return "Product deleted successfully";
    }

    @GetMapping("/all")
    public List<ProductRequest> getAllProducts() {
        return productService.getAllProducts();
    }
}
