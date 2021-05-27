package xyz.softeng.shopping.errorhandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Consumer<String> log() {
        return item -> {
            System.out.println("######################################");
            System.out.println(item);
            System.out.println("######################################");
        };
    }
}