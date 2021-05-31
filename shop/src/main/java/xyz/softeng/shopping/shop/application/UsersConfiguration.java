package xyz.softeng.shopping.shop.application;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.softeng.shopping.shop.ShopConfigurationProperties;

@Configuration
public class UsersConfiguration {
    @Bean
    public FanoutExchange usersExchange(@Value("${shopping.rabbit.exchange.users}") String name) {
        return ExchangeBuilder.fanoutExchange(name).suppressDeclaration().build();
    }

    @Bean
    public Queue usersQueue(ShopConfigurationProperties properties) {
        return QueueBuilder.durable(properties.getUsersQueue()).build();
    }

    @Bean
    public Binding usersBinding(FanoutExchange usersExchange, Queue usersQueue) {
        return BindingBuilder.bind(usersQueue).to(usersExchange);
    }
}
