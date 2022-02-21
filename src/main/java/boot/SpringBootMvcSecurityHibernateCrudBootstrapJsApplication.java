package boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication(scanBasePackages = {"boot"})
@PropertySources({
        @PropertySource("classpath:application.properties")
})
public class SpringBootMvcSecurityHibernateCrudBootstrapJsApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMvcSecurityHibernateCrudBootstrapJsApplication.class, args);
    }
}
