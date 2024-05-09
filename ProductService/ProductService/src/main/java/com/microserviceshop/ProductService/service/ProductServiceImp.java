package com.microserviceshop.ProductService.service;

import com.microserviceshop.ProductService.entity.Product;
import com.microserviceshop.ProductService.exception.ProductServiceCustomException;
import com.microserviceshop.ProductService.model.ProductRequest;
import com.microserviceshop.ProductService.model.ProductResponse;
import com.microserviceshop.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@Log4j2
public class ProductServiceImp implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding Product");

        Product product
                = Product.builder()
                .productName(productRequest.getName())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);

        log.info("Product Create");

        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        log.info("Getting Product by ID: {}", productId);

        Product product
                = productRepository.findById(productId)
                .orElseThrow(() -> new ProductServiceCustomException("Product not found","PRODUCT_NOT_FOUND"));

        ProductResponse productResponse
                = new ProductResponse();
        copyProperties(product, productResponse);

        return productResponse;
    }

    @Override
    public void reduceQuantity(Long productId, Long quantity) {
        log.info("Reduce Quantity {} for productId: {}", quantity,productId);

        Product product
                = productRepository.findById(productId)
                .orElseThrow(() -> new ProductServiceCustomException(
                        "Product not found",
                        "PRODUCT_NOT_FOUND"));

        if(product.getQuantity() < quantity){
            throw new ProductServiceCustomException(
                    "product does not have sufficient Quantity",
                    "INSUFFICIENT_QUANTITY"
            );
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        log.info("Product Reduce Quantity Successfully");
    }
}
