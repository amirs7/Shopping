package xyz.softeng.shopping.products;

import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import xyz.softeng.shopping.common.configuration.RabbitBaseConfiguration;

@Configuration
@Import(RabbitBaseConfiguration.class)
public class RabbitConfiguration {
    @Bean
    public FanoutExchange productExchange(ProductServiceProperties properties) {
        return ExchangeBuilder.fanoutExchange(properties.getProductExchange()).build();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, FanoutExchange productExchange) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(productExchange.getName());
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}
