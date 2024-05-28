package com.microserviceshop.UserService.service;

import com.microserviceshop.UserService.entity.Permission;
import com.microserviceshop.UserService.entity.Role;
import com.microserviceshop.UserService.entity.User;
import com.microserviceshop.UserService.exception.NotFoundException;
import com.microserviceshop.UserService.model.JwtAuthenticationResponse;
import com.microserviceshop.UserService.model.LoginRequest;
import com.microserviceshop.UserService.model.SignUpRequest;
import com.microserviceshop.UserService.repository.PermissionRepository;
import com.microserviceshop.UserService.repository.RoleRepository;
import com.microserviceshop.UserService.repository.UserRepository;
import com.microserviceshop.UserService.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {


    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public void createUser(SignUpRequest signUpRequest) {

        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        // Add permission to admin role
        Permission createOrderPermission = permissionRepository.findByName("CREATE_ORDER");
        if (createOrderPermission == null) {
            createOrderPermission = Permission.builder().name("CREATE_ORDER").build();
            permissionRepository.save(createOrderPermission);
        }
        Permission get_orderPermission = permissionRepository.findByName("GET_ORDER");
        if (get_orderPermission == null) {
            get_orderPermission = Permission.builder().name("GET_ORDER").build();
            permissionRepository.save(get_orderPermission);
        }
        Permission do_paymentPermission = permissionRepository.findByName("DO_PAYMENT");
        if (do_paymentPermission == null) {
            do_paymentPermission = Permission.builder().name("DO_PAYMENT").build();
            permissionRepository.save(do_paymentPermission);
        }
        Permission get_paymentPermission = permissionRepository.findByName("GET_PAYMENT");
        if (get_paymentPermission == null) {
            get_paymentPermission = Permission.builder().name("GET_PAYMENT").build();
            permissionRepository.save(get_paymentPermission);
        }
        Permission add_productPermission = permissionRepository.findByName("ADD_PRODUCT");
        if (add_productPermission == null) {
            add_productPermission = Permission.builder().name("ADD_PRODUCT").build();
            permissionRepository.save(add_productPermission);
        }Permission get_productPermission = permissionRepository.findByName("GET_PRODUCT");
        if (get_productPermission == null) {
            get_productPermission = Permission.builder().name("GET_PRODUCT").build();
            permissionRepository.save(get_productPermission);
        }Permission reduce_quantityPermission = permissionRepository.findByName("REDUCE_PRODUCT");
        if (reduce_quantityPermission == null) {
            reduce_quantityPermission = Permission.builder().name("REDUCE_PRODUCT").build();
            permissionRepository.save(reduce_quantityPermission);
        }
        if(signUpRequest.getRole().equals("ADMIN") && signUpRequest.getAuthAdmin().equals("admin123")){

            Role adminRole = roleRepository.findByName("ADMIN");
            if (adminRole == null) {
                adminRole = Role.builder().name("ADMIN").permissions(new HashSet<>()).build();
                roleRepository.save(adminRole);
            }

            //ADMIN
            adminRole.getPermissions().add(get_orderPermission);
            roleRepository.save(adminRole);
            adminRole.getPermissions().add(get_paymentPermission);
            roleRepository.save(adminRole);
            adminRole.getPermissions().add(add_productPermission);
            roleRepository.save(adminRole);
            adminRole.getPermissions().add(get_productPermission);
            roleRepository.save(adminRole);
            adminRole.getPermissions().add(reduce_quantityPermission);
            roleRepository.save(adminRole);

            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
        }else if (signUpRequest.getRole().equals("ADMIN")) {
            throw new NotFoundException("Pass Admin faile!");
        } else if (signUpRequest.getRole().equals("") && signUpRequest.getAuthAdmin().equals("")) {
            Role userRole = roleRepository.findByName("USER");
            if (userRole == null) {
                userRole = Role.builder().name("USER").permissions(new HashSet<>()).build();
                roleRepository.save(userRole);
            }

            //USER
            userRole.getPermissions().add(createOrderPermission);
            roleRepository.save(userRole);
            userRole.getPermissions().add(get_orderPermission);
            roleRepository.save(userRole);
            userRole.getPermissions().add(do_paymentPermission);
            roleRepository.save(userRole);
            userRole.getPermissions().add(get_paymentPermission);
            roleRepository.save(userRole);
            userRole.getPermissions().add(get_productPermission);
            roleRepository.save(userRole);
            userRole.getPermissions().add(reduce_quantityPermission);
            roleRepository.save(userRole);

            user.setRoles(Set.of(userRole));
            userRepository.save(user);
        }else{
            throw new NotFoundException("Pass User faile!");
        }
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

                return jwtAuthenticationResponse;
            }
        }
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}

