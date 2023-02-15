package com.example.springbootmagicdemo.service;

import config.annotation.Magic;
import org.springframework.stereotype.Service;

@Service
public class HelloService {
    @Magic
    public void hello() {

        System.out.println("Hello from " + Thread.currentThread().getName());
    }
}
