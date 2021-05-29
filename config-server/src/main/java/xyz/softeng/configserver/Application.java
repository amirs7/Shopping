package xyz.softeng.configserver;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
@ConfigurationPropertiesScan
public class Application {
    @Autowired
    private ShoppingProperties configuration;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
