package com.example.MockApi.aspect;



import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Логирование до выполнения метода
    @Before("execution(* com.example.MockApi.controller.UserController.*(..))")
    public void logBefore() {
        logger.info("Запрос поступил в UserController.");
    }

    // Логирование после успешного выполнения метода
    @AfterReturning("execution(* com.example.MockApi.controller.UserController.*(..))")
    public void logAfter() {
        logger.info("Запрос успешно обработан в UserController.");
    }

    // Логирование в случае ошибки
    @AfterThrowing(pointcut = "execution(* com.example.MockApi.controller.UserController.*(..))", throwing = "exception")
    public void logError(Exception exception) {
        logger.error("Произошла ошибка в UserController: ", exception);
    }
}

