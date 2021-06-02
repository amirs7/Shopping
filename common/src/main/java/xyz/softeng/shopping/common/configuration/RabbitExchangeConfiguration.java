package xyz.softeng.shopping.common.configuration;

import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;

public class RabbitExchangeConfiguration {
    public static final String USERS_EXCHANGE = "users-exchange";

    @Bean
    public FanoutExchange usersExchange() {
        return ExchangeBuilder.fanoutExchange(USERS_EXCHANGE).build();
    }
}
