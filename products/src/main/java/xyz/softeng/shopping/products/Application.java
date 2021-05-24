package xyz.softeng.shopping.products;


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
    public Consumer<BuyEvent> processBuy(ProductRepository repository) {
        return buyEvent -> {
            Product product = repository.findById(buyEvent.getProductId()).orElseThrow();
            product.increaseBuyCount();
            repository.save(product);
        };
    }
}
