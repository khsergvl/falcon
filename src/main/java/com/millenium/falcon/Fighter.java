package com.millenium.falcon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
        (exclude = { org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration.class })
public class Fighter {
    public static void main(String[] args) {
        SpringApplication.run(Fighter.class, args);
    }
}