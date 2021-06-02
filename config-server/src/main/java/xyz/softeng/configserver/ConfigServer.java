package xyz.softeng.configserver;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.test.context.ActiveProfiles;

@EnableConfigServer
@SpringBootApplication
@ActiveProfiles("native")
@ConfigurationPropertiesScan
public class ConfigServer {
    @Autowired
    private ShoppingProperties configuration;

    public static void main(String[] args) {
        SpringApplication.run(ConfigServer.class, args);
    }
}
