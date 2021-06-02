package xyz.softeng.shopping.users;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserService {
    public static void main(String[] args) {
        SpringApplication.run(UserService.class, args);
    }
}
