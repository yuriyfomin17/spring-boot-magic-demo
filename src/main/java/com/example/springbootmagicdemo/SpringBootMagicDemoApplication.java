package com.example.springbootmagicdemo;

import com.example.springbootmagicdemo.service.HelloService;
import config.annotation.EnableMagic;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
@EnableMagic
public class SpringBootMagicDemoApplication {
    @SneakyThrows
    public static void main(String[] args) {
        var context = SpringApplication.run(SpringBootMagicDemoApplication.class, args);
        var helloService = context.getBean(HelloService.class);
        helloService.hello();
        Thread.sleep(1000);
    }

}
