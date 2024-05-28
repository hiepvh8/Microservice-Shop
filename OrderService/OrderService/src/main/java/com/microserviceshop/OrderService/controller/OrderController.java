package com.microserviceshop.OrderService.controller;

import com.microserviceshop.OrderService.model.OrderRequest;
import com.microserviceshop.OrderService.model.OrderResponse;
import com.microserviceshop.OrderService.security.JwtUtil;
import com.microserviceshop.OrderService.service.OrderService;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static javax.crypto.Cipher.SECRET_KEY;

@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest) {

        Long orderId = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }
//    @PostMapping("/placeOrder")
//    //@PreAuthorize("hasPermission('CREATE_ORDER')")
//    public ResponseEntity<Long> placeOrder(@RequestHeader("Authorization") String token,@RequestBody OrderRequest orderRequest) {
//        Claims claims = jwtUtil.extractClaims(token, String.valueOf(SECRET_KEY));
//        List<String> roles = jwtUtil.extractRoles(claims);
//        List<String> permissions = jwtUtil.extractPermissions(claims);
//
//        Long orderId = orderService.placeOrder(orderRequest);
//        return new ResponseEntity<>(orderId, HttpStatus.OK);
//    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable Long orderId) {
        OrderResponse orderResponse
                = orderService.getOrderDetails(orderId);

        return new ResponseEntity<>(orderResponse,
                HttpStatus.OK);
    }
}
