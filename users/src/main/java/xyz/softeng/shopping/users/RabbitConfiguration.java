package xyz.softeng.shopping.users;

import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import xyz.softeng.shopping.common.configuration.RabbitBaseConfiguration;

@Configuration
@Import(RabbitBaseConfiguration.class)
public class RabbitConfiguration {
    @Bean
    FanoutExchange userExchange(UserServiceProperties properties) {
        return ExchangeBuilder.fanoutExchange(properties.getUserExchange()).build();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter, FanoutExchange usersExchange) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(usersExchange.getName());
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
