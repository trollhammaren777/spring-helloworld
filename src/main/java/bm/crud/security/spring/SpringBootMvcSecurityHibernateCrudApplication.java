package bm.crud.security.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = {"bm.crud.security.spring"})
public class SpringBootMvcSecurityHibernateCrudApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMvcSecurityHibernateCrudApplication.class, args);
    }
}
