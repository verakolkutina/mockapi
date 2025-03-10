package com.example.MockApi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${mock.delay-ms:0}")
    private long responseDelay;

    @Value("${logging.level.root:INFO}")
    private String loggingLevel;

    public long getResponseDelay() {
        return responseDelay;
    }

    public String getLoggingLevel() {
        return loggingLevel;
    }
}
