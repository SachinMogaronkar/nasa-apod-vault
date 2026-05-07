package com.openapi.nasa.service;

import com.openapi.nasa.daorepo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HealthService {

    private final UserRepository userRepository;

    public HealthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Map<String, Object> checkHealth() {

        Map<String, Object> response = new HashMap<>();

        try {

            long userCount = userRepository.count();

            response.put("status", "UP");
            response.put("database", "CONNECTED");
            response.put("users", userCount);

        } catch (Exception e) {

            response.put("status", "DOWN");
            response.put("database", "DISCONNECTED");
            response.put("error", e.getMessage());
        }

        return response;
    }
}