package com.example.MockApi.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DelayService {
    @Value(value = "${mock.delay:2000}")  // Читаем значение из application.properties
    private long delayMs;

    public void simulateDelay() {
        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public long getDelayMs() {
        return delayMs;
    }

    public void setDelayMs(long delayMs) {  // Позволяет менять задержку без перезапуска
        this.delayMs = delayMs;
    }
}
