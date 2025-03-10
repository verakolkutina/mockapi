package com.example.MockApi.controller;

import com.example.MockApi.model.PostGetRequest;
import com.example.MockApi.model.UserRequest;
import com.example.MockApi.service.DelayService;
import com.example.MockApi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final DelayService delayService;

    public UserController(UserService userService, DelayService delayService) {
        this.userService = userService;
        this.delayService = delayService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserRequest userRequest) {
        delayService.simulateDelay();
        Map<String, String> response = new HashMap<>();
        String token = userService.register(userRequest.getLogin(), userRequest.getPassword());
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/request")
    public ResponseEntity<Map<String, String>> postRequest(@RequestBody PostGetRequest postRequest) {
        delayService.simulateDelay();
        Map<String, String> response = new HashMap<>();

        if (!userService.isValidToken(postRequest.getToken())) {
            response.put("error", "Токен не найден");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        response.put("lastName", postRequest.getLastName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/request")
    public ResponseEntity<Map<String, String>> getRequest(
            @RequestParam("lastName") String lastName,
            @RequestHeader("Authorization") String token) {
        delayService.simulateDelay();
        Map<String, String> response = new HashMap<>();

        if (!userService.isValidToken(token)) {
            response.put("error", "Токен не найден");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        response.put("lastName", lastName);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteUser(@RequestBody UserRequest userRequest) {
        delayService.simulateDelay();
        Map<String, String> response = new HashMap<>();
        try {
            userService.deleteUser(userRequest.getLogin());
            response.put("status", "Пользователь удалён");
        } catch (IllegalArgumentException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(response);
    }
}


//  curl -X POST http://localhost:8888/api/register -H "Content-Type: application/json" -d '{"login": "ivanov", "password": "password123"}'

