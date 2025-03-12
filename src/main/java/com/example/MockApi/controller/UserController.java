package com.example.MockApi.controller;

import com.example.MockApi.entity.User;
import com.example.MockApi.model.PostGetRequest;
import com.example.MockApi.model.TokenRequest;
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


        // Выводим содержимое хранилищ в консоль
        userService.printUserStorage();
        userService.printTokenStorage();

        return ResponseEntity.ok(response);

    }


    @PostMapping("/request")
    public ResponseEntity<Map<String, String>> postRequest(@RequestBody PostGetRequest postRequest) {
        delayService.simulateDelay();
        Map<String, String> response = new HashMap<>();

        // Логирование входящих данных
        System.out.println("Получен запрос:");
        System.out.println("Токен: " + postRequest.getToken());
        System.out.println("Фамилия: " + postRequest.getLastName());

        // Проверка валидности токена
        if (!userService.isValidToken(postRequest.getToken())) {
            response.put("error", "Токен не найден");

            // Логирование ошибки
            System.out.println("Токен не найден: " + postRequest.getToken());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // Добавляем фамилию в ответ
        response.put("lastName", postRequest.getLastName());

        // Логирование ответа
        System.out.println("Ответ: " + response);

        // Возвращаем успешный ответ
        return ResponseEntity.ok(response);
    }

    @GetMapping("/request")
    public ResponseEntity<Map<String, String>> getRequest(
            @RequestParam("lastName") String lastName,
            @RequestHeader("Authorization") String token) {
        delayService.simulateDelay();
        Map<String, String> response = new HashMap<>();

        System.out.println("Токен: " + token);

        if (!userService.isValidToken(token)) {
            response.put("error", "Токен не найден");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setToken(token);
        tokenRequest.setLastName(lastName);

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

            // Выводим содержимое хранилищ в консоль после удаления
            userService.printUserStorage();
            userService.printTokenStorage();

        } catch (IllegalArgumentException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);


        }
        return ResponseEntity.ok(response);
    }
}
