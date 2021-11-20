package com.github.ivaninkv.jtgbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class JavaTelegramBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaTelegramBotApplication.class, args);
    }

}
