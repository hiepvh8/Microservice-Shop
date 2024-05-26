package com.microserviceshop.UserService.controller;

import com.microserviceshop.UserService.model.ApiResponse;
import com.microserviceshop.UserService.model.JwtAuthenticationResponse;
import com.microserviceshop.UserService.model.LoginRequest;
import com.microserviceshop.UserService.model.SignUpRequest;
import com.microserviceshop.UserService.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//package com.microserviceshop.UserService.controller;
//
//import com.microserviceshop.UserService.entity.UserPrincipal;
//import com.microserviceshop.UserService.model.ApiResponse;
//import com.microserviceshop.UserService.model.JwtAuthenticationResponse;
//import com.microserviceshop.UserService.model.LoginRequest;
//import com.microserviceshop.UserService.model.SignUpRequest;
//import com.microserviceshop.UserService.security.JwtTokenProvider;
//import com.microserviceshop.UserService.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UserService userService;
////
////    @Autowired
////    private AuthenticationManager authenticationManager;
////
////    @Autowired
////    private JwtTokenProvider tokenProvider;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        if(userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Email is already taken!"));
        }
        // Create user account
        userService.createUser(signUpRequest);

        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully"));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }
}
