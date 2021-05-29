package xyz.softeng.shopping.errorhandler;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    private RabbitTemplate rabbitTemplate;

    public ProductController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setExchange("products");
    }

    @GetMapping
    public void addProduct(@RequestParam Long id, @RequestParam String name) {
        Product product = new Product(id, name);
        rabbitTemplate.convertAndSend(product);
    }
}
