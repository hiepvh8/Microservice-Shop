package com.microserviceshop.UserService.service;

import com.microserviceshop.UserService.entity.User;
import com.microserviceshop.UserService.exception.NotFoundException;
import com.microserviceshop.UserService.model.JwtAuthenticationResponse;
import com.microserviceshop.UserService.model.LoginRequest;
import com.microserviceshop.UserService.model.SignUpRequest;
import com.microserviceshop.UserService.repository.UserRepository;
import com.microserviceshop.UserService.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {


    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void createUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        // Set default role or permissions if needed
        // user.setRoles(...);
        userRepository.save(user);
    }

    public JwtAuthenticationResponse login(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        if (!user.isPresent()) {
            throw new NotFoundException("Người dùng không tồn tại!");
        }else {
            // Kiểm tra tính đúng đắn của mật khẩu
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
                throw new NotFoundException("Sai tên đăng nhập hoặc mật khẩu.");
            }else {
                // Tạo mã token
                String jwt = jwtService.generateToken((UserDetails) user.get());

                JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
                jwtAuthenticationResponse.setAccessToken(jwt);
                // Có thể thêm thông tin người dùng khác vào đây nếu cần
                return jwtAuthenticationResponse;
            }
        }
    }


    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}

