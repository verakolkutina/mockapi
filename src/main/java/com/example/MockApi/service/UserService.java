package com.example.MockApi.service;

import com.example.MockApi.model.UserData;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    private final Map<String, UserData> userStorage = new ConcurrentHashMap<>();
    private final Map<String, String> tokenStorage = new ConcurrentHashMap<>();


    public String register(String login, String password) {
        if (userStorage.containsKey(login)) {
            throw new IllegalArgumentException("Пользователь уже существует.");
        }
        String token = UUID.randomUUID().toString();
        userStorage.put(login, new UserData(token, password));
        tokenStorage.put(token, login);  // Храним токен отдельно
        return token;
    }

    public void printUserStorage() {
        System.out.println("User Storage: " + userStorage);
    }

    public void printTokenStorage() {
        System.out.println("Token Storage: " + tokenStorage);
    }

    public void printAllTokens() {
        System.out.println("Текущие токены: " + tokenStorage);
    }

    public boolean isValidToken(String token) {
        return tokenStorage.containsKey(token);  // Проверка O(1)
    }

    public String deleteUser(String login) {
        UserData removedUser = userStorage.remove(login);
        if (removedUser == null) {
            throw new IllegalArgumentException("Пользователь не найден.");
        }
        tokenStorage.remove(removedUser.getToken());  // Удаляем токен
        return "Пользователь удален.";
    }
}

