package com.openapi.nasa.restcontroller;

import com.openapi.nasa.requestdto.AuthRequest;
import com.openapi.nasa.entity.AppUser;
import com.openapi.nasa.security.JwtUtil;
import com.openapi.nasa.service.CustomUserDetailsService;
import com.openapi.nasa.daorepo.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authManager,
                          JwtUtil jwtUtil,
                          CustomUserDetailsService userService,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {

        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            UserDetails user =
                    userService.loadUserByUsername(request.getUsername());

            String token = jwtUtil.generateToken(user.getUsername());

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "username", user.getUsername()
            ));

        } catch (Exception e) {
            return ResponseEntity.status(401)
                    .body("Invalid username or password");
        }
    }

    //REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body("User already exists");
        }

        AppUser user = new AppUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }
}