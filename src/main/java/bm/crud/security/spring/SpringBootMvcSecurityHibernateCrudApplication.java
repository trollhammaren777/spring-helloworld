package bm.crud.security.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"bm.crud.security.spring"})
public class SpringBootMvcSecurityHibernateCrudApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMvcSecurityHibernateCrudApplication.class, args);
    }
}
