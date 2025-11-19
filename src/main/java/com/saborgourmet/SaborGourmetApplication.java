package com.saborgourmet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SaborGourmetApplication {
    public static void main(String[] args) {
        SpringApplication.run(SaborGourmetApplication.class, args);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println("Contraseña ADMIN: " + encoder.encode("admin123"));
        System.out.println("Contraseña MOZO: " + encoder.encode("mozoPass123"));
        System.out.println("Contraseña COCINERO: " + encoder.encode("cocineroPass123"));
        System.out.println("Contraseña CAJERO: " + encoder.encode("cajeroPass123"));
    }
}

