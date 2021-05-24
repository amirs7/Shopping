package xyz.softeng.shopping.shop;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@Slf4j
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Consumer<User> updateUser(UserRepository userRepository) {
        return user -> {
            log.info("Processing User: {}", user);
            userRepository.save(user);
        };
    }

    @Bean
    public Consumer<Product> updateProduct(ProductRepository productRepository) {
        return product -> {
            log.info("Processing Product: {}", product);
            product = productRepository.save(product);
            log.info("Saved Product: {}", product);
        };
    }
}
