package com.microserviceshop.ProductService.service;

import com.microserviceshop.ProductService.model.ProductRequest;
import com.microserviceshop.ProductService.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(Long productId);

    void reduceQuantity(Long productId, Long quantity);
}
