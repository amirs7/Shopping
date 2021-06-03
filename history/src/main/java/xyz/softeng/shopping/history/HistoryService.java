package xyz.softeng.shopping.history;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class HistoryService {
    public static void main(String[] args) {
        SpringApplication.run(HistoryService.class, args);
    }
}
