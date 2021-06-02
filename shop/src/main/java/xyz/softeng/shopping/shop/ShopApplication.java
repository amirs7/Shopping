package xyz.softeng.shopping.shop;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import xyz.softeng.shopping.shop.domain.Product;
import xyz.softeng.shopping.shop.domain.ProductRepository;
import xyz.softeng.shopping.shop.domain.User;
import xyz.softeng.shopping.shop.domain.UserRepository;

import java.util.function.Consumer;

@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan
public class ShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

    @Bean
    public Consumer<User> users(UserRepository userRepository) {
        return user -> {
            log.info("Processing User: {}", user);
            userRepository.save(user);
        };
    }

    @Bean
    public Consumer<Product> products(ProductRepository productRepository) {
        return product -> {
            log.info("Processing Product: {}", product);
            product = productRepository.save(product);
            log.info("Saved Product: {}", product);
        };
    }
}
