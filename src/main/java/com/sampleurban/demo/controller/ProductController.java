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
                product.getname(), // Removed productId since it will be auto-generated
                product.getPrice(),
                product.getQuantity(),
                product.getCategory(),
                product.getDescription(),
                product.getImageUrl(),
                product.getSupplierId()
        );
        return "Product added successfully";
    }


    @PutMapping("/update")
    public String updateProduct(@RequestBody ProductRequest product) {
        productService.updateProduct(
                product.getProductId(),
                product.getname(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory(),
                product.getDescription(),
                product.getImageUrl(),
                product.getSupplierId()
        );
        return "Product updated successfully";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return "Product deleted successfully";
    }

    @GetMapping("/by-supplier/{supplierId}")
    public List<ProductRequest> getProductsBySupplier(@PathVariable String supplierId) {
        return productService.getProductsBySupplier(supplierId);
    }

}
