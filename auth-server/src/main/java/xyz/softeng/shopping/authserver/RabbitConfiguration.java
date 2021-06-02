package xyz.softeng.shopping.authserver;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    @Bean
    public FanoutExchange usersExchange(@Value("${shopping.rabbit.exchange.users}") String usersExchange) {
        return ExchangeBuilder.fanoutExchange(usersExchange).suppressDeclaration().build();
    }

    @Bean
    public Queue usersQueue(AuthServerProperties properties) {
//        return QueueBuilder.durable(properties.getUsersQueue()).build();
        return QueueBuilder.nonDurable().autoDelete().build();
    }

    @Bean
    public Binding usersBinding(Queue usersQueue, FanoutExchange usersExchange) {
        return BindingBuilder.bind(usersQueue).to(usersExchange);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
}
