package xyz.softeng.shopping.users;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    @Value("${shopping.rabbit.exchange.users}")
    private String usersExchange;
    @Value("${shopping.rabbit.exchange.purchases}")
    private String purchasesExchange;

    @Bean
    public Queue purchasesQueue(UsersConfigurationProperties properties) {
        return QueueBuilder.durable(properties.getPurchasesQueue()).build();
    }

    @Bean
    public FanoutExchange purchasesExchange() {
        return ExchangeBuilder.fanoutExchange(purchasesExchange)
                .suppressDeclaration()
                .build();
    }

    @Bean
    public Binding purchasesBinding(Queue queue) {
        return BindingBuilder.bind(queue).to(purchasesExchange());
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate setup(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(usersExchange);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
