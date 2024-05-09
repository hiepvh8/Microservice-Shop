package com.microserviceshop.ProductService.controller;

import com.microserviceshop.ProductService.entity.Product;
import com.microserviceshop.ProductService.model.ProductRequest;
import com.microserviceshop.ProductService.model.ProductResponse;
import com.microserviceshop.ProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest) {
        long productId = productService.addProduct(productRequest);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Long productId) {
        ProductResponse productResponse
                = productService.getProductById(productId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PutMapping("/reduceQuantity/{id}")
    public ResponseEntity<Void> reduceQuantity(
            @PathVariable("id") Long productId,
            @RequestParam Long quantity) {
        productService.reduceQuantity(productId,quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
