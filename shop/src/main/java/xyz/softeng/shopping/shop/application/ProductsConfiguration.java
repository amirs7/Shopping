package xyz.softeng.shopping.shop.application;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.softeng.shopping.shop.ShopConfigurationProperties;

@Configuration
public class ProductsConfiguration {
    @Bean
    public FanoutExchange productsExchange(@Value("${shopping.rabbit.exchange.products}") String name) {
        return ExchangeBuilder.fanoutExchange(name).suppressDeclaration().build();
    }

    @Bean
    public Queue productsQueue(ShopConfigurationProperties properties) {
        return QueueBuilder.durable(properties.getProductsQueue()).build();
    }

    @Bean
    public Binding productsBinding(FanoutExchange productsExchange, Queue productsQueue) {
        return BindingBuilder.bind(productsQueue).to(productsExchange);
    }
}
